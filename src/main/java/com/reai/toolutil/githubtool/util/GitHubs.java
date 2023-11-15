package com.reai.toolutil.githubtool.util;

import com.reai.toolutil.Reais;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.TimeZone;

/**
 * GitHub 工具类。
 */
public final class GitHubs {

    /**
     * 日志记录器。
     */
    private static final Logger LOGGER = Logger.getLogger(GitHubs.class);

    /**
     * 私有构造器。
     */
    private GitHubs() {
    }

    /**
     * 获取 GitHub 仓库。
     *
     * @param githubUserId 指定的 GitHub 用户 ID
     * @return GitHub 仓库，如果未找到则返回 {@code null}
     */
    public static JSONArray getGitHubRepos(final String githubUserId) {
        try {
            // 发起HTTP请求，获取 GitHub 用户的仓库信息
            final HttpResponse res = HttpRequest.get(
                    "https://api.github.com/users/" + githubUserId + "/repos")
                .connectionTimeout(20000)
                .timeout(60000)
                .header("User-Agent", Reais.USER_AGENT)
                .send();
            if (HttpServletResponse.SC_OK != res.statusCode()) {
                return null;
            }
            res.charset("UTF-8");
            final JSONArray result = new JSONArray(res.bodyText());

            String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            // 重组兼容 JSONArray
            JSONArray compatibleResult = new JSONArray();
            for (int i = 0; i < result.length(); i++) {
                JSONObject resultObject = result.optJSONObject(i);
                JSONObject compatibleObject = new JSONObject();

                if (resultObject.getBoolean("fork")) {
                    continue;
                }

                compatibleObject.put("githubrepoId", resultObject.optString("id"));
                compatibleObject.put("githubrepoStatus", 0);
                compatibleObject.put("oId", "" + System.currentTimeMillis());
                compatibleObject.put("githubrepoDescription",
                    resultObject.optString("description"));
                compatibleObject.put("githubrepoHomepage", resultObject.optString("homepage"));
                compatibleObject.put("githubrepoForksCount", resultObject.optLong("forks_count"));
                compatibleObject.put("githubrepoOwnerId",
                    resultObject.optJSONObject("owner").optString("id"));
                compatibleObject.put("githubrepoStargazersCount",
                    resultObject.optLong("stargazers_count"));
                compatibleObject.put("githubrepoWatchersCount",
                    resultObject.optLong("watchers_count"));
                compatibleObject.put("githubrepoOwnerLogin",
                    resultObject.optJSONObject("owner").optString("login"));
                compatibleObject.put("githubrepoHTMLURL", resultObject.optString("html_url"));
                compatibleObject.put("githubrepoLanguage", resultObject.optString("language"));
                compatibleObject.put("githubrepoUpdated",
                    simpleDateFormat.parse(resultObject.optString("updated_at")).getTime());
                compatibleObject.put("githubrepoName", resultObject.optString("name"));
                compatibleObject.put("githubrepoFullName", resultObject.optString("full_name"));

                compatibleResult.put(compatibleObject);
            }

            // 排序
            ArrayList<String> tempResultList = new ArrayList<>();
            for (int i = 0; i < compatibleResult.length(); i++) {
                JSONObject compatibleObject = compatibleResult.optJSONObject(i);
                tempResultList.add(compatibleObject.toString());
            }
            tempResultList.sort((o1, o2) -> {
                int o1star = new JSONObject(o1).optInt("githubrepoStargazersCount");
                int o2star = new JSONObject(o2).optInt("githubrepoStargazersCount");
                return o2star - o1star;
            });
            JSONArray sortedCompatibleResult = new JSONArray();
            for (String json : tempResultList) {
                sortedCompatibleResult.put(new JSONObject(json));
            }

            return sortedCompatibleResult;
        } catch (JSONException e) {
            LOGGER.log(Level.ERROR, "获取 GitHub 仓库失败，因为请求已达到 GitHub 的限制，请稍后重试。");

            return null;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "获取 GitHub 仓库失败，请检查您与 github.com 的网络连接。");

            return null;
        }
    }

    /**
     * 通过指定的个人访问令牌、GitHub登录名、仓库名称、文件路径和文件内容更新文件。
     *
     * @param pat       指定的个人访问令牌
     * @param loginName 指定的GitHub登录名
     * @param repoName  指定的仓库名称
     * @param filePath  指定的文件路径
     * @param content   指定的文件内容
     * @return 如果成功返回true，如果失败返回false
     */
    public static boolean updateFile(final String pat, final String loginName,
        final String repoName, final String filePath, final byte[] content) {
        final String fullRepoName = loginName + "/" + repoName;
        try {
            // 获取 GitHub 仓库树信息
            HttpResponse response = HttpRequest.get(
                    "https://api.github.com/repos/" + fullRepoName + "/git/trees/main")
                .header("Authorization", "token " + pat)
                .connectionTimeout(7000)
                .timeout(60000)
                .header("User-Agent", Reais.USER_AGENT)
                .send();
            int statusCode = response.statusCode();
            response.charset("UTF-8");
            String responseBody = response.bodyText();
            if (200 != statusCode && 409 != statusCode) {
                LOGGER.log(Level.ERROR, "获取文件树失败 [" + filePath + "] 失败: " + responseBody);
                return false;
            }

            final JSONObject body = new JSONObject()
                .put("message", ":memo: 自动备份")
                .put("content", Base64.getEncoder().encodeToString(content));
            if (200 == statusCode) {
                final JSONObject responseData = new JSONObject(responseBody);
                final JSONArray tree = responseData.optJSONArray("tree");
                for (int i = 0; i < tree.length(); i++) {
                    final JSONObject file = tree.optJSONObject(i);
                    if (StringUtils.equals(filePath, file.optString("path"))) {
                        body.put("sha", file.optString("sha"));
                        break;
                    }
                }
            }

            response = HttpRequest.put(
                    "https://api.github.com/repos/" + fullRepoName + "/contents/" + filePath)
                .header("Authorization", "token " + pat)
                .connectionTimeout(7000)
                .timeout(60000 * 2)
                .header("User-Agent", Reais.USER_AGENT)
                .bodyText(body.toString())
                .send();
            statusCode = response.statusCode();
            response.charset("UTF-8");
            responseBody = response.bodyText();
            if (200 != statusCode && 201 != statusCode) {
                LOGGER.log(Level.ERROR,
                    "更新仓库 [" + repoName + "] 文件 [" + filePath + "] 失败: " + responseBody);
                return false;
            }
            return true;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR,
                "更新仓库 [" + repoName + "] 文件 [" + filePath + "] 失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 使用指定的个人访问令牌、GitHub登录名、仓库名称、仓库描述和仓库主页创建或更新GitHub仓库。
     *
     * @param pat          指定的个人访问令牌
     * @param loginName    指定的GitHub登录名
     * @param repoName     指定的仓库名称
     * @param repoDesc     指定的仓库描述
     * @param repoHomepage 指定的仓库主页
     * @return {@code true} 如果成功，返回{@code false} 如果失败
     */
    public static boolean createOrUpdateGitHubRepo(final String pat, final String loginName,
        final String repoName, final String repoDesc, final String repoHomepage) {
        try {
            final JSONObject body = new JSONObject()
                .put("name", repoName)
                .put("description", repoDesc)
                .put("homepage", repoHomepage)
                .put("has_wiki", false)
                .put("has_projects", false);
            HttpResponse response = HttpRequest.post("https://api.github.com/user/repos")
                .header("Authorization", "token " + pat)
                .connectionTimeout(7000)
                .timeout(30000)
                .header("User-Agent", Reais.USER_AGENT)
                .bodyText(body.toString())
                .send();
            int statusCode = response.statusCode();
            response.charset("UTF-8");
            String responseBody = response.bodyText();
            if (201 != statusCode && 422 != statusCode) {
                LOGGER.log(Level.ERROR, "创建GitHub仓库[" + repoName + "]失败: " + responseBody);
                return false;
            }
            if (201 == statusCode) {
                return true;
            }

            response = HttpRequest.patch(
                    "https://api.github.com/repos/" + loginName + "/" + repoName)
                .header("Authorization", "token " + pat)
                .connectionTimeout(7000)
                .timeout(30000)
                .header("User-Agent", Reais.USER_AGENT)
                .bodyText(body.toString())
                .send();
            statusCode = response.statusCode();
            responseBody = response.bodyText();
            if (200 != statusCode) {
                LOGGER.log(Level.ERROR, "更新GitHub仓库[" + repoName + "]失败: " + responseBody);
                return false;
            }
            return true;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "创建或更新GitHub仓库失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 使用指定的个人访问令牌获取GitHub用户。
     *
     * @param pat 指定的个人访问令牌
     * @return GitHub用户，如果失败则返回{@code null}
     */
    public static JSONObject getGitHubUser(final String pat) {
        try {
            final HttpResponse response = HttpRequest.get("https://api.github.com/user")
                .header("Authorization", "token " + pat)
                .connectionTimeout(7000)
                .timeout(30000)
                .header("User-Agent", Reais.USER_AGENT)
                .send();
            if (200 != response.statusCode()) {
                return null;
            }
            response.charset("UTF-8");
            return new JSONObject(response.bodyText());
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "获取GitHub用户信息失败: " + e.getMessage());
            return null;
        }
    }

    public static JSONArray getGitHubRepoInfo(final String githubRepoId,
        final String personalAccessToken, final String filePath) {
        try {
            final String apiUrl =
                "https://api.github.com/repos/" + githubRepoId + "/contents/" + filePath;
            final HttpResponse response = HttpRequest.get(apiUrl)
                .connectionTimeout(20000)
                .timeout(60000)
                .header("Authorization", "token " + personalAccessToken)
                .send();

            if (response.statusCode() == HttpServletResponse.SC_OK) {
                response.charset("UTF-8");
                final JSONArray result = new JSONArray(response.bodyText());
                // 重组兼容 JSONArray
                JSONArray compatibleResult = new JSONArray();
                for (int i = 0; i < result.length(); i++) {
                    JSONObject resultObject = result.optJSONObject(i);
                    compatibleResult.put(resultObject);
                }
                return compatibleResult;
            } else {
                LOGGER.log(Level.ERROR, "无法检索GitHub仓库信息。HTTP状态代码: " + response.statusCode());
                return null;
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "无法检索GitHub仓库信息。请检查您与github.com的网络连接。");
            return null;
        }
    }

    public static JSONArray getGitHubFileContent(String githubRepoId, String personalAccessToken,
        String filePath) throws InterruptedException, ExecutionException {
        JSONArray fileInfo = getGitHubRepoInfo(githubRepoId, personalAccessToken, filePath);
        ExecutorService executor = Executors.newFixedThreadPool(fileInfo.length());
        List<Future<JSONObject>> futures = new ArrayList<>();
        int executionCount = 0;
        for (int i = 0; i < fileInfo.length(); i++) {
            JSONObject fileObject = fileInfo.getJSONObject(i);
            String fileHtmlUrl = fileObject.getString("git_url");
            String name = fileObject.getString("name");
            Future<JSONObject> future = executor.submit(() -> {
//                LOGGER.log(Level.INFO, "线程开始执行，文件URL: " + fileHtmlUrl);
                long startTime = System.currentTimeMillis();
                final HttpResponse fileResponse = HttpRequest.get(fileHtmlUrl)
                    .connectionTimeout(20000)
                    .timeout(60000)
                    .header("Authorization", "token " + personalAccessToken)
                    .send();
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                LOGGER.log(Level.INFO, "线程执行完毕，文件URL: " + name + "，执行时间: " + executionTime + "毫秒");
                if (fileResponse.statusCode() == HttpServletResponse.SC_OK) {
                    fileResponse.charset("UTF-8");
                    String s = fileResponse.bodyText();
                    JSONArray compatibleResult = new JSONArray("[" + s + "]");
                    String content = compatibleResult.getJSONObject(0).getString("content");
                    byte[] decodedBytes = Base64.getMimeDecoder().decode(content);
                    String decodedString = new String(decodedBytes);
                    JSONObject compatibleObject = new JSONObject();
                    compatibleObject.put("articleTitle", name);
                    compatibleObject.put("articleContent", decodedString);
                    compatibleObject.put("articlePermalink", "https://blog.reaicc.com/");
                    return compatibleObject;
                } else {
                    LOGGER.log(Level.ERROR,
                        "无法检索GitHub文件内容。HTTP状态代码: " + fileResponse.statusCode());
                    return null;
                }
            });
            futures.add(future);
            executionCount++;

        }
        JSONArray postList = new JSONArray();
        for (Future<JSONObject> future : futures) {
            JSONObject compatibleObject = future.get();
            if (compatibleObject != null) {
                postList.put(compatibleObject);
            }
        }
        executor.shutdown();
        LOGGER.log(Level.INFO, "共执行了 " + executionCount + " 次 ");
        return postList;
    }

}

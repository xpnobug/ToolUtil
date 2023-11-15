package com.reai.toolutil.githubtool.service.impl;

import com.reai.toolutil.githubtool.model.Article;
import com.reai.toolutil.githubtool.util.GitHubs;
import com.reai.toolutil.Reais;
import com.reai.toolutil.sendEmail.util.ToEmail;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jodd.io.ZipUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.Repository;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.jdbc.util.Connections;
import org.b3log.latke.util.Execs;
import org.b3log.latke.util.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.yaml.snakeyaml.Yaml;

/**
 * 导出服务。
 *
 * @author 86136
 * @since 2.5.0
 */
@Service
public class ExportService {
    @Autowired
    ToEmail toEmail;
    /**
     * 日志记录器。
     */
    private static final Logger LOGGER = Logger.getLogger(ExportService.class);
    public static ThreadLocal<JSONObject> articleThreadLocal = new ThreadLocal<>();

    /**
     * 导出SQL为zip字节。
     *
     * @return 数据字节，如果发生异常则返回{@code null}。
     */
    public byte[] exportSQL() {
        final Latkes.RuntimeDatabase runtimeDatabase = Latkes.getRuntimeDatabase();
        if (Latkes.RuntimeDatabase.H2 != runtimeDatabase
            && Latkes.RuntimeDatabase.MYSQL != runtimeDatabase) {
            LOGGER.log(Level.ERROR, "目前只支持MySQL/H2导出");
            return null;
        }

        final String dbUser = Latkes.getLocalProperty("jdbc.username");
        final String dbPwd = Latkes.getLocalProperty("jdbc.password");
        final String dbURL = Latkes.getLocalProperty("jdbc.URL");
        String sql = ""; // 导出的SQL脚本
        if (Latkes.RuntimeDatabase.MYSQL == runtimeDatabase) {
            String db = StringUtils.substringAfterLast(dbURL, "/");
            db = StringUtils.substringBefore(db, "?");
            try {
                if (StringUtils.isNotBlank(dbPwd)) {
                    sql = Execs.exec("mysqldump -u" + dbUser + " -p" + dbPwd + " --databases " + db,
                        60 * 1000 * 5);
                } else {
                    sql = Execs.exec("mysqldump -u" + dbUser + " --databases " + db, 60 * 1000 * 5);
                }
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "导出失败，请检查系统上是否有\"mysqldump\"命令", e);
                return null;
            }
        } else if (Latkes.RuntimeDatabase.H2 == runtimeDatabase) {
            try (final Connection connection = Connections.getConnection();
                final Statement statement = connection.createStatement()) {
                final StringBuilder sqlBuilder = new StringBuilder();
                final ResultSet resultSet = statement.executeQuery("SCRIPT");
                while (resultSet.next()) {
                    final String stmt = resultSet.getString(1);
                    sqlBuilder.append(stmt).append(Strings.LINE_SEPARATOR);
                }
                resultSet.close();
                sql = sqlBuilder.toString();
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "导出失败", e);
                return null;
            }
        }
        if (StringUtils.isBlank(sql)) {
            LOGGER.log(Level.ERROR, "导出失败，执行导出脚本返回空");
            return null;
        }
        final String tmpDir = System.getProperty("java.io.tmpdir");
        final String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String localFilePath = tmpDir + File.separator + "solo-" + date + ".sql";
        final File localFile = new File(localFilePath);
        try {
            final byte[] data = sql.getBytes("UTF-8");
            try (final OutputStream output = new FileOutputStream(localFile)) {
                IOUtils.write(data, output);
            }
            final File zipFile = ZipUtil.zip(localFile);
            byte[] ret;
            try (final FileInputStream inputStream = new FileInputStream(zipFile)) {
                ret = IOUtils.toByteArray(inputStream);
            }
            FileUtils.deleteQuietly(localFile);
            FileUtils.deleteQuietly(zipFile);
            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "导出失败", e);
            return null;
        }
    }

    /**
     * 导出公共文章到GitHub仓库 。 同步 GitHub 仓库功能
     */
    public void exportGitHub(String githubPAT) {
        try {

            if (StringUtils.isBlank(githubPAT)) {
                return;
            }

            JSONArray gitHubRepoInfo = GitHubs.getGitHubFileContent(Reais.repoName, Reais.token,
                Reais.filePath);

            final JSONObject mds = exportHexoMDs(gitHubRepoInfo);
            final List<JSONObject> posts = (List<JSONObject>) mds.opt("posts");

            final String tmpDir = System.getProperty("java.io.tmpdir");
            final String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
//            String localFilePath = "E:\\github" + File.separator + "auto-blog-repo-" + date;
            String localFilePath = tmpDir + File.separator + "auto-blog-repo-" + date;
            final File localFile = new File(localFilePath);

            final File postDir = new File(localFilePath + File.separator + "posts");
            exportHexoMd(posts, postDir.getPath());

            final File zipFile = ZipUtil.zip(localFile);
            byte[] zipData;
            try (final FileInputStream inputStream = new FileInputStream(zipFile)) {
                zipData = IOUtils.toByteArray(inputStream);
            }
//
            FileUtils.deleteQuietly(localFile);
            FileUtils.deleteQuietly(zipFile);

//            final String clientTitle = preference.optString(Option.ID_C_BLOG_TITLE);
//            final String clientSubtitle = preference.optString(Option.ID_C_BLOG_SUBTITLE);

            final JSONObject gitHubUser = GitHubs.getGitHubUser(githubPAT);
            if (null == gitHubUser) {
                return;
            }

            final String loginName = gitHubUser.optString("login");
            final String repoName = "Reai-BlogBF";

            //上传
            boolean ok = GitHubs.createOrUpdateGitHubRepo(githubPAT, loginName, repoName,
                "✍️ " + "https://blog.reaicc.com/", "");
            if (!ok) {
                return;
            }

            final String readme = genReadme("xpnobug", "xpnobug",
                "https://blog.reaicc.com/img/Cat.svg", loginName + "/" + repoName, gitHubRepoInfo);

            LOGGER.info("begin get README.md");
            String tmpFilePath = "/tmp/readme.md";
            File file = FileUtils.getFile(tmpFilePath);
            String oldReadme = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

//            if (oldReadme != null && oldReadme.equals(readme)) {
//                LOGGER.info("不需要更新自述文件。");
//                return ;
//            }
            ok = GitHubs.updateFile(githubPAT, loginName, repoName, "README.md",
                readme.getBytes(StandardCharsets.UTF_8));
            if (ok) {
                LOGGER.log(Level.INFO, "更新自述文件。");
                ok = GitHubs.updateFile(githubPAT, loginName, repoName, "backup.zip", zipData);
                LOGGER.log(Level.INFO, "更新backup.zip。");
            }
            FileUtils.write(file, readme, StandardCharsets.UTF_8);
            if (ok) {
                LOGGER.log(Level.INFO, "导出公共文章到您的repo [blog]");
                //上传成功后发送邮件
                toEmail.sendEmail();
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "将公共项目导出到存储库失败: " + e.getMessage());
        }
    }

    private String genReadme(final String blogTitle, final String blogSubTitle,
        final String favicon, final String repoFullName, final JSONArray recentArticles)
        throws RepositoryException {
//        final Set<String> articleIds = new HashSet<>();

        final StringBuilder bodyBuilder = new StringBuilder("### 最新\n");
        for (int i = 0; i < recentArticles.length(); i++) {
            JSONObject article = recentArticles.getJSONObject(i);
            final String title = article.optString(Article.ARTICLE_TITLE);
            final String link = article.optString(Article.ARTICLE_PERMALINK);
            bodyBuilder.append("\n* [").append(title).append("](").append(link).append(")");
//            articleIds.add(article.optString(Keys.OBJECT_ID));
        }

        bodyBuilder.append("\n\n");
        String ret =
            "<p align=\"center\"><img alt=\"${title}\" src=\"${favicon}\"></p><h2 align=\"center\">\n"
                +
                "${title}\n" +
                "</h2>\n" +
                "\n" +
                "<h4 align=\"center\">${subtitle}</h4>\n" +
                "<p align=\"center\">" +
                "<a title=\"${title}\" target=\"_blank\" href=\"https://github.com/${repoFullName}\"><img src=\"https://img.shields.io/github/last-commit/${repoFullName}.svg?style=flat-square&color=FF9900\"></a>\n"
                +
                "<a title=\"GitHub repo size in bytes\" target=\"_blank\" href=\"https://github.com/${repoFullName}\"><img src=\"https://img.shields.io/github/repo-size/${repoFullName}.svg?style=flat-square\"></a>\n"
                +
                "<a title=\"Author\" target=\"_blank\" href=\"https://github.com/xpnobug\"><img src=\"https://flat.badgen.net/badge/author/xpnobug\"></a>\n"
                +
                "<a title=\"Hits\" target=\"_blank\" href=\"https://github.com/xpnobug/ceshi\"><img src=\"https://hits.b3log.org/${repoFullName}.svg\"></a>"
                +
                "</p>\n" +
                "\n" +
                "${body}\n\n" +
                "---\n" +
                "\n" +
                "本仓库通过 [REAI](https://blog.reaicc.com) 自动进行同步更新 ❤  ";
        ret = ret.replace("${title}", blogTitle).
            replace("${subtitle}", blogSubTitle).
            replace("${favicon}", favicon).
            replace("${repoFullName}", repoFullName).
            replace("${soloVer}", Reais.VERSION).
            replace("${body}", bodyBuilder.toString());
        return ret;
    }

    /**
     * 导出指定文章到指定目录路径。
     *
     * @param articles 文章列表
     * @param dirPath  目录路径
     */
    public void exportHexoMd(final List<JSONObject> articles, final String dirPath) {
        articles.forEach(article -> {
            final String filename = Reais.sanitizeFilename(article.optString("title"));
            final String text =
                article.optString("front") + "---" + Strings.LINE_SEPARATOR + article.optString(
                    "content");
            try {
                final String date = DateFormatUtils.format(article.optLong("created"), "yyyyMM");
                final String dir = dirPath + File.separator + date + File.separator;
                new File(dir).mkdirs();
                FileUtils.writeStringToFile(new File(dir + filename), text, "UTF-8");
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "写入markdown文件失败", e);
            }
        });
    }

    /**
     * 导出为Hexo markdown格式。
     *
     * @return 文章、密码保护的文章和草稿， <pre>
     * {
     *     "posts": [
     *         {
     *             "front": "", // yaml front matter,
     *             "title": "",
     *             "content": "",
     *             "created": long
     *         }, ....
     *     ],
     *     "passwords": [], // 格式与文章相同
     *     "drafts": [] // 格式与文章相同
     * }
     * </pre>
     */
    public JSONObject exportHexoMDs(JSONArray articles) {
        final JSONObject ret = new JSONObject();
        final List<JSONObject> posts = new ArrayList<>();
        ret.put("posts", (Object) posts);
        final List<JSONObject> passwords = new ArrayList<>();
        ret.put("passwords", (Object) passwords);
        final List<JSONObject> drafts = new ArrayList<>();
        ret.put("drafts", (Object) drafts);

        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.optJSONObject(i);

            final Map<String, Object> front = new LinkedHashMap<>();
            final String title = article.optString(Article.ARTICLE_TITLE);
            final String content = article.optString(Article.ARTICLE_CONTENT);
            front.put("title", title);
//            final String date = DateFormatUtils.format(article.optLong(Article.ARTICLE_CREATED), "yyyy-MM-dd HH:mm:ss");
//            front.put("date", date);
//            front.put("updated", DateFormatUtils.format(article.optLong(Article.ARTICLE_UPDATED), "yyyy-MM-dd HH:mm:ss"));
//            final List<String> tags = Arrays.stream(article.optString(Article.ARTICLE_TAGS_REF).split(",")).filter(StringUtils::isNotBlank).map(String::trim).collect(Collectors.toList());
//            if (tags.isEmpty()) {
//                tags.add("Solo");
//            }
            front.put("tags", "tags");
            front.put("permalink", article.optString(Article.ARTICLE_PERMALINK));
            final JSONObject one = new JSONObject();
//            one.put("front", new Yaml().dump(front));
            one.put("title", title);
            one.put("content", content);
            one.put("created", new Date());
            drafts.add(one);
            posts.add(one);
//            if (StringUtils.isNotBlank(article.optString(Article.ARTICLE_VIEW_PWD))) {
//                passwords.add(one);
//            } else if (Article.ARTICLE_STATUS_C_PUBLISHED == article.optInt(Article.ARTICLE_STATUS)) {
//                posts.add(one);
//            } else {
//                drafts.add(one);
//            }
        }
        return ret;
    }

    /**
     * 以JSON格式获取所有数据。
     */
    public JSONObject getJSONs() {
        final JSONObject ret = new JSONObject();
        return ret;
    }

    private JSONArray getJSONs(final Repository repository) {
        try {
            return repository.get(new Query()).optJSONArray(Keys.RESULTS);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "从仓库[" + repository.getName() + "]获取数据失败", e);
            return new JSONArray();
        }
    }
}
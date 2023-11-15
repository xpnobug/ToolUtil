package com.reai.toolutil.githubtool.service.impl;

import com.reai.toolutil.githubtool.model.Article;
import com.reai.toolutil.githubtool.service.ArticleMgmtService;
import com.reai.toolutil.githubtool.util.GitHubs;
import com.reai.toolutil.sendEmail.util.ToEmail;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 物品管理服务。
 *
 * @author 86136
 * @since 0.3.5
 */
@Service
public class ArticleMgmtServiceImpl implements ArticleMgmtService {
    @Autowired
    ToEmail toEmail;
    /**
     * 日志记录器。
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleMgmtServiceImpl.class);


    /**
     * 同步拉取 GitHub 仓库 刷新 GitHub 仓库。
     */
    @Override
    public void refreshGitHub(String githubId) {
        final JSONArray gitHubRepos = GitHubs.getGitHubRepos(githubId);
        if (null == gitHubRepos || gitHubRepos.length() == 0) {
            return;
        }
        final StringBuilder contentBuilder = new StringBuilder();
        String stats = "\n![Github统计](https://github-readme-stats.vercel.app/api?username={username}&show_icons=true) \n\n";
        stats = stats.replace("{username}", githubId);
        contentBuilder.append("![GitHub仓库](/images/github_repo.jpg)\n\n");
        contentBuilder.append("## Github统计\n").append(stats);
        contentBuilder.append("## 所有开源项目\n");
        contentBuilder.append("| 仓库 |  项目简介 | Stars | fork | 编程语言 |\n");
        contentBuilder.append("| ---- | ---- | ---- | ---- | ---- |\n");
        for (int i = 0; i < gitHubRepos.length(); i++) {
            final JSONObject repo = gitHubRepos.optJSONObject(i);
            final String url = repo.optString("githubrepoHTMLURL");
            final String desc = repo.optString("githubrepoDescription");
            final String name = repo.optString("githubrepoName");
            final String stars = repo.optString("githubrepoStargazersCount");
            final String forks = repo.optString("githubrepoForksCount");
            final String lang = repo.optString("githubrepoLanguage");
            final String hp = repo.optString("githubrepoHomepage");
            contentBuilder.append("| [").append(name).append("](").append(url).append(") | ")
                .append(desc).append(" | ")
                .append(stars).append(" | ")
                .append(forks).append(" | ")
                .append(lang).append("|\n");
        }
        final String content = contentBuilder.toString();

        try {
            final String permalink = "/github";
            JSONObject article = null;

            article = new JSONObject();
            article.put(Article.ARTICLE_TITLE, "我在 GitHub 上的开源项目");
//            article.put(Article.ARTICLE_ABSTRACT, Article.getAbstractText(content));
            article.put(Article.ARTICLE_TAGS_REF, "开源,GitHub");
            article.put(Article.ARTICLE_PERMALINK, permalink);
            article.put(Article.ARTICLE_CONTENT, content);
            article.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);
            article.put("postToCommunity", false);
            article.put(Article.ARTICLE_COMMENTABLE, true);

            final JSONObject addArticleReq = new JSONObject();
            addArticleReq.put(Article.ARTICLE, article);
//            JSONObject page = null;
//            page = new JSONObject();
//            final int maxOrder = pageRepository.getMaxOrder();
//            page.put(Page.PAGE_ORDER, gitHubRepos.length() + 1);
//            page.put(Page.PAGE_TITLE, "我的开源");
//            page.put(Page.PAGE_OPEN_TARGET, "_self");
//            page.put(Page.PAGE_PERMALINK, permalink);

            LOGGER.log(Level.INFO, "我的 GitHub 仓库页面已生成。");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "更新 GitHub 仓库页面失败", e);
        }
    }

}

package com.reai.toolutil.githubtool.model;

import com.reai.toolutil.githubtool.util.Markdowns;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * 这个类定义了所有与文章模型相关的键。
 *
 * @author 86136
 * @since 0.3.1
 */
public final class Article {

    /**
     * 文章。
     */
    public static final String ARTICLE = "article";
    /**
     * 文章列表。
     */
    public static final String ARTICLES = "articles";
    /**
     * 标题的键。
     */
    public static final String ARTICLE_TITLE = "articleTitle";
    /**
     * 摘要的键。
     */
    public static final String ARTICLE_ABSTRACT = "articleAbstract";
    /**
     * 摘要文本的键。
     */
    public static final String ARTICLE_ABSTRACT_TEXT = "articleAbstractText";
    /**
     * 内容的键。
     */
    public static final String ARTICLE_CONTENT = "articleContent";
    /**
     * 创建时间的键。
     */
    public static final String ARTICLE_CREATED = "articleCreated";
    /**
     * 创建日期的键。
     */
    public static final String ARTICLE_T_CREATE_DATE = "articleCreateDate";
    /**
     * 创建时间的键。
     */
    public static final String ARTICLE_CREATE_TIME = "articleCreateTime";
    /**
     * 更新时间的键。
     */
    public static final String ARTICLE_UPDATED = "articleUpdated";
    /**
     * 更新日期的键。
     */
    public static final String ARTICLE_T_UPDATE_DATE = "articleUpdateDate";
    /**
     * 更新时间的键。
     */
    public static final String ARTICLE_UPDATE_TIME = "articleUpdateTime";
    /**
     * 标签的键。
     */
    public static final String ARTICLE_TAGS_REF = "articleTags";
    /**
     * 分类的键。
     */
    public static final String CATEGORY_REF = "category";
    /**
     * 评论数量的键。
     */
    public static final String ARTICLE_COMMENT_COUNT = "articleCommentCount";
    /**
     * 浏览数量的键。
     */
    public static final String ARTICLE_VIEW_COUNT = "articleViewCount";
    /**
     * 评论的键。
     */
    public static final String ARTICLE_COMMENTS_REF = "articleComments";
    /**
     * 签名ID的键。
     */
    public static final String ARTICLE_SIGN_ID = "articleSignId";
    /**
     * 永久链接的键。
     */
    public static final String ARTICLE_PERMALINK = "articlePermalink";
    /**
     * 置顶的键。
     */
    public static final String ARTICLE_PUT_TOP = "articlePutTop";
    /**
     * 作者ID的键。
     */
    public static final String ARTICLE_AUTHOR_ID = "articleAuthorId";
    /**
     * 随机数的键。
     */
    public static final String ARTICLE_RANDOM_DOUBLE = "articleRandomDouble";
    /**
     * 是否可评论的键。
     */
    public static final String ARTICLE_COMMENTABLE = "articleCommentable";
    /**
     * 查看密码的键。
     */
    public static final String ARTICLE_VIEW_PWD = "articleViewPwd";
    /**
     * 文章图片1的URL的键。 https://github.com/b3log/solo/issues/12670
     */
    public static final String ARTICLE_IMG1_URL = "articleImg1URL";
    /**
     * 文章状态的键。
     */
    public static final String ARTICLE_STATUS = "articleStatus";
    //// 状态常量
    /**
     * 文章状态 - 已发布。
     */
    public static final int ARTICLE_STATUS_C_PUBLISHED = 0;
    /**
     * 文章状态 - 草稿。
     */
    public static final int ARTICLE_STATUS_C_DRAFT = 1;
    //// Transient ////
    /**
     * 文章目录的键。
     */
    public static final String ARTICLE_T_TOC = "articleToC";
    //// 其他常量
    /**
     * 文章第一张图片的宽度。
     */
    public static final int ARTICLE_THUMB_IMG_WIDTH = 1280;
    /**
     * 文章第一张图片的高度。
     */
    public static final int ARTICLE_THUMB_IMG_HEIGHT = 720;
    /**
     * 文章摘要长度。
     */
    private static final int ARTICLE_ABSTRACT_LENGTH = 500;
    /**
     * 私有构造函数。
     */

    /**
     * Gets the abstract plain text of the specified content.
     *
     * @param content the specified content
     * @return the abstract plain text
     */
    public static String getAbstractText(final String content) {
        final String ret = Jsoup.clean(Markdowns.toHTML(content), Whitelist.none());
        if (ret.length() > ARTICLE_ABSTRACT_LENGTH) {
            return ret.substring(0, ARTICLE_ABSTRACT_LENGTH) + "....";
        }

        return ret;
    }

}
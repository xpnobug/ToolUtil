package com.reai.toolutil.githubtool.model;

/**
 * 该类定义了与选项模型相关的键值常量。
 */
public final class Option {

    /**
     * 选项。
     */
    public static final String OPTION = "option";
    /**
     * 选项列表。
     */
    public static final String OPTIONS = "options";
    /**
     * 选项值的键。
     */
    public static final String OPTION_VALUE = "optionValue";
    /**
     * 选项分类的键。
     */
    public static final String OPTION_CATEGORY = "optionCategory";
    // oId常量
    /**
     * 代码块行号显示标志的键。支持代码块行号显示 https://github.com/88250/solo/issues/4
     */
    public static final String ID_C_SHOW_CODE_BLOCK_LN = "showCodeBlockLn";
    /**
     * 编辑器模式的键。支持配置编辑器模式 https://github.com/88250/solo/issues/95
     * <ul>
     *     <li>wysiwyg：所见即所得</li>
     *     <li>ir：即时渲染</li>
     *     <li>sv：分屏预览</li>
     * </ul>
     * 模式细节介绍详见 <a href="https://github.com/Vanessa219/vditor">Vditor</a> 编辑器文档。
     */
    public static final String ID_C_EDITOR_MODE = "editorMode";
    /**
     * hljs主题的键。在设置中可选择语法高亮主题 https://github.com/b3log/solo/issues/12722
     */
    public static final String ID_C_HLJS_THEME = "hljsTheme";
    /**
     * 启用备份公开文章到HacPai的键。备份公开文章到社区 https://ld246.com/article/1557238327458
     */
    public static final String ID_C_SYNC_GITHUB = "syncGitHub";
    /**
     * 启用同步（拉取）GitHub的键。拉取并展示仓库 https://ld246.com/article/1557238327458
     * https://github.com/b3log/solo/issues/12825
     */
    public static final String ID_C_PULL_GITHUB = "pullGitHub";
    /**
     * 网站图标URL的键。
     */
    public static final String ID_C_FAVICON_URL = "faviconURL";
    /**
     * 自定义变量的键。
     */
    public static final String ID_C_CUSTOM_VARS = "customVars";
    /**
     * 博客标题的键。
     */
    public static final String ID_C_BLOG_TITLE = "blogTitle";
    /**
     * 博客副标题的键。
     */
    public static final String ID_C_BLOG_SUBTITLE = "blogSubtitle";
    /**
     * 相关文章显示数量的键。
     */
    public static final String ID_C_RELEVANT_ARTICLES_DISPLAY_CNT = "relevantArticlesDisplayCount";
    /**
     * 随机文章显示数量的键。
     */
    public static final String ID_C_RANDOM_ARTICLES_DISPLAY_CNT = "randomArticlesDisplayCount";
    /**
     * 最近文章显示数量的键。
     */
    public static final String ID_C_RECENT_ARTICLE_DISPLAY_CNT = "recentArticleDisplayCount";
    /**
     * 最近评论显示数量的键。
     */
    public static final String ID_C_RECENT_COMMENT_DISPLAY_CNT = "recentCommentDisplayCount";
    /**
     * 最常用标签显示数量的键。
     */
    public static final String ID_C_MOST_USED_TAG_DISPLAY_CNT = "mostUsedTagDisplayCount";
    /**
     * 最多评论文章显示数量的键。
     */
    public static final String ID_C_MOST_COMMENT_ARTICLE_DISPLAY_CNT = "mostCommentArticleDisplayCount";
    /**
     * 最多浏览文章显示数量的键。
     */
    public static final String ID_C_MOST_VIEW_ARTICLE_DISPLAY_CNT = "mostViewArticleDisplayCount";
    /**
     * 文章列表显示数量的键。
     */
    public static final String ID_C_ARTICLE_LIST_DISPLAY_COUNT = "articleListDisplayCount";
    /**
     * 文章列表分页窗口大小的键。
     */
    public static final String ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE = "articleListPaginationWindowSize";
    /**
     * 语言字符串的键。
     */
    public static final String ID_C_LOCALE_STRING = "localeString";
    /**
     * 时区ID的键。
     */
    public static final String ID_C_TIME_ZONE_ID = "timeZoneId";
    /**
     * 公告栏的键。
     */
    public static final String ID_C_NOTICE_BOARD = "noticeBoard";
    /**
     * HTML头部的键。
     */
    public static final String ID_C_HTML_HEAD = "htmlHead";
    /**
     * meta关键词的键。
     */
    public static final String ID_C_META_KEYWORDS = "metaKeywords";
    /**
     * meta描述的键。
     */
    public static final String ID_C_META_DESCRIPTION = "metaDescription";
    /**
     * 福利音乐服务的键。
     */
    public static final String ID_C_WELFARE_LUTE_SERVICE = "welfareLuteService";
    /**
     * 文章更新提示标志的键。
     */
    public static final String ID_C_ENABLE_ARTICLE_UPDATE_HINT = "enableArticleUpdateHint";
    /**
     * 标记的键。
     */
    public static final String ID_C_SIGNS = "signs";
    /**
     * 允许通过永久链接访问草稿的键。
     */
    public static final String ID_C_ALLOW_VISIT_DRAFT_VIA_PERMALINK = "allowVisitDraftViaPermalink";
    /**
     * 版本的键。
     */
    public static final String ID_C_VERSION = "version";
    /**
     * 文章列表显示样式的键。
     * <p>
     * 可选值：
     * <ul>
     * <li>"titleOnly"</li>
     * <li>"titleAndContent"</li>
     * <li>"titleAndAbstract"</li>
     * </ul>
     * </p>
     */
    public static final String ID_C_ARTICLE_LIST_STYLE = "articleListStyle";
    /**
     * 文章/页面是否可评论的键。
     */
    public static final String ID_C_COMMENTABLE = "commentable";
    /**
     * Feed（Atom/RSS）输出模式的键。
     * <p>
     * 可选值：
     * <ul>
     * <li>"abstract"</li>
     * <li>"fullContent"</li>
     * </ul>
     * </p>
     */
    public static final String ID_C_FEED_OUTPUT_MODE = "feedOutputMode";
    /**
     * Feed（Atom/RSS）输出条目数量的键。
     */
    public static final String ID_C_FEED_OUTPUT_CNT = "feedOutputCnt";
    /**
     * 皮肤目录名称的键。
     */
    public static final String ID_C_SKIN_DIR_NAME = "skinDirName";
    /**
     * 移动端皮肤目录名称的键。
     */
    public static final String ID_C_MOBILE_SKIN_DIR_NAME = "mobileSkinDirName";
    /**
     * 页脚内容的键。
     */
    public static final String ID_C_FOOTER_CONTENT = "footerContent";
    /**
     * 博客浏览量统计的键。
     */
    public static final String ID_C_STATISTIC_BLOG_VIEW_COUNT = "statisticBlogViewCount";
    /**
     * GitHub仓库的键。
     */
    public static final String ID_C_GITHUB_REPOS = "githubRepos";
    /**
     * USite的键。
     */
    public static final String ID_C_USITE = "usite";
    // 分类常量
    /**
     * 分类 - 偏好设置。
     */
    public static final String CATEGORY_C_PREFERENCE = "preference";
    /**
     * 分类 - 统计信息。
     */
    public static final String CATEGORY_C_STATISTIC = "statistic";
    /**
     * 分类 - GitHub。
     */
    public static final String CATEGORY_C_GITHUB = "github";
    /**
     * 分类 - HacPai。
     */
    public static final String CATEGORY_C_HACPAI = "hacpai";
    /**
     * 分类 - 皮肤。
     */
    public static final String CATEGORY_C_SKIN = "skin";
    //// Transient ////
    /**
     * 统计已发布博客文章数量的键。
     */
    public static final String ID_T_STATISTIC_PUBLISHED_ARTICLE_COUNT = "statisticPublishedBlogArticleCount";
    /**
     * 统计已发布博客评论数量的键。
     */
    public static final String ID_T_STATISTIC_PUBLISHED_BLOG_COMMENT_COUNT = "statisticPublishedBlogCommentCount";
    /**
     * GitHub PAT（个人访问令牌）的键。
     */
    public static final String ID_C_GITHUB_PAT = "githubPAT";
    // Bolo
    /**
     * HacPai用户名的键。
     */
    public static final String ID_C_HACPAI_USER = "hacpaiUser";
    /**
     * B3log密钥的键。
     */
    public static final String ID_C_B3LOG_KEY = "b3logKey";
    /**
     * 首页显示的最大归档数量的键。
     */
    public static final String ID_C_MAX_ARCHIVE = "maxArchive";
    /**
     * 邮箱的键。
     */
    public static final String ID_C_MAIL_BOX = "mailBox";
    /**
     * 邮箱用户名的键。
     */
    public static final String ID_C_MAIL_USERNAME = "mailUsername";
    /**
     * 邮箱密码的键。
     */
    public static final String ID_C_MAIL_PASSWORD = "mailPassword";
    /**
     * 邮箱用户上下文的键。
     */
    public static final String ID_C_MAIL_USER_CONTEXT = "mailUserContext";
    /**
     * 图床配置的键。
     */
    public static final String ID_C_TUCHUANG_CONFIG = "tuChuangConfig";
    /**
     * 回复提醒的键。
     */
    public static final String ID_C_REPLY_REMIND = "replyRemind";
    /**
     * KanBanNiang选择器的键。
     */
    public static final String ID_C_KANBANNIANG_SELECTOR = "kanbanniangSelector";
    /**
     * 垃圾评论关键词的键。
     */
    public static final String ID_C_SPAM = "spam";
    /**
     * 互动开关的键。
     */
    public static final String ID_C_INTERACTIVE = "interactive";
    /**
     * WAF的键。
     */
    public static final String ID_C_WAF_POWER = "wafPower";
    /**
     * WAF每秒限制的键。
     */
    public static final String ID_C_WAF_CURRENT_LIMIT_SECOND = "wafCurrentLimitSecond";
    /**
     * WAF每次限制的键。
     */
    public static final String ID_C_WAF_CURRENT_LIMIT_TIMES = "wafCurrentLimitTimes";
    /**
     * 管理员激活发送到邮箱的键。
     */
    public static final String ID_C_ADMIN_ACTIVE_SENT_TO_MAILBOX = "adminActiveSentToMailbox";
    /**
     * 启用自动刷新GitHub的键。
     */
    public static final String ID_C_ENABLE_AUTO_FLUSH_GITHUB = "enableAutoFlushGitHub";
    /**
     * 我的GitHub ID的键。
     */
    public static final String ID_C_MY_GITHUB_ID = "myGitHubID";
    /**
     * Server酱发送密钥的键。
     */
    public static final String ID_C_SEND_KEY = "sendKey";
    /**
     * 改进帮助计划的键。
     */
    public static final String ID_C_HELP_IMPROVE_PLAN = "helpImprovePlan";

    /**
     * 私有构造函数。
     */
    private Option() {
    }

    /**
     * 默认偏好设置。
     *
     * @since 0.3.1
     */
    public static final class DefaultPreference {

        /**
         * 默认的代码块行号显示。
         */
        public static final String DEFAULT_SHOW_CODE_BLOCK_LN = "false";
        /**
         * 默认的hljs主题。
         */
        public static final String DEFAULT_HLJS_THEME = "github";
        /**
         * 默认启用同步推送GitHub。
         */
        public static final String DEFAULT_SYNC_GITHUB = "true";
        /**
         * 默认启用同步拉取GitHub。
         */
        public static final String DEFAULT_PULL_GITHUB = "true";
        /**
         * 默认的网站图标URL。
         */
        public static final String DEFAULT_FAVICON_URL = "https://pic.stackoverflow.wiki/uploadImages/114/246/231/87/2020/06/06/16/41/3e4a3ce8-8882-4258-9860-a337bf859605.png";
        /**
         * 默认的自定义变量。
         */
        public static final String DEFAULT_CUSTOM_VARS = "key0=val0|key1=list|key2=val2";
        /**
         * 默认的最近文章显示数量。
         */
        public static final int DEFAULT_RECENT_ARTICLE_DISPLAY_COUNT = 10;
        /**
         * 默认的最近评论显示数量。
         */
        public static final int DEFAULT_RECENT_COMMENT_DISPLAY_COUNT = 10;
        /**
         * 默认的最常用标签显示数量。
         */
        public static final int DEFAULT_MOST_USED_TAG_DISPLAY_COUNT = 20;
        /**
         * 默认的文章列表显示数量。
         */
        public static final int DEFAULT_ARTICLE_LIST_DISPLAY_COUNT = 20;
        /**
         * 默认的文章列表分页窗口大小。
         */
        public static final int DEFAULT_ARTICLE_LIST_PAGINATION_WINDOW_SIZE = 15;
        /**
         * 默认的最多评论文章显示数量。
         */
        public static final int DEFAULT_MOST_COMMENT_ARTICLE_DISPLAY_COUNT = 5;
        /**
         * 默认的博客副标题。
         */
        public static final String DEFAULT_BLOG_SUBTITLE = "记录精彩的程序人生";
        /**
         * 默认的皮肤目录名称。
         */
        public static final String DEFAULT_SKIN_DIR_NAME = "bolo-nexmoe";
        /**
         * 默认的移动端皮肤目录名称。
         */
        public static final String DEFAULT_MOBILE_SKIN_DIR_NAME = "bolo-nexmoe";
        /**
         * 默认的语言。
         */
        public static final String DEFAULT_LANGUAGE = "zh_CN";
        /**
         * 默认的时区。
         *
         * @see java.util.TimeZone#getAvailableIDs()
         */
        public static final String DEFAULT_TIME_ZONE = "Asia/Shanghai";
        /**
         * 默认的福利音乐服务。
         */
        public static final String DEFAULT_WELFARE_LUTE_SERVICE = "false";
        /**
         * 默认的启用文章更新提示。
         */
        public static final String DEFAULT_ENABLE_ARTICLE_UPDATE_HINT = "false";
        /**
         * 默认的公告栏。
         */
        public static final String DEFAULT_NOTICE_BOARD = "Open Source, Open Mind, <br/>Open Sight, Open Future!\n\n<!-- 公告栏可使用 HTML、JavaScript，比如可以在此加入第三方统计 js -->";
        /**
         * 默认的meta关键词。
         */
        public static final String DEFAULT_META_KEYWORDS = "Solo,Java,博客,开源";
        /**
         * 默认的meta描述。
         */
        public static final String DEFAULT_META_DESCRIPTION = "A small and beautiful blogging system. 一款小而美的博客系统。";
        /**
         * 默认的HTML头部追加内容。
         */
        public static final String DEFAULT_HTML_HEAD = "";
        /**
         * 默认的页脚内容。
         */
        public static final String DEFAULT_FOOTER_CONTENT = "";
        /**
         * 默认的相关文章显示数量。
         */
        public static final int DEFAULT_RELEVANT_ARTICLES_DISPLAY_COUNT = 5;
        /**
         * 默认的随机文章显示数量。
         */
        public static final int DEFAULT_RANDOM_ARTICLES_DISPLAY_COUNT = 5;
        /**
         * 默认的外部相关文章显示数量。
         */
        public static final int DEFAULT_EXTERNAL_RELEVANT_ARTICLES_DISPLAY_COUNT = 0;
        /**
         * 默认的最多浏览文章显示数量。
         */
        public static final int DEFAULT_MOST_VIEW_ARTICLES_DISPLAY_COUNT = 5;
        /**
         * 默认的允许通过永久链接访问草稿。
         */
        public static final String DEFAULT_ALLOW_VISIT_DRAFT_VIA_PERMALINK = "false";
        /**
         * 默认的允许评论文章/页面。
         */
        public static final String DEFAULT_COMMENTABLE = "true";
        /**
         * 默认的文章列表显示样式。
         */
        public static final String DEFAULT_ARTICLE_LIST_STYLE = "titleAndAbstract";
        /**
         * 默认的Feed输出模式。
         */
        public static final String DEFAULT_FEED_OUTPUT_MODE = "abstract";
        /**
         * 默认的Feed输出条目数量。
         */
        public static final int DEFAULT_FEED_OUTPUT_CNT = 10;
        // Bolo
        /**
         * 默认的最大归档数量。
         */
        public static final String DEFAULT_MAX_ARCHIVE = "-1";
        /**
         * 默认的B3log用户名。
         */
        public static final String DEFAULT_B3LOG_USERNAME = "BoloDefault";
        /**
         * 默认的B3log密码。
         */
        public static final String DEFAULT_B3LOG_PASSWORD = "123456";

        /**
         * 私有构造函数。
         */
        private DefaultPreference() {
        }
    }
}
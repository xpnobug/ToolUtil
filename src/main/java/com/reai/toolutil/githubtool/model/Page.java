
package com.reai.toolutil.githubtool.model;

/**
 * 此类定义了所有页面模型相关的键。
 *
 * @author 86136
 */
public final class Page {

    /**
     * 页面。
     */
    public static final String PAGE = "page";

    /**
     * 页面列表。
     */
    public static final String PAGES = "pages";

    /**
     * 标题键。
     */
    public static final String PAGE_TITLE = "pageTitle";

    /**
     * 排序键。
     */
    public static final String PAGE_ORDER = "pageOrder";

    /**
     * 永久链接键。
     */
    public static final String PAGE_PERMALINK = "pagePermalink";

    /**
     * 打开目标键。
     * <p>
     * 可用值：
     * <ul>
     * <li>_blank</li>
     * 在新窗口或标签页中打开链接的文档。
     * <li>_self</li>
     * 在点击时在相同的框架中打开链接的文档（默认）。
     * <li>_parent</li>
     * 在父框架中打开链接的文档。
     * <li>_top</li>
     * 在窗口的完整主体中打开链接的文档。
     * <li><i>frame name</i></li>
     * 在指定名称的框架中打开链接的文档。
     * </ul>
     * 更多详情请参见<a href="http://www.w3schools.com/tags/att_a_target.asp">这里</a>。
     * </p>
     */
    public static final String PAGE_OPEN_TARGET = "pageOpenTarget";

    /**
     * 图标 URL 键。
     */
    public static final String PAGE_ICON = "pageIcon";

    /**
     * 私有构造函数。
     */
    private Page() {
    }
}

package com.reai.toolutil.githubtool.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;

/**
 * 图片工具类.
 */
public final class Images {

    /**
     * 日志记录器.
     */
    private static final Logger LOGGER = Logger.getLogger(Images.class);

    /**
     * 社区文件服务 URL.
     */
    public static String COMMUNITY_FILE_URL = "https://b3logfile.com";

    /**
     * 私有构造器.
     */
    private Images() {
    }

    /**
     * 七牛图片处理.
     *
     * @param html 指定的内容 HTML
     * @return 处理后的内容
     */
    public static String qiniuImgProcessing(final String html) {
        String ret = html;
        final String[] imgSrcs = StringUtils.substringsBetween(html, "<img src=\"", "\"");
        if (null == imgSrcs) {
            return ret;
        }

        for (final String imgSrc : imgSrcs) {
            if (StringUtils.contains(imgSrc, ".gif") || StringUtils.containsIgnoreCase(imgSrc,
                "imageView")) {
                continue;
            }

            ret = StringUtils.replace(ret, imgSrc,
                imgSrc + "?imageView2/2/w/1280/format/jpg/interlace/1/q/100");
        }

        return ret;
    }

    /**
     * 返回指定宽度和高度的七牛图片处理样式的图片 URL.
     *
     * @param imageURL 指定的图片 URL
     * @param width    指定的宽度
     * @param height   指定的高度
     * @return 图片 URL
     */
    public static String imageSize(final String imageURL, final int width, final int height) {
        if (StringUtils.containsIgnoreCase(imageURL, "imageView")) {
            return imageURL;
        }

        return imageURL + "?imageView2/1/w/" + width + "/h/" + height + "/interlace/1/q/100";
    }

    /**
     * 随机获取一张图片 URL. 详见 https://github.com/b3log/bing.
     *
     * @return 图片 URL
     */
    public static String randImage() {
        try {
            final long min = DateUtils.parseDate("20171104", new String[]{"yyyyMMdd"}).getTime();
            final long max = System.currentTimeMillis();
            final long delta = max - min;
            final long time = ThreadLocalRandom.current().nextLong(0, delta) + min;

            return COMMUNITY_FILE_URL + "/bing/" + DateFormatUtils.format(time, "yyyyMMdd")
                + ".jpg";
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "生成随机图片 URL 失败", e);

            return COMMUNITY_FILE_URL + "/bing/20171104.jpg";
        }
    }

    /**
     * 随机获取指定数量的图片 URL.
     *
     * @param n 指定的数量
     * @return 图片 URL 列表
     */
    public static List<String> randomImages(final int n) {
        final List<String> ret = new ArrayList<>();

        int i = 0;
        while (i < n * 5) {
            final String url = randImage();
            if (!ret.contains(url)) {
                ret.add(url);
            }

            if (ret.size() >= n) {
                return ret;
            }

            i++;
        }

        return ret;
    }
}

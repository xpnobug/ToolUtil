package com.reai.toolutil.githubtool.model;

import com.reai.toolutil.Reais;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.b3log.latke.ioc.Singleton;
import org.json.JSONObject;

/**
 * 选择缓存
 *
 * @author 86136
 */
@Singleton
public class OptionCache {

    /**
     * 选择缓存
     */
    private final Map<String, JSONObject> cache = new ConcurrentHashMap<>();

    /**
     * 类别选项缓存
     */
    private final Map<String, JSONObject> categoryCache = new ConcurrentHashMap<>();

    /**
     * 获取指定类别的合并选项作为JSON对象
     */
    public JSONObject getCategory(final String category) {
        JSONObject ret = categoryCache.get(category);
        if (null == ret) {
            return null;
        }

        return Reais.clone(ret);
    }

    /**
     * 将指定的合并选项放入指定的类别
     *
     * @param category      the specified category
     * @param mergedOptions the specified merged options
     */
    public void putCategory(final String category, final JSONObject mergedOptions) {
        categoryCache.put(category, mergedOptions);
    }

    /**
     * 清除所有缓存数据。
     */
    public void clear() {
        cache.clear();
        categoryCache.clear();
    }
}

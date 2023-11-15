package com.reai.toolutil.githubtool.service.impl;

import com.reai.toolutil.githubtool.model.Option;
import com.reai.toolutil.githubtool.model.OptionCache;
import java.util.List;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.repository.AbstractRepository;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.annotation.Repository;
import org.json.JSONObject;

/**
 * 选择存储库
 */
@Repository
public class OptionRepository extends AbstractRepository {

    /**
     * Option cache.
     */
    @Inject
    private OptionCache optionCache;

    /**
     * 构造具有指定名称的存储库
     *
     * @param name the specified name
     */
    public OptionRepository(String name) {
        super(name);
    }

    /**
     * 获取具有指定类别的选项。
     * <p>
     * 所有具有指定类别的选项将合并为一个json对象作为返回值。
     * </p>
     *
     * @param category the specified category
     * @return 具有指定类别的所有选项，例如，
     * <pre>
     * {
     *     "${optionId}": "${optionValue}",
     *     ....
     * }
     * </pre>
     * 如果找不到则返回{@code null}
     * @throws RepositoryException 存储库异常
     */
    public JSONObject getOptions(final String category) throws RepositoryException {
        final JSONObject cached = optionCache.getCategory(category);
        if (null != cached) {
            return cached;
        }

        final JSONObject ret = new JSONObject();
        try {
            final List<JSONObject> options = getList(new Query().setFilter(new PropertyFilter(
                Option.OPTION_CATEGORY, FilterOperator.EQUAL, category)));
            if (0 == options.size()) {
                return null;
            }

            options.stream().forEach(option -> ret.put(option.optString(Keys.OBJECT_ID),
                option.opt(Option.OPTION_VALUE)));
            optionCache.putCategory(category, ret);

            return ret;
        } catch (final Exception e) {
            throw new RepositoryException(e);
        }
    }
}

package cn.tenmg.dsql.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tenmg.dsql.DSQLFactory;
import cn.tenmg.dsql.NamedSQL;
import cn.tenmg.dsql.ParamsConverter;
import cn.tenmg.dsql.ParamsFilter;
import cn.tenmg.dsql.config.model.Converter;
import cn.tenmg.dsql.config.model.Dsql;
import cn.tenmg.dsql.config.model.Filter;
import cn.tenmg.dsql.config.model.ParamsHandler;
import cn.tenmg.dsql.utils.CollectionUtils;
import cn.tenmg.dsql.utils.DSQLUtils;
import cn.tenmg.dsql.utils.ParamsConverterUtils;
import cn.tenmg.dsql.utils.ParamsFilterUtils;

/**
 * 抽象动态结构化查询语言工厂
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public abstract class AbstractDSQLFactory implements DSQLFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -169658678380590492L;

	abstract Map<String, Dsql> getDsqls();

	@Override
	public Dsql getDsql(String id) {
		return getDsqls().get(id);
	}

	@Override
	public String getScript(String id) {
		Dsql dsql = getDsql(id);
		if (dsql == null) {
			return null;
		}
		return dsql.getScript();
	}

	@Override
	public NamedSQL parse(String dsql, Object... params) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		if (params != null) {
			for (int i = 0; i < params.length - 1; i++) {
				paramsMap.put(params[i].toString(), params[++i]);
			}
		}
		return parse(dsql, paramsMap);
	}

	@Override
	public NamedSQL parse(String dsql, Map<String, ?> params) {
		NamedSQL namedSQL = null;
		Dsql obj = getDsql(dsql);
		if (obj == null) {
			namedSQL = DSQLUtils.parse(dsql, params);
		} else {
			namedSQL = parse(obj, params);
		}
		return namedSQL;
	}

	/**
	 * 根据指定的参数params分析转换动态SQL对象dsql为SQL对象
	 * 
	 * @param dsql
	 *            动态SQL配置对象
	 * @param params
	 *            参数列表
	 * @return SQL对象
	 */
	protected NamedSQL parse(Dsql dsql, Map<String, ?> params) {
		Filter filter = dsql.getFilter();
		if (!CollectionUtils.isEmpty(params)) {
			if (filter != null) {
				doFilter(params, filter);
			}
			Converter converter = dsql.getConverter();
			if (converter != null) {
				Map<String, Object> paramaters = new HashMap<String, Object>();
				paramaters.putAll(params);
				convert(paramaters, converter);
				params = paramaters;
			}
		}
		NamedSQL namedSQL = DSQLUtils.parse(dsql.getScript(), params);
		namedSQL.setId(dsql.getId());
		return namedSQL;
	}

	/**
	 * 过滤参数
	 * 
	 * @param params
	 *            参数查找表
	 * @param filter
	 *            参数过滤器
	 */
	private void doFilter(Map<String, ?> params, Filter filter) {
		List<ParamsHandler> paramsFilters = filter.getParamsFilters();
		if (!CollectionUtils.isEmpty(paramsFilters)) {
			ParamsHandler config;
			ParamsFilter<ParamsHandler> paramsFilter;
			for (int i = 0, size = paramsFilters.size(); i < size; i++) {
				config = paramsFilters.get(i);
				paramsFilter = ParamsFilterUtils.getParamsFilter(config.getClass());
				paramsFilter.doFilter(params, config);
			}
		}
	}

	/**
	 * 参数转换
	 * 
	 * @param params
	 *            参数查找表
	 * @param converter
	 *            参数转换器配置
	 */
	private void convert(Map<String, Object> params, Converter converter) {
		List<ParamsHandler> paramsConverters = converter.getParamsConverters();
		if (!CollectionUtils.isEmpty(paramsConverters)) {
			for (int i = 0, size = paramsConverters.size(); i < size; i++) {
				ParamsHandler config = paramsConverters.get(i);
				ParamsConverter<ParamsHandler> paramsConverter = ParamsConverterUtils
						.getParamsConverter(config.getClass());
				paramsConverter.convert(params, config);
			}
		}
	}

}

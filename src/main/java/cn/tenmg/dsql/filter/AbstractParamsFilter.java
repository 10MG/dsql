package cn.tenmg.dsql.filter;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.tenmg.dsl.utils.StringUtils;
import cn.tenmg.dsql.ParamsFilter;
import cn.tenmg.dsql.config.model.filter.CompareableFilter;
import cn.tenmg.dsql.utils.ParamsFilterUtils;
import cn.tenmg.dsql.utils.ParamsFilterUtils.FilterTuple;

public abstract class AbstractParamsFilter implements ParamsFilter<CompareableFilter> {

	/**
	 * 比较参数值是否满足过滤条件
	 * 
	 * @param paramValue
	 *            参数值
	 * @param value
	 *            比较值
	 */
	abstract boolean compare(String paramValue, String value);

	/**
	 * 过滤参数
	 * 
	 * @param params
	 *            参数索引表
	 */
	@Override
	public void doFilter(Map<String, ?> params, CompareableFilter config) {
		String paramsConfig = config.getParams(), value = config.getValue();
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(paramsConfig)) {
			String paramNames[] = paramsConfig.split(","), paramName;
			for (int j = 0; j < paramNames.length; j++) {
				paramName = paramNames[j].trim();
				if ("*".equals(paramName)) {
					filterAll(params, value);
					break;
				} else if (params.containsKey(paramName)) {
					filter(params, paramName, value);
				}
			}
		}
	}

	/**
	 * 过滤全部满足比较条件的参数
	 * 
	 * @param params
	 *            参数索引表
	 * @param value
	 *            指定参数名
	 */
	<T extends Object> void filterAll(Map<String, T> params, String value) {
		Iterator<Entry<String, T>> it = params.entrySet().iterator();
		Object paramValue;
		while (it.hasNext()) {
			paramValue = it.next().getValue();
			if (paramValue != null && isFilterAble(paramValue, value)) {
				it.remove();
			}
		}
	}

	/**
	 * 过滤指定名称且满足比较条件的参数
	 * 
	 * @param params
	 *            参数索引表
	 * @param paramName
	 *            指定参数名
	 * @param value
	 *            给定的值
	 */
	<T extends Object> void filter(Map<String, T> params, String paramName, String value) {
		Object paramValue = params.get(paramName);
		if (paramValue != null && isFilterAble(paramValue, value)) {
			params.remove(paramName);
		}
	}

	/**
	 * 比较参数值与比较值确定该是否需要过滤
	 * 
	 * @param paramValue
	 *            参数值
	 * @param value
	 *            比较值
	 * @return 参数需要过滤返回true，否则返回false
	 */
	boolean isFilterAble(Object paramValue, String value) {
		FilterTuple tuple = ParamsFilterUtils.convert(paramValue, value);
		return compare(tuple.getParamValue(), tuple.getValue());
	}
}

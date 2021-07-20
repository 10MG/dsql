package cn.tenmg.dsql.utils;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;

import cn.tenmg.dsl.utils.StringUtils;
import cn.tenmg.dsql.ParamsFilter;
import cn.tenmg.dsql.config.model.ParamsHandler;

/**
 * 过滤器工具类
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class ParamsFilterUtils {

	public static class FilterTuple {

		private String paramValue;

		private String value;

		public String getParamValue() {
			return paramValue;
		}

		public void setParamValue(String paramValue) {
			this.paramValue = paramValue;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	/**
	 * 补位0
	 */
	private static final String ZERO = "0";

	/**
	 * 日期格式整理正则表达式
	 */
	private static final String DATE_REGEX = "(-| |/|:)";

	/**
	 * 日期对象格式化模板
	 */
	private static final String DATE_PATTERN = "yyyyMMddHHmmss";

	/**
	 * 日期对象格式化模板长度
	 */
	private static final int DATE_PATTERN_LENGTH = DATE_PATTERN.length();

	/**
	 * 时间格式整理正则表达式
	 */
	private static final String TIME_REGEX = ":";

	/**
	 * 时间对象格式化模板
	 */
	private static final String TIME_PATTERN = "HHmmss";

	/**
	 * 时间对象格式化模板长度
	 */
	private static final int TIME_PATTERN_LENGTH = TIME_PATTERN.length();

	private static final String PARAMS_FILTER = "ParamsFilter";

	private static final Map<String, ParamsFilter<ParamsHandler>> paramsFilters = new HashMap<String, ParamsFilter<ParamsHandler>>();

	static {
		ServiceLoader<ParamsFilter> loader = ServiceLoader.load(ParamsFilter.class);
		ParamsFilter paramsFilter;
		for (Iterator<ParamsFilter> it = loader.iterator(); it.hasNext();) {
			paramsFilter = it.next();
			paramsFilters.put(paramsFilter.getClass().getSimpleName(), paramsFilter);
		}
	}

	public static ParamsFilter<ParamsHandler> getParamsFilter(Class<? extends ParamsHandler> configType) {
		return paramsFilters.get(configType.getSimpleName().concat(PARAMS_FILTER));
	}

	/**
	 * 整理参数值和比较值，将参数值转换为字符串，java.sql.Time类型参数将使用模板“HHmmss”转换，java.util.Date或java.util.Calendar类型参数将使用模板“yyyyMMddHHmmss”转换。如果参数值为java.sql.Time类型且比较值位数不足6位则在末尾补0;如果参数值为java.util.Date或java.util.Calendar类型且比较值位数不足14位则在末尾补0
	 * 
	 * @param paramValue
	 *            参数值（不能为null）
	 * @param value
	 *            比较值（不能为null）
	 * @return 返回整理后的参数值和比较值的二元组FilterTuple对象
	 */
	public static FilterTuple convert(Object paramValue, String value) {
		FilterTuple filterTuple = new FilterTuple();
		if (paramValue instanceof String) {
			filterTuple.setParamValue((String) paramValue);
		} else if (paramValue instanceof Time) {
			value = value.replaceAll(TIME_REGEX, "");// 格式整理
			if (StringUtils.isNumber(value)) {
				StringBuilder sb = new StringBuilder(value);
				for (int j = value.length(); j < TIME_PATTERN_LENGTH; j++) {// 位数不足补0
					sb.append(ZERO);
				}
				value = sb.toString();
				filterTuple.setParamValue(DateUtils.format(paramValue, TIME_PATTERN));
			}
		} else if (paramValue instanceof Date || paramValue instanceof Calendar) {
			value = value.replaceAll(DATE_REGEX, "");// 格式整理
			if (StringUtils.isNumber(value)) {
				StringBuilder sb = new StringBuilder(value);
				for (int j = value.length(); j < DATE_PATTERN_LENGTH; j++) {// 位数不足补0
					sb.append(ZERO);
				}
				value = sb.toString();
				filterTuple.setParamValue(DateUtils.format(paramValue, DATE_PATTERN));
			}
		} else {
			filterTuple.setParamValue(paramValue.toString());
		}
		filterTuple.setValue(value);
		return filterTuple;
	}

	/**
	 * 将所有值为空白字符串的参数过滤掉
	 * 
	 * @param params
	 *            参数查找表
	 */
	public static <T extends Object> void blankFilter(Map<String, T> params) {
		Iterator<Entry<String, T>> it = params.entrySet().iterator();
		Object paramValue;
		while (it.hasNext()) {
			paramValue = it.next().getValue();
			if (paramValue == null || StringUtils.isBlank(paramValue.toString())) {
				it.remove();
			}
		}
	}

	/**
	 * 将指定参数名且其值为空白字符串的参数过滤掉
	 * 
	 * @param paramName
	 *            参数名
	 * @param params
	 *            参数查找表
	 */
	public static <T extends Object> void blankFilter(String paramName, Map<String, T> params) {
		Object paramValue = params.get(paramName);
		if (paramValue == null || StringUtils.isBlank(paramValue.toString())) {
			params.remove(paramName);
		}
	}

}

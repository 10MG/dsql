package cn.tenmg.dsql.converter;

import cn.tenmg.dsql.config.model.converter.WrapString;
import cn.tenmg.dsql.exception.IllegalConfigException;

/**
 * 字符串参数包装转换器
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class WrapStringParamsConverter extends AbstractParamsConverter {

	@Override
	boolean isValid(String formatter) {
		if (formatter.indexOf(WrapString.VALUE) < 0) {
			throw new IllegalConfigException(
					"The formatter configuration of wrap-string is incorrect, it must contain: " + WrapString.VALUE
							+ ", but it is actually: " + formatter);
		}
		return true;
	}

	@Override
	Object convert(String paramName, Object paramValue, String formatter) {
		if (paramValue == null) {
			return null;
		} else {
			return formatter.replaceAll(WrapString.VALUE_REGEX, paramValue.toString());
		}
	}

}

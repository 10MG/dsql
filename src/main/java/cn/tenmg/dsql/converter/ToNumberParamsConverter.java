package cn.tenmg.dsql.converter;

import java.text.ParseException;

import cn.tenmg.dsql.exception.ConvertException;
import cn.tenmg.dsql.utils.DecimalUtils;

/**
 * 参数数字类型转换器
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class ToNumberParamsConverter extends AbstractParamsConverter {

	@Override
	boolean isValid(String formatter) {
		return true;
	}

	@Override
	Object convert(String paramName, Object paramValue, String formatter) {
		Number number = null;
		if (paramValue != null) {
			try {
				number = DecimalUtils.parse(paramValue, formatter);
			} catch (ParseException e) {
				throw new ConvertException("Failed to convert parameter " + paramName + ": " + paramValue.toString()
						+ " by formatter: " + formatter + " to java.lang.Number", e);
			}
		}
		return number;
	}

}

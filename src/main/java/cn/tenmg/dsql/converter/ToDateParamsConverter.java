package cn.tenmg.dsql.converter;

import java.text.ParseException;
import java.util.Date;

import cn.tenmg.dsql.exception.ConvertException;
import cn.tenmg.dsql.utils.DateUtils;

/**
 * 参数日期类型转换器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
public class ToDateParamsConverter extends AbstractParamsConverter {

	@Override
	boolean isValid(String formatter) {
		return true;
	}

	@Override
	Object convert(String paramName, Object paramValue, String formatter) {
		Date date = null;
		if (paramValue != null) {
			try {
				date = DateUtils.parse(paramValue, formatter);
			} catch (ParseException e) {
				throw new ConvertException("Failed to convert parameter " + paramName + ": " + paramValue.toString()
						+ " by formatter: " + formatter + " to java.util.Date", e);
			}
		}
		return date;
	}

}

package cn.tenmg.dsql.converter;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.tenmg.dsl.utils.StringUtils;
import cn.tenmg.dsql.ParamsConverter;
import cn.tenmg.dsql.config.model.converter.BasicConverter;

/**
 * 参数转换器抽象实现类
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
public abstract class AbstractParamsConverter implements ParamsConverter<BasicConverter> {

	abstract boolean isValid(String formatter);

	abstract Object convert(String name, Object value, String formatter);

	@Override
	public void convert(Map<String, Object> params, BasicConverter config) {
		String paramsConfig = config.getParams();
		String formatter = config.getFormatter();
		if (StringUtils.isNotBlank(paramsConfig) && isValid(formatter)) {
			String paramNames[] = paramsConfig.split(","), paramName;
			Object paramValue;
			for (int j = 0; j < paramNames.length; j++) {
				paramName = paramNames[j].trim();
				if ("*".equals(paramName)) {
					Set<String> set = params.keySet();
					for (Iterator<String> it = set.iterator(); it.hasNext();) {
						paramName = it.next();
						paramValue = params.get(paramName);
						params.put(paramName, convert(paramName, paramValue, formatter));
					}
					break;
				} else if (params.containsKey(paramName)) {
					paramValue = params.get(paramName);
					params.put(paramName, convert(paramName, paramValue, formatter));
				}
			}
		}
	}
}

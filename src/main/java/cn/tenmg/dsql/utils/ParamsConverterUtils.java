package cn.tenmg.dsql.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import cn.tenmg.dsql.ParamsConverter;
import cn.tenmg.dsql.config.model.ParamsHandler;

/**
 * 过滤器工具类
 * 
 * @author June wjzhao@aliyun.com
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class ParamsConverterUtils {

	private static final String PARAMS_CONVERTER = "ParamsConverter";

	private static final Map<String, ParamsConverter<ParamsHandler>> paramsConverters = new HashMap<String, ParamsConverter<ParamsHandler>>();

	static {
		ServiceLoader<ParamsConverter> loader = ServiceLoader.load(ParamsConverter.class);
		ParamsConverter paramsConverter;
		for (Iterator<ParamsConverter> it = loader.iterator(); it.hasNext();) {
			paramsConverter = it.next();
			paramsConverters.put(paramsConverter.getClass().getSimpleName(), paramsConverter);
		}
	}

	public static ParamsConverter<ParamsHandler> getParamsConverter(Class<? extends ParamsHandler> configType) {
		return paramsConverters.get(configType.getSimpleName().concat(PARAMS_CONVERTER));
	}

}

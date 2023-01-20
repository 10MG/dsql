package cn.tenmg.dsql.model;

import java.lang.reflect.ParameterizedType;

import cn.tenmg.dsql.ParamsHandlerTest;

/**
 * 参数处理器器测试抽象类
 * 
 * @author June wjzhao@aliyun.com
 *
 * @param <T>
 *            测试的参数处理器
 * @since 1.3.0
 */
public class AbstractParamsHandlerTest<T> implements ParamsHandlerTest<T> {

	private Class<T> paramsHandlerType;

	@SuppressWarnings("unchecked")
	public AbstractParamsHandlerTest() {
		paramsHandlerType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Override
	public Class<T> getParamsHandlerType() {
		return paramsHandlerType;
	}

}

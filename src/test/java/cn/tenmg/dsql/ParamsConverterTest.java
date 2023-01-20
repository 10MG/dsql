package cn.tenmg.dsql;

import cn.tenmg.dsl.ParamsConverter;

/**
 * 参数转换器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @param <T>
 *            测试的参数转换器
 * @since 1.3.0
 *
 */
public interface ParamsConverterTest<T extends ParamsConverter<?>> extends ParamsHandlerTest<T> {

	void test(T converter);

}

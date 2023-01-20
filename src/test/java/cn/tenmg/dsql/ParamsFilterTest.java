package cn.tenmg.dsql;

import cn.tenmg.dsl.ParamsFilter;

/**
 * 参数过滤器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @param <T>
 *            测试的参数过滤器
 * @since 1.3.0
 */
public interface ParamsFilterTest<T extends ParamsFilter> extends ParamsHandlerTest<T> {

	void test(T filter);

}

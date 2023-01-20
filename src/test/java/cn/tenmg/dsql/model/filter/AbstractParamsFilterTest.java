package cn.tenmg.dsql.model.filter;

import cn.tenmg.dsl.ParamsFilter;
import cn.tenmg.dsql.ParamsFilterTest;
import cn.tenmg.dsql.model.AbstractParamsHandlerTest;

/**
 * 参数过滤器测试抽象类
 * 
 * @author June wjzhao@aliyun.com
 *
 * @param <T>
 *            测试的参数过滤器
 * @since 1.3.0
 */
public abstract class AbstractParamsFilterTest<T extends ParamsFilter> extends AbstractParamsHandlerTest<T>
		implements ParamsFilterTest<T> {

}

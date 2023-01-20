package cn.tenmg.dsql.model.converter;

import cn.tenmg.dsl.ParamsConverter;
import cn.tenmg.dsql.ParamsConverterTest;
import cn.tenmg.dsql.model.AbstractParamsHandlerTest;

/**
 * 参数转换器测试抽象类
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @param <T>
 *            测试的参数转换器
 * @since 1.3.0
 */
public abstract class AbstractParamsConverterTest<T extends ParamsConverter<?>> extends AbstractParamsHandlerTest<T>
		implements ParamsConverterTest<T> {

}

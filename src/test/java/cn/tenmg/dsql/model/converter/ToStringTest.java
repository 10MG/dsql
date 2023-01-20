package cn.tenmg.dsql.model.converter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsql.config.model.converter.ToString;

/**
 * 将参数转换为 {@code java.lang.String} 类型的转换器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class ToStringTest extends AbstractParamsConverterTest<ToString> {

	@Override
	public void test(ToString toString) {
		Assertions.assertEquals("state", toString.getParams());
		Assertions.assertEquals("#", toString.getFormatter());
		Assertions.assertEquals(Integer.valueOf(Integer.MAX_VALUE).toString(), toString.convert(Integer.MAX_VALUE));
	}

}

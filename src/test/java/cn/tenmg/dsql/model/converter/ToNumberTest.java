package cn.tenmg.dsql.model.converter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsl.utils.DecimalUtils;
import cn.tenmg.dsql.config.model.converter.ToNumber;

/**
 * 将参数转换为数字 {@code java.lang.Number} 类型的转换器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class ToNumberTest extends AbstractParamsConverterTest<ToNumber> {

	private static final String value = "1", formatter = "";

	@Override
	public void test(ToNumber toNumber) {
		Assertions.assertEquals("enabled", toNumber.getParams());
		Assertions.assertEquals(formatter, toNumber.getFormatter());
		Assertions.assertEquals(DecimalUtils.parse(value, formatter), toNumber.convert(value));
	}

}

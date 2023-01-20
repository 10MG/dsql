package cn.tenmg.dsql.model.converter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsl.utils.StringUtils;
import cn.tenmg.dsql.config.model.converter.WrapString;

/**
 * 将字符串参数使用模板进行装饰的转换器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class WrapStringTest extends AbstractParamsConverterTest<WrapString> {

	public static final String staffName = "June", expectedAfterWrap = StringUtils.concat("%", staffName, "%");

	@Override
	public void test(WrapString wrapString) {
		Assertions.assertEquals("staffName", wrapString.getParams());
		Assertions.assertEquals("%${value}%", wrapString.getFormatter());
		Assertions.assertEquals(expectedAfterWrap, wrapString.convert(staffName));
	}

}

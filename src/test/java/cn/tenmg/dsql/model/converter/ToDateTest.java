package cn.tenmg.dsql.model.converter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsl.utils.DateUtils;
import cn.tenmg.dsql.config.model.converter.ToDate;

/**
 * 将参数转为 {@code java.util.Date} 类型的转换器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class ToDateTest extends AbstractParamsConverterTest<ToDate> {

	private static final String value = "2021-07-02", formatter = "yyyy-MM-dd";

	@Override
	public void test(ToDate toDate) {
		Assertions.assertEquals("beginDate,endDate", toDate.getParams());
		Assertions.assertEquals(formatter, toDate.getFormatter());
		Assertions.assertEquals(DateUtils.parse(value, formatter), toDate.convert(value));
	}

}

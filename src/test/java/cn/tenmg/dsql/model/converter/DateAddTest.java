package cn.tenmg.dsql.model.converter;

import java.util.Date;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsl.utils.DateUtils;
import cn.tenmg.dsql.config.model.converter.DateAdd;

/**
 * 对 java.util.Date 类型的参数做加法运算的转换器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 */
public class DateAddTest extends AbstractParamsConverterTest<DateAdd> {

	@Override
	public void test(DateAdd dateAdd) {
		Assertions.assertEquals("endDate", dateAdd.getParams());
		Assertions.assertEquals(1, dateAdd.getAmount());
		Assertions.assertEquals("day", dateAdd.getUnit());
		Date now = new Date();
		Assertions.assertEquals(DateUtils.addDays(now, 1), dateAdd.convert(now));
	}

}

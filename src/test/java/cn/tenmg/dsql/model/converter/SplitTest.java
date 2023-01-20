package cn.tenmg.dsql.model.converter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsql.config.model.converter.Split;

/**
 * 将类型为 {@code java.lang.String} 的非 {@code null} 参数值进行分割的转换器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class SplitTest extends AbstractParamsConverterTest<Split> {

	public static final String positions = "Chairman,CEO,COO,CFO,CIO,OD,MD,OM,PM,Staff", regex = ",";
	
	public static final int limit = 500;

	@Override
	public void test(Split split) {
		Assertions.assertEquals("positions", split.getParams());
		Assertions.assertEquals(regex, split.getRegex());
		Assertions.assertEquals(limit, split.getLimit());
		Assertions.assertArrayEquals(positions.split(regex, limit), (String[]) split.convert(positions));
	}

}

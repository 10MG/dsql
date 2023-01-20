package cn.tenmg.dsql.model.filter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsql.config.model.filter.Blank;

/**
 * 空白字符串参数过滤器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class BlankTest extends AbstractParamsFilterTest<Blank> {

	@Override
	public void test(Blank filter) {
		Assertions.assertEquals("null,emptyString,blankSpace", filter.getParams());
		Assertions.assertTrue(filter.determine("null", null));
		Assertions.assertTrue(filter.determine("emptyString", ""));
		Assertions.assertTrue(filter.determine("blankSpace", " "));
		Assertions.assertFalse(filter.determine("others", null));
		Assertions.assertFalse(filter.determine("others", ""));
		Assertions.assertFalse(filter.determine("others", " "));
	}

}

package cn.tenmg.dsql.model.filter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsql.config.model.filter.Eq;

/**
 * 等值参数过滤器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class EqTest extends AbstractParamsFilterTest<Eq> {

	@Override
	public void test(Eq eq) {
		Assertions.assertEquals("eq,noteq", eq.getParams());
		Assertions.assertEquals("0", eq.getValue());
		Assertions.assertTrue(eq.determine("eq", "0"));
		Assertions.assertTrue(eq.determine("eq", 0));
		Assertions.assertFalse(eq.determine("eq", 0.1));
		Assertions.assertFalse(eq.determine("eq", "0.1"));
		Assertions.assertFalse(eq.determine("eq", "1"));
		Assertions.assertFalse(eq.determine("noteq", "1"));
		Assertions.assertFalse(eq.determine("others", "0"));
		Assertions.assertFalse(eq.determine("others", 0));
	}

}

package cn.tenmg.dsql.model.filter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsql.config.model.filter.Gte;

/**
 * 大于等于比较值参数过滤器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class GteTest extends AbstractParamsFilterTest<Gte> {

	@Override
	public void test(Gte gte) {
		Assertions.assertEquals("gte,notgte", gte.getParams());
		Assertions.assertEquals("0", gte.getValue());
		Assertions.assertTrue(gte.determine("gte", "0"));
		Assertions.assertTrue(gte.determine("gte", 0));
		Assertions.assertTrue(gte.determine("gte", "0.1"));
		Assertions.assertTrue(gte.determine("gte", 0.1));
		Assertions.assertFalse(gte.determine("notgte", -0.1));
		Assertions.assertFalse(gte.determine("notgte", "-0.1"));
		Assertions.assertFalse(gte.determine("others", "0"));
		Assertions.assertFalse(gte.determine("others", 0));
		Assertions.assertFalse(gte.determine("others", "0.1"));
		Assertions.assertFalse(gte.determine("others", 0.1));
	}

}

package cn.tenmg.dsql.model.filter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsql.config.model.filter.Lte;

/**
 * 小于等于比较值参数过滤器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class LteTest extends AbstractParamsFilterTest<Lte> {

	@Override
	public void test(Lte lte) {
		Assertions.assertEquals("lte,notlte", lte.getParams());
		Assertions.assertEquals("0", lte.getValue());
		Assertions.assertTrue(lte.determine("lte", "0"));
		Assertions.assertTrue(lte.determine("lte", 0));
		Assertions.assertTrue(lte.determine("lte", "-0.1"));
		Assertions.assertTrue(lte.determine("lte", -0.1));
		Assertions.assertFalse(lte.determine("notlte", 0.1));
		Assertions.assertFalse(lte.determine("notlte", "0.1"));
		Assertions.assertFalse(lte.determine("others", "0"));
		Assertions.assertFalse(lte.determine("others", 0));
		Assertions.assertFalse(lte.determine("others", "-0.1"));
		Assertions.assertFalse(lte.determine("others", -0.1));
	}

}

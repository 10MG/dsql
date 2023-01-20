package cn.tenmg.dsql.model.filter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsql.config.model.filter.Gt;

/**
 * 大于比较值参数过滤器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class GtTest extends AbstractParamsFilterTest<Gt> {

	@Override
	public void test(Gt gt) {
		Assertions.assertEquals("gt,notgt", gt.getParams());
		Assertions.assertEquals("0", gt.getValue());
		Assertions.assertTrue(gt.determine("gt", "0.1"));
		Assertions.assertTrue(gt.determine("gt", 0.1));
		Assertions.assertFalse(gt.determine("gt", "0"));
		Assertions.assertFalse(gt.determine("gt", 0));
		Assertions.assertFalse(gt.determine("notgt", -0.1));
		Assertions.assertFalse(gt.determine("notgt", "-0.1"));
		Assertions.assertFalse(gt.determine("others", "0.1"));
		Assertions.assertFalse(gt.determine("others", 0.1));
	}

}

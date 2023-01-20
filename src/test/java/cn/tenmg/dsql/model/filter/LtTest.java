package cn.tenmg.dsql.model.filter;

import org.junit.jupiter.api.Assertions;

import cn.tenmg.dsql.config.model.filter.Lt;

/**
 * 小于等于比较值参数过滤器测试
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 *
 */
public class LtTest extends AbstractParamsFilterTest<Lt> {

	@Override
	public void test(Lt lt) {
		Assertions.assertEquals("lt,notlt", lt.getParams());
		Assertions.assertEquals("0", lt.getValue());
		Assertions.assertTrue(lt.determine("lt", "-0.1"));
		Assertions.assertTrue(lt.determine("lt", -0.1));
		Assertions.assertFalse(lt.determine("lt", "0"));
		Assertions.assertFalse(lt.determine("lt", 0));
		Assertions.assertFalse(lt.determine("notlt", 0.1));
		Assertions.assertFalse(lt.determine("notlt", "0.1"));
		Assertions.assertFalse(lt.determine("others", "-0.1"));
		Assertions.assertFalse(lt.determine("others", -0.1));
	}

}

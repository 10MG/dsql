package cn.tenmg.dsql.filter;

/**
 * 小于等于比较值字符串参数过滤器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
public class LteParamsFilter extends AbstractParamsFilter {

	@Override
	boolean compare(String paramValue, String value) {
		return paramValue.compareTo(value) <= 0;
	}
}

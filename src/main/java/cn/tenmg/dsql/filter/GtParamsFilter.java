package cn.tenmg.dsql.filter;

/**
 * 大于比较值字符串参数过滤器
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public class GtParamsFilter extends AbstractParamsFilter {

	@Override
	boolean compare(String paramValue, String value) {
		return paramValue.compareTo(value) > 0;
	}
}

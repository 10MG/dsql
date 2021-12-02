package cn.tenmg.dsql.filter;

/**
 * 小于比较值字符串参数过滤器
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class LtParamsFilter extends AbstractParamsFilter {

	@Override
	boolean compare(String paramValue, String value) {
		return paramValue.compareTo(value) < 0;
	}

}

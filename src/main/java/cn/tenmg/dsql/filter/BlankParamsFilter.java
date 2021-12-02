package cn.tenmg.dsql.filter;

import java.util.Map;

import cn.tenmg.dsl.utils.StringUtils;
import cn.tenmg.dsql.ParamsFilter;
import cn.tenmg.dsql.config.model.filter.Blank;
import cn.tenmg.dsql.utils.ParamsFilterUtils;

/**
 * 空白字符串参数过滤器
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class BlankParamsFilter implements ParamsFilter<Blank> {

	@Override
	public void doFilter(Map<String, ?> params, Blank blank) {
		String paramsConfig = blank.getParams();
		if (StringUtils.isNotBlank(paramsConfig)) {
			String paramNames[] = paramsConfig.split(","), paramName;
			for (int j = 0; j < paramNames.length; j++) {
				paramName = paramNames[j].trim();
				if ("*".equals(paramName)) {
					ParamsFilterUtils.blankFilter(params);
					break;
				} else if (params.containsKey(paramName)) {
					ParamsFilterUtils.blankFilter(paramName, params);
				}
			}
		}
	}

}

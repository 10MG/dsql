package cn.tenmg.dsql.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tenmg.dsql.ParamConverter;
import cn.tenmg.dsql.config.model.Converter;
import cn.tenmg.dsql.config.model.converter.WrapString;
import cn.tenmg.dsql.exception.IllegalConfigException;
import cn.tenmg.dsql.utils.CollectionUtils;
import cn.tenmg.dsql.utils.StringUtils;

public class WrapStringParamConverter implements ParamConverter {

	@Override
	public void convert(Converter converter, Map<String, Object> params) {
		List<WrapString> wrapStrings = converter.getWrapStrings();
		if (CollectionUtils.isEmpty(wrapStrings)) {
			return;
		}
		for (Iterator<WrapString> it = wrapStrings.iterator(); it.hasNext();) {
			WrapString wrapString = it.next();
			String paramsConfig = wrapString.getParams();
			String formatter = wrapString.getFormatter();
			if (StringUtils.isNotBlank(paramsConfig)) {
				if (formatter.indexOf(WrapString.VALUE) < 0) {
					throw new IllegalConfigException(
							"wrap-string的formatter配置有误，必须包含：" + WrapString.VALUE + "，但实际是：" + formatter);
				}
				String paramsNames[] = paramsConfig.split(",");
				for (int i = 0; i < paramsNames.length; i++) {
					String paramName = paramsNames[i].trim();
					if ("*".equals(paramName)) {
						Set<String> set = params.keySet();
						for (Iterator<String> nit = set.iterator(); nit.hasNext();) {
							paramName = nit.next();
							Object v = params.get(paramName);
							String s = null;
							if (v != null) {
								s = formatter.replaceAll(WrapString.VALUE, v.toString());
							}
							params.put(paramName, s);
						}
						break;
					} else if (params.containsKey(paramName)) {
						Object v = params.get(paramName);
						String s = null;
						if (v != null) {
							s = formatter.replaceAll(WrapString.PARAM_REGEX, v.toString());
						}
						params.put(paramName, s);
					}
				}
			}
		}
	}

}

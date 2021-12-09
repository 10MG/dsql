package cn.tenmg.dsql.config.model.converter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 字符串参数包装转换器配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WrapString extends BasicConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 785627212732177000L;

	public static final String VALUE = "${value}";

	public static final String VALUE_REGEX = "\\$\\{value\\}";

	public static final String DEFAULT_FORMATTER = "%".concat(VALUE).concat("%");

	public WrapString() {
		super(DEFAULT_FORMATTER);
	}

}

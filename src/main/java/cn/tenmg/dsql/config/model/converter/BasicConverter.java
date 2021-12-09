package cn.tenmg.dsql.config.model.converter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsql.config.model.ParamsHandler;

/**
 * 基本参数转换器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BasicConverter implements ParamsHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8639639549856024104L;

	/**
	 * 参数列表，使用逗号分隔
	 */
	@XmlAttribute
	protected String params;

	/**
	 * 格式化模板
	 */
	@XmlAttribute
	protected String formatter;

	public BasicConverter() {
		super();
	}

	public BasicConverter(String formatter) {
		super();
		this.formatter = formatter;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
}

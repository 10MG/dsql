package cn.tenmg.dsql.config.model.filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsql.config.model.ParamsHandler;

/**
 * 可比较的参数过滤器配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CompareableFilter implements ParamsHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7853611960108517435L;

	/**
	 * 参数
	 */
	@XmlAttribute
	private String params;

	/**
	 * 比较值
	 */
	@XmlAttribute
	private String value;

	@Override
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

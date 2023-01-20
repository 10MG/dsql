package cn.tenmg.dsql.config.model.converter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsl.converter.ToStringParamsConverter;

/**
 * 将参数转换为 {@code java.lang.String} 类型的转换器
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ToString extends ToStringParamsConverter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5796557354465371183L;

	@Override
	@XmlAttribute
	public void setParams(String params) {
		super.setParams(params);
	}

	@Override
	@XmlAttribute
	public void setFormatter(String formatter) {
		super.setFormatter(formatter);
	}
}

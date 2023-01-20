package cn.tenmg.dsql.config.model.converter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsl.converter.ToNumberParamsConverter;

/**
 * 将参数转换为数字 {@code java.lang.Number} 类型的转换器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ToNumber extends ToNumberParamsConverter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6497358067539806959L;

	public ToNumber() {
		super.setFormatter("");
	}

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

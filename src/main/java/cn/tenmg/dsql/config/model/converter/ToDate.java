package cn.tenmg.dsql.config.model.converter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsl.converter.ToDateParamsConverter;

/**
 * 将参数转为 {@code java.util.Date} 类型的转换器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ToDate extends ToDateParamsConverter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -152984659922974934L;

	public ToDate() {
		super.setFormatter("yyyy-MM-dd");// 格式化模板默认值
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

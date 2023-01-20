package cn.tenmg.dsql.config.model.converter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsl.converter.WrapStringParamsConverter;

/**
 * 将字符串参数使用模板进行装饰的转换器。包装模板可使用 <code> ${value}</code> 占位符代表参数值
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class WrapString extends WrapStringParamsConverter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2705914476427714927L;

	public WrapString() {
		super.setFormatter("%${value}%");
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

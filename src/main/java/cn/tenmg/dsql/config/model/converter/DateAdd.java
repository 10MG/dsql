package cn.tenmg.dsql.config.model.converter;

import java.io.Serializable;

import cn.tenmg.dsl.converter.DateAddParamsConverter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * 对 {@code java.util.Date} 类型的参数做加法运算的转换器
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DateAdd extends DateAddParamsConverter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 374736591517621578L;

	@Override
	@XmlAttribute
	public void setParams(String params) {
		super.setParams(params);
	}

	@Override
	@XmlAttribute
	public void setAmount(int amount) {
		super.setAmount(amount);
	}

	@Override
	@XmlAttribute
	public void setUnit(String unit) {
		super.setUnit(unit);
	}
}

package cn.tenmg.dsql.config.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import cn.tenmg.dsql.config.model.converter.ToDate;
import cn.tenmg.dsql.config.model.converter.ToNumber;
import cn.tenmg.dsql.config.model.converter.WrapString;

/**
 * 参数类型转换器容器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Converter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5619546911375538778L;

	@XmlElements({ @XmlElement(name = "to-date", type = ToDate.class, namespace = Dsqls.NAMESPACE), // 参数日期类型转换器
			@XmlElement(name = "to-number", type = ToNumber.class, namespace = Dsqls.NAMESPACE), // 参数数字类型转换器
			@XmlElement(name = "wrap-string", type = WrapString.class, namespace = Dsqls.NAMESPACE) // 字符串参数包装转换器
	})
	private List<ParamsHandler> paramsConverters;

	public List<ParamsHandler> getParamsConverters() {
		return paramsConverters;
	}

	public void setParamsConverters(List<ParamsHandler> paramsConverters) {
		this.paramsConverters = paramsConverters;
	}

}

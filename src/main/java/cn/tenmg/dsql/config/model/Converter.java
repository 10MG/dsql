package cn.tenmg.dsql.config.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import cn.tenmg.dsl.ParamsConverter;
import cn.tenmg.dsql.config.model.converter.DateAdd;
import cn.tenmg.dsql.config.model.converter.Split;
import cn.tenmg.dsql.config.model.converter.ToDate;
import cn.tenmg.dsql.config.model.converter.ToNumber;
import cn.tenmg.dsql.config.model.converter.ToString;
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

	@XmlElements({ @XmlElement(name = "to-date", type = ToDate.class, namespace = Dsqls.NAMESPACE),
			@XmlElement(name = "date-add", type = DateAdd.class, namespace = Dsqls.NAMESPACE),
			@XmlElement(name = "to-number", type = ToNumber.class, namespace = Dsqls.NAMESPACE),
			@XmlElement(name = "to-string", type = ToString.class, namespace = Dsqls.NAMESPACE),
			@XmlElement(name = "wrap-string", type = WrapString.class, namespace = Dsqls.NAMESPACE),
			@XmlElement(name = "split", type = Split.class, namespace = Dsqls.NAMESPACE) })
	private List<ParamsConverter<?>> paramsConverters;

	public List<ParamsConverter<?>> getParamsConverters() {
		return paramsConverters;
	}

	public void setParamsConverters(List<ParamsConverter<?>> paramsConverters) {
		this.paramsConverters = paramsConverters;
	}

}

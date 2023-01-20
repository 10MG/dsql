package cn.tenmg.dsql.config.model.converter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import cn.tenmg.dsl.converter.SplitParamsConverter;
import cn.tenmg.dsql.config.model.Dsqls;

/**
 * 将类型为 {@code java.lang.String} 的非 {@code null} 参数值进行分割的转换器
 * 
 * @author June wjzhao@aliyun.com
 * 
 * @since 1.3.0
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Split extends SplitParamsConverter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -297414441862676021L;

	@Override
	@XmlAttribute
	public void setParams(String params) {
		super.setParams(params);
	}

	@Override
	@XmlAttribute
	public void setRegex(String regex) {
		super.setRegex(regex);
	}

	@XmlElement(name = "regex", namespace = Dsqls.NAMESPACE)
	public void setRegexByElementValue(String regex) {
		super.setRegex(regex);
	}

	@Override
	@XmlAttribute
	public void setLimit(Integer limit) {
		super.setLimit(limit);
	}
}

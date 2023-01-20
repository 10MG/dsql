package cn.tenmg.dsql.config.model.filter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsl.filter.EqParamsFilter;

/**
 * 等值参数过滤器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Eq extends EqParamsFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5639075852556456952L;

	@Override
	@XmlAttribute
	public void setParams(String params) {
		super.setParams(params);
	}

	@XmlAttribute
	public void setValue(String value) {
		super.setValue(value);
	}

}

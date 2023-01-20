package cn.tenmg.dsql.config.model.filter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsl.filter.BlankParamsFilter;

/**
 * 空白字符串参数过滤器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Blank extends BlankParamsFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8464802447170629011L;

	@XmlAttribute
	public void setParams(String params) {
		super.setParams(params);
	}

}

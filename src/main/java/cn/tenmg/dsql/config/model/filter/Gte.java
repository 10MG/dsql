package cn.tenmg.dsql.config.model.filter;

import java.io.Serializable;

import cn.tenmg.dsl.filter.GteParamsFilter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * 大于等于比较值参数过滤器
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Gte extends GteParamsFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 401125715179553093L;

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

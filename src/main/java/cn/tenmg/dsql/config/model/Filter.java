package cn.tenmg.dsql.config.model;

import java.io.Serializable;
import java.util.List;

import cn.tenmg.dsl.ParamsFilter;
import cn.tenmg.dsql.config.model.filter.Blank;
import cn.tenmg.dsql.config.model.filter.Eq;
import cn.tenmg.dsql.config.model.filter.Gt;
import cn.tenmg.dsql.config.model.filter.Gte;
import cn.tenmg.dsql.config.model.filter.Lt;
import cn.tenmg.dsql.config.model.filter.Lte;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;

/**
 * 参数过滤器容器配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422794588330208026L;

	@XmlElements({ @XmlElement(name = "blank", type = Blank.class, namespace = Dsqls.NAMESPACE), // 空白字符串参数移除器
			@XmlElement(name = "eq", type = Eq.class, namespace = Dsqls.NAMESPACE), // 等于指定值字符串参数移除器
			@XmlElement(name = "gt", type = Gt.class, namespace = Dsqls.NAMESPACE), // 大于指定值字符串参数移除器
			@XmlElement(name = "gte", type = Gte.class, namespace = Dsqls.NAMESPACE), // 大于等于指定值字符串参数移除器
			@XmlElement(name = "lt", type = Lt.class, namespace = Dsqls.NAMESPACE), // 小于指定值字符串参数移除器
			@XmlElement(name = "lte", type = Lte.class, namespace = Dsqls.NAMESPACE)// 小于等于指定值字符串参数处理器
	})
	private List<ParamsFilter> paramsFilters;

	public List<ParamsFilter> getParamsFilters() {
		return paramsFilters;
	}

	public void setParamsFilters(List<ParamsFilter> paramsFilters) {
		this.paramsFilters = paramsFilters;
	}

}

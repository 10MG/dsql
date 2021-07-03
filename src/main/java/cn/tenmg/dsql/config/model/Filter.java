package cn.tenmg.dsql.config.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import cn.tenmg.dsql.config.model.filter.Blank;
import cn.tenmg.dsql.config.model.filter.Eq;
import cn.tenmg.dsql.config.model.filter.Gt;
import cn.tenmg.dsql.config.model.filter.Gte;
import cn.tenmg.dsql.config.model.filter.Lt;
import cn.tenmg.dsql.config.model.filter.Lte;

/**
 * 参数过滤器容器配置模型
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
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
	private List<ParamsHandler> paramsFilters;

	public List<ParamsHandler> getParamsFilters() {
		return paramsFilters;
	}

	public void setParamsFilters(List<ParamsHandler> paramsFilters) {
		this.paramsFilters = paramsFilters;
	}

}

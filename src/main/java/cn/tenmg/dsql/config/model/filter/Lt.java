package cn.tenmg.dsql.config.model.filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import cn.tenmg.dsql.config.model.Dsqls;

/**
 * 小于比较值字符串参数过滤器配置模型
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
@XmlRootElement(namespace = Dsqls.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class Lt extends CompareableFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8474184483713601091L;
}

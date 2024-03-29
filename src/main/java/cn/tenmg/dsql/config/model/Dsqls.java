package cn.tenmg.dsql.config.model;

import java.io.Serializable;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * DSQL根配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlRootElement(namespace = Dsqls.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class Dsqls implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 927678194402355324L;

	/**
	 * 可扩展标记语言（XML）模式定义（Schemas Definition）文件的命名空间
	 */
	public static final String NAMESPACE = "http://www.10mg.cn/schema/dsql";

	/**
	 * 动态SQL配置列表
	 */
	@XmlElement(name = "dsql", namespace = NAMESPACE)
	private List<Dsql> dsqls;

	public List<Dsql> getDsqls() {
		return dsqls;
	}

	public void setDsqls(List<Dsql> dsqls) {
		this.dsqls = dsqls;
	}

}

package cn.tenmg.dsql.config.model;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * DSQL配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Dsql implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6101977085012651699L;

	/**
	 * SQL编号
	 */
	@XmlAttribute
	private String id;

	/**
	 * 参数过滤器配置
	 */
	@XmlElement(namespace = Dsqls.NAMESPACE)
	private Filter filter;

	/**
	 * 参数类型转换器配置
	 */
	@XmlElement(namespace = Dsqls.NAMESPACE)
	private Converter converter;

	/**
	 * 动态SQL配置
	 */
	@XmlElement(namespace = Dsqls.NAMESPACE)
	private String script;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Converter getConverter() {
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

}

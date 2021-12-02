package cn.tenmg.dsql.config.model.filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import cn.tenmg.dsql.config.model.ParamsHandler;

/**
 * 空白字符串参数过滤器配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Blank implements ParamsHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9061591158744475010L;

	/**
	 * 参数
	 */
	@XmlAttribute
	protected String params = ParamsHandler.ALL_PARAMS;

	@Override
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}

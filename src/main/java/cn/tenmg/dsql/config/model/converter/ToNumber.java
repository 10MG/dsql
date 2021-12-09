package cn.tenmg.dsql.config.model.converter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 参数数字类型转换器配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ToNumber extends BasicConverter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6252977370264893061L;

	public ToNumber() {
		super("");
	}

}

package cn.tenmg.dsql.config.model.converter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 参数日期类型转换器配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ToDate extends BasicConverter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7602665349013024784L;

	public ToDate() {
		super("yyyy-MM-dd");// 格式化模板默认值yyyy-MM-dd
	}

}

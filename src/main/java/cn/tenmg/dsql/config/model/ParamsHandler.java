package cn.tenmg.dsql.config.model;

import java.io.Serializable;

/**
 * 参数处理器配置模型
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
public interface ParamsHandler extends Serializable {
	
	public static final String ALL_PARAMS = "*";

	/**
	 * 获取参数名集
	 * 
	 * @return 返回参数名集
	 */
	String getParams();

}

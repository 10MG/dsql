package cn.tenmg.dsql;

import java.util.Map;

import cn.tenmg.dsql.config.model.ParamsHandler;

/**
 * 参数类型转换器
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public interface ParamsConverter<T extends ParamsHandler> {

	/**
	 * 转换参数
	 * 
	 * @param params
	 *            参数索引表
	 * @param config
	 *            配置模型
	 */
	void convert(Map<String, Object> params, T config);

}

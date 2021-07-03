package cn.tenmg.dsql;

import java.util.Map;

import cn.tenmg.dsql.config.model.ParamsHandler;

/**
 * 参数过滤器
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public interface ParamsFilter<T extends ParamsHandler> {
	/**
	 * 过滤参数
	 * 
	 * @param params
	 *            参数索引表
	 * @param config
	 *            过滤器配置对象
	 */
	void doFilter(Map<String, ?> params, T config);
}

package cn.tenmg.dsql;

/**
 * 参数处理器测试
 * 
 * @author June wjzhao@aliyun.com
 *
 * @param <T>
 *            测试的参数处理器类型
 * @since 1.3.0
 */
public interface ParamsHandlerTest<T> {

	/**
	 * 获取测试的参数处理器类型
	 * 
	 * @return 测试的参数处理器类型
	 */
	Class<T> getParamsHandlerType();

}

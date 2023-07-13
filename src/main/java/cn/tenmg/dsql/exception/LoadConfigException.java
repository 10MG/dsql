package cn.tenmg.dsql.exception;

/**
 * 加载配置异常
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
public class LoadConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4981941372991563546L;

	public LoadConfigException() {
		super();
	}

	public LoadConfigException(String massage) {
		super(massage);
	}

	public LoadConfigException(Throwable cause) {
		super(cause);
	}

	public LoadConfigException(String massage, Throwable cause) {
		super(massage, cause);
	}

}

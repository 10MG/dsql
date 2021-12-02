package cn.tenmg.dsql.exception;

/**
 * 转换异常
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class ConvertException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8684977502348790296L;

	public ConvertException() {
		super();
	}

	public ConvertException(String massage) {
		super(massage);
	}

	public ConvertException(Throwable cause) {
		super(cause);
	}

	public ConvertException(String massage, Throwable cause) {
		super(massage, cause);
	}

}

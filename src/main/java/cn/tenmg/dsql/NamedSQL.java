package cn.tenmg.dsql;

import java.util.Map;

import cn.tenmg.dsl.NamedScript;

/**
 * 使用命名参数的SQL对象模型
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class NamedSQL extends NamedScript {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3876821753500865601L;

	/**
	 * 编号
	 */
	private String id;

	public NamedSQL() {
		super();
	}

	public NamedSQL(NamedScript namedScript) {
		super(namedScript.getScript(), namedScript.getParams());
	}

	public NamedSQL(String id, NamedScript namedScript) {
		this(id, namedScript.getScript(), namedScript.getParams());
	}

	public NamedSQL(String script, Map<String, Object> params) {
		super(script, params);
	}

	public NamedSQL(String id, String script, Map<String, Object> params) {
		super(script, params);
		this.id = id;
	}

	public String getId() {
		return id;
	}

}

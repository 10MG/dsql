package cn.tenmg.dsql;

import java.util.Map;

import cn.tenmg.dsl.NamedScript;

/**
 * 使用命名参数的SQL对象模型
 * 
 * @author 赵伟均 wjzhao@aliyun.com
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

	public NamedSQL(String script, Map<String, Object> params) {
		super(script, params);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

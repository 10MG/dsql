package cn.tenmg.dsql.macro;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import cn.tenmg.dsql.Macro;

/**
 * if判断宏
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public class If extends AbstractMacro implements Macro {

	@Override
	Object excute(ScriptEngine scriptEngine, String code, Map<String, Object> context) throws ScriptException {
		Object result = scriptEngine.eval(code);
		if (result != null) {
			if (result instanceof Boolean) {
				context.put("if", result);
			} else {
				return null;
			}
		}
		return result;
	}

}

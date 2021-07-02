package cn.tenmg.dsql.config.model.filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import cn.tenmg.dsql.config.model.Dsqls;

/**
 * 大于给定值字符串参数移除器
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
@XmlRootElement(namespace = Dsqls.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class Gt extends Eq {

}

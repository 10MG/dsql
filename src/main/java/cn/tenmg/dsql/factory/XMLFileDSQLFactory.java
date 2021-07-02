package cn.tenmg.dsql.factory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.tenmg.dsql.config.loader.XMLConfigLoader;
import cn.tenmg.dsql.config.model.Dsql;
import cn.tenmg.dsql.exception.IllegalConfigException;
import cn.tenmg.dsql.utils.ClassUtils;
import cn.tenmg.dsql.utils.CollectionUtils;
import cn.tenmg.dsql.utils.FileUtils;

/**
 * 基于XML文件配置的动态结构化查询语言工厂
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public class XMLFileDSQLFactory extends AbstractDSQLFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8125151681490092061L;

	private static final Logger log = LogManager.getLogger(XMLFileDSQLFactory.class);

	private final Map<String, Dsql> dsqls = new HashMap<String, Dsql>();

	private String basePackages;

	private String suffix = ".dsql.xml";

	public String getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public XMLFileDSQLFactory(String basePackages) {
		this.basePackages = basePackages;
		init();
	}

	public XMLFileDSQLFactory(String basePackages, String suffix) {
		this.basePackages = basePackages;
		this.suffix = suffix;
		init();
	}

	@Override
	Map<String, Dsql> getDsqls() {
		return dsqls;
	}

	private void init() {
		if (basePackages == null) {
			log.warn("The parameter basePackages is null");
		} else {
			String basePackages[] = this.basePackages.split(","), fileName;
			for (int i = 0; i < basePackages.length; i++) {
				String basePackage = basePackages[i];
				List<String> fileNames;
				try {
					log.info("Scan package: ".concat(basePackage));
					fileNames = FileUtils.scanPackage(basePackage, suffix);
					if (!CollectionUtils.isEmpty(fileNames)) {
						for (int j = 0, size = fileNames.size(); j < size; j++) {
							fileName = fileNames.get(j);
							log.info("Start parsing " + fileName);
							List<Dsql> dsqls = XMLConfigLoader.getInstance()
									.load(ClassUtils.getDefaultClassLoader().getResourceAsStream(fileName));
							if (!CollectionUtils.isEmpty(dsqls)) {
								for (Iterator<Dsql> dit = dsqls.iterator(); dit.hasNext();) {
									Dsql dsql = dit.next();
									this.dsqls.put(dsql.getId(), dsql);
								}
							}
							log.info("Finished parsing " + fileName);
						}
					} else {
						log.warn("File with suffix: " + suffix + " not found in package " + basePackage);
					}
				} catch (IOException e) {
					throw new IllegalConfigException(
							"Failed to scan and load report configuration file from package: " + basePackage, e);
				}
			}
		}
	}

}

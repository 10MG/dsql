package cn.tenmg.dsql.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.tenmg.dsl.utils.ClassUtils;
import cn.tenmg.dsl.utils.CollectionUtils;
import cn.tenmg.dsl.utils.FileUtils;
import cn.tenmg.dsql.config.loader.XMLConfigLoader;
import cn.tenmg.dsql.config.model.Dsql;
import cn.tenmg.dsql.exception.LoadConfigException;

/**
 * 基于XML文件配置的动态结构化查询语言工厂
 * 
 * @author June wjzhao@aliyun.com
 *
 * @since 1.0.0
 */
public class XMLFileDSQLFactory extends AbstractDSQLFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8125151681490092061L;

	private static final Logger log = LoggerFactory.getLogger(XMLFileDSQLFactory.class);

	private final Map<String, Dsql> dsqls = new HashMap<String, Dsql>(512);

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
					if (CollectionUtils.isNotEmpty(fileNames)) {
						InputStream inStream = null;
						for (int j = 0, size = fileNames.size(); j < size; j++) {
							fileName = fileNames.get(j);
							log.info("Start parsing " + fileName);
							try {
								inStream = ClassUtils.getDefaultClassLoader().getResourceAsStream(fileName);
								List<Dsql> dsqls = XMLConfigLoader.getInstance().load(inStream);
								if (CollectionUtils.isNotEmpty(dsqls)) {
									for (Iterator<Dsql> dit = dsqls.iterator(); dit.hasNext();) {
										Dsql dsql = dit.next();
										this.dsqls.put(dsql.getId(), dsql);
									}
								}
							} finally {
								if (inStream != null) {
									inStream.close();
									inStream = null;
								}
							}
							log.info("Finished parsing " + fileName);
						}
					} else {
						log.warn("File with suffix: " + suffix + " not found in package " + basePackage);
					}
				} catch (IOException e) {
					throw new LoadConfigException(
							"Failed to scan and load report configuration file from package: " + basePackage, e);
				}
			}
		}
	}

}

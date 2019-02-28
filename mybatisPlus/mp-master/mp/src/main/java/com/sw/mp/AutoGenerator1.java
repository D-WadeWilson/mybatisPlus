package com.sw.mp;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;

/**
 * 鐢熸垚鏂囦欢
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
public class AutoGenerator1 {

    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator1.class);
    /**
     * 閰嶇疆淇℃伅
     */
    protected ConfigBuilder config;
    /**
     * 娉ㄥ叆閰嶇疆
     */
    protected InjectionConfig injectionConfig;
    /**
     * 鏁版嵁婧愰厤缃�
     */
    private DataSourceConfig dataSource;
    /**
     * 鏁版嵁搴撹〃閰嶇疆
     */
    private StrategyConfig strategy;
    /**
     * 鍖� 鐩稿叧閰嶇疆
     */
    private PackageConfig packageInfo;
    /**
     * 妯℃澘 鐩稿叧閰嶇疆
     */
    private TemplateConfig template;
    /**
     * 鍏ㄥ眬 鐩稿叧閰嶇疆
     */
    private GlobalConfig globalConfig;
    /**
     * 妯℃澘寮曟搸
     */
    private AbstractTemplateEngine templateEngine;

    /**
     * 鐢熸垚浠ｇ爜
     */
    public void execute() {
        logger.debug("==========================鍑嗗鐢熸垚鏂囦欢...==========================");
        // 鍒濆鍖栭厤缃�
        if (null == config) {
            config = new ConfigBuilder(packageInfo, dataSource, strategy, template, globalConfig);
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        if (null == templateEngine) {
            // 涓轰簡鍏煎涔嬪墠閫昏緫锛岄噰鐢� Velocity 寮曟搸 銆� 榛樿 銆�
            templateEngine = new VelocityTemplateEngine1();
        }
        // 妯℃澘寮曟搸鍒濆鍖栨墽琛屾枃浠惰緭鍑�
        templateEngine.init(this.pretreatmentConfigBuilder(config)).mkdirs().batchOutput().open();
        logger.debug("==========================鏂囦欢鐢熸垚瀹屾垚锛侊紒锛�==========================");
    }

    /**
     * <p>
     * 寮�鏀捐〃淇℃伅銆侀鐣欏瓙绫婚噸鍐�
     * </p>
     *
     * @param config 閰嶇疆淇℃伅
     * @return
     */
    protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
        return config.getTableInfoList();
    }

    /**
     * <p>
     * 棰勫鐞嗛厤缃�
     * </p>
     *
     * @param config 鎬婚厤缃俊鎭�
     * @return 瑙ｆ瀽鏁版嵁缁撴灉闆�
     */
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        /**
         * 娉ㄥ叆鑷畾涔夐厤缃�
         */
        if (null != injectionConfig) {
            injectionConfig.initMap();
            config.setInjectionConfig(injectionConfig);
        }
        /**
         * 琛ㄤ俊鎭垪琛�
         */
        List<TableInfo> tableList = this.getAllTableInfoList(config);
        for (TableInfo tableInfo : tableList) {
            /* ---------- 娣诲姞瀵煎叆鍖� ---------- */
            if (config.getGlobalConfig().isActiveRecord()) {
                // 寮�鍚� ActiveRecord 妯″紡
                tableInfo.setImportPackages(Model.class.getCanonicalName());
            }
            if (tableInfo.isConvert()) {
                // 琛ㄦ敞瑙�
                tableInfo.setImportPackages(TableName.class.getCanonicalName());
            }
            if (config.getStrategyConfig().getLogicDeleteFieldName() != null && tableInfo.isLogicDelete(config.getStrategyConfig().getLogicDeleteFieldName())) {
                // 閫昏緫鍒犻櫎娉ㄨВ
                tableInfo.setImportPackages(TableLogic.class.getCanonicalName());
            }
            if (StringUtils.isNotEmpty(config.getStrategyConfig().getVersionFieldName())) {
                // 涔愯閿佹敞瑙�
                tableInfo.setImportPackages(Version.class.getCanonicalName());
            }
            if (StringUtils.isNotEmpty(config.getSuperEntityClass())) {
                // 鐖跺疄浣�
                tableInfo.setImportPackages(config.getSuperEntityClass());
            } else {
                tableInfo.setImportPackages(Serializable.class.getCanonicalName());
            }
            // Boolean绫诲瀷is鍓嶇紑澶勭悊
            if (config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix()) {
                tableInfo.getFields().stream().filter(field -> "boolean".equalsIgnoreCase(field.getPropertyType()))
                    .filter(field -> field.getPropertyName().startsWith("is"))
                    .forEach(field -> field.setPropertyName(config.getStrategyConfig(),
                        StringUtils.removePrefixAfterPrefixToLower(field.getPropertyName(), 2)));
            }
        }
        return config.setTableInfoList(tableList);
    }

    // ==================================  鐩稿叧閰嶇疆  ==================================

    public DataSourceConfig getDataSource() {
        return dataSource;
    }

    public AutoGenerator1 setDataSource(DataSourceConfig dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public StrategyConfig getStrategy() {
        return strategy;
    }

    public AutoGenerator1 setStrategy(StrategyConfig strategy) {
        this.strategy = strategy;
        return this;
    }

    public PackageConfig getPackageInfo() {
        return packageInfo;
    }

    public AutoGenerator1 setPackageInfo(PackageConfig packageInfo) {
        this.packageInfo = packageInfo;
        return this;
    }

    public TemplateConfig getTemplate() {
        return template;
    }

    public AutoGenerator1 setTemplate(TemplateConfig template) {
        this.template = template;
        return this;
    }

    public ConfigBuilder getConfig() {
        return config;
    }

    public AutoGenerator1 setConfig(ConfigBuilder config) {
        this.config = config;
        return this;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public AutoGenerator1 setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    public InjectionConfig getCfg() {
        return injectionConfig;
    }

    public AutoGenerator1 setCfg(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }

    public AbstractTemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public AutoGenerator1 setTemplateEngine(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }
   }
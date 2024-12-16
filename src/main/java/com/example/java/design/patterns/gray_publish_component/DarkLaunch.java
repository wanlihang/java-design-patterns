package com.example.java.design.patterns.gray_publish_component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.dts.shade.io.netty.util.concurrent.DefaultThreadFactory;

import com.example.java.design.patterns.gray_publish_component.DarkRuleConfig.DarkFeatureConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/**
 * @author mojue
 */
public class DarkLaunch {
    private static final Logger log = LoggerFactory.getLogger(DarkLaunch.class);
    private static final int DEFAULT_RULE_UPDATE_TIME_INTERVAL = 60; // in seconds
    private DarkRule rule = new DarkRule();
    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = null;

    public DarkLaunch(int ruleUpdateTimeInterval) {
        loadRule();
        this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
            new DefaultThreadFactory("connection-pool"));
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::loadRule, ruleUpdateTimeInterval, ruleUpdateTimeInterval,
            TimeUnit.SECONDS);
    }

    public DarkLaunch() {
        this(DEFAULT_RULE_UPDATE_TIME_INTERVAL);
    }

    private void loadRule() {
        InputStream in = null;
        DarkRuleConfig ruleConfig = null;
        try {
            in = this.getClass().getResourceAsStream("/dark-rule.yaml");
            if (in != null) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(in, DarkRuleConfig.class);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("close file error:{}", e);
                }
            }
        }

        if (ruleConfig == null) {
            throw new RuntimeException("Can not load dark rule.");
        }

        // 修改：单独更新从配置文件中得到的灰度规则，不覆盖编程实现的灰度规则
        Map<String, IDarkFeature> darkFeatures = new HashMap<>();
        List<DarkFeatureConfig> darkFeatureConfigs = ruleConfig.getFeatures();
        for (DarkRuleConfig.DarkFeatureConfig darkFeatureConfig : darkFeatureConfigs) {
            darkFeatures.put(darkFeatureConfig.getKey(), new DarkFeature(darkFeatureConfig));
        }
        this.rule.setDarkFeatures(darkFeatures);
    }

    // 新增：添加编程实现的灰度规则的接口
    public void addProgrammedDarkFeature(String featureKey, IDarkFeature darkFeature) {
        this.rule.addProgrammedDarkFeature(featureKey, darkFeature);
    }

    public IDarkFeature getDarkFeature(String featureKey) {
        IDarkFeature darkFeature = this.rule.getDarkFeature(featureKey);
        return darkFeature;
    }
}

package com.example.java.design.patterns.gray_publish_component;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mojue
 */
@Getter
public class DarkRuleConfig {
    private List<DarkFeatureConfig> features;

    public void setFeatures(List<DarkFeatureConfig> features) {
        this.features = features;
    }

    @Getter
    @Setter
    public static class DarkFeatureConfig {
        private String key;
        private boolean enabled;
        private String rule;

        public boolean getEnabled() {
            return enabled;
        }
        // 省略getter、setter方法
    }
}

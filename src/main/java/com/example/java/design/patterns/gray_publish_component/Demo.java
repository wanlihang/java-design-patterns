package com.example.java.design.patterns.gray_publish_component;

import com.example.java.design.patterns.gray_publish_component.DarkLaunch;
import com.example.java.design.patterns.gray_publish_component.IDarkFeature;

/**
 * @author mojue
 */
public class Demo {
    public static void main(String[] args) {
        DarkLaunch darkLaunch = new DarkLaunch(); // 默认加载classpath下dark-rule.yaml文件中的灰度规则
        darkLaunch.addProgrammedDarkFeature("user_promotion", new UserPromotionDarkRule()); // 添加编程实现的灰度规则
        IDarkFeature darkFeature = darkLaunch.getDarkFeature("user_promotion");
        System.out.println(darkFeature.enabled());
        System.out.println(darkFeature.dark(893));
    }
}
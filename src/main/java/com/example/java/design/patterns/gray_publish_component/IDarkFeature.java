package com.example.java.design.patterns.gray_publish_component;

public interface IDarkFeature {
    boolean enabled();

    boolean dark(long darkTarget);

    boolean dark(String darkTarget);
}

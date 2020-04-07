package com.babbaj.options.v1_15.tweaker;

import io.github.impactdevelopment.simpletweaker.SimpleTweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class MemeTweaker extends SimpleTweaker {

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        super.injectIntoClassLoader(classLoader);
        classLoader.addTransformerExclusion("com.babbaj.options.v1_15.util.");
        classLoader.registerTransformer("com.babbaj.options.v1_15.tweaker.LaunchWrapperGameSettingTransformerWrapper");
    }
}

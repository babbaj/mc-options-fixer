package com.babbaj.options.v1_15.tweaker;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.util.List;

public class Tweaker implements ITweaker {
    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) { }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.addTransformerExclusion("com.babbaj.options.v1_15.util.");
        classLoader.registerTransformer("com.babbaj.options.v1_15.tweaker.LaunchWrapperGameSettingTransformerWrapper");
    }

    @Override
    public String getLaunchTarget() {
        return null;
    }

    @Override
    public String[] getLaunchArguments() {
        return null;
    }
}

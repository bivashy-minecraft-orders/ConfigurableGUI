package com.bivashy.bukkit.library.gui.config.impl.click;

import com.bivashy.bukkit.library.gui.config.click.ClickSettings;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public class ClickSettingsConfig implements ClickSettings {

    @Setting(nodeFromParent = true)
    private ConfigurationNode root;
    private String open;
    private String callback;
    private List<String> executeCommands;

    ClickSettingsConfig() {
    }

    public ClickSettingsConfig(ConfigurationNode root,
                               String open,
                               String callback,
                               List<String> executeCommands) {
        this.root = root;
        this.open = open;
        this.callback = callback;
        this.executeCommands = executeCommands;
    }

    @Override
    public String open() {
        return open;
    }

    @Override
    public String callback() {
        return callback;
    }

    @Override
    public List<String> executeCommands() {
        return executeCommands;
    }

    protected boolean isNull() {
        return root.virtual() || root.empty();
    }

}

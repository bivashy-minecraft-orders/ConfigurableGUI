package com.bivashy.bukkit.library.gui.config.impl.click;

import com.bivashy.bukkit.library.gui.config.click.ClickSettings;
import com.bivashy.bukkit.library.gui.config.click.RootClickSettings;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;
import java.util.Optional;

@ConfigSerializable
public class RootClickSettingsConfig extends ClickSettingsConfig implements RootClickSettings {

    private ClickSettingsConfig left;
    private ClickSettingsConfig middle;
    private ClickSettingsConfig right;

    RootClickSettingsConfig() {
    }

    public RootClickSettingsConfig(ConfigurationNode root,
                                   String open,
                                   String callback,
                                   List<String> executeCommands,
                                   ClickSettingsConfig left,
                                   ClickSettingsConfig middle,
                                   ClickSettingsConfig right) {
        super(root, open, callback, executeCommands);
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    @Override
    public Optional<ClickSettings> left() {
        return Optional.ofNullable(left).map(left -> left.isNull() ? null : left);
    }

    @Override
    public Optional<ClickSettings> middle() {
        return Optional.ofNullable(middle).map(middle -> middle.isNull() ? null : middle);
    }

    @Override
    public Optional<ClickSettings> right() {
        return Optional.ofNullable(right).map(right -> right.isNull() ? null : right);
    }

}

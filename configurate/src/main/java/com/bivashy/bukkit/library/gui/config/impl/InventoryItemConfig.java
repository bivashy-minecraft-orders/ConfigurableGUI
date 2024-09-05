package com.bivashy.bukkit.library.gui.config.impl;

import com.bivashy.bukkit.library.gui.config.InventoryItem;
import com.bivashy.bukkit.library.gui.config.click.RootClickSettings;
import com.bivashy.bukkit.library.gui.config.impl.click.RootClickSettingsConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;

import java.util.List;
import java.util.Optional;

@ConfigSerializable
public class InventoryItemConfig implements InventoryItem {

    @Required
    private String material;
    private String name;
    private List<String> lore;
    private RootClickSettingsConfig onClick;

    InventoryItemConfig() {
    }

    public InventoryItemConfig(String material,
                               String name,
                               List<String> lore,
                               RootClickSettingsConfig onClick) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.onClick = onClick;
    }

    @Override
    public String material() {
        return material;
    }

    @Override
    public Optional<String> name() {
        return Optional.ofNullable(name);
    }

    @Override
    public Optional<List<String>> lore() {
        return Optional.ofNullable(lore);
    }

    @Override
    public RootClickSettings clickSettings() {
        return onClick;
    }

}

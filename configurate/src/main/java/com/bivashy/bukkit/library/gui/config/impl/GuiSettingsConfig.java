package com.bivashy.bukkit.library.gui.config.impl;

import com.bivashy.bukkit.library.gui.config.GuiSettings;
import com.bivashy.bukkit.library.gui.config.InventorySettings;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class GuiSettingsConfig implements GuiSettings {

    private String title;
    private InventorySettingsConfig inventory;

    GuiSettingsConfig() {
    }

    public GuiSettingsConfig(String title, InventorySettingsConfig inventory) {
        this.title = title;
        this.inventory = inventory;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public InventorySettings inventory() {
        return inventory;
    }

}

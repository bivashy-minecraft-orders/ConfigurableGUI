package com.bivashy.bukkit.library.gui.config;

import com.bivashy.bukkit.library.gui.config.click.RootClickSettings;

import java.util.List;
import java.util.Optional;

public interface InventoryItem {
    String material();

    Optional<String> name();

    Optional<List<String>> lore();

    RootClickSettings onClick();
}

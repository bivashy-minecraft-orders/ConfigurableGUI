package com.bivashy.bukkit.library.gui.config;

import java.util.Map;

public interface InventorySettings {

    Character pageItem();

    String pageResolver();

    String structure();

    Map<Character, String> mapping();

    Map<String, InventoryItem> items();

}

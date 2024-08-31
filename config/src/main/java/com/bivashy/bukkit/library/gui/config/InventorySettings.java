package com.bivashy.bukkit.library.gui.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InventorySettings {

    Character pageItem();

    String pageResolver();

    Optional<InventoryItem> pageIngredientItem();

    Map<Character, InventoryItem> mappingIngredients();

    List<String> structureLines();

}

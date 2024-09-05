package com.bivashy.bukkit.library.gui.config.impl;

import com.bivashy.bukkit.library.gui.config.InventoryItem;
import com.bivashy.bukkit.library.gui.config.InventorySettings;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

@ConfigSerializable
public class InventorySettingsConfig implements InventorySettings {

    private Character pageItem;
    private String pageResolver;
    private String structure;
    private Map<Character, String> mapping;
    private Map<String, InventoryItemConfig> items;

    InventorySettingsConfig() {
    }

    public InventorySettingsConfig(Character pageItem,
                                   String pageResolver,
                                   String structure,
                                   Map<Character, String> mapping,
                                   Map<String, InventoryItemConfig> items) {
        this.pageItem = pageItem;
        this.pageResolver = pageResolver;
        this.structure = structure;
        this.mapping = mapping;
        this.items = items;
    }

    @Override
    public Character pageItem() {
        return pageItem;
    }

    @Override
    public String pageResolver() {
        return pageResolver;
    }

    @Override
    public Optional<InventoryItem> pageIngredientItem() {
        return ingredient(pageItem);
    }

    @Override
    public Map<Character, InventoryItem> mappingIngredients() {
        return mapping.entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), findOrThrow(entry.getValue())))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    @Override
    public List<String> structureLines() {
        return Arrays.stream(structure.split("\\r?\\n")).collect(Collectors.toList());
    }

    private InventoryItem findOrThrow(String key) {
        return item(key).orElseThrow(() -> new IllegalStateException("Item '" + key + "' not found"));
    }

    private Optional<InventoryItem> item(String key) {
        return Optional.ofNullable(items.get(key));
    }

    private Optional<InventoryItem> ingredient(Character mappingKey) {
        Optional<String> keyOptional = Optional.ofNullable(mapping.get(mappingKey));
        return keyOptional.flatMap(this::item);
    }

}

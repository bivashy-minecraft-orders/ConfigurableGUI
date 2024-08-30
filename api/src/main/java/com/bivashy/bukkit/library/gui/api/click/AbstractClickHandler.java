package com.bivashy.bukkit.library.gui.api.click;

import com.bivashy.bukkit.library.gui.api.context.InventoryContext;
import com.bivashy.bukkit.library.gui.config.InventoryItem;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class AbstractClickHandler implements ClickHandler {

    private final InventoryContext context;

    public AbstractClickHandler(InventoryContext context) {
        this.context = context;
    }

    protected InventoryContext context() {
        return context;
    }

    protected Player player() {
        return requireArg(PLAYER);
    }

    protected InventoryItem itemConfig() {
        return requireArg(ITEM_CONFIG);
    }

    public <T> T requireArg(String key) {
        return context.require(key);
    }

    public <T> Optional<T> arg(String key) {
        return context.get(key);
    }

}

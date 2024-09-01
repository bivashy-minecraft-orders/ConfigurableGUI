package com.bivashy.bukkit.library.gui.invui.click;

import com.bivashy.bukkit.library.gui.api.InventoryGUI;
import com.bivashy.bukkit.library.gui.api.InventoryGUIFactory;
import com.bivashy.bukkit.library.gui.api.click.ClickHandler;
import com.bivashy.bukkit.library.gui.api.click.ClickHandlerFactory;
import com.bivashy.bukkit.library.gui.api.click.ClickHandlerRegistry;
import com.bivashy.bukkit.library.gui.api.context.InventoryContext;
import com.bivashy.bukkit.library.gui.api.transformer.CommandInterpreter;
import com.bivashy.bukkit.library.gui.config.InventoryItem;
import com.bivashy.bukkit.library.gui.config.click.ClickSettings;
import com.bivashy.bukkit.library.gui.config.click.RootClickSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.xenondevs.invui.item.Click;

import static com.bivashy.bukkit.library.gui.api.click.ClickHandler.ITEM_CONFIG;
import static com.bivashy.bukkit.library.gui.api.click.ClickHandler.PLAYER;

public class RootClickHandlerFactory implements ClickHandlerFactory {

    private final ClickHandlerRegistry clickRegistry;
    private final InventoryGUIFactory inventoryGUIFactory;
    private final CommandInterpreter commandInterpreter;

    public RootClickHandlerFactory(ClickHandlerRegistry clickRegistry, InventoryGUIFactory inventoryGUIFactory, CommandInterpreter commandInterpreter) {
        this.clickRegistry = clickRegistry;
        this.inventoryGUIFactory = inventoryGUIFactory;
        this.commandInterpreter = commandInterpreter;
    }

    @Override
    public ClickHandler create(InventoryContext context) {
        InventoryItem itemConfig = context.require(ITEM_CONFIG);
        RootClickSettings settings = itemConfig.clickSettings();
        Click click = context.require("click");
        ClickHandler clickHandler = createHandler(context, settings);
        if (click.getClickType().isLeftClick())
            clickHandler = extendHandler(clickHandler, context, settings.left().orElse(null));
        if (click.getClickType().isRightClick())
            clickHandler = extendHandler(clickHandler, context, settings.right().orElse(null));
        if (click.getClickType() == ClickType.MIDDLE)
            clickHandler = extendHandler(clickHandler, context, settings.middle().orElse(null));

        return clickHandler;
    }

    private ClickHandler extendHandler(ClickHandler clickHandler, InventoryContext context, ClickSettings settings) {
        if (settings == null)
            return clickHandler;
        return clickHandler.push(createHandler(context, settings));
    }

    private ClickHandler createHandler(InventoryContext context, ClickSettings settings) {
        ClickHandler clickHandler = ClickHandler.empty();
        Player clicker = context.require(PLAYER);

        if (settings.callback() != null) {
            ClickHandler callbackClickHandler = clickRegistry
                    .findAndCreate(settings.callback(), context)
                    .orElseThrow(() -> new IllegalStateException("Cannot find ClickHandler with name '" + settings.callback() + "'"));
            clickHandler = clickHandler.push(callbackClickHandler);
        }

        if (settings.open() != null) {
            clickHandler = clickHandler.push(() -> {
                InventoryGUI gui = inventoryGUIFactory
                        .create(settings.open(), clicker, context)
                        .orElseThrow(() -> new IllegalStateException("Cannot find inventory with name '" + settings.open() + "'"));
                gui.open();
            });
        }

        if (settings.executeCommands() != null)
            clickHandler = clickHandler.push(() -> settings.executeCommands().forEach(executeCommand -> commandInterpreter.execute(clicker, executeCommand)));
        return clickHandler;
    }

}

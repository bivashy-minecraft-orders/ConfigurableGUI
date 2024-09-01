package com.bivashy.bukkit.library.gui.invui;

import com.bivashy.bukkit.library.gui.api.InventoryGUI;
import com.bivashy.bukkit.library.gui.api.context.InventoryContext;
import com.bivashy.bukkit.library.gui.api.transformer.InventoryComponentSerializer;
import com.bivashy.bukkit.library.gui.config.GuiSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

public class BaseInventoryGUI implements InventoryGUI {

    private final Player player;
    private final GuiSettings settings;
    private final Window window;

    public BaseInventoryGUI(Player player, GuiSettings settings, Gui gui, InventoryContext context, InventoryComponentSerializer componentSerializer) {
        this.player = player;
        this.settings = settings;

        Component title = componentSerializer.serialize(settings.title());
        this.window = Window.single()
                .setGui(gui)
                .setTitle(new AdventureComponentWrapper(title))
                .setViewer(player)
                .build();
        context.extend("window", window);
    }

    @Override
    public void open() {
        window.open();
    }

    public Player getPlayer() {
        return player;
    }

    public GuiSettings getSettings() {
        return settings;
    }

    public Window getWindow() {
        return window;
    }

}

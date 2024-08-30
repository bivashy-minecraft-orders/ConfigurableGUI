package com.bivashy.bukkit.library.gui.api;

import com.bivashy.bukkit.library.gui.api.context.InventoryContext;
import org.bukkit.entity.Player;

import java.util.Optional;

public interface InventoryGUIFactory {

    Optional<InventoryGUI> create(String guiName, Player player, InventoryContext context);

}

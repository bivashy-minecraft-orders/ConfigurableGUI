package com.bivashy.bukkit.library.gui.api.paged;

import com.bivashy.bukkit.library.gui.api.context.InventoryContext;
import org.bukkit.entity.Player;

import java.util.List;

public interface PageResolver<T> {

    List<T> resolve(Player player, InventoryContext context);

}

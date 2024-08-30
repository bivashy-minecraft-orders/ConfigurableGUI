package com.bivashy.bukkit.library.gui.api.click;

import com.bivashy.bukkit.library.gui.api.context.InventoryContext;

public interface ClickHandlerFactory {

    ClickHandler create(InventoryContext context);

}

package com.bivashy.bukkit.library.gui.api.transformer;

import com.bivashy.bukkit.library.gui.api.context.InventoryContext;

public interface ConfigurationTransformer {

    <T> T transform(InventoryContext context, T model);

}

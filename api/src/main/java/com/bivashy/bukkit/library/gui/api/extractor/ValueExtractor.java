package com.bivashy.bukkit.library.gui.api.extractor;

import com.bivashy.bukkit.library.gui.api.context.InventoryContext;

public interface ValueExtractor {

    String extract(InventoryContext context, String text, Object model);

}

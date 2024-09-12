package com.bivashy.bukkit.library.gui.api.transformer;

import net.kyori.adventure.text.Component;

public interface InventoryComponentSerializer {

    Component serialize(String rawComponent);

}

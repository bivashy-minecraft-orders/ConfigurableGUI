package com.bivashy.bukkit.library.gui.api.click;

public interface ClickHandler {
    String PLAYER = "player";
    String ITEM_CONFIG = "itemConfig";

    static ClickHandler empty() {
        return () -> {
        };
    }

    default ClickHandler prepend(ClickHandler other) {
        return other.push(this);
    }

    default ClickHandler push(ClickHandler other) {
        return () -> {
            this.handle();
            other.handle();
        };
    }

    void handle();

}

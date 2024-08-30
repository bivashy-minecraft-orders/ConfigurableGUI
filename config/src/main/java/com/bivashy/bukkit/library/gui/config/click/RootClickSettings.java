package com.bivashy.bukkit.library.gui.config.click;

import java.util.Optional;

public interface RootClickSettings extends ClickSettings {

    Optional<ClickSettings> left();

    Optional<ClickSettings> middle();

    Optional<ClickSettings> right();

}

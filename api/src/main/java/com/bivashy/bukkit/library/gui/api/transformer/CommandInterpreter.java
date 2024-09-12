package com.bivashy.bukkit.library.gui.api.transformer;

import org.bukkit.entity.Player;

public interface CommandInterpreter {

    void execute(Player target, String command);

}

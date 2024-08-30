package com.bivashy.bukkit.library.gui.api.context;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class InventoryContextRegistry {

    private final Map<UUID, InventoryContext> contexts = new HashMap<>();

    public Optional<InventoryContext> contextOf(Player player) {
        return Optional.ofNullable(contexts.get(player.getUniqueId()));
    }

    public void setContext(Player player, InventoryContext context) {
        requireNonNull(player);
        requireNonNull(context);
        context.extend("player", player);
        contexts.put(player.getUniqueId(), context);
    }

    public void update(InventoryContext context) {
        requireNonNull(context);
        Player player = context.require("player");
        contexts.put(player.getUniqueId(), context);
    }

}

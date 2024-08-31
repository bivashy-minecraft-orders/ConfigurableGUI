package com.bivashy.bukkit.library.gui.api.click;

import com.bivashy.bukkit.library.gui.api.context.InventoryContext;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ClickHandlerRegistry {

    private final Map<String, ClickHandlerFactory> factories = new HashMap<>();

    public void register(String key, ClickHandlerFactory factory) {
        requireNonNull(key);
        requireNonNull(factory);
        factories.put(key, factory);
    }

    public void register(String key, ClickHandlerFacade facade) {
        requireNonNull(key);
        requireNonNull(facade);
        factories.put(key, context -> new AbstractClickHandler(context) {
            @Override
            public void handle() {
                facade.handle(context(), player(), this);
            }
        });
    }

    public Optional<ClickHandler> findAndCreate(String key, InventoryContext context) {
        Optional<ClickHandlerFactory> factoryOptional = Optional.ofNullable(factories.get(key));
        return factoryOptional.map(factory -> factory.create(context));
    }

    public ClickHandler createHandler(InventoryContext context) {
        return findAndCreate("root", context).orElseThrow(() -> new IllegalStateException("Cannot find root factory for ClickHandler"));
    }

    public interface ClickHandlerFacade {

        void handle(InventoryContext context, Player player, AbstractClickHandler handler);

    }

}

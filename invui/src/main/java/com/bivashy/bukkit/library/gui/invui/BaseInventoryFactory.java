package com.bivashy.bukkit.library.gui.invui;

import com.bivashy.bukkit.library.gui.api.InventoryGUI;
import com.bivashy.bukkit.library.gui.api.InventoryGUIFactory;
import com.bivashy.bukkit.library.gui.api.click.ClickHandlerRegistry;
import com.bivashy.bukkit.library.gui.api.context.InventoryContext;
import com.bivashy.bukkit.library.gui.api.context.InventoryContextRegistry;
import com.bivashy.bukkit.library.gui.api.paged.PageResolver;
import com.bivashy.bukkit.library.gui.api.paged.PageResolverRegistry;
import com.bivashy.bukkit.library.gui.api.transformer.ConfigurationTransformer;
import com.bivashy.bukkit.library.gui.api.transformer.InventoryComponentSerializer;
import com.bivashy.bukkit.library.gui.config.GuiSettings;
import com.bivashy.bukkit.library.gui.config.InventoryItem;
import com.bivashy.bukkit.library.gui.config.InventorySettings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xenondevs.inventoryaccess.InventoryAccess;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.Gui.Builder;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.gui.structure.Structure;
import xyz.xenondevs.invui.item.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.bivashy.bukkit.library.gui.api.click.ClickHandler.ITEM_CONFIG;
import static com.bivashy.bukkit.library.gui.api.click.ClickHandler.PLAYER;

public class BaseInventoryFactory implements InventoryGUIFactory {

    private final Map<String, GuiSettings> guis;
    private final InventoryContextRegistry contextRegistry;
    private final PageResolverRegistry pageResolverRegistry;
    private final ClickHandlerRegistry clickHandlerRegistry;
    private final InventoryComponentSerializer componentSerializer;
    private final ConfigurationTransformer transformer;

    public BaseInventoryFactory(Map<String, GuiSettings> guis,
                                InventoryContextRegistry contextRegistry,
                                PageResolverRegistry pageResolverRegistry,
                                ClickHandlerRegistry clickHandlerRegistry,
                                InventoryComponentSerializer componentSerializer,
                                ConfigurationTransformer transformer) {
        this.guis = guis;
        this.contextRegistry = contextRegistry;
        this.pageResolverRegistry = pageResolverRegistry;
        this.clickHandlerRegistry = clickHandlerRegistry;
        this.componentSerializer = componentSerializer;
        this.transformer = transformer;
    }

    @Override
    public Optional<InventoryGUI> create(String guiName, Player player, InventoryContext context) {
        Optional<GuiSettings> guiSettings = Optional.ofNullable(guis.get(guiName));
        return guiSettings.map(settings -> {
            contextRegistry.setContext(player, context);
            settings = transformer.transform(context, settings);

            InventorySettings inventorySettings = settings.inventory();
            context.extend("inventorySettings", inventorySettings);

            Structure structure = new Structure(inventorySettings.structureLines().toArray(new String[0]));
            Optional<PageResolver<?>> pageResolverOptional = pageResolverRegistry.pageResolver(inventorySettings.pageResolver());
            if (pageResolverOptional.isEmpty()) {
                Builder.Normal builder = Gui.normal().setStructure(structure);

                inventorySettings.mappingIngredients().forEach((key, itemConfig) -> builder.addIngredient(key, create(player, itemConfig, context, null)));

                Gui gui = builder.build();
                context.extend("gui", gui);
                return new BaseInventoryGUI(player, settings, gui, context, componentSerializer);
            } else {
                InventoryItem pageItem = inventorySettings.pageIngredientItem().orElseThrow(() -> new IllegalStateException("Cannot find page item"));
                PagedGui.Builder<Item> builder = PagedGui.items().setStructure(structure);

                inventorySettings.mappingIngredients().forEach((key, itemConfig) -> {
                    if (key.equals(inventorySettings.pageItem())) {
                        builder.addIngredient(key, Markers.CONTENT_LIST_SLOT_HORIZONTAL);
                        return;
                    }
                    builder.addIngredient(key, create(player, itemConfig, context, null));
                });

                PageResolver<?> pageResolver = pageResolverOptional.get();
                List<?> pageItems = pageResolver.resolve(player, context);
                pageItems.forEach(item -> builder.addContent(create(player, pageItem, context, item)));

                PagedGui<Item> pagedGui = builder.build();
                context.extend("gui", pagedGui);
                return new BaseInventoryGUI(player, settings, pagedGui, context, componentSerializer);
            }
        });
    }

    private Item create(Player player, InventoryItem item, InventoryContext context, Object model) {
        InventoryContext cloneContext = context.copy();
        cloneContext.extend("model", model);
        return createItem(player, () -> transformer.transform(cloneContext, item), context);
    }

    private Item createItem(Player player, Supplier<InventoryItem> itemSupplier, InventoryContext context) {
        SimpleItemWrapper simpleItem = new SimpleItemWrapper(ignored -> {
            InventoryItem item = itemSupplier.get();
            ItemStack itemStack = new ItemStack(Material.valueOf(item.material()));
            ItemMeta meta = itemStack.getItemMeta();
            if (meta != null) {
                item.name().ifPresent(name -> InventoryAccess.getItemUtils().setDisplayName(meta, new AdventureComponentWrapper(componentSerializer.serialize(name))));
                item.lore().ifPresent(lore -> InventoryAccess.getItemUtils().setLore(meta, lore.stream()
                        .map(componentSerializer::serialize)
                        .map(AdventureComponentWrapper::new)
                        .collect(Collectors.toList())));
                itemStack.setItemMeta(meta);
            }
            return itemStack;
        });
        simpleItem.setClickHandler(click -> {
            InventoryItem item = itemSupplier.get();
            InventoryContext cloneContext = context.copy();
            cloneContext.extend("click", click);
            cloneContext.extend(PLAYER, player);
            cloneContext.extend(ITEM_CONFIG, item);
            cloneContext.extend("item", simpleItem);

            clickHandlerRegistry.createHandler(cloneContext).handle();
        });
        return simpleItem;
    }

}

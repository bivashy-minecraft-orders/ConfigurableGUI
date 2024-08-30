package com.bivashy.bukkit.library.gui.api.extractor;

import com.bivashy.bukkit.library.gui.api.context.InventoryContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ValueExtractorRegistry {

    private final List<ValueExtractor> valueExtractors = new ArrayList<>();

    public void register(ValueExtractor extractor) {
        requireNonNull(extractor);
        valueExtractors.add(extractor);
    }

    public String extract(InventoryContext context, String text, Object model) {
        for (ValueExtractor valueExtractor : valueExtractors) {
            String result = valueExtractor.extract(context, text, model);
            if (result == null)
                continue;
            text = result;
        }
        return text;
    }

    public Collection<ValueExtractor> all() {
        return Collections.unmodifiableCollection(valueExtractors);
    }

}

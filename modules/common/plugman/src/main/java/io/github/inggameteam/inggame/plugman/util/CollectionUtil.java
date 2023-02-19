package io.github.inggameteam.inggame.plugman.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class CollectionUtil {
    public static <T> int maxCollectionsSize(Collection<T> collection1, Collection<T> collection2) {
        return Math.max(collection1.size(), collection2.size());
    }

    public static <T> T getElementOrDefault(List<T> list, int index, Supplier<T> defaults) {
        if (index < 0) throw new IllegalArgumentException("index < 0");
        if (index >= list.size()) return defaults.get();
        return list.get(index);
    }
}

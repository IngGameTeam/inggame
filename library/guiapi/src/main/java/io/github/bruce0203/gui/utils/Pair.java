package io.github.bruce0203.gui.utils;

public class Pair<K, V> {

    private final K first;
    private final V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (!equals && obj instanceof Pair<?, ?> p) {
            if (first.equals(p) && second.equals(p)) {
                return true;
            }
        }
        return equals;
    }
}

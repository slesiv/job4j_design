package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class SimpleMap<K, V> implements Map<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        int index = indexFor(hash(key.hashCode()));
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        if (table[index] == null) {
            table[index] = new MapEntry<K, V>(key, value);
            count++;
            modCount++;
            return true;
        }
        return false;
    }

    private int hash(int hashCode) {
        int h = hashCode;
        return (hashCode == 0) ? 0 : h ^ (h >>> capacity);
    }

    private int indexFor(int hash) {
        return hash & capacity - 1;
    }

    private void expand() {
        capacity = capacity * 2;
        MapEntry<K, V>[] tableNew = new MapEntry[capacity];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                tableNew[indexFor(hash(table[i].key.hashCode()))] = table[i];
            }
        }
        table = tableNew;
        modCount++;
    }

    @Override
    public Object get(Object key) {
        int index = indexFor(hash(key.hashCode()));
        if (table[index] != null) {
            return table[index].value;
        }
        return null;
    }

    @Override
    public boolean remove(Object key) {
        int index = indexFor(hash(key.hashCode()));
        if (table[index] != null) {
            table[index] = null;
            modCount++;
            return true;
        }
        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            int expectedModCount = modCount;
            int indexIt = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                for (int i = indexIt; i < table.length - 1; i++) {
                    if (table[i] != null) {
                        return true;
                    }
                    indexIt++;
                }
                return false;
            }

            @Override
            public K next() {
                return (K) table[indexIt++].key;
            }
        };
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}

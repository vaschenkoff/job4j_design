package ru.job4j.map;

import java.util.*;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if (capacity * LOAD_FACTOR <= count) {
            expand();
        }
        int index = indexByKey(key);
        boolean result = table[index] == null;
        if (result) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
        }
        return result;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> element : table) {
            if (element != null) {
                int index = indexByKey(element.key);
                newTable[index] = element;
            }
        }
        table = newTable;
    }

    @Override
    public V get(K key) {
        if (keysIsEqual(key)) {
            return table[indexByKey(key)].value;
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        if (keysIsEqual(key)) {
            int index = indexByKey(key);
            table[index] = null;
            count--;
            modCount++;
            result = true;
        }
        return result;
    }

    private int indexByKey(K key) {
        int hashCode = hash(Objects.hashCode(key));
        return indexFor(hashCode);
    }

    private boolean keysIsEqual(K key) {
        boolean result = false;
        int index = indexByKey(key);
        if (table[index] != null && Objects.hashCode(key) == Objects.hashCode(table[index].key) && Objects.equals(key, table[index].key)) {
            result = true;
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {

            private int index;

            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < table.length && table[index] == null) {
                    index++;
                }
                return index < table.length;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
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



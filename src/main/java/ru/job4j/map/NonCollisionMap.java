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
        if ((float) count / capacity >= LOAD_FACTOR) {
            expand();
        }
        boolean result = false;
        int hashCode = (key == null) ? 0 : Objects.hashCode(key);
        int hash = hash(hashCode);
        int index = indexFor(hash);
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            result = true;
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
                int hashcode = element.key == null ? 0 : Objects.hashCode(element.key);
                int index = indexFor(hash(hashcode));
                newTable[index] = element;
            }
        }
        table = newTable;
    }

    @Override
    public V get(K key) {
        int hashCode = hash(Objects.hashCode(key));
        int index = indexFor(hashCode);
        if (table[index] != null) {
            int hashCode2 = hash(Objects.hashCode(table[index].key));
            if (hashCode == hashCode2 && Objects.equals(key, table[index].key)) {
                return table[index].value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        int hashCode = hash(Objects.hashCode(key));
        int index = indexFor(hashCode);
        if (table[index] != null) {
            int hashCode2 = hash(Objects.hashCode(table[index].key));
            if (hashCode == hashCode2 && Objects.equals(key, table[index].key)) {
                table[index] = null;
                count--;
                modCount++;
                return true;
            }
        }
        return false;
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



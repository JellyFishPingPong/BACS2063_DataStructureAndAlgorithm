/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author OLIVER TAN ZU ZHUO
 */
public class ArrayList<T> implements List<T> {

    public static final int DEFAULT_SIZE = 50;
    public Object[] list;
    public int size;

    public ArrayList() {
        list = new Object[DEFAULT_SIZE];
        size = 0;
    }

    public ArrayList(List list) {
        ArrayList newList = (ArrayList) list;
        this.size = newList.size;   
        this.list = new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            this.list[i] = list.get(i);
        }
    }

    private class ArrayListIterator implements Iterator<T> {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(currentIndex++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() method is not supported.");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object entry) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(entry)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(int index) {
        return (T) list[index];
    }

    @Override
    public void set(int index, T entry) {
        list[index] = entry;
    }

    @Override
    public void add(T newEntry) {
        checkSize(size + 1);
        list[size++] = newEntry;
    }

    @Override
    public void remove(T entry) {
        if (!contains(entry)) {
            return;
        }

        for (int i = 0; i < size; i++) {
            if (list[i].equals(entry)) {
                removeGap(i);
                size--;
                break;
            }
        }
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public void add(T newEntry, int index) {
        if (index < 0 || index >= size) {
            return;
        }

        checkSize(size + 1);
        createGap(index);
        list[index] = newEntry;
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            return;
        }

        removeGap(index);
        size--;
    }

    @Override
    public int indexOf(Object entry) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(entry)) {
                return i;
            }
        }

        return -1;
    }

    private void checkSize(int reqSize) {
        int l = list.length;
        if (reqSize > l) {
            int newSize = l * 2;
            if (newSize < reqSize) {
                newSize = reqSize;
            }

            Object[] newList = new Object[newSize];
            for (int i = 0; i < l; i++) {
                newList[i] = list[i];
            }

            list = newList;
        }
    }

    private void removeGap(int index) {
        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
    }

    private void createGap(int index) {
        for (int i = size; i >= index; i--) {
            list[i] = list[i - 1];
        }
    }

    @Override
    public void addRange(List<T> subList) {
        for (T t : subList) {
            add(t);
        }
    }

}

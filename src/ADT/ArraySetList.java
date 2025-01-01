/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author LEE VESE
 */
public class ArraySetList<T> implements SetListInterface<T> {

    private T[] setListArray;
    private int totalEntries;
    private static final int DEFAULT_CAPACITY = 25;

    //constructor
    public ArraySetList() {
        this.setListArray = (T[]) new Object[DEFAULT_CAPACITY];
        totalEntries = 0;
    }

    public ArraySetList(int capacity) {
        this.setListArray = (T[]) new Object[capacity];
        totalEntries = 0;
    }

    public ArraySetList(ArraySetList<T> anotherSetList) {
        this.totalEntries = anotherSetList.totalEntries;
        
        int newSize;
        
        if(totalEntries < DEFAULT_CAPACITY) {
            newSize = DEFAULT_CAPACITY;
        } else {
            newSize = anotherSetList.totalEntries;
        }
        
        this.setListArray = (T[]) new Object[newSize];

        System.arraycopy(anotherSetList.setListArray, 0, this.setListArray, 0, totalEntries);
    }

    public T[] getSetListArray() {
        return setListArray;
    }

    public void setSetListArray(T[] setListArray) {
        this.setListArray = setListArray;
    }

    //private methods
    private void doubleArray() {
        T[] oldArray = setListArray;
        int newCapacity = 2 * oldArray.length;
        setListArray = Arrays.copyOf(oldArray, newCapacity);
    }

    private void removeGap(int removedIndex) {
        for (int i = removedIndex; i < totalEntries - 1; i++) {
            setListArray[i] = setListArray[i + 1];

        }
    }

    private void makeRoom(int newIndex) {
        if (isFull()) {
            doubleArray();
        }
        for (int i = totalEntries - 1; i >= newIndex; i--) {
            setListArray[i + 1] = setListArray[i];
        }
    }

    @Override
    public int size() {
        return this.totalEntries;
    }

    private class SetIterator implements Iterator<T> {

        //under the Iterator interface given by Java
        //2 compulsory methods to be override (hasNext & next)
        private int nextIndex; //counter

        public SetIterator() {
            nextIndex = 0; //starting counter value
        }

        public boolean hasNext() {
            return nextIndex < totalEntries;
        }

        public T next() { //return the item value
            if (hasNext()) {
                T nextElement = (T) setListArray[nextIndex++];
                return nextElement;
            } else {
                return null;
            }
        }
    }

    @Override
    public int indexOf(T anEntry) {
        for (int i = 0; i < totalEntries; i++) {
            if (setListArray[i].equals(anEntry)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean add(T newEntry) {
        //check uniqueness
        if (this.contains(newEntry)) {
            return false;
        }
        if (isFull()) {
            doubleArray();
        }
        //perform add
        setListArray[totalEntries++] = newEntry;
        return true;
    }

    @Override
    public boolean remove(T anEntry) {
        if (this.contains(anEntry)) {
            removeGap(indexOf(anEntry));
            totalEntries--;
            return true;
        }
        return false;
    }

    @Override
    public SetListInterface<T> union(SetListInterface<T> anotherSetList) {
        // Create a new setList for the union result
        SetListInterface<T> unionSetList = new ArraySetList<>();

        for (int i = 0; i < this.totalEntries; i++) {
            unionSetList.add(this.setListArray[i]); // Add elements from the current setList
        }

        if (anotherSetList instanceof ArraySetList) {
            ArraySetList<T> aSetList = (ArraySetList<T>) anotherSetList;

            for (int i = 0; i < aSetList.totalEntries; i++) {
                if (!unionSetList.contains(aSetList.setListArray[i])) {
                    unionSetList.add(aSetList.setListArray[i]); // Add unique elements from aSetList
                }
            }
        }

        return unionSetList;
    }

    @Override
    public boolean isSubset(SetListInterface anotherSetList) {
        if (anotherSetList instanceof ArraySetList) {
            ArraySetList<T> aSetList = (ArraySetList<T>) anotherSetList;

            if (aSetList.totalEntries > this.totalEntries) {
                return false; // If the other set has more elements, it cannot be a subset.
            }

            for (int i = 0; i < aSetList.totalEntries; i++) {
                T element = (T) aSetList.setListArray[i]; //store current aSetList element
                boolean found = false; // Initialize found as false for each element in aSetList

                if (this.contains(element)) {
                    found = true; // Element from aSetList found in this set
                } else {
                    return false; // Element from aSetList not found in this set
                }
            }
            return true; // All elements from aSetList found in this set

        } else {
            return false; // anotherSetList is not an instance of ArraySetList
        }
    }

    @Override
    public SetListInterface<T> intersection(SetListInterface anotherSetList) {
        // Create a new setList for intersect elements
        SetListInterface<T> intersectSetList = new ArraySetList<>();

        if (anotherSetList instanceof ArraySetList) {
            ArraySetList<T> aSetList = (ArraySetList<T>) anotherSetList;

            for (int i = 0; i < aSetList.totalEntries; i++) {
                T element = (T) aSetList.setListArray[i];

                if (this.contains(element)) {
                    intersectSetList.add(element); //Element from aSetList found in current set
                }
            }
        }
        return intersectSetList;
    }

    @Override
    public SetListInterface<T> complement(SetListInterface<T> anotherSetList) {
        // Create a new setList for complement elements that copies elements from this setList
        SetListInterface<T> complementSetList = new ArraySetList<>(this);

        if (anotherSetList instanceof ArraySetList) {
            ArraySetList<T> aSetList = (ArraySetList<T>) anotherSetList;

            for (int i = 0; i < aSetList.totalEntries; i++) {

                T element = (T) aSetList.setListArray[i];
                complementSetList.remove(element);
            }
        }
        return complementSetList;
    }

    @Override
    public void replace(T oldEntry, T newEntry) {
        this.setListArray[indexOf(oldEntry)] = newEntry;
    }

    @Override
    public Iterator getIterator() {
        return new SetIterator();
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((newPosition >= 1) && (newPosition <= totalEntries + 1)) {
            if (isFull()) {
                doubleArray();
            }
            makeRoom(indexOf(newEntry));
            setListArray[newPosition - 1] = newEntry;
            totalEntries++;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= totalEntries)) {
            result = setListArray[givenPosition - 1];

            if (givenPosition < totalEntries) {
                totalEntries--; //4
                removeGap(givenPosition - 1);
            }
            totalEntries--;
        }
        return result;
    }

    @Override
    public void clear() {
        this.totalEntries = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((givenPosition >= 1) && (givenPosition <= totalEntries)) {
            setListArray[givenPosition - 1] = newEntry;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= totalEntries)) {
            result = setListArray[givenPosition - 1];
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int i = 0; !found && i < this.totalEntries; i++) {
            if (this.setListArray[i].equals(anEntry)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public int getTotalEntries() {
        return this.totalEntries;
    }

    @Override
    public boolean isEmpty() {
        return this.totalEntries == 0;
    }

    @Override
    public boolean isFull() {
        return this.setListArray.length == this.totalEntries;
    }

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < this.totalEntries; i++) {
            string += (this.setListArray[i] + "\n");
        }
        return string;
    }
}

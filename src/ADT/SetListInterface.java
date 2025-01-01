/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ADT;

import java.util.Iterator;

/**
 * @author LEE VESE
 */
public interface SetListInterface<T> {

    //SET
    int indexOf(T anEntry); 
    
    boolean add(T newEntry);

    boolean remove(T anEntry);

    SetListInterface<T> union(SetListInterface<T> anotherSetList);

    boolean isSubset(SetListInterface anotherSetList);

    SetListInterface<T> intersection(SetListInterface<T> anotherSetList);
    
    SetListInterface<T> complement(SetListInterface<T> anotherSetList);

    void replace(T oldEntry, T newEntry);

    Iterator getIterator();

    //LIST
    boolean add(int newPosition, T newEntry);

    T remove(int givenPosition);

    void clear();

    boolean replace(int givenPosition, T newEntry);

    T getEntry(int givenPosition);

    boolean contains(T anEntry);

    int getTotalEntries();

    boolean isEmpty();

    boolean isFull();
    
    int size();
}

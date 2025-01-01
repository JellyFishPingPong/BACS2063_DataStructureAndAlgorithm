/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ADT;

import java.util.Iterator;

/**
 *
 * @author OLIVER TAN ZU ZHUO
 */
public interface List<T> extends Iterable<T> {
    int size();
    
    boolean isEmpty();
    
    boolean contains(Object entry);
    
    T get(int index);
    
    void set(int index, T entry);
    
    void add(T newEntry);
    
    void remove(T entry);
    
    void clear();
    
    void add(T newEntry, int index);
    
    void addRange(List<T> subList);
    
    void remove(int index);
    
    int indexOf(T entry);
    
    @Override 
    Iterator<T> iterator();
}

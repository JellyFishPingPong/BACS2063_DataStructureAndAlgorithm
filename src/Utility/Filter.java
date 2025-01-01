/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import ADT.ArrayList;
import ADT.List;

/**
 *
 * @author OLIVER TAN ZU ZHUO
 */
public class Filter<T> {

    List<Integer> filters;

    public Filter(List<Integer> filters) {
        this.filters = filters;
    }

    public Filter() {
        this.filters = new ArrayList();
    }

    public boolean hasFilter() {
        return filters.size() != 0;
    }

    public boolean filters(int check) {
        return filters.contains(check);
    }

    public void addFilter(int newFilter) {
        filters.add(newFilter);
    }

    public void removeFilter(Integer entry) {
        filters.remove(entry);
    }
    
    public void clear() {
        filters.clear();
    }
}

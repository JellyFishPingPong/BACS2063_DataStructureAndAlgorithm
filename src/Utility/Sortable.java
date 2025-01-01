/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Utility;

import ADT.List;

/**
 *
 * @author OLIVER TAN ZU ZHUO
 */
public interface Sortable<T> {
      public List<T> sort(List<T> objectList, int sortType);
      
      public List<T> filter(List<T> objectList, Filter filter);
      
      public List<T> search(List<T> objectList, String query);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import java.util.Comparator;
import ADT.List;

/**
 *
 * @author OLIVER TAN ZU ZHUO
 */
public class Sorter {
    public static <T extends Sortable> void sort(List<T> list, Comparator<T> c) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (c.compare(list.get(j), (list.get(j + 1))) > 0) {
                    // Swap elements if they are in the wrong order
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    public static <T extends Sortable> void reverse(List<T> list) {
        int n = list.size();
        for (int i = 0; i < n/2; i++) {
            T temp = list.get(i);
            list.set(i, list.get(n - 1 - i));
            list.set(n - 1 - i, temp);
        }
    } 
    
    
}

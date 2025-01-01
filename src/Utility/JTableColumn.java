/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import javax.swing.JTable;

/**
 *
 * @author OLIVER TAN ZU ZHUO
 */
public class JTableColumn {

    public static void setColumnWidthPercentage(JTable table, int width, int... percentages) {
        int size = percentages.length;
        int total = 0;


        for (int i = 0; i < size; i++) {
            total += percentages[i];
        }

        for (int i = 0; i < size; i++) {
            var column = table.getColumnModel().getColumn(i);
            var columnWidth = (int) width * percentages[i] / total;
            column.setPreferredWidth(columnWidth);
        }
    }

}

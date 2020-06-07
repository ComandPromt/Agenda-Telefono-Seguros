
package utils;

import java.awt.Component;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FormatoTabla extends DefaultTableCellRenderer {
	public FormatoTabla() {
	}

	int paso = 0;

	public static LinkedList<String> FechasVencimientosRojos = new LinkedList<String>();

	public static LinkedList<String> FechasVencimientosVerdes = new LinkedList<String>();

	public static LinkedList<String> FechasVencimientosNaranjas = new LinkedList<String>();

	public static LinkedList<String> FechasVencimientosAmarillos = new LinkedList<String>();

	int indice = 0;

	String fecha;

	@Override

	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {

		try {

			try {

				if (indice < table.getRowCount()) {

					fecha = table.getValueAt(indice, 2).toString();

					if (FechasVencimientosRojos.contains(fecha)) {

						table.setValueAt("- 15 DÍAS", indice, 3);

					}

					if (FechasVencimientosNaranjas.contains(fecha)) {
						table.setValueAt("+15 DÍAS <--> 1 MES", indice, 3);

					}

					if (FechasVencimientosAmarillos.contains(fecha)) {
						table.setValueAt("+1 MES <--> -2 MESES", indice, 3);

					}

					if (FechasVencimientosVerdes.contains(fecha)) {
						table.setValueAt("¡LLAMAR YA!", indice, 3);

					}

					++indice;

				}

			} catch (Exception e) {

			}

			super.getTableCellRendererComponent(table, value, selected, focused, row, column);

		} catch (Exception e) {

		}
		return this;

	}

}
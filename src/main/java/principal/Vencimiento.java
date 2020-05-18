package principal;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import utils.Metodos;

public class Vencimiento extends TimerTask {

	static ArrayList<String> arrayList1 = new ArrayList();

	static ArrayList<Objeto> arrayList2 = new ArrayList();

	static LinkedList<Integer> indiceDeceso = new <Integer>LinkedList();

	public static LinkedList<Integer> getIndiceDeceso() {
		return indiceDeceso;
	}

	public void setIndiceDeceso(LinkedList<Integer> indiceDeceso) {
		this.indiceDeceso = indiceDeceso;
	}

	int vueltas = 0;

	@Override
	public void run() {

		try {

			Agenda.ponerFechasDeceso();

			java.util.Date fecha = new Date();

			indiceDeceso = Metodos.buscarVencimientos(Agenda.fechaDecesos,
					Agenda.convertirFecha(fecha.toString(), false));

			if (indiceDeceso.size() > 0) {

				arrayList1.clear();

				for (int i = 0; i < indiceDeceso.size(); i++) {

					if (indiceDeceso.get(i).toString() != null) {
						arrayList1.add(indiceDeceso.get(i).toString());

					}

				}

				ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream("fechas.dat"));

				escribiendoFichero.writeObject(arrayList1);

				escribiendoFichero.close();

				arrayList2 = Agenda.leer("fechas.dat");

				Agenda.vencimientosDecesos.clear();

				for (int i = 0; i < arrayList2.size(); i++) {
					Agenda.vencimientosDecesos.add("" + arrayList2.get(i));

				}

			}

			Agenda.mostrarLlamada.dispose();

			Agenda.mostrarLlamada = new Llamada();

			if (Agenda.vencimientosDecesos.size() > 0) {

				Agenda.mostrarLlamada.setVisible(true);

				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(Llamada.jTable1.getModel());

				Llamada.jTable1.setRowSorter(sorter);

				List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);

				sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
				sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
				sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
				sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));

				sorter.setSortKeys(sortKeys);
			}

			vueltas++;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

}

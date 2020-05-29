package principal;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimerTask;

import utils.Metodos;

public class Vencimiento extends TimerTask {

	static ArrayList<String> arrayList1 = new ArrayList();

	static LinkedList<String> arrayList2 = new LinkedList();

	static LinkedList<Integer> indiceDeceso = new <Integer>LinkedList();
	public static LinkedList<String> colores = new LinkedList();
	public static LinkedList<String> contactosVerdes = new LinkedList();
	int contador = 0;

	public static LinkedList<Integer> getIndiceDeceso() {
		return indiceDeceso;
	}

	public void setIndiceDeceso(LinkedList<Integer> indiceDeceso) {
		this.indiceDeceso = indiceDeceso;
	}

	@Override

	public void run() {

		try {

			colores.add("R");
			colores.add("V");
			contactosVerdes.add("ghhjhg");
			contactosVerdes.add("ttt");
			Agenda.ponerFechasDeceso();

			java.util.Date fecha = new Date();

			indiceDeceso = Metodos.buscarVencimientos(Agenda.fechaDecesos,
					Agenda.convertirFecha(fecha.toString(), false));

			Agenda.vencimientosDecesos.clear();

			System.out.println("Vtos decesos: " + indiceDeceso.size());

			if (indiceDeceso.size() > 0) {

				arrayList1.clear();

				arrayList2 = Metodos.leer("fechas.dat");

				Agenda.vencimientosDecesos.clear();

				if (Agenda.vencimientos.size() > 0) {

					ponerVencimientos();

				}

				ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream("fechas.dat"));

				escribiendoFichero.writeObject(arrayList1);

				escribiendoFichero.close();

			}

			Agenda.mostrarLlamada = new Llamada();

			if (Agenda.contador == 0) {

				Agenda.setVto(Agenda.getVto() + Agenda.vencimientosDecesos.size());

				if (Agenda.vencimientosDecesos.size() > 0) {

					Agenda.mostrarLlamada.setVisible(true);

				}

			}

			else {

				// Si hay vencimientos de algun seguro aparece
				// en el boton

				Agenda.setVto("Vtos: " + Agenda.vencimientosDecesos.size());

			}

			Agenda.contador += 1;

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void ponerVencimientos() {
		String indicevtoDeceso;
		String fechaVtoDeceso;

		int indice1 = 0;

		int indice2 = 0;

		for (int i = 0; i < indiceDeceso.size(); i++) {

			if (indiceDeceso.get(i).toString() != null) {

				arrayList1.add(indiceDeceso.get(i).toString());

				if (indiceDeceso.get(i) < Agenda.vencimientos.size()) {

					indicevtoDeceso = Agenda.vencimientos.get(indiceDeceso.get(i));

					indice1 = indicevtoDeceso.indexOf("> ");

					indice2 = indicevtoDeceso.indexOf("V");

					if (indice1 > -1 && indice2 > -1) {

						indice1 += 2;

						fechaVtoDeceso = indicevtoDeceso.substring(indice1, indice2);

						fechaVtoDeceso = fechaVtoDeceso.trim();

						Agenda.vencimientosDecesos.add(fechaVtoDeceso);

					}

				}

			}

		}
	}

}

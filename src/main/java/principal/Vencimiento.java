package principal;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimerTask;

import utils.FormatoTabla;
import utils.Metodos;

public class Vencimiento extends TimerTask {

	static ArrayList<String> arrayList1 = new ArrayList();

	static LinkedList<String> arrayList2 = new LinkedList();

	public static LinkedList<String> contactosVencimientos = new LinkedList();

	static LinkedList<Integer> indiceDeceso = new <Integer>LinkedList();

	static LinkedList<Integer> indiceVida = new <Integer>LinkedList();

	static LinkedList<Integer> vencimientos = new <Integer>LinkedList();

	static LinkedList<Integer> vencimientosVerdes = new <Integer>LinkedList();

	public static LinkedList<String> colores = new LinkedList();

	int contador = 0;

	public static LinkedList<Integer> getIndiceVida() {
		return indiceVida;
	}

	public static LinkedList<Integer> getIndiceDeceso() {
		return indiceDeceso;
	}

	@Override

	public void run() {

		try {

			FormatoTabla.FechasVencimientosRojos.clear();

			FormatoTabla.FechasVencimientosVerdes.clear();

			FormatoTabla.FechasVencimientosAmarillos.clear();

			FormatoTabla.FechasVencimientosNaranjas.clear();

			vencimientos.clear();

			indiceDeceso.clear();

			indiceVida.clear();

			arrayList1.clear();

			arrayList2.clear();

			Agenda.vencimientosDecesos.clear();

			Agenda.vencimientosVida.clear();

			Agenda.verNotas();

			Date fecha = new Date();

			indiceVida = buscarColoresVencimientos(fecha.toString(), Agenda.fechaVida);

			indiceDeceso = buscarColoresVencimientos(fecha.toString(), Agenda.fechaDecesos);

			if (indiceDeceso.size() > 0) {

				arrayList1.clear();

				arrayList2 = Metodos.leer("fechasDecesos.dat");

				if (Agenda.vencimientos.size() > 0) {

					ponerVencimientos(1);

				}

				ObjectOutputStream escribiendoFichero = new ObjectOutputStream(
						new FileOutputStream("fechasDecesos.dat"));

				escribiendoFichero.writeObject(arrayList1);

				escribiendoFichero.close();

			}

			if (indiceVida.size() > 0) {

				arrayList1.clear();

				arrayList2 = Metodos.leer("fechasVida.dat");

				if (Agenda.vencimientos.size() > 0) {

					ponerVencimientos(2);

				}

				ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream("fechasVida.dat"));

				escribiendoFichero.writeObject(arrayList1);

				escribiendoFichero.close();

			}

		}

		catch (java.io.IOException e) {
			Metodos.mensaje("No hay espacio en el equipo", 1, true);
		}

		catch (Exception e) {

		}
	}

	public static LinkedList<Integer> buscarColoresVencimientos(String fecha, LinkedList tipoVencimiento) {

		LinkedList<Integer> todosLosVencimientos = new LinkedList<Integer>();

		LinkedList<Integer> indiceVtos = new LinkedList<Integer>();

		todosLosVencimientos = Metodos.buscarVencimientosRojos(tipoVencimiento, Agenda.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosNaranja(tipoVencimiento, Agenda.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosAmarillo(tipoVencimiento, Agenda.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosVerdes(tipoVencimiento, Agenda.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {
			indiceVtos.add(todosLosVencimientos.get(i));
		}

		return indiceVtos;

	}

	private void ponerVencimientos(int tipo) {

		LinkedList<Integer> indices = new LinkedList<Integer>();

		String indicevtoDeceso;

		String fechaVto = "";

		int indice1 = 0;

		int indice2 = 0;

		switch (tipo) {
		case 1:
			indices = indiceDeceso;
			break;
		case 2:
			indices = indiceVida;
			break;

		}

		for (int i = 0; i < indices.size(); i++) {

			if (indices.get(i).toString() != null) {

				arrayList1.add(indices.get(i).toString());

				if (indices.get(i) < Agenda.vencimientos.size()) {

					indicevtoDeceso = Agenda.vencimientos.get(indices.get(i));

					switch (tipo) {

					case 1:

						indice1 = indicevtoDeceso.indexOf("> ");

						indice2 = indicevtoDeceso.indexOf("V");

						if (indice1 > -1 && indice2 > -1) {

							indice1 += 2;

							fechaVto = indicevtoDeceso.substring(indice1, indice2);

							fechaVto = fechaVto.trim();
						}

						Agenda.vencimientosDecesos.add(fechaVto);

						break;

					case 2:

						indice1 = indicevtoDeceso.indexOf("Vida --> ") + 9;

						indice2 = indicevtoDeceso.indexOf("H");

						if (indice1 > -1 && indice2 > -1) {

							fechaVto = indicevtoDeceso.substring(indice1, indice2);

							fechaVto = fechaVto.trim();
						}

						Agenda.vencimientosVida.add(fechaVto);

						break;

					}

				}

			}

		}
	}

}

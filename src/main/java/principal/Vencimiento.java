package principal;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimerTask;

import utils.Metodos;

public class Vencimiento extends TimerTask {

	static ArrayList<Objeto> arrayList1;

	@Override
	public void run() {

		try {

			Metodos.eliminarFichero("fechas.dat");

			ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

			ArrayList<Objeto> arrayList2;

			for (int i = 0; i < Agenda.fechas.size(); i++) {
				arrayList1.add(new Objeto(Agenda.fechas.get(i)));
			}

			ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream("fechas.dat"));

			escribiendoFichero.writeObject(arrayList1);

			escribiendoFichero.close();

			LinkedList<Integer> indices = new <Integer>LinkedList();

			String hoy = "16/02/2020";

			hoy = Metodos.convertirFecha(hoy);

			indices = Metodos.buscarVencimientos(Agenda.fechas, hoy);

			for (int i = 0; i < indices.size(); i++) {
				System.out.println(indices.get(i));
			}

			Agenda.fechas.clear();

			arrayList2 = Agenda.leer("fechas.dat");

			for (int i = 0; i < arrayList2.size(); i++) {
				Agenda.fechas.add(arrayList2.get(i).toString());
			}

			if (!Agenda.mostrarLlamada.isShowing()) {

				Agenda.mostrarLlamada = null;

				Agenda.mostrarLlamada = new Llamada();

				Agenda.mostrarLlamada.setVisible(true);

			}
			// new Llamada().setVisible(true);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

}

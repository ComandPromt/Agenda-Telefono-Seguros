package principal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;

import utils.FormatoTabla;
import utils.Metodos;

public class Vencimiento {

	static LinkedList<String> arrayList1 = new LinkedList<String>();

	static LinkedList<String> arrayList2 = new LinkedList<String>();

	public static LinkedList<String> contactosVencimientos = new LinkedList<String>();

	static LinkedList<Integer> indiceDeceso = new LinkedList<Integer>();
	static LinkedList<Integer> indiceVida = new LinkedList<Integer>();
	static LinkedList<Integer> indiceHogar = new LinkedList<Integer>();
	static LinkedList<Integer> indiceComercio = new LinkedList<Integer>();
	static LinkedList<Integer> indiceComunidad = new LinkedList<Integer>();
	static LinkedList<Integer> indiceCoche = new LinkedList<Integer>();
	static LinkedList<Integer> vencimientos = new LinkedList<Integer>();

	static LinkedList<Integer> vencimientosVerdes = new LinkedList<Integer>();

	public static LinkedList<Integer> getIndiceHogar() {
		return indiceHogar;
	}

	public static LinkedList<Integer> getIndiceComercio() {
		return indiceComercio;
	}

	public static LinkedList<Integer> getIndiceComunidad() {
		return indiceComunidad;
	}

	private static String nuevoVencimiento = "Vtos: ";

	public static LinkedList<Integer> getIndiceCoche() {
		return indiceCoche;
	}

	public static LinkedList<String> colores = new LinkedList<String>();

	int contador = 0;

	public static LinkedList<Integer> getIndiceVida() {
		return indiceVida;
	}

	public static LinkedList<Integer> getIndiceDeceso() {
		return indiceDeceso;
	}

	public static void actualizarTituloVencimientos() {

		try {

			int numDeceso = Agenda.vencimientosDecesos.size();

			int numVida = Agenda.vencimientosVida.size();

			int numHogar = Agenda.vencimientosHogar.size();

			int numCoche = Agenda.vencimientosCoche.size();

			int numComercio = Agenda.vencimientosComercio.size();

			int numComunidad = Agenda.vencimientosComunidad.size();

			if (numDeceso > 0 || numVida > 0 || numHogar > 0 || numCoche > 0 || numComercio > 0 || numComunidad > 0) {

				Agenda.nuevosVencimientos.setText(nuevoVencimiento);

				LinkedList<String> leerLlamadaSeguro = new LinkedList<String>();

				int sizeLlamada = 0;

				if (numDeceso > 0) {

					leerLlamadaSeguro = Metodos.leer(Metodos.saberArchivoLlamada(1));
					leerLlamadaSeguro = Metodos.formatearArray(leerLlamadaSeguro.get(0));
					sizeLlamada = leerLlamadaSeguro.size();

					numDeceso -= sizeLlamada;

					if (numDeceso > 0) {
						Agenda.nuevosVencimientos
								.setText(Agenda.nuevosVencimientos.getText() + "Decesos: " + numDeceso + " || ");

					}

				}

				if (!Agenda.vencimientosVida.isEmpty()) {

					leerLlamadaSeguro = Metodos.leer(Metodos.saberArchivoLlamada(2));
					leerLlamadaSeguro = Metodos.formatearArray(leerLlamadaSeguro.get(0));
					sizeLlamada = leerLlamadaSeguro.size();

					numVida -= sizeLlamada;

					if (numVida > 0) {
						Agenda.nuevosVencimientos
								.setText(Agenda.nuevosVencimientos.getText() + "Vida: " + numVida + " || ");

					}
				}

				if (!Agenda.vencimientosHogar.isEmpty()) {

					leerLlamadaSeguro = Metodos.leer(Metodos.saberArchivoLlamada(3));
					leerLlamadaSeguro = Metodos.formatearArray(leerLlamadaSeguro.get(0));
					sizeLlamada = leerLlamadaSeguro.size();

					numHogar -= sizeLlamada;

					if (numHogar > 0) {
						Agenda.nuevosVencimientos
								.setText(Agenda.nuevosVencimientos.getText() + "Hogar: " + numHogar + " || ");

					}
				}

				if (!Agenda.vencimientosCoche.isEmpty()) {

					leerLlamadaSeguro = Metodos.leer(Metodos.saberArchivoLlamada(4));
					leerLlamadaSeguro = Metodos.formatearArray(leerLlamadaSeguro.get(0));
					sizeLlamada = leerLlamadaSeguro.size();

					numCoche -= sizeLlamada;
					if (numCoche > 0) {
						Agenda.nuevosVencimientos
								.setText(Agenda.nuevosVencimientos.getText() + "Coche: " + numCoche + " || ");

					}
				}

				if (!Agenda.vencimientosComercio.isEmpty()) {

					leerLlamadaSeguro = Metodos.leer(Metodos.saberArchivoLlamada(5));
					leerLlamadaSeguro = Metodos.formatearArray(leerLlamadaSeguro.get(0));
					sizeLlamada = leerLlamadaSeguro.size();

					numComercio -= sizeLlamada;

					if (numComercio > 0) {
						Agenda.nuevosVencimientos
								.setText(Agenda.nuevosVencimientos.getText() + "Comercio: " + numComercio + " || ");

					}
				}

				if (!Agenda.vencimientosComunidad.isEmpty()) {

					leerLlamadaSeguro = Metodos.leer(Metodos.saberArchivoLlamada(6));
					leerLlamadaSeguro = Metodos.formatearArray(leerLlamadaSeguro.get(0));
					sizeLlamada = leerLlamadaSeguro.size();

					numComunidad -= sizeLlamada;

					if (numComunidad > 0) {
						Agenda.nuevosVencimientos
								.setText(Agenda.nuevosVencimientos.getText() + "Comunidad: " + numComunidad);

					}
				}

				if (Agenda.nuevosVencimientos.getText().indexOf(" || ") == Agenda.nuevosVencimientos.getText().length()
						- 4) {
					Agenda.nuevosVencimientos.setText(Agenda.nuevosVencimientos.getText().substring(0,
							Agenda.nuevosVencimientos.getText().lastIndexOf(" || ")));
				}

			}

			if (numDeceso == 0 && numVida == 0 && numHogar == 0 && numCoche == 0 && numComercio == 0
					&& numComunidad == 0) {

				Agenda.nuevosVencimientos.setText("");
			}

			String textoVencimientos = Agenda.nuevosVencimientos.getText().trim();

			if (textoVencimientos.substring(textoVencimientos.length() - 2, textoVencimientos.length()).equals("||")) {
				Agenda.nuevosVencimientos.setText(textoVencimientos.substring(0, textoVencimientos.lastIndexOf("||")));
			}

		} catch (Exception e) {
			//
		}

	}

	public static void verTablaVencimientos() {

		try {

			FormatoTabla.FechasVencimientosRojos.clear();

			FormatoTabla.FechasVencimientosVerdes.clear();

			FormatoTabla.FechasVencimientosAmarillos.clear();

			FormatoTabla.FechasVencimientosNaranjas.clear();

			vencimientos.clear();

			indiceDeceso.clear();

			indiceVida.clear();

			Agenda.vencimientosDecesos.clear();

			Agenda.vencimientosVida.clear();
			Agenda.vencimientosHogar.clear();
			Agenda.vencimientosCoche.clear();
			Agenda.vencimientosComercio.clear();
			Agenda.vencimientosComunidad.clear();

			Date fecha = new Date();

			indiceVida = buscarColoresVencimientos(fecha.toString(), Agenda.fechaVida);

			indiceDeceso = buscarColoresVencimientos(fecha.toString(), Agenda.fechaDecesos);

			indiceHogar = buscarColoresVencimientos(fecha.toString(), Agenda.fechaHogar);

			indiceCoche = buscarColoresVencimientos(fecha.toString(), Agenda.fechaCoche);

			indiceComercio = buscarColoresVencimientos(fecha.toString(), Agenda.fechaComercio);

			indiceComunidad = buscarColoresVencimientos(fecha.toString(), Agenda.fechaComunidad);

			if (indiceDeceso.size() > 0) {

				actualizarVencimientos("fechasDecesos.dat", 1);
			}

			if (indiceVida.size() > 0) {

				actualizarVencimientos("fechasVida.dat", 2);

			}

			if (indiceHogar.size() > 0) {

				actualizarVencimientos("fechasHogar.dat", 3);

			}

			if (indiceCoche.size() > 0) {

				actualizarVencimientos("fechasCoche.dat", 4);

			}

			if (indiceComercio.size() > 0) {

				actualizarVencimientos("fechasComercio.dat", 5);

			}

			if (indiceComunidad.size() > 0) {

				actualizarVencimientos("fechasComunidad.dat", 6);

			}
			actualizarTituloVencimientos();
		}

		catch (java.io.IOException e) {
			Metodos.mensaje("No hay espacio en el equipo", 1, true);
		}

		catch (Exception e) {
			//
		}
	}

	static void actualizarVencimientos(String archivo, int tipo)
			throws IOException, FileNotFoundException, ClassNotFoundException {

		arrayList2 = Metodos.leer(archivo);

		if (Agenda.vencimientos.size() > 0) {

			ponerVencimientos(tipo);

		}

		ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream(archivo));

		escribiendoFichero.writeObject(arrayList1);

		escribiendoFichero.close();
	}

	public static LinkedList<Integer> buscarColoresVencimientos(String fecha, LinkedList<String> tipoVencimiento) {

		LinkedList<Integer> todosLosVencimientos = new LinkedList<Integer>();

		LinkedList<Integer> indiceVtos = new LinkedList<Integer>();

		todosLosVencimientos = Metodos.buscarVencimientosRojos(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosNaranja(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosAmarillo(tipoVencimiento,
				Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosVerdes(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {
			indiceVtos.add(todosLosVencimientos.get(i));
		}

		return indiceVtos;

	}

	static void ponerVencimientos(int tipo) {

		arrayList1.clear();

		LinkedList<Integer> indices = new LinkedList<Integer>();

		String indicevtoDeceso;

		String fechaVto = "";

		int indice1 = 0;

		int indice2 = 0;

		switch (tipo) {

		case 1:
			Agenda.vencimientosDecesos.clear();

			indices = indiceDeceso;
			break;

		case 2:
			Agenda.vencimientosVida.clear();

			indices = indiceVida;
			break;

		case 3:
			Agenda.vencimientosHogar.clear();

			indices = indiceHogar;
			break;

		case 4:
			Agenda.vencimientosCoche.clear();

			indices = indiceCoche;
			break;

		case 5:
			Agenda.vencimientosComercio.clear();

			indices = indiceComercio;
			break;

		case 6:
			Agenda.vencimientosComunidad.clear();

			indices = indiceComunidad;
			break;

		}

		for (int i = 0; i < indices.size(); i++) {

			if (indices.get(i).toString() != null) {

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

					case 3:

						indice1 = indicevtoDeceso.indexOf("Hogar --> ") + 10;

						indice2 = indicevtoDeceso.indexOf("Coche");

						if (indice1 > -1 && indice2 > -1) {

							fechaVto = indicevtoDeceso.substring(indice1, indice2);

							fechaVto = fechaVto.trim();
						}

						Agenda.vencimientosHogar.add(fechaVto);

						break;

					case 4:

						indice1 = indicevtoDeceso.indexOf("Coche --> ") + 10;

						indice2 = indicevtoDeceso.indexOf("Comercio");

						if (indice1 > -1 && indice2 > -1) {

							fechaVto = indicevtoDeceso.substring(indice1, indice2);

							fechaVto = fechaVto.trim();
						}

						Agenda.vencimientosCoche.add(fechaVto);

						break;

					case 5:

						indice1 = indicevtoDeceso.indexOf("Comercio --> ") + 13;

						indice2 = indicevtoDeceso.indexOf("Comunidad");

						if (indice1 > -1 && indice2 > -1) {

							fechaVto = indicevtoDeceso.substring(indice1, indice2);
							fechaVto = fechaVto.trim();
						}

						Agenda.vencimientosComercio.add(fechaVto);

						break;

					case 6:

						indice1 = indicevtoDeceso.indexOf("Comunidad --> ") + 14;

						indice2 = indicevtoDeceso.length();

						if (indice1 > -1 && indice2 > -1) {

							fechaVto = indicevtoDeceso.substring(indice1, indice2);

							fechaVto = fechaVto.trim();
						}

						Agenda.vencimientosComunidad.add(fechaVto);

						break;

					}

				}

			}

		}
	}

}

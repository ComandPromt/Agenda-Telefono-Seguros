package utils;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONArray;
import org.json.JSONObject;

import alertas.AlertError;
import alertas.AlertInformation;
import alertas.AlertSuccess;
import alertas.AlertWarningSalir;
import principal.Agenda;
import principal.Vencimiento;

public class Metodos {

	public static boolean ultimoDiaMes(int dia, int mes, int year) {

		boolean resultado = false;

		if (dia == 30) {

			switch (mes) {

			case 4:
			case 6:
			case 9:
			case 11:
				resultado = true;
				break;

			}

		}

		if (dia == 31) {

			switch (mes) {

			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				resultado = true;
				break;

			}

		}

		if (year < 3344 && (mes == 2 && (dia == 29 && esBisiesto(year)) || (dia == 28 && !esBisiesto(year)

		))) {
			resultado = true;
		}

		return resultado;
	}

	public static LinkedList<String> leer(String file)
			throws IOException, FileNotFoundException, ClassNotFoundException {

		LinkedList<String> arrayList2 = new LinkedList<String>();

		File archivo = new File(file);

		if (archivo.exists()) {

			ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream(file));

			arrayList2.add(leyendoFichero.readObject().toString());

			leyendoFichero.close();

		}

		return arrayList2;
	}

	public static boolean esBisiesto(int year) {

		if (year >= 3344) {
			return true;
		}

		else {

			if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
				return true;
			}

			else {
				return false;
			}
		}

	}

	public static void eliminarFichero(String archivo) {

		File fichero = new File(archivo);

		if (fichero.exists() && !fichero.isDirectory()) {
			fichero.delete();
		}

	}

	public static LinkedList<Integer> buscarVencimientosRojos(LinkedList<String> lista, String busqueda) {

		LinkedList<Integer> repetido = new LinkedList<Integer>();

		LinkedList<Integer> resultado = new LinkedList<Integer>();

		int dia, mes, year;

		dia = Integer.parseInt(busqueda.substring(0, busqueda.indexOf("/")));

		mes = Integer.parseInt(busqueda.substring(busqueda.indexOf("/") + 1, busqueda.lastIndexOf("/")));

		year = Integer.parseInt(busqueda.substring(busqueda.lastIndexOf("/") + 1, busqueda.length()));

		String ceromes = "";

		LinkedList<String> fechasRojas = new LinkedList<String>();

		fechasRojas.add(busqueda);

		if (ultimoDiaMes(dia, mes, year)) {

			dia = 1;

			if (mes == 12) {
				mes = 1;
			}

			else {
				++mes;
			}

		} else {
			++dia;
		}

		for (int x = 0; x < 15; x++) {

			if (mes <= 9) {
				ceromes = "0";
			}

			busqueda = dia + "/" + ceromes + mes + "/" + year;

			fechasRojas.add(busqueda);

			if (ultimoDiaMes(dia, mes, year)) {

				dia = 1;

				if (mes == 12) {
					mes = 1;
				}

				else {
					++mes;
				}

			} else {
				++dia;
			}

		}

		for (int i = 0; i < 15; i++) {

			repetido = buscarFechasVencimientos(lista, fechasRojas.get(i), 1);

			if (repetido.size() > 0) {

				for (int x = 0; x < repetido.size(); x++) {
					resultado.add(repetido.get(x));
				}

			}

		}

		return resultado;

	}

	public static LinkedList<Integer> buscarVencimientosVerdes(LinkedList<String> lista, String busqueda) {

		LinkedList<Integer> repetido = new LinkedList<Integer>();

		int dia, mes, year;

		dia = Integer.parseInt(busqueda.substring(0, busqueda.indexOf("/")));

		mes = Integer.parseInt(busqueda.substring(busqueda.indexOf("/") + 1, busqueda.lastIndexOf("/")));

		year = Integer.parseInt(busqueda.substring(busqueda.lastIndexOf("/") + 1, busqueda.length()));

		boolean mesEspecial = false;

		int vueltasComprobacion = 1;

		boolean cambioFebrero = false;

		String ceromes = "";

		if (dia != 29 && dia != 30 && dia != 31 && mes != 11 && mes != 12) {

			if (mes == 2 && dia == 28) {
				vueltasComprobacion = 3;
			}

			mes += 2;
		}

		else {

			if (mes == 11) {
				mes = 1;
				++year;
				mesEspecial = true;
			}

			if (mes == 12) {

				mes = 2;

				cambioFebrero = true;

				++year;

				if (dia == 29 || dia == 30 || dia == 31) {

					if ((dia == 31 || dia == 30) && year >= 3344) {
						dia = 30;
					}

					else {

						dia = 28;

						if (esBisiesto(year)) {

							dia = 29;

						}

					}
				}

				mesEspecial = true;
			}

			if ((!cambioFebrero && mes == 2 && dia == 29) || (dia == 30 && mes == 6)) {
				vueltasComprobacion = 2;
			}

			if (!mesEspecial) {
				mes += 2;
			}

		}

		for (int i = 0; i < vueltasComprobacion; i++) {

			if (i > 0) {
				++dia;
			}

			if (mes <= 9) {
				ceromes = "0";
			}

			busqueda = dia + "/" + ceromes + mes + "/" + year;

			repetido = buscarFechasVencimientos(lista, busqueda, 4);

		}

		return repetido;

	}

	public static LinkedList<Integer> buscarFechasVencimientos(LinkedList<String> lista, String busqueda, int color) {

		LinkedList<Integer> repetido = new LinkedList<Integer>();

		int indice = -1;

		indice = lista.indexOf(busqueda);

		String pintura = "";

		switch (color) {

		case 1:
			pintura = "R";
			break;

		case 2:
			pintura = "O";
			break;

		case 3:
			pintura = "A";
			break;

		case 4:
			pintura = "V";
			break;

		default:
			pintura = "V";
			break;

		}

		while (indice != -1) {

			Vencimiento.colores.add(pintura);
			Vencimiento.colores.add(pintura);
			Vencimiento.colores.add(pintura);
			Vencimiento.colores.add(pintura);
			repetido.add(indice);

			lista.set(indice, null);

			indice = lista.indexOf(busqueda);

		}

		return repetido;

	}

	public static String extraerExtension(String nombreArchivo) {

		String extension = "";

		if (nombreArchivo.length() >= 3) {

			extension = nombreArchivo.substring(nombreArchivo.length() - 3, nombreArchivo.length());

			extension = extension.toLowerCase();

			if (extension.equals("peg")) {
				extension = "jpeg";
			}

			if (extension.equals("fif")) {
				extension = "jfif";
			}

			if (extension.equals("ebp")) {
				extension = "webp";
			}

			if (extension.equals("ebm")) {
				extension = "webm";
			}

			if (extension.equals("3u8")) {
				extension = "m3u8";
			}

			if (extension.equals(".ts")) {
				extension = "ts";
			}

		}

		return extension;
	}

	public static java.io.File[] seleccionar(String rotulo, String mensaje) {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = null;

		filter = new FileNameExtensionFilter(rotulo, "vcf");
		chooser.setFileFilter(filter);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (!chooser.isMultiSelectionEnabled()) {
			chooser.setMultiSelectionEnabled(true);
		}

		chooser.showOpenDialog(chooser);
		File[] files = chooser.getSelectedFiles();

		if (files.length == 0) {
			mensaje(mensaje, 3, true);
		}

		return files;
	}

	public static boolean comprobarPatron(String dato, String patron) {

		String regEx = patron;

		Pattern pattern = Pattern.compile(regEx);

		Matcher matcher = pattern.matcher(dato);

		return matcher.matches();
	}

	private static String readAll(Reader rd) throws IOException {

		StringBuilder sb = new StringBuilder();

		int cp;

		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}

		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException {

		InputStream is = new URL(url).openStream();

		BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

		String jsonText = readAll(rd);
		is.close();

		return new JSONObject(jsonText);

	}

	public static JSONObject apiImagenes(String parametros) throws IOException {
		JSONObject json = readJsonFromUrl("https://apiperiquito.herokuapp.com/recibo-json.php?imagenes=" + parametros);
		return json;
	}

	public static String extraerNombreArchivo(String extension) throws IOException {
		JSONObject json = apiImagenes("archivo." + extension);
		JSONArray imagenesBD = json.getJSONArray("imagenes_bd");

		String outputFilePath = Agenda.getDirectorioActual() + "contactos_exportados" + Agenda.getSeparador()
				+ imagenesBD.get(0).toString();
		return outputFilePath;
	}

	public static String saberSeparador(String os) {
		if (os.equals("Linux")) {
			return "/";
		} else {
			return "\\";
		}
	}

	public static String eliminarEspacios(String cadena) {

		cadena = cadena.trim();

		cadena = cadena.replace("  ", " ");

		cadena = cadena.trim();

		return cadena;
	}

	public static void mensaje(String mensaje, int titulo, boolean filtro) {

		String tituloSuperior = "", sonido = "";

		int tipo = 0;

		switch (titulo) {

		case 1:
			if (filtro) {
				AlertError error;

				error = new AlertError(null, false);

				error.setTitulo(mensaje);

				error.setVisible(true);
			}

			else {
				tipo = JOptionPane.ERROR_MESSAGE;
				tituloSuperior = "Error";
			}

			break;

		case 2:

			if (filtro) {
				AlertInformation informacion;

				informacion = new AlertInformation(null, false);

				informacion.setTitulo(mensaje);

				informacion.setVisible(true);
			} else {
				tipo = JOptionPane.INFORMATION_MESSAGE;
				tituloSuperior = "Informacion";
			}
			break;

		case 3:

			if (filtro) {
				AlertWarningSalir salir;

				salir = new AlertWarningSalir(null, false);

				salir.setTitulo(mensaje);

				salir.setVisible(true);
			} else {
				tipo = JOptionPane.WARNING_MESSAGE;
				tituloSuperior = "Advertencia";
			}
			break;

		case 4:

			if (filtro) {
				AlertSuccess exito;

				exito = new AlertSuccess(null, false);

				exito.setTitulo(mensaje);

				exito.setVisible(true);
			} else {
				tipo = JOptionPane.INFORMATION_MESSAGE;
				tituloSuperior = "Informacion";
			}
			break;

		default:
			break;

		}

		if (!filtro) {
			JLabel alerta = new JLabel(mensaje);

			alerta.setFont(new Font("Arial", Font.BOLD, 18));

			JOptionPane.showMessageDialog(null, alerta, tituloSuperior, tipo);
		}

	}

}

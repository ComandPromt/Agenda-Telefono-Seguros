package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pdf.Fecha;
import pdf.Obs;
import pdf.Telefono;
import pdf.Url;

public abstract class MetodosPdf {

	public static void crearPdf(LinkedList<String> usuarios, LinkedList<String> vencimientos,
			LinkedList<String> observaciones, LinkedList<String> telefonos, String plantilla)
			throws Exception, FileNotFoundException {

		for (int i = 0; i < vencimientos.size(); i++) {
			vencimientos.set(i,
					vencimientos.get(i).substring(vencimientos.get(i).indexOf("/") + 1,
							vencimientos.get(i).lastIndexOf("/")) + "/"
							+ vencimientos.get(i).substring(0, vencimientos.get(i).indexOf("/")) + "/"
							+ vencimientos.get(i).substring(vencimientos.get(i).lastIndexOf("/") + 1,
									vencimientos.get(i).length()));
		}

		String tampleFile = "plantillas/" + plantilla;

		Map<String, Object> variables = new HashMap<String, Object>();

		List<Url> users = createUserList(usuarios);

		List<Fecha> fechas = createDateList(vencimientos);

		List<Obs> obs = createObsList(observaciones);

		List<Telefono> tlf = createTlfList(telefonos);

		variables.put("users", users);

		variables.put("vencimientos", fechas);

		variables.put("observaciones", obs);

		variables.put("telefonos", tlf);

		String htmlStr = HtmlGenerator.generate(tampleFile, variables);

		PdfGenerator.generate(htmlStr, new FileOutputStream(Metodos.extraerNombreArchivo("pdf")));

	}

	private static List<Url> createUserList(LinkedList<String> urls) {

		List<Url> users = new ArrayList<Url>();

		for (int i = 0; i < urls.size(); i++) {
			users.add(createUrl(urls.get(i)));
		}

		return users;
	}

	private static List<Fecha> createDateList(LinkedList<String> urls) {

		List<Fecha> users = new ArrayList<Fecha>();

		for (int i = 0; i < urls.size(); i++) {
			users.add(createFecha(urls.get(i)));
		}

		return users;
	}

	private static List<Telefono> createTlfList(LinkedList<String> urls) {

		List<Telefono> users = new ArrayList<Telefono>();

		for (int i = 0; i < urls.size(); i++) {
			users.add(createTlf(urls.get(i)));
		}

		return users;
	}

	private static List<Obs> createObsList(LinkedList<String> urls) {

		List<Obs> users = new ArrayList<Obs>();

		for (int i = 0; i < urls.size(); i++) {
			users.add(createObs(urls.get(i)));
		}

		return users;
	}

	private static Telefono createTlf(String urli) {

		Telefono url = new Telefono();

		url.setUsername(urli);

		return url;
	}

	private static Obs createObs(String urli) {

		Obs url = new Obs();

		url.setUsername(urli);

		return url;
	}

	private static Fecha createFecha(String urli) {

		Fecha url = new Fecha();

		url.setUsername(urli);

		return url;
	}

	private static Url createUrl(String urli) {

		Url url = new Url();

		url.setUsername(urli);

		return url;
	}

}

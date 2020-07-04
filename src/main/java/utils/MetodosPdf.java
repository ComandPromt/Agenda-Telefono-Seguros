package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pdf.Cliente;
import pdf.Fecha;
import pdf.Obs;
import pdf.Telefono;

public abstract class MetodosPdf {

	public static void crearPdf(LinkedList<String> userDeceso, LinkedList<String> tlfDeceso,
			LinkedList<String> vtoDeceso, LinkedList<String> dirDeceso, LinkedList<String> userVida,
			LinkedList<String> tlfVida, LinkedList<String> vtoVida, LinkedList<String> dirVida,
			LinkedList<String> userHogar, LinkedList<String> tlfHogar, LinkedList<String> vtoHogar,
			LinkedList<String> dirHogar, LinkedList<String> userCoche, LinkedList<String> tlfCoche,
			LinkedList<String> vtoCoche, LinkedList<String> dirCoche, LinkedList<String> userComercio,
			LinkedList<String> tlfComercio, LinkedList<String> vtoComercio, LinkedList<String> dirComercio,
			LinkedList<String> userComunidad, LinkedList<String> tlfComunidad, LinkedList<String> vtoComunidad,
			LinkedList<String> dirComunidad, String plantilla) throws Exception, FileNotFoundException {

		Date fecha = new Date();

		String hoy = Metodos.convertirFecha(fecha.toString(), false);

		String hasta = Metodos.mostrarFechaDosMeses(hoy);

		String tampleFile = "plantillas/" + plantilla;

		Map<String, Object> variables = new HashMap<String, Object>();

		List<Cliente> usuarioDeceso = createUserList(userDeceso);
		List<Cliente> usuarioVida = createUserList(userVida);
		List<Cliente> usuarioHogar = createUserList(userHogar);
		List<Cliente> usuarioCoche = createUserList(userCoche);
		List<Cliente> usuarioComercio = createUserList(userComercio);
		List<Cliente> usuarioComunidad = createUserList(userComunidad);

		List<Cliente> telefonoDeceso = createUserList(tlfDeceso);
		List<Cliente> telefonoVida = createUserList(tlfVida);
		List<Cliente> telefonoHogar = createUserList(tlfHogar);
		List<Cliente> telefonoCoche = createUserList(tlfCoche);
		List<Cliente> telefonoComercio = createUserList(tlfComercio);
		List<Cliente> telefonoComunidad = createUserList(tlfComunidad);

		List<Cliente> vencimientoDeceso = createUserList(vtoDeceso);
		List<Cliente> vencimientoVida = createUserList(vtoVida);
		List<Cliente> vencimientoHogar = createUserList(vtoHogar);
		List<Cliente> vencimientoCoche = createUserList(vtoCoche);
		List<Cliente> vencimientoComercio = createUserList(vtoComercio);
		List<Cliente> vencimientoComunidad = createUserList(vtoComunidad);

		List<Cliente> direccionDeceso = createUserList(dirDeceso);
		List<Cliente> direccionVida = createUserList(dirVida);
		List<Cliente> direccionHogar = createUserList(dirHogar);
		List<Cliente> direccionCoche = createUserList(dirCoche);
		List<Cliente> direccionComercio = createUserList(dirComercio);
		List<Cliente> direccionComunidad = createUserList(dirComunidad);

		variables.put("principio", hoy);

		variables.put("fin", hasta);

		variables.put("usuarioDeceso", usuarioDeceso);
		variables.put("usuarioVida", usuarioVida);
		variables.put("usuarioHogar", usuarioHogar);
		variables.put("usuarioCoche", usuarioCoche);
		variables.put("usuarioComercio", usuarioComercio);
		variables.put("usuarioComunidad", usuarioComunidad);

		variables.put("telefonoDeceso", telefonoDeceso);
		variables.put("telefonoVida", telefonoVida);
		variables.put("telefonoHogar", telefonoHogar);
		variables.put("telefonoCoche", telefonoCoche);
		variables.put("telefonoComercio", telefonoComercio);
		variables.put("telefonoComunidad", telefonoComunidad);

		variables.put("vencimientoDeceso", vencimientoDeceso);
		variables.put("vencimientoVida", vencimientoVida);
		variables.put("vencimientoHogar", vencimientoHogar);
		variables.put("vencimientoCoche", vencimientoCoche);
		variables.put("vencimientoComercio", vencimientoComercio);
		variables.put("vencimientoComunidad", vencimientoComunidad);

		variables.put("direccionDeceso", direccionDeceso);
		variables.put("direccionVida", direccionVida);
		variables.put("direccionHogar", direccionHogar);
		variables.put("direccionCoche", direccionCoche);
		variables.put("direccionComercio", direccionComercio);
		variables.put("direccionComunidad", direccionComunidad);

		String htmlStr = HtmlGenerator.generate(tampleFile, variables);

		PdfGenerator.generate(htmlStr, new FileOutputStream(Metodos.extraerNombreArchivo("pdf", 1)));

	}

	private static List<Cliente> createUserList(LinkedList<String> urls) {

		List<Cliente> users = new ArrayList<Cliente>();

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

	private static Cliente createUrl(String urli) {

		Cliente url = new Cliente();

		url.setUsername(urli);

		return url;
	}

}

package principal;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Ocaso {

	public static void main(String[] args) throws IOException, SQLException {

		File carpeta = new File("contactos_exportados");

		carpeta.mkdir();

		new Agenda().setVisible(true);
	}

}

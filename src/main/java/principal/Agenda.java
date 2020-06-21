package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.mangstadt.vinnie.SyntaxStyle;
import com.github.mangstadt.vinnie.io.VObjectWriter;

import utils.Metodos;
import utils.MetodosPdf;

@SuppressWarnings("all")

public class Agenda extends JFrame {
	private JButton btnNewButton = new JButton("");
	private static JLabel iconoSeguro = new JLabel("");

	private static JLabel lblNewLabel_2 = new JLabel("");

	public static JLabel nuevosVencimientos = new JLabel("");

	private JButton observacion = new JButton("Obs");
	private int indice;
	private static JTextPane vtos = new JTextPane();

	private ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();
	private static int seguro = 0;
	private int paso = 0;
	private JButton buscar;
	private static JButton contacto;
	private JButton editar;

	public static JList<String> jList1;
	private static String directorioActual, separador, os;
	private static JTextField nombre;
	public static DefaultListModel<String> modelo = new DefaultListModel<>();
	private String iduser;
	private transient ResultSet rs;
	private transient Statement s;
	private String cnombre;
	private String ctipo;
	private String cnota;
	private static JTextPane tlf = new JTextPane();
	private static JTextPane direccion = new JTextPane();
	public static LinkedList<String> vencimientosDecesos = new <String>LinkedList();
	public static LinkedList<String> vencimientosVida = new <String>LinkedList();
	public static LinkedList<String> vencimientosHogar = new <String>LinkedList();
	public static LinkedList<String> vencimientosCoche = new <String>LinkedList();
	public static LinkedList<String> vencimientosComercio = new <String>LinkedList();
	public static LinkedList<String> vencimientosComunidad = new <String>LinkedList();

	public static LinkedList<String> fechaDecesos = new <String>LinkedList();

	private static LinkedList<String> emails = new <String>LinkedList();

	public static LinkedList<String> fechaVida = new <String>LinkedList();

	public static LinkedList<String> fechaHogar = new <String>LinkedList();

	public static LinkedList<String> fechaComercio = new <String>LinkedList();

	public static LinkedList<String> fechaComunidad = new <String>LinkedList();

	public static LinkedList<String> fechaCoche = new <String>LinkedList();

	private static LinkedList<String> observaciones = new <String>LinkedList();

	public static LinkedList<String> contactos = new <String>LinkedList();

	public static void vaciarCampos() {

		Agenda.nombre.setText("");
		Agenda.tlf.setText("");
		Agenda.vtos.setText("");
		Agenda.direccion.setText("");
		Agenda.email.setText("");
	}

	private boolean yaLlamado(int tipoSeguro, String busqueda)
			throws IOException, FileNotFoundException, ClassNotFoundException {

		LinkedList<String> lectura = Metodos.leer(Metodos.saberArchivoLlamada(tipoSeguro));

		lectura = Metodos.formatearArray(lectura.get(0));

		try {

			if (lectura.contains(busqueda)) {
				return true;
			}

			else {
				return false;
			}

		}

		catch (Exception e) {
			return false;
		}

	}

	private static void verTodasLasLlamadas() {

		verNotas();

		Vencimiento.verTablaVencimientos();

		Vencimiento.ponerVencimientos(1);

		Vencimiento.ponerVencimientos(2);

		Vencimiento.ponerVencimientos(3);

		Vencimiento.ponerVencimientos(4);

		Vencimiento.ponerVencimientos(5);

		Vencimiento.ponerVencimientos(6);

	}

	private void verVencimientoLlamada(int tipoSeguro)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		verNotas();

		Vencimiento.verTablaVencimientos();

		Vencimiento.ponerVencimientos(tipoSeguro);

		int vencimientos = 0;

		switch (tipoSeguro) {

		case 1:

			vencimientos = vencimientosDecesos.size();
			break;

		case 2:
			vencimientos = vencimientosVida.size();
			break;

		case 3:
			vencimientos = vencimientosHogar.size();
			break;

		case 4:
			vencimientos = vencimientosCoche.size();
			break;

		case 5:
			vencimientos = vencimientosComercio.size();
			break;

		case 6:
			vencimientos = vencimientosComunidad.size();
			break;
		}

		if (vencimientos == 0) {

			mensajeNoHayVencimiento();

		}

		else {

			LinkedList<String> lectura = new LinkedList<String>();

			lectura = Metodos.leer(Metodos.saberArchivoLlamada(tipoSeguro));

			if (lectura.size() > 0) {

				lectura = Metodos.formatearArray(lectura.get(0));

				int comparaSeguro = saberArraySeguro(tipoSeguro);

				if (lectura.size() == comparaSeguro) {

					mensajeNoHayVencimiento();
				}

				else {

					mostrarLlamadaConSeguro(tipoSeguro);
				}

			}

			else {

				mostrarLlamadaConSeguro(tipoSeguro);
			}

		}

	}

	private void mostrarLlamadaConSeguro(int tipoSeguro) {
		new Llamada(tipoSeguro).setVisible(true);

		Llamada.lblNewLabel_1.setText(nuevosVencimientos.getText());
	}

	private String saberIcono() {
		String icono;
		icono = lblNewLabel_2.getIcon().toString();

		icono = icono.substring(icono.lastIndexOf(separador) + 1, icono.length());
		return icono;
	}

	public static int saberArraySeguro(int seguro) {

		int resultado = 0;

		switch (seguro) {

		case 1:
			resultado = vencimientosDecesos.size();
			break;

		case 2:
			resultado = vencimientosVida.size();
			break;

		case 3:
			resultado = vencimientosHogar.size();
			break;

		case 4:
			resultado = vencimientosCoche.size();
			break;

		case 5:
			resultado = vencimientosComercio.size();
			break;

		case 6:
			resultado = vencimientosComunidad.size();
			break;
		}

		return resultado;
	}

	public static void mensajeNoHayVencimiento() {

		Agenda.iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

		Agenda.lblNewLabel_2.setIcon(null);

		seguro = 0;

		Metodos.mensaje("No hay vencimientos", 3, true);

		verTodasLasLlamadas();

	}

	private void ponerEnAgenda(int tipo, boolean filtro) {

		btnNewButton.setEnabled(true);

		verNotas();

		Vencimiento.verTablaVencimientos();

		Vencimiento.ponerVencimientos(tipo);

		int vueltas = 0;

		if (!filtro) {
			iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));
		}

		switch (tipo) {

		case 1:

			if (vencimientosDecesos.size() > 0) {
				iconoSeguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/deceso.png")));
			}

			vueltas = vencimientosDecesos.size();
			break;

		case 2:
			if (vencimientosVida.size() > 0) {
				iconoSeguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/heart.png")));
			}

			vueltas = vencimientosVida.size();
			break;

		case 3:
			if (vencimientosHogar.size() > 0) {
				iconoSeguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/home.png")));
			}

			vueltas = vencimientosHogar.size();
			break;

		case 4:
			if (vencimientosCoche.size() > 0) {
				iconoSeguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/car.png")));
			}

			vueltas = vencimientosCoche.size();
			break;

		case 5:
			if (vencimientosComercio.size() > 0) {
				iconoSeguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/shop.png")));
			}

			vueltas = vencimientosComercio.size();
			break;

		case 6:

			if (vencimientosComunidad.size() > 0) {
				iconoSeguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/comunidad.png")));
			}

			vueltas = vencimientosComunidad.size();
			break;

		}

		if (vueltas == 0) {

			if (tipo > 0 && filtro) {
				Metodos.mensaje("No hay vencimientos", 3, true);

			}

			iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

		}

		else {

			tlf.setText("");

			vtos.setText("");

			LinkedList<String> contactoTelefono = new LinkedList();

			LinkedList<String> contactoDirecciones = new LinkedList();
			LinkedList<String> contactoEmail = new LinkedList();
			LinkedList<String> contactoObservaciones = new LinkedList();
			LinkedList<String> fechasDecesos = new LinkedList();

			DefaultListModel model = new DefaultListModel();

			model.clear();

			jList1.setModel(model);

			int indiceVencimiento = -1;

			for (int i = 0; i < vueltas; i++) {

				switch (tipo) {

				case 1:
					indiceVencimiento = Vencimiento.getIndiceDeceso().get(i);
					fechasDecesos.add("Deceso --> " + vencimientosDecesos.get(i));
					break;

				case 2:
					indiceVencimiento = Vencimiento.getIndiceVida().get(i);
					fechasDecesos.add("Vida --> " + vencimientosVida.get(i));
					break;

				case 3:
					indiceVencimiento = Vencimiento.getIndiceHogar().get(i);
					fechasDecesos.add("Hogar --> " + vencimientosHogar.get(i));
					break;

				case 4:
					indiceVencimiento = Vencimiento.getIndiceCoche().get(i);
					fechasDecesos.add("Coche --> " + vencimientosCoche.get(i));
					break;

				case 5:
					indiceVencimiento = Vencimiento.getIndiceComercio().get(i);
					fechasDecesos.add("Comercio --> " + vencimientosComercio.get(i));
					break;

				case 6:
					indiceVencimiento = Vencimiento.getIndiceComunidad().get(i);
					fechasDecesos.add("Comunidad --> " + vencimientosComunidad.get(i));
					break;
				}

				if (indiceVencimiento < telefonos.size()) {
					contactoTelefono.add(telefonos.get(indiceVencimiento));
					contactoDirecciones.add(direcciones.get(indiceVencimiento));
					contactoEmail.add(emails.get(indiceVencimiento));
					contactoObservaciones.add(observaciones.get(indiceVencimiento));
				}

				if (indiceVencimiento < contactos.size()) {

					model.addElement(contactos.get(indiceVencimiento));
				}

			}

			telefonos.clear();

			direcciones.clear();
			contactodirecciones.clear();
			contactocodigopostal.clear();
			contactolocalidades.clear();
			contactoprovincias.clear();
			emails.clear();

			observaciones.clear();

			emails = contactoEmail;

			observaciones = contactoObservaciones;

			direcciones = contactoDirecciones;

			telefonos = contactoTelefono;

			vencimientos.clear();

			vencimientos = fechasDecesos;

			jList1.setModel(model);
		}
	}

	public static ArrayList<Objeto> leer(String file)
			throws IOException, FileNotFoundException, ClassNotFoundException {

		ArrayList<Objeto> arrayList2 = null;

		File archivo = new File(file);

		if (archivo.exists()) {

			ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream(file));

			arrayList2 = (ArrayList<Objeto>) leyendoFichero.readObject();

			leyendoFichero.close();

		}

		return arrayList2;
	}

	private void verContactoAgenda(int indice, String contacto) {

		nombre.setEditable(true);

		try {

			nombre.setText(contacto);

			tlf.setText(telefonos.get(indice));

			email.setText(emails.get(indice));

			vtos.setText(vencimientos.get(indice));

			direccion.setText(direcciones.get(indice));

			if (observaciones.get(indice).isEmpty()) {
				observacion.setEnabled(false);
			}

			else {
				observacion.setEnabled(true);
			}

			if (seguro > 0) {

				++paso;

				if (paso % 2 != 0) {

					if (yaLlamado(seguro, nombre.getText())) {

						lblNewLabel_2.setIcon((new ImageIcon(Agenda.class.getResource("/imagenes/yallamado.png"))));
					}

					else {

						lblNewLabel_2.setIcon((new ImageIcon(Agenda.class.getResource("/imagenes/llamar.png"))));

					}

				}
			}

		} catch (Exception e1) {
//
		}
	}

	protected void guardarContactos() {

		try {

			PrintStream fileOut = new PrintStream("contactos.vcf");

			Writer writer = new OutputStreamWriter(fileOut);

			VObjectWriter vobjectWriter = new VObjectWriter(writer, SyntaxStyle.OLD);

			for (int i = 0; i < contactos.size(); i++) {

				vobjectWriter.writeBeginComponent("VCARD");

				vobjectWriter.writeVersion("2.1");

				vobjectWriter.writeProperty("N", contactos.get(i));
				vobjectWriter.writeProperty("FN", contactos.get(i));

				vobjectWriter.writeProperty("CELL", telefonos.get(i));

				vobjectWriter.writeEndComponent("VCARD");
			}

			vobjectWriter.close();

			File archivo = null;

			FileReader fr = null;

			BufferedReader br = null;

			archivo = new File("contactos.vcf");

			fr = new FileReader(archivo);

			br = new BufferedReader(fr);

			String cadena = "", linea = "";

			while ((linea = br.readLine()) != null) {

				cadena += linea + "\n";
			}

			cadena = cadena.replace("CELL:", "TEL;CELL:");

			String ruta = "contactos.vcf";

			File file = new File(ruta);

			FileWriter fw = new FileWriter(file);

			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(cadena);

			bw.close();
			fr.close();
			br.close();

		} catch (Exception e1) {
			//
		}

	}

	public static String getDirectorioActual() {
		return directorioActual;
	}

	public static void setDirectorioActual(String directorioActual) {
		Agenda.directorioActual = directorioActual;
	}

	public static String getSeparador() {
		return separador;
	}

	public static void setSeparador(String separador) {
		Agenda.separador = separador;
	}

	public LinkedList<String> getContactos() {
		return contactos;
	}

	static LinkedList<String> telefonos = new <String>LinkedList();

	static LinkedList<String> direcciones = new <String>LinkedList();

	static LinkedList<String> contactodirecciones = new <String>LinkedList();
	static LinkedList<String> contactolocalidades = new <String>LinkedList();
	static LinkedList<String> contactoprovincias = new <String>LinkedList();
	static LinkedList<String> contactocodigopostal = new <String>LinkedList();
	static LinkedList<String> vencimientos = new <String>LinkedList();

	private static JTextField email;

	protected static void limpiarContactos() {
		modelo.removeAllElements();

		jList1.setModel(modelo);
	}

	public Agenda() throws IOException, SQLException {

		arrayList1 = new ArrayList<Objeto>();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Agenda.class.getResource("/imagenes/agenda.png")));

		os = System.getProperty("os.name");

		separador = Metodos.saberSeparador(os);

		initComponents();

		Metodos.cearCarpetas();

		verTodasLasLlamadas();

		btnNewButton.setEnabled(false);

		directorioActual = new File(".").getCanonicalPath() + separador;

		setResizable(false);

		setAutoRequestFocus(false);

		this.setSize(new Dimension(760, 700));

		buscar.setToolTipText("Buscar");

		contacto.setToolTipText("Mostrar todos los contactos");

		editar.setToolTipText("Editar");

		this.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenu mnAcciones = new JMenu("Acciones");
		mnAcciones.setForeground(Color.BLACK);
		mnAcciones.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnAcciones.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/utilities.png")));
		menuBar.add(mnAcciones);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Insertar");
		mnAcciones.add(mntmNewMenuItem_1);

		mntmNewMenuItem_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/insert.png")));

		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {
					new Nuevo().setVisible(true);
				}

				catch (Exception e1) {

				}

			}

		});

		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JSeparator separator_2 = new JSeparator();
		mnAcciones.add(separator_2);

		JMenuItem mntmNewMenuItem = new JMenuItem("Actualizar");
		mnAcciones.add(mntmNewMenuItem);

		mntmNewMenuItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				try {

					int indice = jList1.getSelectedIndex();

					String datoContacto = Metodos.eliminarEspacios(nombre.getText());

					if (indice >= 0) {

						if (contactos.get(indice).equals(datoContacto)) {
							Metodos.mensaje("Debes modificar un dato", 3, true);

						}

						else {

							if (true) {
								Metodos.mensaje("Teléfono incorrecto", 3, true);
							}

							else {

								arrayList1.clear();

								Metodos.eliminarFichero("contactos.dat");

								contactos.set(indice, datoContacto);

								for (int i = 0; i < contactos.size(); i++) {

									// actualizao campos

//									fecha = new Date(fechaDecesos.get(i).toString());
//
//									arrayList1.add(new Objeto(contactos.get(i) + "«" + fecha + "»"
//											+ observaciones.get(i) + "¬" + telefonos.get(i)));

								}

								ObjectOutputStream escribiendoFichero;

								escribiendoFichero = new ObjectOutputStream(new FileOutputStream("contactos.dat"));

								escribiendoFichero.writeObject(arrayList1);

								escribiendoFichero.close();
							}
						}

					}
				}

				catch (Exception e1) {

				}

			}

		});

		mntmNewMenuItem.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/actualizar.png")));
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JSeparator separator_4 = new JSeparator();
		mnAcciones.add(separator_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Eliminar");
		mnAcciones.add(mntmNewMenuItem_5);
		mntmNewMenuItem_5.setSelectedIcon(null);
		mntmNewMenuItem_5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/delete.png")));

		mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				String contacto = jList1.getSelectedValue();

				if (contacto != null) {

					if (JOptionPane.showConfirmDialog(null, "Quieres borrar a " + contacto, "Borrar contactos",
							JOptionPane.YES_NO_OPTION) == 0) {

						vaciarCampos();

						vencimientosDecesos.clear();

						vencimientosVida.clear();

						vencimientosHogar.clear();

						vencimientosCoche.clear();

						vencimientosComercio.clear();

						vencimientosComunidad.clear();

						int indice = jList1.getSelectedIndex();

						if (indice >= 0 && contactos.get(indice).equals(contacto)) {

							verNotas();

							nombre.setText("");

							arrayList1.clear();

							if (contactos.size() > 0) {

								Metodos.eliminarFichero("contactos.dat");

								Metodos.eliminarFichero("fechasDecesos.dat");
								Metodos.eliminarFichero("fechasVida.dat");
								Metodos.eliminarFichero("fechasHogar.dat");
								Metodos.eliminarFichero("fechasCoche.dat");
								Metodos.eliminarFichero("fechasComercio.dat");
								Metodos.eliminarFichero("fechasComunidad.dat");

								for (int i = 0; i < contactos.size(); i++) {

									if (!contacto.equals(contactos.get(i))) {

										arrayList1.add(new Objeto(contactos.get(i) + "«" + emails.get(i) + "»"
												+ observaciones.get(i) + "¬" + telefonos.get(i) + "═"
												+ contactodirecciones.get(i) + "▓" + contactolocalidades.get(i) + "░"
												+ contactocodigopostal.get(i) + "┤" + contactoprovincias.get(i) + "▒"
												+ fechaDecesos.get(i) + "╣" + fechaVida.get(i) + "║" + fechaHogar.get(i)
												+ "╝" + fechaCoche.get(i) + "¥" + fechaComercio.get(i) + "¶"
												+ fechaComunidad.get(i)));

									}

								}

								ObjectOutputStream escribiendoFichero;

								try {

									escribiendoFichero = new ObjectOutputStream(new FileOutputStream("contactos.dat"));

									escribiendoFichero.writeObject(arrayList1);

									escribiendoFichero.close();
									vaciarCampos();
									verNotas();

									Vencimiento.actualizarVencimientos("fechasDecesos.dat", 1);

									Vencimiento.actualizarVencimientos("fechasVida.dat", 2);

									Vencimiento.actualizarVencimientos("fechasHogar.dat", 3);

									Vencimiento.actualizarVencimientos("fechasCoche.dat", 4);

									Vencimiento.actualizarVencimientos("fechasComercio.dat", 5);

									Vencimiento.actualizarVencimientos("fechasComunidad.dat", 6);

									for (int x = 1; x <= 6; x++) {
										ponerEnAgenda(x, false);
									}

								}

								catch (Exception e1) {
									Metodos.mensaje("Error", 1, true);
								}

							}

						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mntmNewMenuItem_5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/delete_1.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mntmNewMenuItem_5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/delete.png")));

			}

		});

		mntmNewMenuItem_5.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JMenu mnNewMenu_2 = new JMenu("Ver Vencimientos");
		mnNewMenu_2.setForeground(Color.BLACK);
		mnNewMenu_2.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_2.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/view.png")));
		menuBar.add(mnNewMenu_2);

		JMenu mnNewMenu_3 = new JMenu("Deceso");
		mnNewMenu_3.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_3.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/deceso.png")));
		mnNewMenu_2.add(mnNewMenu_3);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("En Agenda");
		mntmNewMenuItem_8.setFont(new Font("Dialog", Font.PLAIN, 16));

		mnNewMenu_3.add(mntmNewMenuItem_8);

		mntmNewMenuItem_8.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				seguro = 1;

				ponerEnAgenda(seguro, true);

			}

		});

		mntmNewMenuItem_8.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));

		JSeparator separator_11 = new JSeparator();

		mnNewMenu_3.add(separator_11);

		JMenuItem mntmNewMenuItem_16 = new JMenuItem("En Llamadas");

		mntmNewMenuItem_16.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					seguro = 0;
					verVencimientoLlamada(1);
				} catch (Exception e1) {
					//
				}

			}

		});

		mntmNewMenuItem_16.setFont(new Font("Dialog", Font.PLAIN, 16));

		mntmNewMenuItem_16.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));

		mnNewMenu_3.add(mntmNewMenuItem_16);

		JSeparator separator_5 = new JSeparator();

		mnNewMenu_2.add(separator_5);

		JMenu mnNewMenu_4 = new JMenu("Vida");

		mnNewMenu_4.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/heart.png")));
		mnNewMenu_4.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_2.add(mnNewMenu_4);

		JMenuItem mntmNewMenuItem_9 = new JMenuItem("En Agenda");

		mnNewMenu_4.add(mntmNewMenuItem_9);

		mntmNewMenuItem_9.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {
				seguro = 2;
				ponerEnAgenda(seguro, true);

			}

		});

		mntmNewMenuItem_9.setFont(new Font("Dialog", Font.PLAIN, 16));

		mntmNewMenuItem_9.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));

		JMenuItem mntmNewMenuItem_16_1 = new JMenuItem("En Llamadas");
		mntmNewMenuItem_16_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));

		mntmNewMenuItem_16_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					seguro = 0;
					verVencimientoLlamada(2);
				} catch (Exception e1) {
					//
				}
			}

		});

		JSeparator separator_12 = new JSeparator();
		mnNewMenu_4.add(separator_12);

		mntmNewMenuItem_16_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_4.add(mntmNewMenuItem_16_1);

		JSeparator separator_6 = new JSeparator();
		mnNewMenu_2.add(separator_6);

		JMenu mnNewMenu_5 = new JMenu("Hogar");
		mnNewMenu_5.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/home.png")));
		mnNewMenu_2.add(mnNewMenu_5);

		JMenuItem mntmNewMenuItem_10 = new JMenuItem("En Agenda");
		mnNewMenu_5.add(mntmNewMenuItem_10);
		mntmNewMenuItem_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				seguro = 3;
				ponerEnAgenda(seguro, true);
			}
		});
		mntmNewMenuItem_10.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_10.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));

		JMenuItem mntmNewMenuItem_17 = new JMenuItem("En Llamadas");
		mntmNewMenuItem_17.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_17.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));
		mntmNewMenuItem_17.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					seguro = 0;
					verVencimientoLlamada(3);
				} catch (Exception e1) {
					//
				}
			}
		});

		JSeparator separator_13 = new JSeparator();
		mnNewMenu_5.add(separator_13);
		mnNewMenu_5.add(mntmNewMenuItem_17);

		JSeparator separator_7 = new JSeparator();
		mnNewMenu_2.add(separator_7);

		JMenu mnNewMenu_6 = new JMenu("Coche");
		mnNewMenu_6.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_6.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/car.png")));
		mnNewMenu_2.add(mnNewMenu_6);

		JMenuItem mntmNewMenuItem_11 = new JMenuItem("En Agenda");
		mnNewMenu_6.add(mntmNewMenuItem_11);
		mntmNewMenuItem_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				seguro = 4;
				ponerEnAgenda(seguro, true);
			}
		});
		mntmNewMenuItem_11.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_11.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));

		JMenuItem mntmNewMenuItem_18 = new JMenuItem("En Llamadas");
		mntmNewMenuItem_18.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_18.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));
		mntmNewMenuItem_18.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					seguro = 0;
					verVencimientoLlamada(4);
				} catch (Exception e1) {
					//
				}
			}
		});

		JSeparator separator_14 = new JSeparator();
		mnNewMenu_6.add(separator_14);
		mnNewMenu_6.add(mntmNewMenuItem_18);

		JSeparator separator_8 = new JSeparator();
		mnNewMenu_2.add(separator_8);

		JMenu mnNewMenu_7 = new JMenu("Comercio");
		mnNewMenu_7.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_7.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/shop.png")));
		mnNewMenu_2.add(mnNewMenu_7);

		JMenuItem mntmNewMenuItem_12 = new JMenuItem("En Agenda");
		mnNewMenu_7.add(mntmNewMenuItem_12);
		mntmNewMenuItem_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				seguro = 5;
				ponerEnAgenda(seguro, true);
			}
		});
		mntmNewMenuItem_12.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));
		mntmNewMenuItem_12.setFont(new Font("Dialog", Font.PLAIN, 16));

		JMenuItem mntmNewMenuItem_19 = new JMenuItem("En Llamadas");
		mntmNewMenuItem_19.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_19.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));
		mntmNewMenuItem_19.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					seguro = 0;
					verVencimientoLlamada(5);
				} catch (Exception e1) {
					//
				}
			}
		});

		JSeparator separator_15 = new JSeparator();
		mnNewMenu_7.add(separator_15);
		mnNewMenu_7.add(mntmNewMenuItem_19);

		JSeparator separator_9 = new JSeparator();
		mnNewMenu_2.add(separator_9);

		JMenu mnNewMenu_8 = new JMenu("Comunidad");
		mnNewMenu_8.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_8.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/comunidad.png")));
		mnNewMenu_2.add(mnNewMenu_8);

		JMenuItem mntmNewMenuItem_13 = new JMenuItem("En Agenda");
		mnNewMenu_8.add(mntmNewMenuItem_13);
		mntmNewMenuItem_13.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				seguro = 6;
				ponerEnAgenda(seguro, true);
			}
		});
		mntmNewMenuItem_13.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));
		mntmNewMenuItem_13.setFont(new Font("Dialog", Font.PLAIN, 16));

		JMenuItem mntmNewMenuItem_20 = new JMenuItem("En Llamadas");
		mntmNewMenuItem_20.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_20.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));
		mntmNewMenuItem_20.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					seguro = 0;
					verVencimientoLlamada(6);
				} catch (Exception e1) {
					//
				}
			}
		});

		JSeparator separator_16 = new JSeparator();
		mnNewMenu_8.add(separator_16);
		mnNewMenu_8.add(mntmNewMenuItem_20);

		JSeparator separator_18 = new JSeparator();
		mnNewMenu_2.add(separator_18);

		JMenu mnNewMenu_9 = new JMenu("Todos");
		mnNewMenu_9.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_9.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));
		mnNewMenu_2.add(mnNewMenu_9);

		JMenuItem mntmNewMenuItem_14 = new JMenuItem("En Agenda");
		mnNewMenu_9.add(mntmNewMenuItem_14);
		mntmNewMenuItem_14.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

				lblNewLabel_2.setIcon(null);

				btnNewButton.setEnabled(false);

				seguro = 0;

				verTodasLasLlamadas();

			}

		});

		mntmNewMenuItem_14.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));
		mntmNewMenuItem_14.setFont(new Font("Dialog", Font.PLAIN, 16));

		JMenuItem mntmNewMenuItem_15 = new JMenuItem("En Llamadas");
		mntmNewMenuItem_15.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_15.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));
		mntmNewMenuItem_15.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				seguro = 0;
				verNotas();

				new Llamada(0).setVisible(true);
			}
		});

		JSeparator separator_17 = new JSeparator();
		mnNewMenu_9.add(separator_17);
		mnNewMenu_9.add(mntmNewMenuItem_15);

		JMenu mnNewMenu = new JMenu("Importar");
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.setForeground(Color.BLACK);
		mnNewMenu.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/import.png")));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Vcard");

		mntmNewMenuItem_6.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				new ImportarVcard().setVisible(true);
			}

		});

		mntmNewMenuItem_6.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_6.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/vcard.png")));
		mnNewMenu.add(mntmNewMenuItem_6);
		JMenu mnNewMenu_1 = new JMenu("Exportar");
		mnNewMenu_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/config.png")));
		mnNewMenu_1.setForeground(Color.BLACK);
		mnNewMenu_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("PDF");
		mntmNewMenuItem_2.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/pdf.png")));

		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					MetodosPdf.crearPdf(contactos, telefonos, observaciones, telefonos, "template-verurl.html");

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		});

		mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		mnNewMenu_1.add(mntmNewMenuItem_2);

		JSeparator separator = new JSeparator();
		mnNewMenu_1.add(separator);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Excel");
		mntmNewMenuItem_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_3.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/excel.png")));
		mnNewMenu_1.add(mntmNewMenuItem_3);

		JSeparator separator_3 = new JSeparator();
		mnNewMenu_1.add(separator_3);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Txt");

		mntmNewMenuItem_4.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/txt.png")));

		mntmNewMenuItem_4.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		mnNewMenu_1.add(mntmNewMenuItem_4);

		JSeparator separator_1 = new JSeparator();

		mnNewMenu_1.add(separator_1);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("VCard");

		mntmNewMenuItem_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				guardarContactos();
			}
		});

		mntmNewMenuItem_7.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		mntmNewMenuItem_7.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/vcard.png")));

		mntmNewMenuItem_7.setSelectedIcon(null);

		mnNewMenu_1.add(mntmNewMenuItem_7);

		JMenuItem mntmNewMenuItem_21 = new JMenuItem("Sobre");
		mntmNewMenuItem_21.setFont(new Font("Dialog", Font.PLAIN, 16));

		mntmNewMenuItem_21.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					About effect = new About();
					effect.start();

				} catch (Exception e1) {

				}
			}

		});

		JMenu mnNewMenu_10 = new JMenu("Abrir Exportaciones");
		mnNewMenu_10.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_10.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/folder.png")));
		menuBar.add(mnNewMenu_10);

		JMenuItem mntmNewMenuItem_22 = new JMenuItem("PDF");
		mntmNewMenuItem_22.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnNewMenu_10.add(mntmNewMenuItem_22);
		mntmNewMenuItem_22.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_22.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/pdf.png")));

		JSeparator separator_10 = new JSeparator();
		mnNewMenu_10.add(separator_10);

		JMenuItem mntmNewMenuItem_23 = new JMenuItem("Excel");
		mntmNewMenuItem_23.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mntmNewMenuItem_23.setFont(new Font("Dialog", Font.PLAIN, 16));
		mntmNewMenuItem_23.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/excel.png")));
		mnNewMenu_10.add(mntmNewMenuItem_23);

		JSeparator separator_19 = new JSeparator();
		mnNewMenu_10.add(separator_19);

		JMenuItem mntmNewMenuItem_24 = new JMenuItem("TXT");
		mntmNewMenuItem_24.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mntmNewMenuItem_24.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/txt.png")));
		mntmNewMenuItem_24.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_10.add(mntmNewMenuItem_24);

		JSeparator separator_20 = new JSeparator();
		mnNewMenu_10.add(separator_20);

		JMenuItem mntmNewMenuItem_25 = new JMenuItem("VCard");
		mntmNewMenuItem_25.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mntmNewMenuItem_25.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/vcard.png")));
		mntmNewMenuItem_25.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_10.add(mntmNewMenuItem_25);

		mntmNewMenuItem_21.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/about.png")));

		menuBar.add(mntmNewMenuItem_21);

	}

	private void initComponents() throws SQLException, IOException {

		JLabel jLabel1;
		JPanel jPanel3;
		JPanel jPanel4;
		JPanel jPanel5;

		jList1 = new JList<>();

		jList1.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				verContactoAgenda(jList1.getSelectedIndex(), jList1.getSelectedValue());

			}

		});

		jPanel4 = new JPanel();
		jPanel5 = new JPanel();
		jLabel1 = new JLabel();
		contacto = new JButton();
		contacto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		editar = new JButton();
		editar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buscar = new JButton();
		buscar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jPanel3 = new JPanel();
		JScrollPane jScrollPane1;
		JPanel panelCasa;
		jScrollPane1 = new JScrollPane();
		jList1.setFont(new Font("Dialog", Font.PLAIN, 18));
		jList1.setFixedCellHeight(40);

		jPanel5.setBackground(new java.awt.Color(88, 205, 170));

		jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel1.setText("Nombre");

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
						.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 481, GroupLayout.PREFERRED_SIZE)));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE));

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanel4Layout
								.createSequentialGroup().addComponent(jPanel5, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		setTitle("Contactos");
		setBackground(new java.awt.Color(123, 123, 123));

		jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane1.setDoubleBuffered(true);

		jList1.setBackground(new java.awt.Color(254, 254, 254));

		jScrollPane1.setViewportView(jList1);
		JLabel jLabel3;
		JLabel jLabel5;
		panelCasa = new JPanel();
		jLabel5 = new JLabel();
		jLabel5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabel5.setText("Vtos");

		panelCasa.setBackground(new Color(240, 240, 240));

		jLabel5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/tiempo.png")));

		jLabel3 = new JLabel();
		jLabel3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabel3.setText("Nombre");

		jLabel3.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/user.png")));

		nombre = new JTextField();
		nombre.setHorizontalAlignment(SwingConstants.LEFT);
		nombre.setBackground(new Color(255, 255, 255));
		nombre.setEditable(false);
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel = new JLabel("Tlfs");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/telefono.png")));

		vtos.setFont(new Font("Tahoma", Font.PLAIN, 14));

		tlf.setFont(new Font("Tahoma", Font.PLAIN, 16));

		direccion.setFont(new Font("Tahoma", Font.PLAIN, 14));

		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 14));
		email.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/email.png")));

		JLabel lblNewLabel_1_1 = new JLabel("Dirección");
		lblNewLabel_1_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/city.png")));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		observacion.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/view.png")));

		observacion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				indice = jList1.getSelectedIndex();
				if (indice > -1) {
					Metodos.mensaje(observaciones.get(indice), 2, true);
				}
			}

		});

		observacion.setFont(new Font("Tahoma", Font.BOLD, 16));

		JButton btnAadirObservacion = new JButton("+- Obs");
		btnAadirObservacion.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/obs.png")));

		btnAadirObservacion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}

		});

		btnAadirObservacion.setFont(new Font("Tahoma", Font.BOLD, 16));

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {
					String nombreContacto = jList1.getSelectedValue();
					int indice = jList1.getSelectedIndex();

					if (indice > -1 && seguro > 0) {

						String icono = "";
						String archivo = "";

						archivo = Metodos.saberArchivoLlamada(seguro);

						icono = saberIcono();

						if (icono.equals("llamar.png")) {

							lblNewLabel_2.setIcon((new ImageIcon(Agenda.class.getResource("/imagenes/yallamado.png"))));

							guardarLlamadas(archivo, false);

						}

						else {

							lblNewLabel_2.setIcon((new ImageIcon(Agenda.class.getResource("/imagenes/llamar.png"))));
							guardarLlamadas(archivo, true);
						}

						ponerEnAgenda(seguro, true);

						verContactoAgenda(indice, nombreContacto);

					}

					else {
						Metodos.mensaje("Por favor, selecciona un contacto", 3, true);
					}

				} catch (Exception e1) {
					//
				}
			}

			private void guardarLlamadas(String archivo, boolean borrar)
					throws IOException, FileNotFoundException, ClassNotFoundException {
				try {
					LinkedList<String> llamadasVto = new LinkedList<String>();

					llamadasVto = Metodos.leer(archivo);

					llamadasVto = Metodos.formatearArray(llamadasVto.get(0));

					String contacto = jList1.getSelectedValue();

					if (borrar) {
						llamadasVto.remove(contacto);
					} else {
						llamadasVto.add(contacto);

					}

					Metodos.eliminarFichero(archivo);

					ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream(archivo));

					escribiendoFichero.writeObject(llamadasVto);

					escribiendoFichero.close();

				}

				catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		btnNewButton.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));

		GroupLayout panelCasaLayout = new GroupLayout(panelCasa);
		panelCasaLayout.setHorizontalGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(panelCasaLayout.createSequentialGroup().addContainerGap().addGroup(panelCasaLayout
						.createParallelGroup(Alignment.LEADING).addComponent(jLabel3)
						.addComponent(btnAadirObservacion, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel).addComponent(jLabel5)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)).addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(email, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
								.addComponent(direccion, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
								.addComponent(tlf, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
								.addGroup(panelCasaLayout.createSequentialGroup().addComponent(observacion)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel_2)
										.addGap(5))
								.addComponent(nombre, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
								.addComponent(vtos, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
						.addGap(52)));
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (saberIcono().equals("llamar.png")) {
					Metodos.mensaje("Debes llamar a este contacto", 2, true);
				} else {
					Metodos.mensaje("Ya has llamado a este contacto", 2, true);
				}

			}
		});
		panelCasaLayout.setVerticalGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(panelCasaLayout.createSequentialGroup().addContainerGap().addGroup(panelCasaLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAadirObservacion, GroupLayout.PREFERRED_SIZE, 41,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(observacion, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_2)).addGap(22)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addComponent(nombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(
								ComponentPlacement.RELATED)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING).addGroup(panelCasaLayout
								.createSequentialGroup().addGap(6)
								.addComponent(tlf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(vtos, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
								.addGap(7)
								.addComponent(direccion, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(20))
								.addGroup(panelCasaLayout.createSequentialGroup().addComponent(lblNewLabel).addGap(70)
										.addComponent(jLabel5)
										.addPreferredGap(ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
										.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 49,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()))));
		lblNewLabel_2.setIcon(null);

		panelCasa.setLayout(panelCasaLayout);
		nuevosVencimientos.setFont(new Font("Dialog", Font.BOLD, 14));

		nuevosVencimientos.setHorizontalAlignment(SwingConstants.RIGHT);

		iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap(37, Short.MAX_VALUE)
						.addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(nuevosVencimientos, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(jPanel3Layout.createSequentialGroup()
										.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
												.addGroup(jPanel3Layout.createSequentialGroup().addGap(65)
														.addComponent(iconoSeguro).addGap(132))
												.addGroup(Alignment.TRAILING,
														jPanel3Layout.createSequentialGroup()
																.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE,
																		233, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.UNRELATED)))
										.addComponent(panelCasa, GroupLayout.PREFERRED_SIZE, 424,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(49, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel3Layout
				.createSequentialGroup()
				.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel3Layout.createSequentialGroup().addComponent(nuevosVencimientos)
								.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(jPanel3Layout.createSequentialGroup().addGap(3)
												.addComponent(panelCasa, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel3Layout.createSequentialGroup().addGap(51)
												.addComponent(jScrollPane1))))
						.addComponent(iconoSeguro))
				.addGap(23)));
		jPanel3.setLayout(jPanel3Layout);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 755, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(340, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 603, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(69, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);

		if (jList1.getModel().getSize() == 0) {

			try {

				Agenda.limpiarContactos();

				verNotas();

				Vencimiento.verTablaVencimientos();

			} catch (Exception e) {
				//
			}
		}

		pack();
	}

	public static void setfechaDecesos(String fecha) {
		Agenda.fechaDecesos.add(fecha);
	}

	public static void verNotas() {

		String nota;

		ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

		ArrayList<Objeto> arrayList2;

		LinkedList<String> agenda = new <String>LinkedList();

		try {

			limpiarContactos();

			contactos.clear();

			observaciones.clear();

			fechaDecesos.clear();

			emails.clear();

			fechaVida.clear();

			fechaHogar.clear();

			fechaCoche.clear();
			fechaComercio.clear();
			fechaComunidad.clear();
			telefonos.clear();

			direcciones.clear();

			vencimientos.clear();

			contactodirecciones.clear();
			contactocodigopostal.clear();
			contactolocalidades.clear();
			contactoprovincias.clear();

			arrayList2 = leer("contactos.dat");

			String correo, cadena, fechaDeceso, datoVida, datoHogar, datoCoche, datoComercio, datoComunidad, obs,
					telefono;

			String cliente, direccion, localidad, codPostal, provincia;

			if (arrayList2 != null) {

				for (int i = 0; i < arrayList2.size(); i++) {

					agenda.add(arrayList2.get(i).toString());
				}

				for (int i = 0; i < agenda.size(); i++) {

					cadena = agenda.get(i);

					fechaDeceso = cadena.substring(cadena.indexOf("▒") + 1, cadena.indexOf("╣"));

					datoVida = cadena.substring(cadena.indexOf("╣") + 1, cadena.indexOf("║"));

					datoHogar = cadena.substring(cadena.indexOf("║") + 1, cadena.indexOf("╝"));

					datoCoche = cadena.substring(cadena.indexOf("╝") + 1, cadena.indexOf("¥"));

					datoComercio = cadena.substring(cadena.indexOf("¥") + 1, cadena.indexOf("¶"));

					datoComunidad = cadena.substring(cadena.indexOf("¶") + 1, cadena.length());

					obs = cadena.substring(cadena.indexOf("»") + 1, cadena.indexOf("¬"));

					telefono = cadena.substring(cadena.indexOf("¬") + 1, cadena.indexOf("═"));

					cliente = cadena.substring(0, cadena.indexOf("«"));

					correo = cadena.substring(cadena.indexOf("«") + 1, cadena.indexOf("»"));

					direccion = cadena.substring(cadena.indexOf("═") + 1, cadena.indexOf("▓"));

					provincia = cadena.substring(cadena.indexOf("┤") + 1, cadena.indexOf("▒"));

					localidad = cadena.substring(cadena.indexOf("▓") + 1, cadena.indexOf("░"));

					codPostal = cadena.substring(cadena.indexOf("░") + 1, cadena.indexOf("┤"));

					modelo.addElement(cliente);

					contactos.add(cliente);

					telefonos.add(telefono);

					observaciones.add(obs);

					fechaDecesos.add(Metodos.convertirFecha(fechaDeceso, false));

					emails.add(correo);

					fechaVida.add(Metodos.convertirFecha(datoVida, false));

					fechaHogar.add(Metodos.convertirFecha(datoHogar, false));

					fechaCoche.add(Metodos.convertirFecha(datoCoche, false));

					fechaComercio.add(Metodos.convertirFecha(datoComercio, false));

					fechaComunidad.add(Metodos.convertirFecha(datoComunidad, false));

					vencimientos.add("Deceso --> " + fechaDecesos.getLast() + "\n\n" + "Vida --> " + fechaVida.getLast()
							+ "\n\n" + "Hogar --> " + fechaHogar.getLast() + "\n\n" + "Coche --> "
							+ fechaCoche.getLast() + "\n\n" + "Comercio --> " + fechaComercio.getLast() + "\n\n"
							+ "Comunidad --> " + fechaComunidad.getLast());

					if (direccion.isEmpty() || direccion.isEmpty() && localidad.isEmpty()
							|| direccion.isEmpty() && codPostal.isEmpty()
							|| direccion.isEmpty() && provincia.isEmpty()) {
						direcciones.add("");
						contactodirecciones.add("");
						contactocodigopostal.add("");
						contactolocalidades.add("");
						contactoprovincias.add("");
					}

					else {

						if (!codPostal.isEmpty()) {
							codPostal = " (" + codPostal + ")";
						}

						direcciones.add(direccion + "\n\n" + localidad + codPostal + "\n\n" + provincia);
						contactodirecciones.add(direccion);
						contactocodigopostal.add(codPostal);
						contactolocalidades.add(localidad);
						contactoprovincias.add(provincia);
					}

				}

			}

			jList1.setModel(modelo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LinkedList<String> getfechaDecesos() {
		return fechaDecesos;
	}

	public boolean controlarSeleccion() {
		try {
			jList1.getSelectedValue().toString();
			return false;
		} catch (NullPointerException e1) {
			return true;
		}
	}

	public static LinkedList<String> getFechaComercio() {
		return fechaComercio;
	}

	public static LinkedList<String> getFechaComunidad() {
		return fechaComunidad;
	}

	public static void main(String[] args) {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					new Agenda().setVisible(true);
				} catch (IOException | SQLException e) {
					//
				}

			}
		});

	}

	public JTextPane getTlf() {
		return tlf;
	}

	public void setTlf(JTextPane tlf) {
		this.tlf = tlf;
	}

	public static LinkedList<String> getFechaDecesos() {
		return fechaDecesos;
	}

	public static void setFechaDecesos(LinkedList<String> fechaDecesos) {
		Agenda.fechaDecesos = fechaDecesos;
	}

	public static LinkedList<String> getEmails() {
		return emails;
	}

	public static void setEmails(LinkedList<String> emails) {
		Agenda.emails = emails;
	}

	public static LinkedList<String> getFechaVida() {
		return fechaVida;
	}

	public static void setFechaVida(String fecha) {
		Agenda.fechaVida.add(fecha);
	}

	public static LinkedList<String> getFechaHogar() {
		return fechaHogar;
	}

	public static void setFechaHogar(String fecha) {
		Agenda.fechaHogar.add(fecha);
	}

	public static void setFechaComunidad(String fecha) {
		Agenda.fechaComunidad.add(fecha);
	}

	public static void setFechaComercio(String fecha) {
		Agenda.fechaComercio.add(fecha);
	}

	public static LinkedList<String> getFechaCoche() {
		return fechaCoche;
	}

	public static void setFechaCoche(String fecha) {
		Agenda.fechaCoche.add(fecha);

	}

	public static LinkedList<String> getTelefonos() {
		return telefonos;
	}

	public static void setTelefonos(LinkedList<String> telefonos) {
		Agenda.telefonos = telefonos;
	}
}
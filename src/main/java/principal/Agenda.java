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
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	JButton observacion = new JButton("Ver Observacion");
	int indice;
	JTextPane vtos = new JTextPane();
	Timer t = new Timer();
	public static Llamada mostrarLlamada = new Llamada();
	Vencimiento mTask = new Vencimiento();
	ArrayList<Objeto> arrayList1;
	private JButton buscar;
	private static JButton contacto;
	private JButton editar;
	Date fecha;
	public static JList<String> jList1;
	static String directorioActual, separador, os;
	private static JTextField nombre;
	public static DefaultListModel<String> modelo = new DefaultListModel<>();
	private static LinkedList<String> notas = new LinkedList<>();
	String iduser;
	transient ResultSet rs;
	transient Statement s;
	String cnombre;
	String ctipo;
	String cnota;
	JTextPane tlf = new JTextPane();
	JTextPane direccion = new JTextPane();

	public static void vaciarCampos() {
		Agenda.nombre.setText("");

	}

	public void vaciarDatos() {
		nombre.setText("");

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

	static LinkedList<String> vencimientosDecesos = new <String>LinkedList();

	static LinkedList<String> fechaDecesos = new <String>LinkedList();

	static LinkedList<String> emails = new <String>LinkedList();

	static LinkedList<String> fechaVida = new <String>LinkedList();

	static LinkedList<String> fechaHogar = new <String>LinkedList();

	static LinkedList<String> fechaCoche = new <String>LinkedList();

	static LinkedList<String> observaciones = new <String>LinkedList();

	static LinkedList<String> contactos = new <String>LinkedList();

	static LinkedList<String> telefonos = new <String>LinkedList();

	static LinkedList<String> direcciones = new <String>LinkedList();

	static LinkedList<String> vencimientos = new <String>LinkedList();

	private JTextField email;

	protected static void limpiarContactos() {
		modelo.removeAllElements();

		jList1.setModel(modelo);
	}

	static String convertirFecha(String cadena, boolean tipo) {

		String fecha = "";

		if (!cadena.equals("null")) {

			String mes = "", year = "";

			int dia;

			int limiteMes = cadena.indexOf(" ") + 4;

			year = cadena.substring(cadena.lastIndexOf(" ") + 1, cadena.length());

			mes = cadena.substring(cadena.indexOf(" ") + 1, limiteMes);

			cadena = cadena.substring(limiteMes + 1, cadena.length());

			dia = Integer.parseInt(cadena.substring(0, cadena.indexOf(" ")));

			int mesFecha = 0;

			switch (mes) {

			case "Jan":
				mesFecha = 1;
				break;

			case "Feb":
				mesFecha = 2;
				break;

			case "Mar":
				mesFecha = 3;
				break;

			case "Apr":
				mesFecha = 4;
				break;

			case "May":
				mesFecha = 5;
				break;

			case "Jun":
				mesFecha = 6;
				break;

			case "Jul":
				mesFecha = 7;
				break;

			case "Aug":
				mesFecha = 8;
				break;

			case "Sep":
				mesFecha = 9;
				break;

			case "Oct":
				mesFecha = 10;
				break;

			case "Nov":
				mesFecha = 11;
				break;

			case "Dec":
				mesFecha = 12;
				break;

			default:
				break;

			}

			String mesCorto = "";

			String diaCorto = "";

			if (mesFecha <= 9) {
				mesCorto = "0";
			}

			if (dia <= 9) {
				diaCorto = "0";
			}

			if (tipo) {
				fecha = mesCorto + mesFecha + "/" + diaCorto + dia + "/" + year;
			} else {
				fecha = diaCorto + dia + "/" + mesCorto + mesFecha + "/" + year;
			}

		}

		return fecha;
	}

	public Agenda() throws IOException, SQLException {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Agenda.class.getResource("/imagenes/agenda.png")));

		os = System.getProperty("os.name");

		separador = Metodos.saberSeparador(os);

		initComponents();

		directorioActual = new File(".").getCanonicalPath() + separador;

		setResizable(false);

		setAutoRequestFocus(false);

		this.setSize(new Dimension(760, 600));

		buscar.setToolTipText("Buscar");

		contacto.setToolTipText("Mostrar todos los contactos");

		editar.setToolTipText("Editar");

		this.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Insertar");
		mntmNewMenuItem_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/insert.png")));
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new Nuevo().setVisible(true);
			}
		});

		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		menuBar.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem = new JMenuItem("Actualizar");

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
		menuBar.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Eliminar");
		mntmNewMenuItem_5.setSelectedIcon(null);
		mntmNewMenuItem_5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/delete.png")));

		mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				String contacto = jList1.getSelectedValue();

				if (contacto != null) {

					if (JOptionPane.showConfirmDialog(null, "Quieres borrar a " + contacto, "Borrar contactos",
							JOptionPane.YES_NO_OPTION) == 0) {

						int indice = jList1.getSelectedIndex();

						if (indice >= 0 && contactos.get(indice).equals(contacto)) {

							contactos.remove(indice);

							observaciones.remove(indice);

							fechaDecesos.remove(indice);
							emails.remove(indice);
							fechaVida.remove(indice);
							fechaHogar.remove(indice);
							fechaCoche.remove(indice);
							direcciones.remove(indice);
							telefonos.remove(indice);
							vencimientos.remove(indice);
							nombre.setText("");

							limpiarContactos();

							Metodos.eliminarFichero("contactos.dat");

							arrayList1.clear();

							if (contactos.size() > 0) {

								for (int i = 0; i < contactos.size(); i++) {

//									fecha = new Date(fechaDecesos.get(i).toString());
//
//									arrayList1.add(new Objeto(contactos.get(i) + "«" + fecha + "»"
//											+ observaciones.get(i) + "¬" + telefonos.get(i)));

								}

								ObjectOutputStream escribiendoFichero;

								try {

									escribiendoFichero = new ObjectOutputStream(new FileOutputStream("contactos.dat"));
									escribiendoFichero.writeObject(arrayList1);

									escribiendoFichero.close();

									verNotas();

								}

								catch (Exception e1) {

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

		menuBar.add(mntmNewMenuItem_5);

		JMenu mnNewMenu = new JMenu("Importar");
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.setForeground(Color.BLACK);
		mnNewMenu.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/utilities.png")));
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
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("PDF");
		mntmNewMenuItem_2.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/pdf.png")));

		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					MetodosPdf.crearPdf(contactos, fechaDecesos, observaciones, telefonos, "template-verurl.html");

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

	}

	private void initComponents() throws SQLException, IOException {

		arrayList1 = new ArrayList<Objeto>();

		JLabel jLabel1;
		JPanel jPanel3;
		JPanel jPanel4;
		JPanel jPanel5;

		jList1 = new JList<>();

		jList1.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				nombre.setEditable(true);

				try {

					int indice = jList1.getSelectedIndex();

					nombre.setText(jList1.getSelectedValue());

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

				} catch (Exception e1) {

				}

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
		jList1.setFont(new Font("Tahoma", Font.PLAIN, 18));
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

		direccion.setFont(new Font("Tahoma", Font.PLAIN, 16));

		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		email.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/email.png")));

		JLabel lblNewLabel_1_1 = new JLabel("Dirección");
		lblNewLabel_1_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/city.png")));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JCheckBox chckbxNewCheckBox = new JCheckBox("Lo he llamado");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));

		observacion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				indice = jList1.getSelectedIndex();

				Metodos.mensaje(observaciones.get(indice), 2, true);
			}

		});

		observacion.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnAadirObservacion = new JButton("+ Observacion");

		btnAadirObservacion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}

		});

		btnAadirObservacion.setFont(new Font("Tahoma", Font.PLAIN, 14));

		GroupLayout panelCasaLayout = new GroupLayout(panelCasa);
		panelCasaLayout.setHorizontalGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(panelCasaLayout.createSequentialGroup().addContainerGap().addGroup(panelCasaLayout
						.createParallelGroup(Alignment.LEADING).addComponent(jLabel5)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAadirObservacion, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel3).addComponent(lblNewLabel).addComponent(lblNewLabel_1)).addGap(13)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(panelCasaLayout.createSequentialGroup().addGroup(panelCasaLayout
										.createParallelGroup(Alignment.TRAILING)
										.addGroup(panelCasaLayout.createSequentialGroup().addComponent(observacion)
												.addGap(18).addComponent(chckbxNewCheckBox)
												.addPreferredGap(ComponentPlacement.RELATED))
										.addComponent(vtos, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 260,
												Short.MAX_VALUE)
										.addComponent(email, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
										.addGroup(panelCasaLayout.createSequentialGroup()
												.addComponent(nombre, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED))
										.addComponent(direccion, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 260,
												Short.MAX_VALUE))
										.addGap(10))
								.addGroup(panelCasaLayout.createSequentialGroup()
										.addComponent(tlf, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
										.addContainerGap()))));
		panelCasaLayout.setVerticalGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addGroup(panelCasaLayout
				.createSequentialGroup()
				.addGroup(panelCasaLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAadirObservacion, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(observacion).addComponent(chckbxNewCheckBox))
				.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(panelCasaLayout.createSequentialGroup().addGap(18).addComponent(nombre,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(panelCasaLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel)
						.addGroup(panelCasaLayout.createSequentialGroup().addGap(7)
								.addComponent(tlf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
				.addGap(12)
				.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(panelCasaLayout.createSequentialGroup().addGap(32).addComponent(jLabel5))
						.addGroup(panelCasaLayout.createSequentialGroup().addGap(7).addComponent(vtos,
								GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(panelCasaLayout.createSequentialGroup().addGap(6)
								.addComponent(direccion, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(panelCasaLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_1)))
						.addGroup(panelCasaLayout.createSequentialGroup().addGap(22).addComponent(lblNewLabel_1_1,
								GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		panelCasa.setLayout(panelCasaLayout);

		JButton btnNewButton = new JButton("Vtos Decesos");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				LinkedList<String> contactoTelefono = new LinkedList();

				DefaultListModel model = new DefaultListModel();

				model.clear();

				jList1.setModel(model);

				System.out.println("Veo la agenda");

				for (int i = 0; i < vencimientosDecesos.size(); i++) {
					System.out.println(vencimientosDecesos.get(i));
				}

				System.out.println("--------------------------");

				int indiceVencimiento = -1;

				for (int i = 0; i < Vencimiento.getIndiceDeceso().size(); i++) {

					indiceVencimiento = Vencimiento.getIndiceDeceso().get(i);

					contactoTelefono.add(telefonos.get(indiceVencimiento));

					model.addElement(contactos.get(indiceVencimiento));

				}

				telefonos.clear();

				telefonos = contactoTelefono;
//				observaciones.add(obs);
//
//				fechaDecesos.add(convertirFecha(fechaDeceso, false));
//
//				emails.add(correo);
//
//				fechaVida.add(convertirFecha(datoVida, false));
//
//				fechaHogar.add(convertirFecha(datoHogar, false));
//
//				fechaCoche.add(convertirFecha(datoCoche, false));

				jList1.setModel(model);

			}

		});

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnNewButton_1 = new JButton("Vtos Vida");

		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnNewButton_2 = new JButton("Vtos Hogar");

		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnNewButton_3 = new JButton("Vtos Coche");

		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnNewButton_4 = new JButton("Todos");

		btnNewButton_4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

//				DefaultListModel model = new DefaultListModel();
//
//				model.clear();
//
//				jList1.setModel(model);
				limpiarContactos();
				verNotas();

			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 14));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout
				.createSequentialGroup().addGap(18)
				.addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(jPanel3Layout.createSequentialGroup().addComponent(btnNewButton)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnNewButton_1)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton_2)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton_3)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNewButton_4).addGap(143))
						.addGroup(jPanel3Layout.createSequentialGroup()
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(panelCasa, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addContainerGap(42, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel3Layout
				.createSequentialGroup()
				.addGroup(jPanel3Layout
						.createParallelGroup(Alignment.BASELINE).addComponent(btnNewButton).addComponent(btnNewButton_1)
						.addComponent(btnNewButton_2).addComponent(btnNewButton_3).addComponent(btnNewButton_4))
				.addGap(3)
				.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panelCasa, 0, 0, Short.MAX_VALUE)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
				.addContainerGap(43, Short.MAX_VALUE)));
		jPanel3.setLayout(jPanel3Layout);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 727, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(368, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(141, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);

		if (jList1.getModel().getSize() == 0) {

			try {

				Agenda.limpiarContactos();

				verNotas();

				t.scheduleAtFixedRate(mTask, 0, 60000);

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

			contactos.clear();

			observaciones.clear();

			fechaDecesos.clear();

			emails.clear();

			fechaVida.clear();

			fechaHogar.clear();

			fechaCoche.clear();

			telefonos.clear();

			direcciones.clear();

			vencimientos.clear();

			arrayList2 = leer("contactos.dat");

			String correo, cadena, fechaDeceso, datoVida, datoHogar, datoCoche, obs, telefono;

			String cliente, direccion, localidad, codPostal, provincia;

			if (arrayList2 != null) {

				for (int i = 0; i < arrayList2.size(); i++) {

					agenda.add(arrayList2.get(i).toString());
				}

				Collections.sort(agenda);

				for (int i = 0; i < agenda.size(); i++) {

					cadena = agenda.get(i);

					fechaDeceso = cadena.substring(cadena.indexOf("▒") + 1, cadena.indexOf("╣"));

					datoVida = cadena.substring(cadena.indexOf("╣") + 1, cadena.indexOf("║"));

					datoHogar = cadena.substring(cadena.indexOf("║") + 1, cadena.indexOf("╝"));

					datoCoche = cadena.substring(cadena.indexOf("╝") + 1, cadena.length());

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

					fechaDecesos.add(convertirFecha(fechaDeceso, false));

					emails.add(correo);

					fechaVida.add(convertirFecha(datoVida, false));

					fechaHogar.add(convertirFecha(datoHogar, false));

					fechaCoche.add(convertirFecha(datoCoche, false));

					vencimientos.add("Deceso --> " + fechaDecesos.getLast() + "\n\n" + "Vida --> " + fechaVida.getLast()
							+ "\n\n" + "Hogar --> " + fechaHogar.getLast() + "\n\n" + "Coche --> "
							+ fechaCoche.getLast());

					direcciones.add(direccion + "\n\n" + localidad + " (" + codPostal + ")" + "\n\n" + provincia);

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

	protected static ArrayList<Objeto> leer(String file)
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

	public boolean controlarSeleccion() {
		try {
			jList1.getSelectedValue().toString();
			return false;
		} catch (NullPointerException e1) {
			return true;
		}
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

	public static void ponerFechasDeceso() {

		String nota;

		ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

		ArrayList<Objeto> arrayList2;

		LinkedList<String> agenda = new <String>LinkedList();

		try {

			arrayList2 = leer("contactos.dat");

			String cadena, fechaDeceso, datoVida, datoHogar, datoCoche, obs, telefono;

			fechaDecesos = new LinkedList<String>();

			fechaDecesos.clear();

			if (arrayList2 != null) {

				for (int i = 0; i < arrayList2.size(); i++) {

					agenda.add(arrayList2.get(i).toString());
				}

				Collections.sort(agenda);

				for (int i = 0; i < agenda.size(); i++) {

					cadena = agenda.get(i);

					fechaDeceso = cadena.substring(cadena.indexOf("▒") + 1, cadena.indexOf("╣"));
					fechaDecesos.add(convertirFecha(fechaDeceso, false));

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
package principal;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.mangstadt.vinnie.SyntaxStyle;
import com.github.mangstadt.vinnie.io.VObjectWriter;

import rojeru_san.componentes.RSDateChooser;
import utils.Metodos;
import utils.MetodosPdf;

@SuppressWarnings("all")

public class Agenda extends JFrame {
	Timer t = new Timer();
	public static Llamada mostrarLlamada = new Llamada();;
	Vencimiento mTask = new Vencimiento();
	ArrayList<Objeto> arrayList1;
	private JButton buscar;
	private static JButton contacto;
	private JButton editar;
	Date fecha;
	public static JList<String> jList1;
	static String directorioActual, separador, os;
	private static JTextArea nota;
	private static JTextField nombre;
	private static JTextField telefonoc;
	private static RSDateChooser tipo;
	public static DefaultListModel<String> modelo = new DefaultListModel<>();
	private static LinkedList<String> notas = new LinkedList<>();
	String iduser;
	transient ResultSet rs;
	transient Statement s;
	String cnombre;
	String ctipo;
	String cnota;

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

	static LinkedList<String> fechas = new <String>LinkedList();

	static LinkedList<String> observaciones = new <String>LinkedList();

	static LinkedList<String> contactos = new <String>LinkedList();

	static LinkedList<String> telefonos = new <String>LinkedList();

	protected static void limpiarContactos() {
		modelo.removeAllElements();

		jList1.setModel(modelo);
	}

	static String convertirFecha(String cadena) {

		String mes = "", fecha = "", year = "";
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
			mesFecha = 7;
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

		fecha = mesCorto + mesFecha + "/" + diaCorto + dia + "/" + year;

		return fecha;
	}

	public Agenda() throws IOException, SQLException {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Agenda.class.getResource("/imagenes/user.png")));

		os = System.getProperty("os.name");

		separador = Metodos.saberSeparador(os);

		initComponents();

		directorioActual = new File(".").getCanonicalPath() + separador;

		setResizable(false);

		setAutoRequestFocus(false);

		this.setSize(new Dimension(760, 590));

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
					String datoFecha = Metodos.eliminarEspacios(convertirFecha(tipo.getDatoFecha().toString()));
					String datoObs = Metodos.eliminarEspacios(nota.getText());
					String datoTls = Metodos.eliminarEspacios(telefonoc.getText());

					if (indice >= 0) {

						if (contactos.get(indice).equals(datoContacto) && fechas.get(indice).equals(datoFecha)
								&& observaciones.get(indice).equals(datoObs) && telefonos.get(indice).equals(datoTls)) {
							Metodos.mensaje("Debes modificar un dato", 3);

						}

						else {

							if (!Metodos.comprobarTelefono(datoTls)) {
								Metodos.mensaje("Teléfono incorrecto", 3);
							}

							else {

								arrayList1.clear();

								Metodos.eliminarFichero("contactos.dat");

								contactos.set(indice, datoContacto);

								fechas.set(indice, convertirFecha(tipo.getDatoFecha().toString()));

								observaciones.set(indice, Metodos.eliminarEspacios(nota.getText()));

								telefonos.set(indice, Metodos.eliminarEspacios(telefonoc.getText()));

								for (int i = 0; i < contactos.size(); i++) {

									fecha = new Date(fechas.get(i).toString());

									arrayList1.add(new Objeto(contactos.get(i) + "«" + fecha + "»"
											+ observaciones.get(i) + "¬" + telefonos.get(i)));

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
							fechas.remove(indice);
							telefonos.remove(indice);

							nombre.setText("");

							nota.setText("");

							tipo.setDatoFecha(null);

							telefonoc.setText("");

							limpiarContactos();

							Metodos.eliminarFichero("contactos.dat");

							arrayList1.clear();

							if (contactos.size() > 0) {

								for (int i = 0; i < contactos.size(); i++) {

									fecha = new Date(fechas.get(i).toString());

									arrayList1.add(new Objeto(contactos.get(i) + "«" + fecha + "»"
											+ observaciones.get(i) + "¬" + telefonos.get(i)));

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
					System.out.println(fechas.get(0));
					MetodosPdf.crearPdf(contactos, fechas, observaciones, telefonos, "template-verurl.html");

				} catch (Exception e1) {
					//
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

	public static void vaciarCampos() {
		Agenda.nombre.setText("");
		Agenda.telefonoc.setText("");
		Agenda.nota.setText("");
		Agenda.tipo.setDatoFecha(null);
	}

	public void vaciarDatos() {
		nombre.setText("");

		nota.setText("");
	}

	public Agenda(String msg) {
		super(msg);
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

				nota.setEditable(true);

				telefonoc.setEditable(true);

				try {

					int indice = jList1.getSelectedIndex();

					nombre.setText(jList1.getSelectedValue());

					Date fecha = new Date();

					nota.setText(observaciones.get(indice));

					telefonoc.setText(telefonos.get(indice));

					fecha = new Date(fechas.get(indice).toString());

					tipo.setDatoFecha(fecha);

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
		JScrollPane scrollPane;
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
		JLabel jLabel6;
		panelCasa = new JPanel();
		jLabel5 = new JLabel();
		jLabel5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabel5.setText("Vencimiento");

		panelCasa.setBackground(new Color(240, 240, 240));

		jLabel5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/tiempo.png")));

		tipo = new RSDateChooser();

		tipo.setFormatoFecha("dd/MM/yyyy");

		tipo.setBackground(new Color(255, 255, 255));

		tipo.setFont(new Font("Tahoma", Font.PLAIN, 24));

		scrollPane = new JScrollPane((Component) null);

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		nota = new JTextArea("", 0, 50);
		scrollPane.setViewportView(nota);
		nota.setBackground(new Color(255, 255, 255));
		nota.setEditable(false);
		nota.setWrapStyleWord(true);
		nota.setFont(new Font("Monospaced", Font.PLAIN, 16));
		nota.setLineWrap(true);
		jLabel6 = new JLabel();
		jLabel6.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/nota.png")));
		jLabel6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabel6.setText("Obs");

		jLabel3 = new JLabel();
		jLabel3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabel3.setText("Nombre");

		jLabel3.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/user.png")));

		nombre = new JTextField();
		nombre.setHorizontalAlignment(SwingConstants.CENTER);
		nombre.setBackground(new Color(255, 255, 255));
		nombre.setEditable(false);
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 16));

		telefonoc = new JTextField();
		telefonoc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		telefonoc.setHorizontalAlignment(SwingConstants.CENTER);
		telefonoc.setEditable(false);
		telefonoc.setColumns(10);

		JButton btnNewButton = new JButton("");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (jList1.getModel().getSize() > 0 && JOptionPane.showConfirmDialog(null,
						"Quieres borrar todos los contactos?", "Borrar contactos", JOptionPane.YES_NO_OPTION) == 0) {

					limpiarContactos();

					Metodos.eliminarFichero("contactos.dat");

					vaciarCampos();
				}

			}

		});

		btnNewButton.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/clean.png")));

		JLabel lblNewLabel = new JLabel("Tlf");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/telefono.png")));

		GroupLayout panelCasaLayout = new GroupLayout(panelCasa);
		panelCasaLayout.setHorizontalGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(panelCasaLayout.createSequentialGroup().addContainerGap()
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addComponent(jLabel3)
								.addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel).addComponent(jLabel5)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
						.addGap(23)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(tipo, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
										.addComponent(nombre, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 209,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(telefonoc, 209, 209, 209))
						.addContainerGap(279, Short.MAX_VALUE)));
		panelCasaLayout.setVerticalGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addGroup(panelCasaLayout
				.createSequentialGroup()
				.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING).addGroup(panelCasaLayout
						.createSequentialGroup()
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(panelCasaLayout.createSequentialGroup()
										.addComponent(nombre, GroupLayout.PREFERRED_SIZE, 42,
												GroupLayout.PREFERRED_SIZE)
										.addGap(19))
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(telefonoc, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING, false).addGroup(panelCasaLayout
								.createSequentialGroup().addGap(82)
								.addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
								.addGroup(panelCasaLayout.createSequentialGroup()
										.addComponent(tipo, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
										.addGap(18).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 167,
												GroupLayout.PREFERRED_SIZE))))
						.addGroup(panelCasaLayout.createSequentialGroup().addGap(167).addComponent(jLabel5)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panelCasa.setLayout(panelCasaLayout);

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addGap(18)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panelCasa, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(239, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout
				.createSequentialGroup().addGap(28)
				.addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelCasa, Alignment.LEADING, 0, 0, Short.MAX_VALUE).addComponent(jScrollPane1,
								Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(14, Short.MAX_VALUE)));
		jPanel3.setLayout(jPanel3Layout);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 727, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(368, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(214, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);

		if (jList1.getModel().getSize() == 0) {
			try {
				Agenda.limpiarContactos();
				verNotas();
				System.out.println("ejecutar cron");

				t.scheduleAtFixedRate(mTask, 0, 60000);

			} catch (Exception e) {
//
			}
		}

		pack();
	}

	public static void setFechas(String fecha) {
		Agenda.fechas.add(fecha);
	}

	public static void verNotas() {

		String nota;

		ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

		ArrayList<Objeto> arrayList2;

		LinkedList<String> agenda = new <String>LinkedList();

		try {

			arrayList2 = leer("contactos.dat");

			String cadena, fecha, obs, telefono = "";

			contactos.clear();

			observaciones.clear();

			fechas.clear();

			telefonos.clear();

			String cliente;

			if (arrayList2 != null) {

				for (int i = 0; i < arrayList2.size(); i++) {
					agenda.add(arrayList2.get(i).toString());
				}

				Collections.sort(agenda);

				for (int i = 0; i < agenda.size(); i++) {

					cadena = agenda.get(i);

					fecha = cadena.substring(cadena.indexOf("«") + 1, cadena.indexOf("»"));

					obs = cadena.substring(cadena.indexOf("»") + 1, cadena.indexOf("¬"));

					telefono = cadena.substring(cadena.indexOf("¬") + 1, cadena.length());

					cliente = cadena.substring(0, cadena.indexOf("«"));

					modelo.addElement(cliente);

					contactos.add(cliente);

					telefonos.add(telefono);

					observaciones.add(obs);

					fechas.add(convertirFecha(fecha));

				}
			}

			jList1.setModel(modelo);

		} catch (Exception e) {
			//
		}
	}

	public static LinkedList<String> getFechas() {
		return fechas;
	}

	public static void setFechas(LinkedList<String> fechas) {
		Agenda.fechas = fechas;
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
}
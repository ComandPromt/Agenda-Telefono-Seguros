package principal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rojeru_san.componentes.RSDateChooser;
import utils.Metodos;

@SuppressWarnings("all")

public class Nuevo extends javax.swing.JFrame implements ActionListener, ChangeListener {

	private MaterialTextField nombrec;
	JCheckBox checkComercio = new JCheckBox("");
	JCheckBox checkComunidad = new JCheckBox("");
	private JCheckBox checkHogar = new JCheckBox("");

	private JCheckBox checkCoche = new JCheckBox("");

	private JCheckBox checkVida = new JCheckBox("");

	private JCheckBox checkDeceso = new JCheckBox("");

	RSDateChooser deceso = new RSDateChooser();

	JTextArea nota;

	RSDateChooser coche = new RSDateChooser();

	RSDateChooser vida;

	RSDateChooser hogar = new RSDateChooser();

	RSDateChooser comunidad = new RSDateChooser();
	private MaterialTextField direccion;
	private MaterialTextField localidad;
	private MaterialTextField provincia;
	private MaterialTextField email;
	private MaterialTextField telefono;
	private MaterialTextField codpostal;

	RSDateChooser comercio = new RSDateChooser();

	protected void guardar() {

		Agenda.jList1.removeAll();

		String nombre = Metodos.eliminarEspacios(nombrec.getText());

		String notap = Metodos.eliminarEspacios(nota.getText());

		String tipop = vida.getDatoFecha().toString();

		String telefonoc = Metodos.eliminarEspacios(telefono.getText());

		String fechaDecesos, fechaVida, fechaCoche, fechaHogar, fechaComercio, fechaComunidad;

		String datoLocalidad = Metodos.eliminarEspacios(localidad.getText());

		String datoProvincia = Metodos.eliminarEspacios(provincia.getText());

		String datoCodPostal = Metodos.eliminarEspacios(codpostal.getText());

		String datoEmail = Metodos.eliminarEspacios(email.getText());

		String datoDireccion = Metodos.eliminarEspacios(direccion.getText());

		boolean comprobarEmail = Metodos.comprobarPatron(datoEmail, "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");

		boolean comprobarCodPostal = Metodos.comprobarPatron(datoCodPostal, "^[0-9]{5}");

		if (!nombre.isEmpty() && !tipop.isEmpty() && !telefonoc.isEmpty()) {

			if (Agenda.contactos.size() > 0 && Agenda.contactos.indexOf(nombre) >= 0) {
				Metodos.mensaje("Nombre de contacto duplicado", 1, true);
			}

			else {

				if (!datoCodPostal.isEmpty() && !comprobarCodPostal) {
					Metodos.mensaje("Código postal incorrecto", 1, false);
				}

				if (!datoEmail.isEmpty() && !comprobarEmail) {
					Metodos.mensaje("Email incorrecto", 1, false);
				}

				if (!checkDeceso.isSelected() && !checkCoche.isSelected() && !checkVida.isSelected()
						&& !checkHogar.isSelected() && !checkComercio.isSelected() && !checkComunidad.isSelected()) {
					Metodos.mensaje("Por favor, selecciona un seguro como mínimo", 1, false);
				}

				else {

					if (datoEmail.isEmpty() || datoCodPostal.isEmpty() || (comprobarEmail && comprobarCodPostal)) {

						try {

							ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

							ArrayList<Objeto> arrayList2;

							arrayList2 = Agenda.leer("contactos.dat");

							if (arrayList2 != null) {

								for (int i = 0; i < arrayList2.size(); i++) {

									arrayList1.add(arrayList2.get(i));
								}

							}

							fechaDecesos = Agenda.convertirFecha(deceso.getDatoFecha().toString(), true);
							fechaHogar = Agenda.convertirFecha(hogar.getDatoFecha().toString(), true);
							fechaVida = Agenda.convertirFecha(vida.getDatoFecha().toString(), true);
							fechaCoche = Agenda.convertirFecha(coche.getDatoFecha().toString(), true);
							fechaComercio = Agenda.convertirFecha(comercio.getDatoFecha().toString(), true);
							fechaComunidad = Agenda.convertirFecha(comunidad.getDatoFecha().toString(), true);

							Agenda.setfechaDecesos(fechaDecesos);

							Agenda.setFechaCoche(fechaCoche);

							Agenda.setFechaVida(fechaVida);

							Agenda.setFechaHogar(fechaHogar);

							Agenda.setFechaComercio(fechaComercio);

							Agenda.setFechaComunidad(fechaComunidad);

							Object datoFechaDeceso = null;
							Object datoFechaCoche = null;
							Object datoFechaVida = null;
							Object datoFechaHogar = null;
							Object datoFechaComercio = null;
							Object datoFechaComunidad = null;

							if (checkDeceso.isSelected()) {
								datoFechaDeceso = new Date(fechaDecesos);
							}

							if (checkCoche.isSelected()) {
								datoFechaCoche = new Date(fechaCoche);
							}

							if (checkVida.isSelected()) {
								datoFechaVida = new Date(fechaVida);
							}

							if (checkHogar.isSelected()) {
								datoFechaHogar = new Date(fechaHogar);
							}

							if (checkComercio.isSelected()) {
								datoFechaComercio = new Date(fechaComercio);
							}

							if (checkComunidad.isSelected()) {
								datoFechaComunidad = new Date(fechaComunidad);
							}

							System.out.println("DECESO: " + datoFechaDeceso + " VIDA: " + datoFechaVida + " HOGAR: "
									+ datoFechaHogar + " COCHE: " + datoFechaCoche + " COMERCIO: " + datoFechaComercio
									+ " COMUNIDAD: " + datoFechaComunidad);

							arrayList1.add(new Objeto(nombre + "«" + datoEmail + "»" + notap + "¬" + telefonoc + "═"
									+ datoDireccion + "▓" + datoLocalidad + "░" + datoCodPostal + "┤" + datoProvincia
									+ "▒" + datoFechaDeceso + "╣" + datoFechaVida + "║" + datoFechaHogar + "╝"
									+ datoFechaCoche + "¥" + datoFechaComercio + "¶" + datoFechaComunidad));

							ObjectOutputStream escribiendoFichero = new ObjectOutputStream(
									new FileOutputStream("contactos.dat"));

							escribiendoFichero.writeObject(arrayList1);

							escribiendoFichero.close();

							Agenda.limpiarContactos();

							Agenda.vaciarCampos();

							Agenda.verNotas();

						}

						catch (Exception e1) {
							//
						}
					}
				}
			}

		} else

		{

			Metodos.mensaje("Por favor, rellena todos los datos", 3, true);

		}

		Vencimiento.verTablaVencimientos();

	}

	public Nuevo() {

		getContentPane().addKeyListener(new KeyAdapter() {

			@Override

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					guardar();
				}
			}

		});

		setIconImage(Toolkit.getDefaultToolkit().getImage(Nuevo.class.getResource("/imagenes/insert.png")));

		setTitle("Nuevo Contacto");

		initComponents();

		this.setVisible(true);
	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);

		MaterialButtomRectangle btnNewButton = new MaterialButtomRectangle();

		btnNewButton.setText("Insertar");

		btnNewButton.setBackground(new java.awt.Color(0, 102, 0));
		btnNewButton.setForeground(new java.awt.Color(255, 255, 255));

		btnNewButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				guardar();
			}

		});

		nombrec = new MaterialTextField();

		nombrec.setHorizontalAlignment(SwingConstants.CENTER);
		nombrec.setBackground(Color.ORANGE);

		nombrec.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					guardar();
				}
			}
		});
		nombrec.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nombrec.setHorizontalAlignment(SwingConstants.CENTER);
		nombrec.setColumns(10);

		JScrollPane scrollPane = new JScrollPane((Component) null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JLabel jLabel3 = new JLabel();
		jLabel3.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/user.png")));
		jLabel3.setText("Nombre");
		jLabel3.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblNewLabel = new JLabel("Observaciones");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/name.png")));

		vida = new RSDateChooser();

		Date myDate = new Date();
		vida.setFormatoFecha("dd/MM/Y");
		vida.setDatoFecha(myDate);
		coche.setFormatoFecha("dd/MM/Y");
		coche.setDatoFecha(myDate);
		deceso.setFormatoFecha("dd/MM/Y");
		deceso.setDatoFecha(myDate);
		hogar.setFormatoFecha("dd/MM/Y");
		hogar.setDatoFecha(myDate);

		comercio.setFormatoFecha("dd/MM/Y");
		comercio.setDatoFecha(myDate);

		comunidad.setFormatoFecha("dd/MM/Y");
		comunidad.setDatoFecha(myDate);

		JLabel jLabel5 = new JLabel();
		jLabel5.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/heart.png")));
		jLabel5.setText("Vida");
		jLabel5.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel_1 = new JLabel("Tlf");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/telefono.png")));

		JLabel jLabel5_2 = new JLabel();
		jLabel5_2.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/deceso.png")));
		jLabel5_2.setText("Decesos");
		jLabel5_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel jLabel5_2_1 = new JLabel();
		jLabel5_2_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/car.png")));
		jLabel5_2_1.setText("Coche");
		jLabel5_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblHogar = new JLabel();
		lblHogar.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/home.png")));
		lblHogar.setText("Hogar");
		lblHogar.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel_3 = new JLabel("Dirección");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/geo.png")));

		direccion = new MaterialTextField();
		direccion.setHorizontalAlignment(SwingConstants.CENTER);
		direccion.setBackground(Color.ORANGE);

		JLabel lblNewLabel_3_1 = new JLabel("Localidad");
		lblNewLabel_3_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/city.png")));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		localidad = new MaterialTextField();
		localidad.setHorizontalAlignment(SwingConstants.CENTER);
		localidad.setBackground(Color.ORANGE);

		JLabel lblNewLabel_3_2 = new JLabel("Provincia");
		lblNewLabel_3_2.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/city.png")));
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel_3_1_1 = new JLabel("Cod Postal");
		lblNewLabel_3_1_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/tag.png")));
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		provincia = new MaterialTextField();
		provincia.setHorizontalAlignment(SwingConstants.CENTER);
		provincia.setBackground(Color.ORANGE);

		JLabel lblNewLabel_3_1_2 = new JLabel("Cod Postal");
		lblNewLabel_3_1_2.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/tag.png")));
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblNewLabel_4 = new JLabel("Email");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/email.png")));

		email = new MaterialTextField();
		email.setHorizontalAlignment(SwingConstants.CENTER);
		email.setBackground(Color.ORANGE);

		telefono = new MaterialTextField();
		telefono.setHorizontalAlignment(SwingConstants.CENTER);
		telefono.setBackground(Color.ORANGE);

		codpostal = new MaterialTextField();
		codpostal.setHorizontalAlignment(SwingConstants.CENTER);
		codpostal.setBackground(Color.ORANGE);

		JLabel lblNewLabel_2 = new JLabel("*");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel lblNewLabel_2_1 = new JLabel("*");
		lblNewLabel_2_1.setForeground(Color.BLUE);
		lblNewLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel jLabel5_2_1_1 = new JLabel();
		jLabel5_2_1_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/shop.png")));
		jLabel5_2_1_1.setText("Comercio");
		jLabel5_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel jLabel5_2_1_1_1 = new JLabel();
		jLabel5_2_1_1_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/comunidad.png")));
		jLabel5_2_1_1_1.setText("Comunidad");
		jLabel5_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
				.addGap(21)
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 133,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
												.addGroup(layout.createParallelGroup(Alignment.TRAILING)
														.addComponent(provincia, GroupLayout.PREFERRED_SIZE, 173,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(layout.createSequentialGroup()
																.addComponent(email, GroupLayout.DEFAULT_SIZE, 177,
																		Short.MAX_VALUE)
																.addPreferredGap(ComponentPlacement.RELATED))))
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblNewLabel_3, Alignment.LEADING).addComponent(
																jLabel3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
																125, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
												.addComponent(lblNewLabel_2).addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(nombrec)
														.addComponent(direccion, 173, 173, Short.MAX_VALUE))))
								.addGap(18)).addGroup(
										layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(
														Alignment.TRAILING)
														.addGroup(layout.createSequentialGroup().addGroup(layout
																.createParallelGroup(Alignment.LEADING)
																.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 109,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(jLabel5_2_1))
																.addPreferredGap(ComponentPlacement.RELATED, 36,
																		Short.MAX_VALUE)
																.addGroup(layout.createParallelGroup(Alignment.TRAILING)
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(checkVida,
																						GroupLayout.PREFERRED_SIZE, 21,
																						GroupLayout.PREFERRED_SIZE)
																				.addGap(4))
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(checkCoche).addGap(2))))
														.addGroup(layout.createSequentialGroup()
																.addComponent(jLabel5_2_1_1)
																.addPreferredGap(ComponentPlacement.RELATED, 28,
																		Short.MAX_VALUE)
																.addComponent(checkComercio, GroupLayout.PREFERRED_SIZE,
																		21, GroupLayout.PREFERRED_SIZE)
																.addGap(2))
														.addGroup(layout.createSequentialGroup()
																.addComponent(jLabel5_2_1_1_1)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(checkComunidad,
																		GroupLayout.PREFERRED_SIZE, 21,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(2)))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(comercio, GroupLayout.DEFAULT_SIZE, 162,
																Short.MAX_VALUE)
														.addComponent(coche, 0, 0, Short.MAX_VALUE)
														.addComponent(comunidad, GroupLayout.DEFAULT_SIZE, 162,
																Short.MAX_VALUE)
														.addComponent(vida, GroupLayout.PREFERRED_SIZE, 162,
																GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 87,
																Short.MAX_VALUE)
														.addGap(49).addComponent(lblNewLabel_2_1,
																GroupLayout.PREFERRED_SIZE, 8,
																GroupLayout.PREFERRED_SIZE))
												.addComponent(lblNewLabel_3_1_2, GroupLayout.DEFAULT_SIZE, 144,
														Short.MAX_VALUE)
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
																.addComponent(lblHogar, Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(jLabel5_2, Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addComponent(checkHogar, GroupLayout.PREFERRED_SIZE,
																		21, GroupLayout.PREFERRED_SIZE)
																.addComponent(checkDeceso, GroupLayout.PREFERRED_SIZE,
																		21, GroupLayout.PREFERRED_SIZE))))
												.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(layout.createSequentialGroup().addComponent(lblNewLabel).addGap(2)))
										.addGroup(layout.createSequentialGroup().addComponent(lblNewLabel_3_1)
												.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 118,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(hogar, 0, 0, Short.MAX_VALUE)
												.addComponent(deceso, 0, 0, Short.MAX_VALUE)
												.addComponent(codpostal, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
												.addComponent(localidad).addComponent(telefono,
														GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))))
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
						.addGap(124)).addComponent(lblNewLabel_4))
				.addGap(256)
				.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
				.addGap(129)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout
								.createParallelGroup(
										Alignment.LEADING)
								.addGroup(
										layout.createSequentialGroup().addContainerGap()
												.addGroup(
														layout.createParallelGroup(Alignment.BASELINE)
																.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 67,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(lblNewLabel_1)
																.addComponent(telefono, GroupLayout.PREFERRED_SIZE, 29,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(nombrec, GroupLayout.PREFERRED_SIZE, 33,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(lblNewLabel_2))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_3)
														.addComponent(direccion, GroupLayout.PREFERRED_SIZE, 33,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(localidad, GroupLayout.PREFERRED_SIZE, 33,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 63,
																GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout
										.createSequentialGroup().addGap(31).addComponent(
												lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 24,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(18).addComponent(
												lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 63,
												GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addGap(8).addComponent(lblNewLabel_3_2,
												GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addGap(6)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE, 63,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																codpostal, GroupLayout.PREFERRED_SIZE, 33,
																GroupLayout.PREFERRED_SIZE))))
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_4)
														.addComponent(email, GroupLayout.PREFERRED_SIZE, 36,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel5_2, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(deceso, GroupLayout.PREFERRED_SIZE, 41,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(layout.createSequentialGroup().addGap(26).addComponent(checkDeceso,
												GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))))
								.addGroup(
										layout.createSequentialGroup().addGap(18).addComponent(provincia,
												GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(18).addComponent(jLabel5)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(jLabel5_2_1))
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addGap(32)
														.addComponent(checkVida, GroupLayout.PREFERRED_SIZE, 21,
																GroupLayout.PREFERRED_SIZE))
												.addGroup(layout.createSequentialGroup().addGap(23).addComponent(
														vida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)))
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addGroup(layout.createSequentialGroup().addGap(21)
																.addComponent(coche, GroupLayout.PREFERRED_SIZE, 41,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout.createSequentialGroup().addGap(30)
																		.addComponent(checkCoche)))))
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(18).addComponent(comercio,
												GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addGap(26).addComponent(checkComercio,
												GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(jLabel5_2_1_1, GroupLayout.PREFERRED_SIZE, 60,
														GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(14)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(jLabel5_2_1_1_1, GroupLayout.PREFERRED_SIZE, 48,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(comunidad, GroupLayout.PREFERRED_SIZE, 41,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(
												layout.createSequentialGroup().addGap(23).addComponent(checkComunidad,
														GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))))
								.addGroup(layout.createSequentialGroup().addGroup(layout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblHogar))
										.addGroup(layout.createSequentialGroup().addGap(28).addComponent(checkHogar,
												GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addGap(18)
												.addComponent(hogar, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 53,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addGap(76).addComponent(lblNewLabel,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(scrollPane,
												GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
										.addGap(25)))
						.addGap(46)));

		nota = new JTextArea("", 0, 50);

		nota.setWrapStyleWord(true);

		nota.setLineWrap(true);

		nota.setFont(new Font("Monospaced", Font.PLAIN, 16));

		nota.setBackground(Color.WHITE);

		scrollPane.setViewportView(nota);

		getContentPane().setLayout(layout);

		setSize(new Dimension(697, 600));

		setLocationRelativeTo(null);

	}

	@Override

	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

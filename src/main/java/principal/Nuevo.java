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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rojeru_san.componentes.RSDateChooser;
import utils.Metodos;

@SuppressWarnings("all")

public class Nuevo extends javax.swing.JFrame implements ActionListener, ChangeListener {

	private JTextField nombrec;

	JTextArea nota;

	RSDateChooser vida;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_2;
	private JTextField textField_5;
	private JTextField telefono;
	private JTextField textField_4;

	protected void guardar() {

		Agenda.jList1.removeAll();

		String nombre = Metodos.eliminarEspacios(nombrec.getText());

		String notap = Metodos.eliminarEspacios(nota.getText());

		String tipop = vida.getDatoFecha().toString();

		String telefonoc = Metodos.eliminarEspacios(telefono.getText());

		String fecha = "";

		if (!nombre.isEmpty() && !notap.isEmpty() && !tipop.isEmpty() && !telefonoc.isEmpty()) {

			if (Agenda.contactos.size() > 0 && Agenda.contactos.indexOf(nombre) >= 0) {
				Metodos.mensaje("Nombre de contacto duplicado", 1);
			}

			else {

				if (Metodos.comprobarTelefono(telefonoc)) {

					try {

						ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

						ArrayList<Objeto> arrayList2;

						arrayList2 = Agenda.leer("contactos.dat");

						if (arrayList2 != null) {

							for (int i = 0; i < arrayList2.size(); i++) {

								arrayList1.add(arrayList2.get(i));
							}

						}

						fecha = Agenda.convertirFecha(vida.getDatoFecha().toString());

						Agenda.setFechas(fecha);

						arrayList1.add(
								new Objeto(nombrec.getText() + "«" + new Date(fecha) + "»" + notap + "¬" + telefonoc));

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

				else {
					Metodos.mensaje("Teléfono incorrecto", 1);
				}
			}
		}

		else {
			Metodos.mensaje("Por favor, rellena todos los datos", 3);
		}
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

		JButton btnNewButton = new JButton("Insertar");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				guardar();
			}

		});

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/insert.png")));

		nombrec = new JTextField();
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
		vida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					guardar();
				}
			}
		});

		Date myDate = new Date();
		vida.setFormatoFecha("dd/MM/Y");
		vida.setDatoFecha(myDate);

		JLabel jLabel5 = new JLabel();
		jLabel5.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/heart.png")));
		jLabel5.setText("Vida");
		jLabel5.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel_1 = new JLabel("Tlf");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/telefono.png")));

		RSDateChooser tipo_1 = new RSDateChooser();

		JLabel lblNewLabel_2 = new JLabel("Vencimientos");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel jLabel5_2 = new JLabel();
		jLabel5_2.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/deceso.png")));
		jLabel5_2.setText("Decesos");
		jLabel5_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		RSDateChooser tipo_2 = new RSDateChooser();

		JLabel jLabel5_2_1 = new JLabel();
		jLabel5_2_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/car.png")));
		jLabel5_2_1.setText("Coche");
		jLabel5_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		RSDateChooser tipo_1_1 = new RSDateChooser();

		JLabel lblHogar = new JLabel();
		lblHogar.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/home.png")));
		lblHogar.setText("Hogar");
		lblHogar.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel_3 = new JLabel("Dirección");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/geo.png")));

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setColumns(10);

		JLabel lblNewLabel_3_1 = new JLabel("Localidad");
		lblNewLabel_3_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/city.png")));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_1.setColumns(10);

		JLabel lblNewLabel_3_2 = new JLabel("Provincia");
		lblNewLabel_3_2.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/city.png")));
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel_3_1_1 = new JLabel("Cod Postal");
		lblNewLabel_3_1_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/tag.png")));
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_3.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_2.setColumns(10);

		JLabel lblNewLabel_3_1_2 = new JLabel("Cod Postal");
		lblNewLabel_3_1_2.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/tag.png")));
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblNewLabel_4 = new JLabel("Email");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/email.png")));

		textField_5 = new JTextField();
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setColumns(10);

		telefono = new JTextField();
		telefono.setHorizontalAlignment(SwingConstants.CENTER);
		telefono.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_4.setColumns(10);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGap(21)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addComponent(lblNewLabel).addGap(114)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 341,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(46, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
												.createSequentialGroup().addGroup(layout
														.createParallelGroup(Alignment.LEADING).addGroup(layout
																.createSequentialGroup().addComponent(lblNewLabel_3_2,
																		GroupLayout.PREFERRED_SIZE, 133,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		ComponentPlacement.RELATED,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addGroup(layout.createParallelGroup(Alignment.LEADING)
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(textField_5,
																						GroupLayout.DEFAULT_SIZE, 173,
																						Short.MAX_VALUE)
																				.addPreferredGap(
																						ComponentPlacement.RELATED))
																		.addComponent(textField_2, Alignment.TRAILING,
																				GroupLayout.PREFERRED_SIZE, 173,
																				GroupLayout.PREFERRED_SIZE)))
														.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
																.addGroup(layout.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblNewLabel_3).addComponent(
																				jLabel3, GroupLayout.PREFERRED_SIZE,
																				125, GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(Alignment.LEADING,
																				false).addComponent(
																						nombrec)
																				.addComponent(textField, 173, 173,
																						Short.MAX_VALUE))))
												.addGap(18)
												.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
														.createParallelGroup(Alignment.TRAILING)
														.addGroup(layout.createSequentialGroup()
																.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE,
																		85, Short.MAX_VALUE)
																.addGap(215))
														.addGroup(layout.createSequentialGroup().addGroup(layout
																.createParallelGroup(Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addGroup(layout
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(lblNewLabel_3_1_2,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(jLabel5_2))
																		.addGap(18))
																.addGroup(layout.createSequentialGroup()
																		.addComponent(lblHogar,
																				GroupLayout.PREFERRED_SIZE, 125,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(13)))
																.addGroup(layout
																		.createParallelGroup(Alignment.LEADING, false)
																		.addComponent(tipo_2, 0, 0, Short.MAX_VALUE)
																		.addComponent(tipo_1, 0, 0, Short.MAX_VALUE)
																		.addComponent(textField_4,
																				GroupLayout.DEFAULT_SIZE, 146,
																				Short.MAX_VALUE)
																		.addComponent(textField_1).addComponent(
																				telefono, GroupLayout.DEFAULT_SIZE, 146,
																				Short.MAX_VALUE))
																.addGap(16)))
														.addComponent(lblNewLabel_3_1)))
												.addComponent(lblNewLabel_4))
										.addGap(256)
										.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 133,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel_2).addComponent(textField_3, 119, 119, 119)))
								.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jLabel5_2_1, GroupLayout.PREFERRED_SIZE, 125,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(tipo_1_1, 0, 0, Short.MAX_VALUE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 125,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(vida,
														GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))))
								.addGap(0))))
				.addGroup(layout.createSequentialGroup().addGap(356).addComponent(btnNewButton).addContainerGap(656,
						Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1)
								.addComponent(telefono, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(nombrec, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_3)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 63,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(18)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 63,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 33,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(layout.createSequentialGroup().addGap(8).addComponent(lblNewLabel_3_2,
												GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addGap(6)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE, 63,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 33,
																GroupLayout.PREFERRED_SIZE))))
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(18).addComponent(lblNewLabel_2))
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_4)
														.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, 36,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel5_2, GroupLayout.PREFERRED_SIZE, 54,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(tipo_1, GroupLayout.PREFERRED_SIZE, 41,
																GroupLayout.PREFERRED_SIZE)))))
								.addGroup(layout.createSequentialGroup().addGap(18).addComponent(textField_2,
										GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
								.createSequentialGroup()
								.addGroup(layout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(23).addComponent(vida,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(lblHogar, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 58,
																Short.MAX_VALUE))))
								.addGap(18)
								.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel5_2_1)
										.addComponent(tipo_1_1, GroupLayout.PREFERRED_SIZE, 41,
												GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createSequentialGroup().addGap(18).addComponent(
										tipo_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 106,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(36).addComponent(lblNewLabel)))
						.addGap(18).addComponent(btnNewButton).addGap(123)));

		nota = new JTextArea("", 0, 50);

		nota.setWrapStyleWord(true);
		nota.setLineWrap(true);
		nota.setFont(new Font("Monospaced", Font.PLAIN, 16));
		nota.setBackground(Color.WHITE);
		scrollPane.setViewportView(nota);
		getContentPane().setLayout(layout);
		setSize(new Dimension(682, 703));
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

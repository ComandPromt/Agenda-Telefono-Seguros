package principal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	RSDateChooser tipo;

	private JTextField telefono;

	public Nuevo() {
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

				Agenda.jList1.removeAll();

				ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

				String nombre = Metodos.eliminarEspacios(nombrec.getText());

				String notap = Metodos.eliminarEspacios(nota.getText());

				String tipop = tipo.getDatoFecha().toString();

				String telefonoc = Metodos.eliminarEspacios(telefono.getText());

				String fecha = "";

				if (!nombre.isEmpty() && !notap.isEmpty() && !tipop.isEmpty() && !telefonoc.isEmpty()) {

					if (Agenda.contactos.size() > 0 && Agenda.contactos.indexOf(nombre) >= 0) {
						Metodos.mensaje("Nombre de contacto duplicado", 1);
					}

					else {

						if (Metodos.comprobarTelefono(telefonoc)) {

							try {

								ArrayList<Objeto> arrayList2;

								arrayList2 = Agenda.leer();

								if (arrayList2 != null) {

									for (int i = 0; i < arrayList2.size(); i++) {

										arrayList1.add(arrayList2.get(i));
									}

								}

								fecha = Agenda.convertirFecha(tipo.getDatoFecha().toString());

								Agenda.setFechas(fecha);

								arrayList1.add(new Objeto(
										nombrec.getText() + "«" + new Date(fecha) + "»" + notap + "¬" + telefonoc));

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

		});

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/insert.png")));

		nombrec = new JTextField();
		nombrec.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nombrec.setHorizontalAlignment(SwingConstants.CENTER);
		nombrec.setColumns(10);

		JScrollPane scrollPane = new JScrollPane((Component) null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JLabel jLabel3 = new JLabel();
		jLabel3.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/user.png")));
		jLabel3.setText("Nombre");
		jLabel3.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel = new JLabel("Observaciones");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/name.png")));

		tipo = new RSDateChooser();

		Date myDate = new Date();
		tipo.setFormatoFecha("dd/MM/yyyy");
		tipo.setDatoFecha(myDate);

		JLabel jLabel5 = new JLabel();
		jLabel5.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/tiempo.png")));
		jLabel5.setText("Vencimiento");
		jLabel5.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel_1 = new JLabel("Tlf");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/telefono.png")));

		telefono = new JTextField();
		telefono.setFont(new Font("Tahoma", Font.PLAIN, 16));
		telefono.setHorizontalAlignment(SwingConstants.CENTER);
		telefono.setColumns(10);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGap(21)
				.addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(btnNewButton).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel).addComponent(lblNewLabel_1)
								.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
						.addGap(33)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(nombrec, 240, 240, 240)
								.addComponent(telefono, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
								.addComponent(tipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 240,
										GroupLayout.PREFERRED_SIZE))))
				.addGap(347)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(nombrec, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addGap(19)
						.addGroup(
								layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(telefono, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_1))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(33).addComponent(tipo,
										GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(18).addComponent(jLabel5,
										GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(25).addComponent(scrollPane,
										GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(82).addComponent(lblNewLabel)))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnNewButton).addGap(23)));

		nota = new JTextArea("", 0, 50);
		nota.setWrapStyleWord(true);
		nota.setLineWrap(true);
		nota.setFont(new Font("Monospaced", Font.PLAIN, 16));
		nota.setBackground(Color.WHITE);
		scrollPane.setViewportView(nota);
		getContentPane().setLayout(layout);
		setSize(new Dimension(495, 595));
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

package principal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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

	private JTextField textField;

	JTextArea nota;

	RSDateChooser tipo;

	public Nuevo() {

		setTitle("Nuevo Contacto");

		setType(Type.UTILITY);

		initComponents();

		this.setVisible(true);

	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);

		JButton btnNewButton = new JButton("Guardar");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Agenda.jList1.removeAll();

				ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

				String nombre = Metodos.eliminarEspacios(textField.getText());

				String notap = Metodos.eliminarEspacios(nota.getText());

				String tipop = tipo.getDatoFecha().toString();

				String fecha = "";

				if (!nombre.isEmpty() && !notap.isEmpty() && !tipop.isEmpty()) {

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

						arrayList1.add(new Objeto(textField.getText() + "«" + new Date(fecha) + "»" + notap));

						ObjectOutputStream escribiendoFichero = new ObjectOutputStream(
								new FileOutputStream("contactos.dat"));

						escribiendoFichero.writeObject(arrayList1);

						escribiendoFichero.close();

						Agenda.verNotas();

					}

					catch (Exception e1) {
						e1.printStackTrace();
					}

				}

				else {
					Metodos.mensaje("Por favor, rellena todos los datos", 3);
				}
			}

		});

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setIcon(new ImageIcon(Nuevo.class.getResource("/imagenes/save.png")));

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);

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
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGap(23)
				.addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(btnNewButton).addGroup(layout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup().addGap(8).addComponent(jLabel3,
										GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
								.addGap(33)
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(tipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(textField, 240, 240, 240)))
						.addGroup(layout.createSequentialGroup().addComponent(lblNewLabel).addGap(18).addComponent(
								scrollPane, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE))))
				.addGap(347)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGap(8)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(33).addComponent(tipo,
										GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(18).addComponent(jLabel5,
										GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))
						.addGap(47)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 167,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(45).addComponent(lblNewLabel)))
						.addGap(18).addComponent(btnNewButton).addContainerGap(26, Short.MAX_VALUE)));

		nota = new JTextArea("", 0, 50);
		nota.setWrapStyleWord(true);
		nota.setLineWrap(true);
		nota.setFont(new Font("Monospaced", Font.PLAIN, 20));
		nota.setBackground(Color.WHITE);
		scrollPane.setViewportView(nota);
		getContentPane().setLayout(layout);
		setSize(new Dimension(495, 543));
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

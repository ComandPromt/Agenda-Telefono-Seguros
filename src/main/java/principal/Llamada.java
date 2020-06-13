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
import java.util.LinkedList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import utils.FormatoTabla;

public class Llamada extends javax.swing.JFrame {

	static FormatoTabla ft;

	JLabel seguro = new JLabel("");

	private static final long serialVersionUID = 1L;

	private JLabel jLabel2;

	private JPanel jPanel1;

	private JScrollPane jScrollPane1;

	static JTable jTable1;

	private JTextField jTextField1;

	BufferedReader br;

	BufferedWriter bw;

	public static DefaultTableModel model;

	DefaultTableModel model2;

	String[][] dataStok;

	String path;

	File file;

	LinkedList<String> vencimientos = new LinkedList<String>();

	JComboBox<String> comboBox = new JComboBox<String>();

	static int tipoDeceso = 0;

	public Llamada(int tipo) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Llamada.class.getResource("/imagenes/telefono.png")));

		vencimientos.clear();

		tipoDeceso = tipo;

		setAlwaysOnTop(true);

		setTitle("Llamadas de Vencimientos");

		setResizable(false);

		switch (tipo) {

		case 1:
			seguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/deceso.png")));
			vencimientos = Agenda.vencimientosDecesos;
			break;

		case 2:
			seguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/heart.png")));
			vencimientos = Agenda.vencimientosVida;
			break;

		case 3:
			seguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/home.png")));
			vencimientos = Agenda.vencimientosHogar;
			break;

		case 4:
			seguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/car.png")));
			vencimientos = Agenda.vencimientosCoche;
			break;

		case 5:
			seguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/shop.png")));
			vencimientos = Agenda.vencimientosComercio;
			break;

		case 6:
			seguro.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/comunidad.png")));
			vencimientos = Agenda.vencimientosComunidad;
			break;

		}

		initComponents();

		comboBox.setFont(new Font("Dialog", Font.BOLD, 16));

		comboBox.addItem("Decesos");

		comboBox.addItem("Vida");

		comboBox.addItem("Hogar");

		comboBox.addItem("Coche");

		comboBox.addItem("Comercio");

		comboBox.addItem("Comunidad");

		setSize(new Dimension(800, 520));

		model = (DefaultTableModel) jTable1.getModel();

		loadData(vencimientos);

		verTabla();

		// Si hay vencimientos de vida,hogar y coche ponerlo en el combo,

		// si no, mostrar solo los que tienen vencimientos

	}

	static void verTabla() {

		ft = new FormatoTabla();

		jTable1.setDefaultRenderer(Object.class, ft);

		jTable1.getTableHeader().setEnabled(false);

	}

	public static void loadData(LinkedList<String> lista) {

		model.getDataVector().removeAllElements();

		model = (DefaultTableModel) jTable1.getModel();

		String tlf = "";

		try {

			String contactoVto = "";

			ft = new FormatoTabla();

			for (int i = 0; i < lista.size(); i++) {

				switch (tipoDeceso) {

				case 1:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceDeceso().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceDeceso().get(i));
					break;

				case 2:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceVida().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				case 3:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceHogar().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				case 4:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceCoche().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				case 5:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceComercio().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				case 6:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceComunidad().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				}

				model.addRow(new Object[] { contactoVto, tlf, lista.get(i), "" });

			}
		}

		catch (Exception e) {
			//
		}

	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();

		jScrollPane1 = new javax.swing.JScrollPane();

		jTable1 = new javax.swing.JTable();

		jTable1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {
					model.getDataVector().removeAllElements();
					model = (DefaultTableModel) jTable1.getModel();
					loadData(vencimientos);
					verTabla();

				} catch (Exception e1) {
					//
				}
			}
		});

		jTextField1 = new javax.swing.JTextField();
		jTextField1.setFont(new Font("Tahoma", Font.BOLD, 16));
		jTextField1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/lupa.png")));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jPanel1.setBackground(Color.WHITE);
		jTable1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Nombre", "Telefono", "Vencimiento", "Estado" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			public Class<?> getColumnClass(int columnIndex) {

				if (columnIndex >= 4) {
					columnIndex = 3;
				}

				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}

		});

		jScrollPane1.setViewportView(jTable1);

		jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jTextField1KeyReleased(evt);
			}
		});

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18));

		jLabel2.setForeground(new java.awt.Color(255, 255, 255));

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/filtro.png")));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));

		JButton btnNewButton = new JButton("Ver");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String valor = comboBox.getSelectedItem().toString();

				dispose();

				switch (valor) {

				case "Decesos":
					new Llamada(1).setVisible(true);
					break;

				case "Vida":

					new Llamada(2).setVisible(true);

					break;

				case "Hogar":

					new Llamada(3).setVisible(true);

					break;

				case "Coche":

					new Llamada(4).setVisible(true);

					break;

				case "Comercio":

					new Llamada(5).setVisible(true);

					break;

				case "Comunidad":

					new Llamada(6).setVisible(true);

					break;

				}

			}

		});

		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 16));

		btnNewButton.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/view.png")));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout
				.createSequentialGroup().addGap(26)
				.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout
						.createSequentialGroup().addGap(22).addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE).addGap(17)
						.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(seguro))
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(61).addComponent(btnNewButton))))
						.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(16).addComponent(jLabel2)
												.addGap(58).addComponent(jTextField1, GroupLayout.DEFAULT_SIZE, 774,
														Short.MAX_VALUE)))
								.addGap(28)))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(31).addComponent(jLabel2))
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(45).addComponent(jTextField1,
										GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
						.addGap(18)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(seguro)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(183, Short.MAX_VALUE)));
		jPanel1.setLayout(jPanel1Layout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextField1KeyReleased

		try {

			String cari = jTextField1.getText();

			TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);

			jTable1.setRowSorter(tr);

			tr.setRowFilter(RowFilter.regexFilter(cari));

			verTabla();

		} catch (Exception e) {

			try {

				jTextField1.setText("");

				loadData(vencimientos);

				verTabla();

			} catch (Exception e1) {

			}
		}

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		try {

			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}

			}

		}

		catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Llamada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Llamada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Llamada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Llamada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new Llamada(0).setVisible(true);
			}

		});

	}

}

package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Llamada extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	BufferedReader br;
	BufferedWriter bw;
	static DefaultTableModel model;
	DefaultTableModel model2;
	String[][] dataStok;
	String path;
	File file;

	public Llamada() {
		setType(Type.POPUP);
		setAlwaysOnTop(true);

		setTitle("Llamadas de Vencimientos");
		setResizable(false);
		initComponents();
		setSize(new Dimension(800, 450));
		model = (DefaultTableModel) jTable1.getModel();
		loadData();

	}

	public static void loadData() {

		Agenda.ponerFechasDeceso();

		model.getDataVector().removeAllElements();

		try {

			for (int i = 0; i < Agenda.vencimientosDecesos.size(); i++) {

				model.addRow(new Object[] { Agenda.contactos.get(Vencimiento.getIndiceDeceso().get(i)),
						Agenda.telefonos.get(Vencimiento.getIndiceDeceso().get(i)), "ee",
						Agenda.fechaDecesos.get(Integer.parseInt(Agenda.vencimientosDecesos.get(i))) });
			}

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable1.getModel());

			jTable1.setRowSorter(sorter);

			List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);

			sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
			sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
			sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
			sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));

			sorter.setSortKeys(sortKeys);

		}

		catch (Exception e) {
			//
		}

	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jTextField1 = new javax.swing.JTextField();
		jTextField1.setFont(new Font("Tahoma", Font.BOLD, 16));
		jTextField1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/lupa.png")));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jPanel1.setBackground(new java.awt.Color(153, 153, 153));
		jTable1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Nombre y apellidos ", "Telefono", "Seguro", "Vencimiento" }) {
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

		JLabel lblNewLabel = new JLabel("Ordenar por");

		lblNewLabel.setForeground(Color.WHITE);

		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

		JComboBox comboBox = new JComboBox();

		comboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				System.out.println("entro a ordenar la tabla");

			}

		});

		comboBox.addItem("Vencimientos Decesos");

		comboBox.addItem("Vencimientos Vida");

		comboBox.addItem("Vencimientos Hogar");

		comboBox.addItem("Vencimientos Coche");

		comboBox.addItem("Caducidad (primero los que se vayan a cumplir)");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout
				.createSequentialGroup().addGap(26)
				.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
								.createParallelGroup(Alignment.LEADING, false)
								.addGroup(jPanel1Layout.createSequentialGroup().addComponent(lblNewLabel).addGap(18)
										.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel2).addGap(36)
										.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 289,
												GroupLayout.PREFERRED_SIZE)))
								.addGap(196))
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 734, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(108, Short.MAX_VALUE)))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout
				.createSequentialGroup().addContainerGap()
				.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel2)
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(17)
								.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
				.addGap(33)
				.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(
						comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(179, Short.MAX_VALUE)));
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
		// TODO add your handling code here:
		String cari = jTextField1.getText();
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
		jTable1.setRowSorter(tr);
		// set kolom pencarian (indeks kolom)
		tr.setRowFilter(RowFilter.regexFilter(cari));
	}// GEN-LAST:event_jTextField1KeyReleased

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
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new Llamada().setVisible(true);
			}

		});

	}

	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	static javax.swing.JTable jTable1;
	private javax.swing.JTextField jTextField1;

}

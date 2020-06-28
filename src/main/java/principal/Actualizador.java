package principal;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rojeru_san.componentes.RSDateChooser;

@SuppressWarnings("all")

public class Actualizador extends javax.swing.JFrame implements ActionListener, ChangeListener {

	public Actualizador() {
		setAutoRequestFocus(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Actualizador.class.getResource("/imagenes/about.png")));
		setTitle("Modificar contacto");
		setType(Type.UTILITY);
		initComponents();
		this.setVisible(true);

	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);

		JLabel lblNewLabel_3 = new JLabel("Dirección");
		lblNewLabel_3.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/geo.png")));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));

		MaterialTextField direccion = new MaterialTextField();
		direccion.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_3_2 = new JLabel("Provincia");
		lblNewLabel_3_2.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/city.png")));
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		MaterialTextField provincia = new MaterialTextField();
		provincia.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_3_1_2 = new JLabel("Cod Postal");
		lblNewLabel_3_1_2.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/tag.png")));
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));

		MaterialTextField codpostal = new MaterialTextField();
		codpostal.setHorizontalAlignment(SwingConstants.CENTER);

		MaterialTextField localidad = new MaterialTextField();
		localidad.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_3_1 = new JLabel("Localidad");
		lblNewLabel_3_1.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/city.png")));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel jLabel5_2 = new JLabel();
		jLabel5_2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel5_2.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/deceso.png")));
		jLabel5_2.setText("Decesos");
		jLabel5_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblHogar = new JLabel();
		lblHogar.setHorizontalAlignment(SwingConstants.LEFT);
		lblHogar.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/home.png")));
		lblHogar.setText("Hogar");
		lblHogar.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel jLabel5_2_1 = new JLabel();
		jLabel5_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel5_2_1.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/shop.png")));
		jLabel5_2_1.setText("Comercio");
		jLabel5_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel jLabel5_2_2 = new JLabel();
		jLabel5_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel5_2_2.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/heart.png")));
		jLabel5_2_2.setText("Vida");
		jLabel5_2_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		RSDateChooser deceso_2 = new RSDateChooser();

		JLabel jLabel5_2_3 = new JLabel();
		jLabel5_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel5_2_3.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/car.png")));
		jLabel5_2_3.setText("Coche");
		jLabel5_2_3.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel jLabel5_2_3_1 = new JLabel();
		jLabel5_2_3_1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5_2_3_1.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/comunidad.png")));
		jLabel5_2_3_1.setText("Comunidad");
		jLabel5_2_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnNewButton = new JButton("New button");

		RSDateChooser deceso_2_1 = new RSDateChooser();

		RSDateChooser deceso_2_2 = new RSDateChooser();

		RSDateChooser deceso_2_3 = new RSDateChooser();

		RSDateChooser deceso_2_4 = new RSDateChooser();

		RSDateChooser deceso_2_5 = new RSDateChooser();
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(
						Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
						.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(jLabel5_2, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHogar, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel5_2_1))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 133,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(
										lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addComponent(deceso_2_3, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(jLabel5_2_3_1, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
								.addGap(178))
								.addGroup(layout.createSequentialGroup()
										.addComponent(direccion, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
										.addGap(20)
										.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 110,
												GroupLayout.PREFERRED_SIZE)
										.addGap(34).addComponent(localidad, GroupLayout.PREFERRED_SIZE, 158,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(provincia, GroupLayout.PREFERRED_SIZE, 173,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE,
																144, GroupLayout.PREFERRED_SIZE))
												.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
														.addGroup(layout.createSequentialGroup()
																.addComponent(deceso_2_2, GroupLayout.PREFERRED_SIZE,
																		148, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(jLabel5_2_3, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGroup(layout
																.createSequentialGroup()
																.addComponent(deceso_2_1, GroupLayout.PREFERRED_SIZE,
																		148, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(jLabel5_2_2, GroupLayout.PREFERRED_SIZE,
																		151, GroupLayout.PREFERRED_SIZE))))
										.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout
												.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(Alignment.TRAILING)
														.addComponent(deceso_2, GroupLayout.DEFAULT_SIZE, 148,
																Short.MAX_VALUE)
														.addComponent(deceso_2_4, Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE, 148,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(deceso_2_5, Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE, 148,
																GroupLayout.PREFERRED_SIZE)))
												.addGroup(layout.createSequentialGroup().addGap(1).addComponent(
														codpostal, GroupLayout.PREFERRED_SIZE, 159,
														GroupLayout.PREFERRED_SIZE)))))
						.addGap(47))
				.addGroup(layout.createSequentialGroup().addGap(274).addComponent(btnNewButton).addContainerGap(285,
						Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGap(21)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addGap(15)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(direccion, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 47,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createSequentialGroup().addGap(15)
								.addComponent(localidad, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
				.addGap(10)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(provincia, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(codpostal, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
				.addGap(46)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jLabel5_2, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel5_2_2, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addComponent(deceso_2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(deceso_2_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
				.addGap(38)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHogar, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel5_2_3, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addComponent(deceso_2_2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(deceso_2_5, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
				.addGap(32)
				.addGroup(
						layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(jLabel5_2_1, GroupLayout.PREFERRED_SIZE, 54,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel5_2_3_1, GroupLayout.PREFERRED_SIZE, 54,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(deceso_2_3, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addComponent(deceso_2_4, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(btnNewButton).addContainerGap(42, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);
		setSize(new Dimension(680, 547));
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

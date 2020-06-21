package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.TooManyListenersException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.mangstadt.vinnie.VObjectProperty;
import com.github.mangstadt.vinnie.io.Context;
import com.github.mangstadt.vinnie.io.SyntaxRules;
import com.github.mangstadt.vinnie.io.VObjectDataAdapter;
import com.github.mangstadt.vinnie.io.VObjectReader;

import utils.DragAndDrop;
import utils.Metodos;

@SuppressWarnings("all")

public class ImportarVcard extends javax.swing.JFrame implements ActionListener, ChangeListener {

	String valor = "";

	JTextArea drag = new JTextArea();

	LinkedList<String> contactos = new <String>LinkedList();

	LinkedList<String> telefonos = new <String>LinkedList();

	LinkedList<String> emails = new <String>LinkedList();

	LinkedList<String> direcciones = new <String>LinkedList();

	private void guardarContactos(String file) {

		if (!Metodos.extraerExtension(file).equals("vcf")) {
			Metodos.mensaje("Por favor, selecciona un archivo vcf", 3, true);
		}

		else {

			try {

				File archivo = null;

				FileReader fr = null;

				BufferedReader br = null;

				archivo = new File(file);

				fr = new FileReader(archivo);

				br = new BufferedReader(fr);

				String cadena = "", linea = "";

				while ((linea = br.readLine()) != null) {

					cadena += linea + "\n";
				}

				fr.close();

				br.close();

				cadena = cadena.replace("BEGIN:VCARD", "");
				cadena = cadena.replace("END:VCARD", "");
				cadena = cadena.replace(";;;", "");
				cadena = cadena.replace("VERSION:2.1", "");
				cadena = cadena.replace("= ;", "");
				cadena = cadena.replace(";", "");
				cadena = cadena.replace("TELCELLPREF", "TELCELL");

				cadena = "BEGIN:VCARD" + cadena + "END:VCARD";

				Reader reader = new StringReader(cadena);

				SyntaxRules rules = SyntaxRules.vcard();

				VObjectReader vobjectReader = new VObjectReader(reader, rules);

				vobjectReader.parse(new VObjectDataAdapter() {

					private boolean inVCard = false;

					public void onComponentBegin(String name, Context context) {

						if (context.getParentComponents().isEmpty() && "VCARD".equals(name)) {
							inVCard = true;
						}

					}

					public void onComponentEnd(String name, Context context) {

						if (context.getParentComponents().isEmpty()) {
							context.stop();
						}

					}

					public void onProperty(VObjectProperty property, Context context) {

						if (inVCard) {

							if (!property.getName().equals("N")) {

								if (property.getName().equals("FN")) {
									contactos.add(property.getValue());
								}

								if (property.getName().equals("TELHOME") || property.getName().equals("TELCELL")
										|| property.getName().equals("TEL")) {

									valor = property.getValue().toString().replace("+34", "");

									valor = valor.replace(" ", "");

									valor = valor.trim();

									if (telefonos.indexOf(valor) == -1) {
										telefonos.add(valor);
									}

								}

								if (property.getName().equals("EMAILHOME")) {

									valor = property.getValue().toString();

									valor = valor.replace(" ", "");

									valor = valor.trim();

									emails.add(valor);

								}

								if (property.getName().equals("ADRHOME")) {

									valor = property.getValue().toString();

									valor = valor.replace(" ", "");

									valor = valor.trim();

									direcciones.add(valor);

								}

							}

						}

					}

				});

				ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

				ArrayList<Objeto> arrayList2;

				arrayList2 = Agenda.leer("contactos.dat");

				if (arrayList2 != null) {

					for (int i = 0; i < arrayList2.size(); i++) {

						arrayList1.add(arrayList2.get(i));
					}

				}

				Date hoy = new Date();

				for (int i = 0; i < contactos.size(); i++) {

					if (arrayList2 == null || !arrayList2.contains(contactos.get(i))) {

						if (emails.size() == 0 || emails.get(i) == null) {
							emails.add("");
						}

						if (direcciones.size() == 0 || direcciones.get(i) == null) {
							direcciones.add("");
						}

						System.out.println("direccion: " + direcciones.get(i));

						arrayList1.add(new Objeto(contactos.get(i) + "«" + emails.get(i) + "»" + "" + "¬"
								+ telefonos.get(i) + "═" + direcciones.get(i) + "▓" + "" + "░" + "" + "┤" + "" + "▒"
								+ hoy + "╣" + hoy + "║" + hoy + "╝" + hoy + "¥" + hoy + "¶" + hoy));

					}

				}

				ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream("contactos.dat"));

				escribiendoFichero.writeObject(arrayList1);

				escribiendoFichero.close();

				Agenda.limpiarContactos();

				Agenda.vaciarCampos();

				Agenda.verNotas();

				vobjectReader.close();

			}

			catch (IOException e) {

				Metodos.mensaje("Error al mover los archivos", 1, true);
			}

			catch (Exception e) {
				e.printStackTrace();
			}

			dispose();
		}

	}

	public ImportarVcard() {

		setAlwaysOnTop(true);

		setTitle("Importar Contactos");

		setType(Type.NORMAL);

		initComponents();

	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		setResizable(false);

		JButton btnNewButton = new JButton("");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				File[] files = Metodos.seleccionar("VCard (*.vcf)", "Elije un archivo de contacto");

				if (files != null) {

					try {
						guardarContactos(files[0].getCanonicalPath());

					}

					catch (IOException e1) {
						//
					}
				}

			}

		});

		btnNewButton.setIcon(new ImageIcon(ImportarVcard.class.getResource("/imagenes/abrir.png")));

		drag.setText("Arrastra aqui las VCards");
		drag.setForeground(Color.BLACK);
		drag.setFont(new Font("Tahoma", Font.PLAIN, 25));
		drag.setEditable(false);
		drag.setBackground(Color.WHITE);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(14)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(drag, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(36, Short.MAX_VALUE)));
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addGap(20)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(drag, GroupLayout.PREFERRED_SIZE, 60,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnNewButton))
										.addContainerGap(27, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);
		setSize(new Dimension(399, 136));
		setLocationRelativeTo(null);

		javax.swing.border.TitledBorder dragBorder = new javax.swing.border.TitledBorder("Drop 'em");

		try {

			new DragAndDrop(drag, dragBorder, rootPaneCheckingEnabled, new DragAndDrop.Listener() {

				public void filesDropped(java.io.File[] files) {

					try {

						guardarContactos(files[0].getCanonicalPath());

					} catch (IOException e) {
						//
					}

				}

			});

		}

		catch (TooManyListenersException e1) {
			Metodos.mensaje("Error al mover los archivos", 1, true);
		}

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

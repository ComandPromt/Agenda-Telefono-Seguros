package principal;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("all")

public class About extends Thread implements ActionListener, ChangeListener {

	static JLabel lab = new JLabel("Ramón Jesús Gómez Carmona");

	static JLabel email = new JLabel("smr2gocar@gmail.com");

	@Override
	public void run() {

		try {
			scrollEffect();
		}

		catch (InterruptedException e) {
			//
		}

	}

	public About() {

		JFrame jf = new JFrame("Scroll Effect");
		jf.setTitle("Sobre");
		jf.setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/imagenes/about.png")));

		JPanel jp = new JPanel();

		jf.setSize(600, 350);

		jp.setSize(800, 600);
		lab.setIcon(new ImageIcon(About.class.getResource("/imagenes/dev.png")));
		lab.setBounds(184, 251, 340, 48);

		lab.setHorizontalAlignment(SwingConstants.CENTER);

		lab.setFont(new Font("Arial", Font.PLAIN, 20));

		jp.setLayout(null);

		jp.add(lab);

		jf.getContentPane().add(jp);
		email.setHorizontalAlignment(SwingConstants.CENTER);
		email.setIcon(new ImageIcon(About.class.getResource("/imagenes/email.png")));

		email.setFont(new Font("Arial", Font.PLAIN, 20));
		email.setBounds(194, 319, 330, 42);
		jp.add(email);

		JLabel lblNewLabel_1 = new JLabel("Programa creado por");
		lblNewLabel_1.setIcon(new ImageIcon(About.class.getResource("/imagenes/created.png")));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(184, 12, 340, 53);
		jp.add(lblNewLabel_1);

		jf.setVisible(true);
		jf.setLocationRelativeTo(null);
	}

	public static void scrollEffect() throws InterruptedException {

		int x = -80;

		while (true) {

			if (x == -196) {
				x = -80;
			}

			x--;

			About.lab.setBounds(-120, x, 800, 600);

			About.email.setBounds(170, x, 300, 700);

			Thread.sleep(20);

		}

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}

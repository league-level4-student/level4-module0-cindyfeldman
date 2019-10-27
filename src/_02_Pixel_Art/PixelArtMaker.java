package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PixelArtMaker implements MouseListener, ActionListener{
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	ColorSelectionPanel csp;
	JButton save;
	private static final String DATA_FILE = "src/_02_Pixel_Art/saved.dat";
	private static final String Second_File = "src/_02_Pixel_Art/secondSave.dat";

	
	public void start() {
	gp = load();
	window = new JFrame("Pixel Art");
	window.setLayout(new FlowLayout());
	
	window.setResizable(false);
		if(gp==null) {
		
	
		gip = new GridInputPanel(this);	
		
	
		window.add(gip);}
		else {
			dataStuff();
		}
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

	
	}

	public void submitGridData(int w, int h, int r, int c) {
		gp = new GridPanel(w, h, r, c);
		
	
		window.remove(gip);
		dataStuff();
		}
	public void dataStuff() {	
		window.add(gp);
		csp = new ColorSelectionPanel();
		window.add(csp);
		save = new JButton();
		save.setText("Save");
		window.add(save);
		gp.repaint();
		gp.addMouseListener(this);
		window.pack();
		save.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton press = (JButton)e.getSource();

		if(press==save) {
			try (FileOutputStream fos = new FileOutputStream(new File(DATA_FILE)) ;
					
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(gp);
			
			}
			catch(IOException s) {
				s.printStackTrace();
			}
			try (FileOutputStream s = new FileOutputStream(new File(Second_File)) ;
					
					ObjectOutputStream o = new ObjectOutputStream(s)) {
				o.writeObject(csp);
				}
				catch(IOException s) {
					s.printStackTrace();
				}
		}
	}
	private static GridPanel load() {
		try(FileInputStream fis = new FileInputStream(new File(DATA_FILE));
				ObjectInputStream ois = new ObjectInputStream(fis)) {
					return (GridPanel) ois.readObject();
				}
		catch(IOException es) {
			es.printStackTrace();
			return null;
		}
		catch(ClassNotFoundException c) {
			c.printStackTrace();
			return null;
		}
	}

	
}

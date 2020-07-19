import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class Block extends JPanel{
	private static final long serialVersionUID = 1L;

	private static Point[][] arr = new Point[8][8];
	static int size;
	private Pieces pieces;
	private boolean clicked = false;
	private boolean init = true;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(init) {
			init(g);
			pieces = new Pieces(g);
			init = false;
		}
		else {
			init(g);
			pieces.move(g);
		}
	}

	public static Point[][] getArr() {
		return arr;
	}

	public Block(int x) {
		size = x/8;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!clicked) {
					boolean b = pieces.select(findClosest(e.getX(), e.getY()));
					repaint();
					if(b) clicked = true;
				}
				else {
					boolean b = pieces.put(findClosest(e.getX(), e.getY()));
					repaint();
					if(b) clicked = false;
				}
			}});
	}

	private Point findClosest(int x, int y) {
		int locX = arr[7][0].x;
		int locY = arr[0][7].y;

		for(int i = 0; i < 8; i++) {
			if(x < arr[i][0].x) {
				locX = arr[i-1][0].x;
				break;
			}
		}
		for(int i = 0; i < 8; i++) {
			if(y < arr[0][i].y) {
				locY = arr[0][i - 1].y;
				break;
			}
		}
		return new Point(locX, locY);
	}

	private void init(Graphics g) {
		Graphics2D b = (Graphics2D) g;
		b.setColor(Color.white);
		for(int j = 0; j < 8; j++) {
			for(int i = 0; i < 8; i++) {
				b.fillRect(i*size, j*size, size, size);
				arr[i][j] = new Point(i*size, j*size);
				b.setColor(b.getColor().equals(Color.darkGray) ? Color.white : Color.DARK_GRAY);
			}
			b.setColor(b.getColor().equals(Color.darkGray) ? Color.white : Color.DARK_GRAY);
		}	
	}

}

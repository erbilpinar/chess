
import javax.swing.JFrame;
	
public	class game{	

	public static void main(String[] args) {

		JFrame frame = new JFrame("Chess");
		Block rects = new Block(1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(rects);
		frame.setSize(1000, 1025);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}


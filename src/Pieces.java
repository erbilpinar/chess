import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Pieces extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Point[][] arr = Block.getArr();
	//private ArrayList<Point> possMoves;
	private boolean starter = true; //T for white, F for black
	private Point selectedPoint = null;

	private HashMap<Point, BufferedImage> wMap = new HashMap<Point, BufferedImage>();
	private HashMap<Point, BufferedImage> bMap = new HashMap<Point, BufferedImage>();
	///// with order: pawn rock knight bishop king queen
	private BufferedImage[] wMoves = new BufferedImage[6];
	///// with order: pawn rock knight bishop queen king
	private BufferedImage[] bMoves = new BufferedImage[6];
	private File[] wFile = {new File("wPawn.png"),
			new File("wRock.png"), new File("wKnight.png"), new File("wBishop.png"),
			new File("wKing.png"), new File("wQueen.png"),
			new File("wBishop.png"), new File("wKnight.png"), new File("wRock.png")};
	private File[] bFile = {new File("bPawn.png"),
			new File("bRock.png"), new File("bKnight.png"),  new File("bBishop.png"),
			new File("bQueen.png"), new File("bKing.png"),
			new File("bBishop.png"), new File("bKnight.png"), new File("bRock.png")};  

	public Pieces(Graphics g) {
		BufferedImage gg;
		try {
			File[] file = bFile;
			for(int j = 0; j < 2; j++) {
				HashMap<Point, BufferedImage> map = new HashMap<Point, BufferedImage>();
				BufferedImage[] moves = new BufferedImage[6];
				int y = (j == 0 ? 0 :  7);
				for(int i = 1; i < file.length; i++) {
					gg = ImageIO.read(file[i]);
					if(i < 6) moves[i] = gg;
					map.put(arr[i - 1][y], gg);
					g.drawImage(gg, arr[i - 1][y].x, arr[i - 1][y].y, 125, 125, this);
				}
				gg = ImageIO.read(file[0]);
				moves[0] = gg;
				for(int i = 0; i < 8; i++) {
					int x = (j == 0 ? 1 :  6);
					map.put(arr[i][x], gg);
					g.drawImage(gg, arr[i][x].x, arr[i][x].y, 125, 125, this);
				}
				if(y == 0) {
					bMap = map;
					bMoves = moves;
				}
				else if(y == 7) {
					wMap = map;
					wMoves = moves;
				}

				file = wFile;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public boolean select(Point p) {
		if(starter && wMap.containsKey(p)) {
			selectedPoint = p;
			starter = false;
			return true;
		}
		if(!starter && bMap.containsKey(p)) {
			selectedPoint = p;
			starter = true;
			return true;
		}
		return false;
	}

	public boolean put(Point p) {
		HashMap<Point, BufferedImage> map = bMap; //selected
		HashMap<Point, BufferedImage> map2 = wMap; //other
		if(wMap.containsKey(selectedPoint)) {
			map = wMap;
			map2 = bMap;
		}
		if(map.containsKey(p)) return false;  //blackOnBlack or WhiteOnWhite
		if(map2.get(p) != null) { //killing the other
			map2.remove(p);
		}
		map.put(p, map.get(selectedPoint));
		map.remove(selectedPoint);
		return true;
	}

	public void move(Graphics g) {
		if(starter) { //white moves
			BufferedImage moveingImage = null;
			for(Point p : wMap.keySet()) {
				BufferedImage gg  = wMap.get(p);
				if(p.equals(selectedPoint)) {
					moveingImage = gg;
					g.drawImage(gg, p.x + 15, p.y + 15, 95, 95, this);
				}
				else
					g.drawImage(gg, p.x, p.y, 125, 125, this);
			}
			//if(moveingImage != null) whereToMove(moveingImage, g);

			for(Point p : bMap.keySet()) {
				BufferedImage gg  = bMap.get(p);
				if(p.equals(selectedPoint)) {
					moveingImage = gg;
					g.drawImage(gg, p.x + 15, p.y + 15, 95, 95, this);
				}
				else
					g.drawImage(gg, p.x, p.y, 125, 125, this);
			}
		}
		else { //black moves
			BufferedImage moveingImage = null;
			for(Point p : bMap.keySet()) {
				BufferedImage gg  = bMap.get(p);
				if(p.equals(selectedPoint)) {
					moveingImage = gg;
					g.drawImage(gg, p.x + 15, p.y + 15, 95, 95, this);
				}
				else
					g.drawImage(gg, p.x, p.y, 125, 125, this);
			}
			for(Point p : wMap.keySet()) {
				BufferedImage gg  = wMap.get(p);
				if(p.equals(selectedPoint)) {
					moveingImage = gg;
					g.drawImage(gg, p.x + 15, p.y + 15, 95, 95, this);
				}
				else
					g.drawImage(gg, p.x, p.y, 125, 125, this);
			}
			//if(moveingImage != null) whereToMove(moveingImage, g);

		}

	}

//	private void whereToMove(BufferedImage image, Graphics g) {
//		possMoves = new ArrayList<Point>();
//		int k = 0, l = 0;
//		for(int i = 0; i < 8; i++) 
//			for(int j = 0; j < 8; j++) 
//				if(arr[i][j].equals(selectedPoint)) {
//					k = i;
//					l = j;
//				}
//		
//		Graphics2D b = (Graphics2D) g;
//		b.setPaintMode();
//		b.setColor(Color.lightGray);
//
//		if(starter) { //we selected black
//			if(image.equals(bMoves[0])) {  //pawn
//				if(l == 1) { //it can move two blocks for the first move
//					b.fillRect(k*Block.size + 5, (l+2)*Block.size + 5, Block.size - 10,Block.size - 10);
//					possMoves.add(new Point(k*Block.size, (l+2)*Block.size));
//				}
//				b.fillRect(k*Block.size + 5, (l+1)*Block.size + 5, Block.size - 10,Block.size - 10);
//				possMoves.add(new Point(k*Block.size, (l+1)*Block.size));
//				
//			}
//			else if(image.equals(bMoves[1])) { //rock
//
//			}else if(image.equals(bMoves[2])) { //knight
//
//			}else if(image.equals(bMoves[3])) { //bishop
//
//			}else if(image.equals(bMoves[4])) { //queen
//
//			}else if(image.equals(bMoves[5])) { //king
//
//			}
//		}else { //we selected white
//			if(image.equals(wMoves[0])) {  //pawn
//
//			}else if(image.equals(wMoves[1])) { //rock
//
//			}else if(image.equals(wMoves[2])) { //knight
//
//			}else if(image.equals(wMoves[3])) { //bishop
//
//			}else if(image.equals(wMoves[4])) { //king
//
//			}else if(image.equals(wMoves[5])) { //queen
//
//			}
//		}
//
//	}
}

package Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;
import sun.audio.*;
import java.io.*;
public class Main extends JFrame {

	Image dbImage;
	Graphics dbg;
	boolean stop=false;
	static Ball b = new Ball(300, 300);
	Thread ball = new Thread(b), paddle1 = new Thread(b.p1), paddle2 = new Thread(b.p2);
	Rectangle start = new Rectangle(240, 220, 100, 30);
	Rectangle quit = new Rectangle(240, 270, 100, 30);
	boolean gamestarted = false, changecolor[] = { false, false, false }, difficulty = false, gameover = false;
	String winner = "Winner is P1";

	public Main() {
		setTitle("Java Game");
		setSize(600, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(Color.black);
		addKeyListener(new KAL());
		addMouseListener(new MAL());
		addMouseMotionListener(new MAL());
		musicplay();
	}

	public void startgame() {
		gamestarted = true;
		ball.start();
		paddle1.start();
		paddle2.start();
	}

	@Override
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		draw(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void draw(Graphics g) {
		if (!gamestarted && !gameover) {
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			g.setColor(Color.white);
			g.drawString("Pong Game", 190, 180);
			if (!changecolor[0])
				g.setColor(Color.green);
			else if (changecolor[0])
				g.setColor(Color.yellow);
			g.fillRect(start.x, start.y, start.width, start.height);
			if (!changecolor[1])
				g.setColor(Color.green);
			else if (changecolor[1])
				g.setColor(Color.yellow);
			g.fillRect(quit.x, quit.y, quit.width, quit.height);
			g.setFont(new Font("Times New Roman", Font.BOLD, 22));
			g.setColor(Color.gray);
			g.drawString("Start", start.x + 30, start.y + 22);
			if (!difficulty) {
				g.drawString("Easy", quit.x + 28, quit.y + 20);
				b.setDiffculity(5);
			} else {
				g.drawString("Hard", quit.x + 28, quit.y + 20);
				b.setDiffculity(3);
			}

		} else if (gamestarted && !gameover) {
			g.setFont(new Font("Times New Roman", Font.BOLD, 15));
			g.setColor(Color.gray);
			g.drawString("Press space to stop", 230, 480);
			b.draw(g);
			b.p1.draw(g);
			b.p2.draw(g);
			g.setColor(Color.blue);
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			g.drawString("" + b.p1Score, 150, 60);
			g.setColor(Color.red);
			g.drawString("" + b.p2Score, 450, 60);
			dashedline(g);
		} else if (gamestarted && gameover) {
			g.setFont(new Font("Times New Roman", Font.BOLD, 60));
			g.setColor(Color.orange);
			g.drawString("Game Over", 140, 220);
			g.drawString(winner, 125, 300);
			if (!changecolor[2])
				g.setColor(Color.green);
			else if (changecolor[2])
				g.setColor(Color.yellow);
			g.fillRect(250, 350, 100, 30);
			g.setFont(new Font("Times New Roman", Font.BOLD, 22));
			g.setColor(Color.gray);
			g.drawString("Reset", 250 + 25, 350 + 20);
		}
		gameovered();
		repaint();
	}

	public void gameovered() {
		if (b.p1Score >= 5 || b.p2Score >= 5) {
			gameover = true;
			threadstop();
		}
		if (b.p1Score >= 5)
			winner = "Winner is P1";
		else if (b.p2Score >= 5)
			winner = "Winner is P2";
	}

	public void threadstop() {
		ball.stop();
		paddle1.stop();
		paddle2.stop();
	}

	public void dashedline(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		float dash[] = { 10.0f };
		g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g2.setPaint(Color.white);
		g2.drawLine(300, 0, 300, 500);
		g2.dispose();
	}

	public class KAL extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent k) {
			int keycode=k.getKeyCode();
			if (keycode==k.VK_SPACE) {
				if (stop==false && gamestarted && !gameover) {
					ball.stop();
		 			paddle1.stop();
					paddle2.stop();
					stop=true;
				}
				else {
					stop=false;
					ball = new Thread(b); paddle1 = new Thread(b.p1); paddle2 = new Thread(b.p2);
					ball.start();
		 			paddle1.start();
					paddle2.start();
				}
			}
			b.p1.keyPreased(k);
			b.p2.keyPreased(k);
		}

		@Override
		public void keyReleased(KeyEvent k) {
			b.p1.keyReleased(k);
			b.p2.keyReleased(k);
		}
	}

	public class MAL extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent m) {
			int mx = m.getX(), my = m.getY();
			if (mx > start.x && mx < start.x + start.width && my > start.y && my < start.y + start.height)
				changecolor[0] = true;
			else
				changecolor[0] = false;
			if (mx > quit.x && mx < quit.x + quit.width && my > quit.y && my < quit.y + quit.height)
				changecolor[1] = true;
			else
				changecolor[1] = false;
			if (mx > 250 && mx < 250 + 100 && my > 350 && my < 350 + 30)
				changecolor[2] = true;
			else
				changecolor[2] = false;
		}

		@Override
		public void mousePressed(MouseEvent m) {
			int mx = m.getX(), my = m.getY();
			if (mx > start.x && mx < start.x + start.width && my > start.y && my < start.y + start.height)
				startgame();
			if (mx > quit.x && mx < quit.x + quit.width && my > quit.y && my < quit.y + quit.height)
				difficulty = !difficulty;
			if (mx > 250 && mx < 250 + 100 && my > 350 && my < 350 + 30) {
				gameover = false;
				gamestarted = false;
				b.p1Score = 0;
				b.p2Score = 0;
				b = new Ball(300, 300);
				b.p1 = new Paddle(15, 250, 1); b.p2 = new Paddle(575, 250, 2);
				ball = new Thread(b); paddle1 = new Thread(b.p1); paddle2 = new Thread(b.p2);
			}
		}
	}
	public static void musicplay(){
		{
			try {
					FileInputStream fileau=new  FileInputStream("b.wav");
					AudioStream as=new AudioStream(fileau);
					AudioPlayer.player.start(as);
				}				
			catch (Exception e) {}
		}
	}
	public static void main(String[] args) {
		Main game = new Main();
	}

}
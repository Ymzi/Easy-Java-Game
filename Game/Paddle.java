package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle implements Runnable {

	int x, y, yDir, id;
	Rectangle paddle;

	public Paddle(int _x, int _y, int playerid) {
		x = _x;
		y = _y;
		id = playerid;
		paddle = new Rectangle(x, y, 10, 60);
	}

	public void keyPreased(KeyEvent k) {
		int keycode = k.getKeyCode();
		switch (id) {
		case 2:
			if (keycode == k.VK_UP) {
				SetyDir(-1);
			}
			if (keycode == k.VK_DOWN) {
				SetyDir(1);
			}
			break;
		case 1:
			if (keycode == k.VK_W) {
				SetyDir(-1);
			}
			if (keycode == k.VK_S) {
				SetyDir(1);
			}
			break;
		default:
			System.out.println("Error No Such a playerid");
			break;
		}
	}

	public void keyReleased(KeyEvent k) {
		int keycode = k.getKeyCode();
		switch (id) {
		case 2:
			if (keycode == k.VK_UP) {
				SetyDir(0);
			}
			if (keycode == k.VK_DOWN) {
				SetyDir(0);
			}
			break;
		case 1:
			if (keycode == k.VK_W) {
				SetyDir(0);
			}
			if (keycode == k.VK_S) {
				SetyDir(0);
			}
			break;
		default:
			System.out.println("Error No Such a playerid");
			break;
		}
	}

	public void SetyDir(int _yDir) {
		yDir = _yDir;
	}

	public void move() {
		paddle.y += yDir;
		if (paddle.y <= 30)
			paddle.y = 30;
		if (paddle.y >= 440)
			paddle.y = 440;
	}

	public void draw(Graphics g) {
		switch (id) {
		case 1:
			g.setColor(Color.blue);
			g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
			break;
		case 2:
			g.setColor(Color.red);
			g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
			break;
		default:
			System.out.println("Error No Such a playerid");
			break;
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				move();
				Thread.sleep(4);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}

package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import sun.audio.*;
import java.io.*;
public class Ball implements Runnable {

	
	int x, y, xDir, yDir,difficulty = 5;
	Rectangle ball;
	int p1Score, p2Score;
	static Paddle p1 = new Paddle(15, 250, 1);
	static Paddle p2 = new Paddle(575, 250, 2);
	public Ball(int _x, int _y) {
		p1Score = p2Score = 0;
		x = _x;
		y = _y;

		Random r = new Random();
		int rxDir = r.nextInt(2);
		if (rxDir == 0)
			rxDir = -1;
		SetxDir(rxDir);
		int ryDir = r.nextInt(2);
		if (ryDir == 0)
			;
		ryDir = -1;
		SetyDir(ryDir);

		ball = new Rectangle(x, y, 10, 10);
	}

	public void SetxDir(int _xDir) {
		xDir = _xDir;
	}

	public void SetyDir(int _yDir) {
		yDir = _yDir;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(ball.x, ball.y, ball.width, ball.height);
	}

	public void collision() {
		if (ball.intersects(p1.paddle)){
			SetxDir(1);
			try {
				FileInputStream fileau=new  FileInputStream("a.wav");
				AudioStream as=new AudioStream(fileau);
				AudioPlayer.player.start(as);
			}
				catch (Exception e) {}
		}
		if (ball.intersects(p2.paddle)){
			SetxDir(-1);
			try {
				FileInputStream fileau=new  FileInputStream("a.wav");
				AudioStream as=new AudioStream(fileau);
				AudioPlayer.player.start(as);
			}
				catch (Exception e) {}
		}
	}

	public void move() {
		collision();
		ball.x += xDir;
		ball.y += yDir;
		if (ball.x <= 0) {
			SetxDir(1);
			++p2Score;
		}
		if (ball.x >= 580) {
			SetxDir(-1);
			++p1Score;
		}
		if (ball.y <= 30)
			SetyDir(1);
		if (ball.y >= 490)
			SetyDir(-1);
	}
	
	public void setDiffculity(int _difficulty) {
		difficulty =_difficulty;
	}

	@Override
	public void run() {
		try {
			while (true) {
				move();
				Thread.sleep(difficulty);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}

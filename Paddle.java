import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{

	int id;					// variable used to distinguish between two paddles
	int yVelocity;           // To move paddele up and down direction
	int speed = 10;			// Paddle speed
	
	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
		super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);   // calls the constructor of the rectangle class
		this.id=id;   // assigning id to the class variable
	}
	
	public void keyPressed(KeyEvent e) {       // KeyEvent is a part of awt library
		switch(id) {
		case 1:                                     // Paddle 1
			if(e.getKeyCode()==KeyEvent.VK_W) {        // VK = Virtual Key
				setYDirection(-speed);					// upward direction
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {			// getKeyEvent method is part of KeyEvent
				setYDirection(speed);					// downward direction
			}
			break;
		case 2:                                    // Paddle 2
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(-speed);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				setYDirection(speed);
			}
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch(id) {
		case 1:										// for paddle 1
			if(e.getKeyCode()==KeyEvent.VK_W) {
				setYDirection(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				setYDirection(0);
			}
			break;
		case 2:										// for paddle 2
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				setYDirection(0);
			}
			break;
		}
	}
	
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}

	public void move() {    //The move method updates the y-coordinate of the paddle (y) by adding the yVelocity, thus moving the paddle up or down.
		y = y + yVelocity;
	}
	public void draw(Graphics g) {     // The draw method is responsible for drawing the paddle on the screen using the Graphics object (g).
		if(id==1)
			g.setColor(Color.blue);   // color of paddle 1
		else
			g.setColor(Color.red);		// color of paddle 2
		g.fillRect(x, y, width, height);
	}
}
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
	// initialization of constants
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 150;
	Thread gameThread;  // The gameThread is used to run the game loop in a separate thread.


	// creating the instance variables
	// paddle1, paddle2, ball, and score are objects for the paddles, ball, and scoring.
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	Random random;  // random is used for generating random values.
	//image and graphics are used for double buffering, which helps prevent flickering in the game rendering.
	Image image;  
	Graphics graphics;
	
	GamePanel(){
		newPaddles();   // calls the newPaddle method 
		newBall();      // calls the newBall method
		score = new Score(GAME_WIDTH,GAME_HEIGHT);  // This line creates an instance of a Score class, passing GAME_WIDTH and GAME_HEIGHT as parameters.


		// configuring panel properties
		this.setFocusable(true);  // This line sets the GamePanel to be focusable, meaning it can receive keyboard input. This is essential for handling key events 
		this.addKeyListener(new AL());  // This means the GamePanel will listen for key events and respond to them using the methods defined in the AL class.
		this.setPreferredSize(SCREEN_SIZE);
		

		// starting the game thread
		gameThread = new Thread(this);  // These lines create a new thread (gameThread) and start it. Starting the thread suggests that the game will be run in the background, allowing the game logic to run independently of the main application thread.
		gameThread.start();
	}
	
	// initialization of reference variables of ball
	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
	}

	// initialization of reference variables of paddle
	public void newPaddles() {
		paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	public void draw(Graphics g) {  // The draw method is responsible for rendering the game objects. It calls the draw method of the Paddle, Ball, and Score objects.
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		Toolkit.getDefaultToolkit().sync(); // it helps with the animation
	}
	public void move() {  // The move method updates the position of the paddles and the ball.
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	public void checkCollision() {  // The checkCollision method handles various collision events, including bouncing the ball off the window edges, bouncing the ball off paddles, and handling scoring.
		
		//bounce ball off top & bottom window edges
		if(ball.y <=0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		//bounce ball off paddles
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty
			if(ball.yVelocity>0)
				ball.yVelocity++; //optional for more difficulty
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
			//paddle1.adjustSize(ball.xVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //optional for more difficulty
			if(ball.yVelocity>0)
				ball.yVelocity++; //optional for more difficulty
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
			//paddle2.adjustSize(ball.xVelocity);
		}
		
		//stops paddles at window edges
		if(paddle1.y<=0)
			paddle1.y=0;
		if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
		if(paddle2.y<=0)
			paddle2.y=0;
		if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
		//give a player 1 point and creates new paddles & ball
		if(ball.x <=0) {
			score.player2++;
			newPaddles();
			newBall();
			System.out.println("Player 2: "+score.player2);
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
			System.out.println("Player 1: "+score.player1);
		}

		if (ball.x <= 0) {
            score.player2++;
            checkWinner();
            newPaddles();
            newBall();
            System.out.println("Player 2: " + score.player2);
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++;
            checkWinner();
            newPaddles();
            newBall();
            System.out.println("Player 1: " + score.player1);
        }
	}

	private void checkWinner() {
        // Check if a player has won (score more than 5)
        if (score.player1 > 5 || score.player2 > 5) {
            if (score.player1 > score.player2) {
                System.out.println("Player 1 wins!");
            } else {
                System.out.println("Player 2 wins!");
            }
            System.exit(0); // Terminate the program
        }
    }

	public void run() {  // The run method is the game loop. It continually updates the game, checks for collisions, and repaints the screen to create the animation. It uses a fixed frame rate of 60 frames per second.

		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	// AL - Action Listener
	public class AL extends KeyAdapter{  //The AL inner class extends KeyAdapter to handle keyboard input. It overrides the keyPressed and keyReleased methods to manage user input for the paddles.
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}

}
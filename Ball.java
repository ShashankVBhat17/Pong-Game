import java.util.*;       // for the utility packages 
						  // to import random package

import java.awt.*;     //AWT stands for abstract window toolkit 
						// for graphical user interface, and for handling window, graphics and user interface components

public class Ball extends Rectangle
// The Rectangle class, as the name suggests, is a class used to define and work with rectangles.
{
	// variables
	int xVelocity;     // to store the current speed and direction of ball in x axes    
	int yVelocity;     // ... in y axes. 
	int initialSpeed = 3;   // initial speed of the ball is set to 3
	
	Ball(int x, int y, int width, int height)   // for movement of the ball 
	{
		super(x,y,width,height);        // will call super class constructor(Rectangle) and set the width, height and positon x and y

		Random random = new Random();

		// randomXdirection = 0 ---> ball will move left
		// randomXdirection = 1 ---> ball will move right
		int randomXDirection = random.nextInt(2);   // will generate a random numbers i.e., 0 and 1 (Note: 2 is excluded)
		if(randomXDirection == 0)		// if the randonXDirection is 0
			randomXDirection--;                  // ball will go left
		setXDirection(randomXDirection*initialSpeed);
		
		// randomYdirection = 0 ---> ball will move up
		// randomYdirection = 1 ---> ball will move down
		int randomYDirection = random.nextInt(2);
		if(randomYDirection == 0)
			randomYDirection--;                 
		setYDirection(randomYDirection*initialSpeed);	
	}
	
	public void setXDirection(int randomXDirection) // to set velocity of ball in x axis
	{
		xVelocity = randomXDirection;
	}

	public void setYDirection(int randomYDirection) // to set the velocity of ball in y axis
	{
		yVelocity = randomYDirection;
	}

	public void move() //The move method updates the position of the ball by adding the x and y velocities to its current position.
	{
		x += xVelocity;  // update the position of the ball
		y += yVelocity;
	}

	public void draw(Graphics g) // to draw ball on the screen 
								//'g' is the object of Graphics
	{
		g.setColor(Color.yellow);  // set the color of the ball
		g.fillOval(x, y, height, width); // to draw a filled oval
	}

	
}

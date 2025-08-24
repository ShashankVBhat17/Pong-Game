import java.awt.*;
import javax.swing.*;   // for graphical user interface and handling events
						 //to import JFrame
						// JFrame is used to create game window

public class GameFrame extends JFrame{     
//The GameFrame class extends JFrame, which allows you to create a window for your game.
// JFrame ---> for minimize and maximize button                        
	
	GameFrame(){ // to set the game frame
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setTitle("Playing Pong Game");     // sets the title on the top of the output window
		this.setResizable(false);               // to avoid changing the size of the screen        
		this.setBackground(Color.black);                           // For black bg color
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // the game window will be closed when the user clicks the close button.
		this.pack();                           //which adjusts the frame's size to fit its contents.
		this.setVisible(true);    // It sets the window to be visible
		this.setLocationRelativeTo(null);  // It centers the window on the screen
	}
}
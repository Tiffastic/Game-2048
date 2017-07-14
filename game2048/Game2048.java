package game2048;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Program        :  Game2048
 * Programmer :  Thuy Nguyen
 * Date               :  April 4, 2014
 * Description   :  A programming challenge to create the Game 2048 like the one online
 * 					      nested for loops and if statements is the heart of the algorithm
 *
 */
public class Game2048 extends JFrame implements ActionListener
{
	private GamePanel gamePanel;
	private Timer timer;
	String userName;
	public static void main(String[] args)
	{
		new Game2048();
	}
	
	public Game2048()
	{   
		try
	{
		int sizeOfBoard = Integer.parseInt(JOptionPane.showInputDialog("What size board would you like?"));
			this.gamePanel = new GamePanel(sizeOfBoard);
			
			userName = JOptionPane.showInputDialog("What is your name?");
			setVisible(true);
			setExtendedState(MAXIMIZED_BOTH);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			trackScore();
			GamePanel.LabelListener labelListen = gamePanel.new LabelListener();  // calling an inner class of the GamePanel
			addKeyListener(labelListen);   // it's the frame that the gamePanel is added on to that needs the key listener
			add(gamePanel);
			
			timer = new Timer(100, this);
			timer.start();
	} catch (NumberFormatException e)
	{
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(null, "Game board must be at least 3x3");
	} catch (HeadlessException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		trackScore();
	}

	private void trackScore()
	{
		setTitle(String.format("%s's high score: %d", userName, gamePanel.getScore()));
	}
	

	
}

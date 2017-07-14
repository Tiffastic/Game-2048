package game2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Program:  Game 2048 Panel
 * Programmer:  Thuy Nguyen
 * Date: April 4, 2014
 * Description:  A programming challenge to create the Game 2048 like on one online.  
 * 						Combining the knowledge of gui and nested loops to create a fantastic game
 * @author Thuy
 *
 */
public class GamePanel extends JPanel
{
	
	private Random generator = new Random();
	private GameLabel[][] array;
	private int sleep = 100;
	private int myRows, myCols;
	private int score;
	
	public GamePanel(int size)
	{
		// Set up the game board and initialize score to zero
		setBoardSize(size);
		score = 0;
		array = new GameLabel[this.myRows][this.myCols];
		
		// make the main panel into a grid layout so we can add new panels filled with labels to each row of main panel
		setLayout(new GridLayout(this.myRows, 1, 10, 5));
		makeGameBoard();
		
		// set random labels with the value of 2, print the board and refresh the screen
		setRandomTitles();
		printBoard();
		validate();
	}

	// fill the game board with our custom JLabels
	private void makeGameBoard()
	{
		for(int row = 0; row < array.length; row++)
		{
			JPanel panel = new JPanel(new GridLayout(1, this.myCols, 5, 5));  // do a grid layout to add the labels, flow layout would just make them all squashed in the center
			for (int col = 0; col <array.length; col++)   // for each column
			{
				array[row][col] = new GameLabel();  // add a new GameLabel to the column
				panel.add(array[row][col]);
				
			}
			add(panel);
		}
	}
	
	
	// the game board has the size chosen by the user
	public void setBoardSize(int size)
	{
		if (size >= 3)
		{
			myRows = size;
			myCols = size;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Game board must be at least 3x3");
			System.exit(0);
		}
	}
	// this private class allows us to customize our labels so we can add them up during the game
	private class GameLabel extends JLabel
	{
		public int value;  // this private variable of each Label will help us determine the empty spaces and which label to add up
		private Font font = new Font("Times Roman", Font.BOLD, 55-myRows); GameLabel()
		{
			setOpaque(true);
			//setBackground(new Color(generator.nextInt(256), generator.nextInt(256), (generator.nextInt(256))));
			setBackground(Color.pink);
			setHorizontalAlignment(JLabel.CENTER);  //need to set the HorizontalAlignment to JLabel.CENTER so the number appears in the middle of the label
			setFont(font);
			
		}
		
	}
	
	// select a random label on the game board and set its value to 2
	public void setRandomTitles()
	{
		for (int i = 1; i <= 4; i ++)
		{
			
			array[generator.nextInt(myRows)][generator.nextInt(myCols)].value = 2;
		}
	}
	
	public void emergeRandom2()
	{
		boolean hasEmptySpaces = false;
		// check to see if there are empty spaces still:
		mainLoop:
		for (int i = 0; i < array.length; i++)  // for every row
		{
			for (int j = 0; j < array.length; j++)  // for every column
			{
				if (array[i][j].value == 0)  // if the value is zero, then there is still an empty space
				{
					hasEmptySpaces = true;
					break mainLoop;  // using label to break out of a nested for loop
				}
			}
		}
		
		if (hasEmptySpaces)
		{
			boolean done = false;
			
			do
			{
				int row = generator.nextInt(myRows);  // select a random row
				int col = generator.nextInt(myCols);   // select a random column
				
				if (array[row][col].value == 0)   // when an empty space is found
				{
					array[row][col].value = 2;   // set the value of the label to 2
					array[row][col].setBackground(new Color(generator.nextInt(256), generator.nextInt(256), generator.nextInt(256)));
					done = true;
				}
		
			}
			while (!done);
		}
	
	}
	
	
	public class LabelListener extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			switch(keyCode)
			{
			case KeyEvent.VK_UP:
				System.out.println("Up");
														moveLabelsUp();
														break;
			
			case KeyEvent.VK_DOWN:
				System.out.println("Down");
													moveLabelsDown();
														break;
		
			case KeyEvent.VK_RIGHT:
				System.out.println("Right");
													moveLabelsRight();
														break;
		
			case KeyEvent.VK_LEFT:
				System.out.println("Left");
													 moveLabelsLeft();
														break;
				
				
			}
		}
	}
	
	public void moveLabelsUp()
	{
	
		for (int row = 0; row < array.length-1; row++)  // starting at the first row
		{   
			for (int col = 0; col < array.length; col++)   // for every column
			{
				int currentValue = array[row][col].value;   
				for (int up = row+1; up < array.length; up++)    // check every value above
				{
					if (array[up][col].value != 0)   // if the above value is not equal to zero
					{
						if (currentValue == array[up][col].value)   // if the above value is equal to the current value
						{
							array[row][col].value *= 2;   // then double the current value
							score += array[row][col].value;
							array[up][col].value = 0;  // and set the above value to zero
							try
							{
								Thread.sleep(sleep);
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						
						break;  // once we find a value that is not zero, break out of the loop regardless of equality or not
					}
				}
			}
		}
		
		for (int row = 0; row < array.length-1; row++)   // starting at the first row
		{
			for (int col = 0; col < array.length; col++)   // for every column
			{
				if (array[row][col].value == 0)  // if the value is zero (empty space)
				{
					for (int up = row +1; up < array.length; up++)  // check each space underneath
					{
						if (array[up][col].value != 0)   // if the value underneath is not equal to zero
						{
							array[row][col].value = array[up][col].value;  // then this value takes on the value underneath
							array[up][col].value = 0;   // then set the value underneath to zero
							break;  // once we find a value that is not zero, then break out the loop
						}
					}
				}
			}
		}
		emergeRandom2();
		printBoard();
	}
	
	public void moveLabelsDown()
	{
		for (int row = array.length-1; row > 0; row--)  // starting at the last row
		{
			for (int col = 0; col < array.length; col++)  // for every column
			{
				int currentValue = array[row][col].value;   
				for (int down = row-1; down >= 0; down--)  // check the space above
				{
					if (array[down][col].value != 0)  // if the space above does not equal zero
					{
						if (currentValue == array[down][col].value)   // if the value above equals this value
						{
							array[row][col].value *=2;  // double this value
							score += array[row][col].value;
							array[down][col].value = 0;  // set the value above to zero
							try
							{
								Thread.sleep(sleep);
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						break;
					}
				}
			}
		}
		
		for (int row = array.length-1; row > 0; row--)  // starting at the last row
		{
			for (int col = 0; col < array.length; col++)  // for every column
			{
				innerLoop:
				if (array[row][col].value == 0)  // if this value is equal to zero (empty space)
				{
					for (int down = row-1; down >= 0; down--)  // then check the spaces above
					{
						if (array[down][col].value != 0)  // when there is a number found above
						{
							array[row][col].value = array[down][col].value;  // then this value takes on the value above
							array[down][col].value = 0;   // the above value is set to zero
							break innerLoop;
						}
					}
				}
			}
		}
		emergeRandom2();
		printBoard();
	}

	
	public void moveLabelsRight()
	{
		for (int col = array.length-1; col > 0; col--)  // starting at the last column
		{
			for (int row = 0; row < array.length; row++)  // for every row
			{
				int currentValue = array[row][col].value;  
				
				for (int left = col - 1; left >= 0; left--)  // checking all the columns to the left
				{
					if (array[row][left].value != 0)  // if the left value is not zero
					{
						if (array[row][left].value == currentValue)  // check to see if the left value is equal to this value
						{
							array[row][col].value *= 2;    // if the left value is equal to this value, then double this value
							score += array[row][col].value;
							array[row][left].value = 0;     // then set the left value to zero
							try
							{
								Thread.sleep(sleep);
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						break;   // break regardless of equality
					}
				}
				
			}
		}
		
		for (int col = array.length-1; col > 0; col--)  // starting at the last column
		{
			for (int row = 0; row < array.length; row++)  // for every row
			{
				int currentValue = array[row][col].value; 
				innerLoop:
				if (currentValue == 0)   // if the current value is zero (empty space)
				{
					for (int left = col-1; left >= 0; left--)   // check the values to the left
					{
						if (array[row][left].value != 0)  // when a number is found
						{
							array[row][col].value = array[row][left].value;  // set this value to the left value
							array[row][left].value = 0;   // set the left value to zero
							break innerLoop;
						}
					}
				}
			}
		}
		emergeRandom2();
		printBoard();
	}
	
	
	public void moveLabelsLeft()
	{
		for (int col = 0; col < array.length-1; col++)  // starting at the first column
		{
			for (int row = 0; row < array.length; row++)  // for every row
			{
				int currentValue = array[row][col].value;  
				innerLoop:
				for (int right = col+1; right < array.length; right++)   // check the spaces to the right
				{
					if (array[row][right].value != 0)  // when there is a number found to the right
					{
						if (array[row][right].value == currentValue) // if the right value equals to this value
						{
							array[row][col].value *= 2;  // double this value
							score += array[row][col].value;
							array[row][right].value = 0; // set the right value to zero
							
							try
							{
								Thread.sleep(sleep);
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break innerLoop;
						}
						break innerLoop;
					}
					
				}
			}
		}
		
		for (int col = 0; col < array.length-1; col++)  // starting at the first column
		{
			for (int row = 0; row < array.length; row++)  // for every row
			{
				int currentValue = array[row][col].value;  
				if (currentValue == 0)                                     // if the this value is zero (empty space)
				{
					for (int right = col+1; right < array.length; right++)  // check the spaces to the right
					{
						if (array[row][right].value != 0)   // when a number to the right is found
						{
							array[row][col].value = array[row][right].value;  // set this value to the right value
							array[row][right].value = 0;   // set the right value to zero
							break;
						}
						
					}
				}
			}
		}
		emergeRandom2();
		printBoard();
	}
	
	// print out the board after each move
	private void printBoard()
	{
		for (int row = 0; row < array.length; row++)  // for every row
		{
			for (int col = 0; col < array.length; col++) // for every column
			{
				int labelValue = array[row][col].value;
				if (labelValue != 0)   								    // if the value of the label is not zero
				{
					array[row][col].setText(String.valueOf(labelValue));   // then set its text to its value
					validate();
				}
				else
				{
					array[row][col].setText(null);         
					validate();  /// else, set its text to null because zero is empty space
				}
			}
		}
	}
	
	public int getScore()
	{
		return score;
	}

}

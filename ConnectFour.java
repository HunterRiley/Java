import java.util.Scanner;
/* Project2 - Connect four game
 * 
 * Author: Hunter Riley
 * 
 * Date: 5/28/2017
 */
public class ConnectFour {

	public static void main(String[] args) {
		char[][] grid = new char [6][7];
		displayBoard(grid);
		connectFour(grid);
		

	}
	
	//Displays the empty connect four board
	public static void displayBoard(char[][] grid) {
		for (int i=0; i < grid.length; i++){
			for (int j=0; j < grid[i].length; j++)
				System.out.print("|" + grid[i][j]);
				System.out.println("|");
		}
		
		for (int i=0; i < grid.length - 1; i++)
			System.out.print("---");
		System.out.println();
	}
	
	//Ask the user for input and plays the game
	//Game ends after four of same color in same row, column, or diagonal. Can also tie
	public static void connectFour(char[][] grid) {
		Scanner input = new Scanner(System.in);
		
		boolean gameOver = false;
		boolean playerTurn = true;
		int columnInput = 0;
		char colorDisk;
		//Ask user for input
		while (!gameOver) {
			if (playerTurn) {
				System.out.print("Drop a red disk at column (0-6):");
				colorDisk = 'R';
			}
			
			else {
				System.out.print("Drop a yellow disk at column (0-6):");
				colorDisk = 'Y';
			}
			
			columnInput = input.nextInt();
			
			//Display error if user does not input an integer 0-6, try again.
			while (columnInput < 0 || columnInput > 6) {
				System.out.println("ERROR! Choose a column (0-6):");
				columnInput = input.nextInt();
			}
			//Changes player
			playerTurn = !playerTurn;
			
			if (diskDrop(grid, columnInput, colorDisk))
				playerTurn = !playerTurn;
			else {
					displayBoard(grid);
					//Displays winner
					if (gameStatus(grid, columnInput, colorDisk)) {
						gameOver = true;
						System.out.println(colorDisk + " is the winner! Good job!");
					}
					//Displays tie
					else if (statusTie(grid)) {
						gameOver = true;
						System.out.println("Tie game! Game Over!");
					}
			}
		}
		input.close();
	}
	
	//Drop disk to bottom of grid. If not empty, drop disk on top. If column is full, ask player to drop disk elsewhere
	public static boolean diskDrop(char[][] grid, int columnInput, char colorDisk) {
		for (int i = grid.length - 1; i >= 0; i--)
			if (grid[i][columnInput] == 0) {
				grid[i][columnInput] = colorDisk;
				return false;
			}
		System.out.println("Column full, choose another column!");
		return true;
	}
	
	//Returns true if four of the same color disks in a row, column, or diagonal
	public static boolean gameStatus(char[][] grid, int columnInput, char colorDisk) {
		int row = 0;
		
		for(int i = 0; i < grid.length; i++)
			if(grid[i][columnInput] != 0) {
				row = i;
				break;
			}
		if(statusVertical(grid, columnInput, colorDisk, row))
			return true;
		if(statusHorizontal(grid, columnInput, colorDisk, row))
			return true;
		if(statusLeftDiagonal(grid, columnInput, colorDisk, row))
			return true;
		if(statusRightDiagonal(grid, columnInput, colorDisk, row))
			return true;
		
		return false;
	}
	
	//If there are 4 disks of same color horizontally, returns true.
	public static boolean statusHorizontal(char[][] grid, int columnInput, char colorDisk, int row) {
		int diskCount = 1;
		//Check to the left of the disk
		for(int i = columnInput - 1; i >= 0; i--)
			if(colorDisk == grid[row][i])
				diskCount++;
			else
				break;
		
		if(diskCount >= 4)
			return true;
		//Check to the right of the disk
		for(int i = columnInput + 1; i < grid[0].length; i++)
			if(colorDisk == grid[row][i])
				diskCount++;
			else
				break;
		
		if(diskCount >= 4)
			return true;
		
		return false;
	}
	
	//If there are 4 disks of same color vertically, returns true.
	public static boolean statusVertical(char[][] grid, int columnInput, char colorDisk, int row) {
		int diskCount = 1;
		
		if((row + 4) <= 6)
			for(int i = row + 1; i <= (row +3); i++)
				if(colorDisk == grid[i][columnInput])
					diskCount++;
				else
					break;
		if(diskCount == 4)
			return true;
		
		return false;
	}
	
	//If there are 4 disks of same color diagonally to the left, returns true.
	public static boolean statusLeftDiagonal(char[][] grid, int columnInput, char colorDisk, int row) {
		int diskCount = 1;
		
		for(int i = row - 1, j = columnInput - 1; i >= 0 && j >= 0; i--, j--)
			if(colorDisk == grid[i][j])
				diskCount++;
			else
				break;
		
		if(diskCount >= 4)
			return true;
		
		for(int i = row + 1, j = columnInput + 1; i <grid.length && j < grid[0].length; i++, j++)
			if(colorDisk == grid[i][j])
				diskCount++;
			else
				break;
		
		if(diskCount >= 4)
			return true;
		
		return false;
	}
	
	//If there are 4 disks of same color diagonally to the right, returns true.
	public static boolean statusRightDiagonal(char[][] grid, int columnInput, char colorDisk, int row) {
		int diskCount = 1;
		
		for(int i = row + 1, j = columnInput - 1; i < grid.length && j >= 0; i++, j--)
			if(colorDisk == grid[i][j])
				diskCount++;
			else
				break;
		
		if(diskCount >= 4)
			return true;
		
		for(int i = row - 1, j = columnInput + 1; i >= 0 && j < grid[0].length; i--, j++)
			if(colorDisk == grid[i][j])
				diskCount++;
			else
				break;
		
		if(diskCount >= 4)
			return true;
		
		return false;
	}
	
	//If row 0 is full without a winner, returns true. Tie game
	public static boolean statusTie(char[][] grid) {
		for(int i = 0; i <grid[0].length; i++)
			if (grid[0][i] == 0)
				return false;
		
		return true;
	}
	

}

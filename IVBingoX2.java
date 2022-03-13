import java.util.Scanner;
public class IVBingoX2 {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int[][] GameMap1 = {{24, 2, 8, 1, 25},    //player 1 game Map
                {12, 16, 7, 17, 15}, {5, 6, 20, 19, 13}, {14, 23, 22, 4, 3},
                {10, 18, 11, 21, 9}};
        int[][] GameMap2 = {{24, 21, 17, 15, 6},  // player 2 game Map
                {10, 3, 8, 18, 20}, {14, 7, 16, 12, 5}, {25, 23, 13, 19, 11},
                {22, 4, 9, 1, 2}};
        PrintAllMap(GameMap1, GameMap2);
        RunGame(GameMap1, GameMap2);
    }

    private static void PrintAllMap(int[][] GameMap1, int[][] GameMap2) {
        PrintPlayerMap(GameMap1, 1);  // print player 1 game map
        PrintPlayerMap(GameMap2, 2);  // print player 2 game map
    }

    private static void PrintPlayerMap(int[][] Map, int Player) { // print player game map
        System.out.println("Player" + Player + "'s Card:");
        for (int[] temp : Map) {
            for (int index : temp) {
                if (index != 0 && index < 10) System.out.print(" "); // program format setting
                System.out.print(" " + (index == 0 ? "XX" : index) + " "); // print "XX" or array index
            }
            System.out.println();
        }
    }

    private static void RunGame(int[][] GameMap1, int[][] GameMap2) {
        boolean PlayerWin = false, Player2Win = false;  // boolean all player bingo is it win
        int[] alreadyNumber = new int[GameMap1[0].length * GameMap1.length]; // record the number user have input already
        int timeOfUserInputRightNumber = 0; // record the time user have input number
        while (true) {
            System.out.print("Game Host call (0 to exit):");    //input number message
            int number = sc.nextInt();
            if (number == 0) System.exit(0); // game over with input number = 0
            else if (number > GameMap1[0].length * GameMap1.length || number < 0) //expected number type is not match
                System.out.println("The number must be between 1 to 25, please call again!");
            else if (CheckArrayContainNumber(alreadyNumber, number)) // check the number is repeated input or not
                System.out.println("The number " + number + " is repeated,please call again!");
            else {  // process input number and player map
                for (int x = 0; x < GameMap1[0].length; x++) {
                    for (int y = 0; y < GameMap1.length; y++) {
                        if (GameMap1[x][y] == number) { // if player map number is match,the number be zero
                            GameMap1[x][y] = 0;
                            if (timeOfUserInputRightNumber >= 4) //check bingo if the times of game running is more than 4
                                PlayerWin = (IsBingoHorizontal(GameMap1, x) || IsBingoVertical(GameMap1, y) || IsBingoCross1(GameMap1) || IsBingoCross2(GameMap1)); // check victory condition is match
                        }
                        if (GameMap2[x][y] == number) {
                            GameMap2[x][y] = 0;
                            if (timeOfUserInputRightNumber >= 4)
                                Player2Win = (IsBingoHorizontal(GameMap2, x) || IsBingoVertical(GameMap2, y) || IsBingoCross1(GameMap2) || IsBingoCross2(GameMap2));
                        }
                    }
                }
                alreadyNumber[timeOfUserInputRightNumber++] = number; // record number to array for implement method as "CheckArrayContainNumber"
                PrintAllMap(GameMap1, GameMap2);   // print all player game map
                IsPlayerBingo(PlayerWin, Player2Win);  // behavior of player bingo
            }
        }
    }

    private static boolean CheckArrayContainNumber(int[] array, int p) { // check array contain integer as "p"
        for (int x : array)
            if (x == p) return true;
        return false;
    }

    private static boolean IsBingoHorizontal(int[][] map, int x) { // check bingo like |
        for (int p = 0; p < map.length; p++)
            if (map[x][p] != 0) return false;
        return true;
    }

    private static boolean IsBingoVertical(int[][] map, int y) { // check bingo like -
        for (int p = 0; p < map[y].length; p++)
            if (map[p][y] != 0) return false;
        return true;
    }

    private static boolean IsBingoCross1(int[][] map) { // check bingo like X
        for (int l = 0; l < map.length; l++)
            if (map[l][l] != 0) return false;
        return true;
    }

    private static boolean IsBingoCross2(int[][] map) { // check bingo like X
        int y = 4;
        for (int[] ints : map)
            if (ints[y--] != 0) return false;
        return true;
    }

    private static void IsPlayerBingo(boolean PlayerWin, boolean Player2Win) { // player is bingo message
        if (PlayerWin) System.out.println("Player1 bingo!");     //print player 1 bingo message if player 1 is won
        if (Player2Win) System.out.println("Player2 bingo!");    //print player 2 bingo message if player 2 is won
        if (PlayerWin || Player2Win) System.exit(0);  // if any player has won, quit game
    }
}
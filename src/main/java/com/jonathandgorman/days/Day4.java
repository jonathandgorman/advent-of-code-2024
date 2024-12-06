package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day4 {

    private static final int[][] directions = {
            {0, 1}, {1, 0}, {1, 1}, {1, -1},
            {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}
    };

    private static final int[][] leftToRightDiagonalCoordinates = {
            {-1, 1}, {1, -1}
    };

    private static final int[][] rightToLeftDiagonalCoordinates = {
            {1, 1}, {-1, -1}
    };

    public static void main(String[] args) throws IOException {
        var input = Files.readAllLines(Paths.get("src/main/resources/day4.txt"));
        var gridLength = input.size();
        char[][] grid = new char[gridLength][gridLength];

        for (int i = 0; i < gridLength; i++) {
            var letters = input.get(i).toCharArray();
            for (int j = 0; j < letters.length; j++) {
                grid[i][j] = letters[j];
            }
        }

        // PART 1
        String word = "XMAS";
        int xmasCount = getXmasCount(gridLength, grid, word);
        System.out.println("The total number of XMAS found is: " + xmasCount);

        // PART 2
        int masCrossCount = getMasCrossCount(gridLength, grid);
        System.out.println("The total number of MAS X's found is: " + masCrossCount);
    }

    private static int getXmasCount(int gridLength, char[][] grid, String word) {
        int xmasCount = 0;

        for (int row = 0; row < gridLength; row++) {
            for (int col = 0; col < gridLength; col++) {
                if (grid[row][col] == 'X') {
                    for (int[] dir : directions) {
                        boolean isMatch = true;
                        for (int l = 0; l < word.length(); l++) {
                            int r = row + l * dir[0];
                            int c = col + l * dir[1];
                            if (r < 0 || r >= gridLength || c < 0 || c >= gridLength || grid[r][c] != word.charAt(l)) {
                                isMatch = false;
                                break;
                            }
                        }
                        if (isMatch) {
                            xmasCount++;
                        }
                    }
                }
            }
        }
        return xmasCount;
    }

    private static int getMasCrossCount(int gridLength, char[][] grid) {
        int masCrossCount = 0;

        for (int row = 0; row < gridLength; row++) {
            for (int col = 0; col < gridLength; col++) {
                if (grid[row][col] == 'A') {

                    boolean leftToRightCheck = false;
                    boolean rightToLeftCheck = false;
                    boolean inBounds = (row - 1 >= 0 && row + 1 < gridLength && col - 1 >= 0 && col + 1 < gridLength);

                    if (inBounds) {
                        if ((grid[row - 1][col - 1] == 'M' && grid[row + 1][col + 1] == 'S') ||
                                (grid[row - 1][col - 1] == 'S' && grid[row + 1][col + 1] == 'M')) {
                            leftToRightCheck = true;
                        }

                        if ((grid[row - 1][col + 1] == 'M' && grid[row + 1][col - 1] == 'S') ||
                                (grid[row - 1][col + 1] == 'S' && grid[row + 1][col - 1] == 'M')) {
                            rightToLeftCheck = true;
                        }
                    }

                    if (leftToRightCheck && rightToLeftCheck) masCrossCount++;
                }

            }
        }
        return masCrossCount;
    }
}

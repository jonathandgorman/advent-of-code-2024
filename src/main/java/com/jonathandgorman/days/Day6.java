package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day6 {

    static List<int[]> directions = Arrays.asList(
            new int[]{-1, 0},  // N
            new int[]{0, 1},   // E
            new int[]{1, 0},   // S
            new int[]{0, -1}   // W
    );

    public static void main(String[] args) throws IOException {

        var inputLines = Files.readAllLines(Paths.get("src/main/resources/day6.txt"));
        var gridLength = inputLines.size();
        List<List<Character>> grid = new ArrayList<>(gridLength);
        int[] currentPosition = {0, 0};
        int currentDirection = 0;

        for (int i = 0; i < inputLines.size(); i++) {
            String line = inputLines.get(i);
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                char cell = line.charAt(j);
                row.add(cell);
                if (cell == '^') {
                    currentPosition = new int[]{i, j};
                }
            }
            grid.add(row);
        }

        int validPositionsCount = 0;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) == '^' || grid.get(i).get(j) == '#') continue; // never place an obstacle where there is the guard or a #
                grid.get(i).set(j, 'O');

                Set<String> positionsVisitedThisRun = new HashSet<>();
                int[] simulatedPosition = Arrays.copyOf(currentPosition, currentPosition.length);
                int simulatedDirection = currentDirection;
                boolean loopDetected = false;

                while (true) {
                    int row = simulatedPosition[0];
                    int col = simulatedPosition[1];

                    String positionWithDirection = row + "," + col + "," + simulatedDirection; // THIS WAS THE KEY - position and direction must be considered together in order to determine if a loop is formed, not just position
                    if (positionsVisitedThisRun.contains(positionWithDirection)) {
                        loopDetected = true;
                        break;
                    }

                    int[] direction = directions.get(simulatedDirection);
                    int nextRow = row + direction[0];
                    int nextCol = col + direction[1];

                    if (isOutOfBounds(new int[]{nextRow, nextCol}, grid)) {
                        break; // if we go out of bounds, exit as we can't do anything else
                    }

                    char nextCell = grid.get(nextRow).get(nextCol);
                    if (nextCell == '#' || nextCell == 'O') {
                        simulatedDirection = (simulatedDirection + 1) % 4;
                    } else {
                        positionsVisitedThisRun.add(positionWithDirection);
                        simulatedPosition = new int[]{nextRow, nextCol};
                    }
                }

                if (loopDetected) {
                    validPositionsCount++;
                }

                grid.get(i).set(j, '.'); // remove obstruction
            }
        }

        System.out.println("Distinct positions that cause the guard to get stuck in a loop: " + validPositionsCount);
    }

    public static boolean isOutOfBounds(int[] position, List<List<Character>> grid) {
        int row = position[0];
        int col = position[1];
        return row < 0 || row >= grid.size() || col < 0 || col >= grid.get(0).size();
    }
}

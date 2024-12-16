package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day8 {

    public static void main(String[] args) throws IOException {
        var input = Files.readAllLines(Paths.get("src/main/resources/day8.txt"));
        var gridLength = input.size();
        char[][] grid = new char[gridLength][gridLength];

        for (int i = 0; i < gridLength; i++) {
            var letters = input.get(i).toCharArray();
            for (int j = 0; j < letters.length; j++) {
                grid[i][j] = letters[j];
            }
        }

        // PART1
        calculateUniqueAntiNodes(grid, gridLength);
        // PART2
        calculateUniqueAntiNodesWithHarmonics(grid, gridLength);
    }

    private static void calculateUniqueAntiNodes(char[][] grid, int gridLength) {
        Map<Character, List<int[]>> antennaToCoordinates = new HashMap<>();
        Set<String> antinodes = new HashSet<>();

        for (int i = 0; i < gridLength; i++) {
            for (int j = 0; j < gridLength; j++) {
                if (grid[i][j] != '.') {
                    addCoordinate(antennaToCoordinates, grid[i][j], i, j);
                }
            }
        }

        for (Map.Entry<Character, List<int[]>> entry : antennaToCoordinates.entrySet()) {
            List<int[]> coordinates = entry.getValue();

            for (int i = 0; i < coordinates.size(); i++) {
                int[] coord1 = coordinates.get(i);
                int row1 = coord1[0];
                int col1 = coord1[1];

                for (int j = 0; j < coordinates.size(); j++) {
                    if (i == j) continue;

                    int[] coord2 = coordinates.get(j);
                    int row2 = coord2[0];
                    int col2 = coord2[1];

                    int dx1 = row2 - row1;
                    int dy1 = col2 - col1;
                    int antiNodeRow1 = row1 + 2*dx1;
                    int antoNodeCol1 = col1 + 2*dy1;

                    if (isValidCoordinate(antiNodeRow1, antoNodeCol1, gridLength)) {
                        String antinode1 = antiNodeRow1 + "," + antoNodeCol1;
                        if (!antinodes.contains(antinode1)) {
                            antinodes.add(antinode1);
                        }
                    }

                    int dx2 = row1 - row2;
                    int dy2 = col1 - col2;
                    int antiNodeRow2 = row2 + 2*dx2;
                    int antoNodeCol2 = col2 + 2*dy2;

                    if (isValidCoordinate(antiNodeRow2, antoNodeCol2, gridLength)) {
                        String antinode2 = antiNodeRow2 + "," + antoNodeCol2;
                        if (!antinodes.contains(antinode2)) {
                            antinodes.add(antinode2);
                        }
                    }
                }
            }
        }
        System.out.println("Total unique antinodes: " + antinodes.size());
    }

    public static void calculateUniqueAntiNodesWithHarmonics(char[][] grid, int gridLength) {
        Map<Character, List<int[]>> antennaToCoordinates = new HashMap<>();
        Set<String> antinodes = new HashSet<>();

        for (int i = 0; i < gridLength; i++) {
            for (int j = 0; j < gridLength; j++) {
                if (grid[i][j] != '.') {
                    addCoordinate(antennaToCoordinates, grid[i][j], i, j);
                }
            }
        }


        for (Map.Entry<Character, List<int[]>> entry : antennaToCoordinates.entrySet()) {
            char antenna = entry.getKey();
            List<int[]> coordinates = entry.getValue();



            if (coordinates.size() == 1) {
                continue; // no matching antenna found, so skip it
            }

            for (int i = 0; i < coordinates.size(); i++) {
                int[] coord1 = coordinates.get(i);
                int row1 = coord1[0];
                int col1 = coord1[1];


                String antinode = row1 + "," + col1;
                if (!antinodes.contains(antinode)) {
                    antinodes.add(antinode);
                }

                for (int j = 0; j < coordinates.size(); j++) {
                    if (i == j) continue;

                    int[] coord2 = coordinates.get(j);
                    int row2 = coord2[0];
                    int col2 = coord2[1];

                    int dx1 = row2 - row1;
                    int dy1 = col2 - col1;
                    int antiNodeRow1 = row1 + 2 * dx1;
                    int antoNodeCol1 = col1 + 2 * dy1;

                    addAntinode(antiNodeRow1, antoNodeCol1, gridLength, antinodes);

                    int currentRow = antiNodeRow1;
                    int currentCol = antoNodeCol1;

                    while (true) {
                        currentRow = currentRow + dx1;
                        currentCol = currentCol + dy1;

                        if (isValidCoordinate(currentRow, currentCol, gridLength)) {
                            addAntinode(currentRow, currentCol, gridLength, antinodes);
                        } else {
                            break;
                        }
                    }

                    int dx2 = row1 - row2;
                    int dy2 = col1 - col2;
                    int antiNodeRow2 = row2 + 2 * dx2;
                    int antoNodeCol2 = col2 + 2 * dy2;

                    addAntinode(antiNodeRow2, antoNodeCol2, gridLength, antinodes);

                    currentRow = antiNodeRow2;
                    currentCol = antoNodeCol2;

                    while (true) {
                        currentRow = currentRow + dx2;
                        currentCol = currentCol + dy2;

                        if (isValidCoordinate(currentRow, currentCol, gridLength)) {
                            addAntinode(currentRow, currentCol, gridLength, antinodes);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Total unique antinodes: " + antinodes.size());
    }

    private static void addCoordinate(Map<Character, List<int[]>> coordinateMap, char key, int row, int col) {
        coordinateMap.putIfAbsent(key, new ArrayList<>());
        coordinateMap.get(key).add(new int[]{row, col});
    }

    private static boolean isValidCoordinate(int row, int col, int gridLength) {
        return row >= 0 && row < gridLength && col >= 0 && col < gridLength;
    }

    private static void addAntinode(int row, int col, int gridLength, Set<String> antinodes) {
        if (isValidCoordinate(row, col, gridLength)) {
            String antinode = row + "," + col;
            if (!antinodes.contains(antinode)) {
                antinodes.add(antinode);
            }
        }
    }
}

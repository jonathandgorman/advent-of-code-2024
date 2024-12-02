package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) {
        int totalDifference = 0;
        int similarityScore = 0;

        try {
            var inputList = Files.readAllLines(Paths.get("src/main/resources/day1.txt"));

            // Preprocess lists
            List<Integer> leftNumbers = new ArrayList<>();
            List<Integer> rightNumbers = new ArrayList<>();

            for (String line : inputList) {
                var columns = line.split("   ");
                if (columns.length != 2) {
                    throw new IllegalArgumentException("Invalid line format: " + line);
                }
                leftNumbers.add(Integer.parseInt(columns[0]));
                rightNumbers.add(Integer.parseInt(columns[1]));
            }

            Collections.sort(leftNumbers);
            Collections.sort(rightNumbers);

            // PART 1 - Distance between lists
            for (int i = 0; i < rightNumbers.size(); i++) {
                totalDifference += Math.abs(leftNumbers.get(i) - rightNumbers.get(i));
            }

            // PART 2 - Similarity score between lists
            var distinctLeftNumbers = leftNumbers.stream().distinct().toList();
            var groupedRightMap = rightNumbers.stream()
                    .collect(Collectors.groupingBy(num -> num, Collectors.counting()));

            for (int num : distinctLeftNumbers) {
                similarityScore += num * groupedRightMap.getOrDefault(num, 0L).intValue();
            }

            System.out.println("The total difference between the lists is: " + totalDifference);
            System.out.println("The similarity score between the lists is: " + similarityScore);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }
}

package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day9 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("src/main/resources/day9.txt"));

        StringBuilder rearrangedInput = new StringBuilder();
        int currId = 0;
        boolean isFreeSpaceNext = false;

        for (char currTimes : input.toCharArray()) {
            int intCurrTimes = Integer.parseInt(String.valueOf(currTimes));
            if (!isFreeSpaceNext) {
                String currStringId = String.valueOf(currId);
                rearrangedInput.repeat(currStringId, intCurrTimes);
                isFreeSpaceNext = true;
                currId++;
            } else {
                rearrangedInput.repeat('.', intCurrTimes);
                isFreeSpaceNext = false;
            }
        }

        int currIndex = 0;
        rearrangedInput.reverse();
        for (char currChar : rearrangedInput.toString().toCharArray()) {
            int lastDotIndex = rearrangedInput.lastIndexOf(".");
            if (allDotsOnLeft(rearrangedInput, lastDotIndex)) {
                break;
            } else {
                rearrangedInput.setCharAt(lastDotIndex, currChar);
                rearrangedInput.setCharAt(currIndex, '.');
            }
            currIndex++;
        }

        rearrangedInput.reverse();
        long checksum = 0;
        for (int i = 0; i < rearrangedInput.length(); i++) {
            char c = rearrangedInput.charAt(i);
            if (c != '.') {
                int value = Character.getNumericValue(c);
                checksum += (long) i * value;
            }
        }
        System.out.println(checksum);
    }

    private static boolean allDotsOnLeft(StringBuilder input, int lastDotIndex) {
        if (lastDotIndex == -1) {
            return true;
        }
        for (int i = lastDotIndex; i < input.length(); i--) {
            if (i == 0 && input.charAt(i) == '.') {
                return true;
            } else if (input.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }
}

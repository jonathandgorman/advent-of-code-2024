package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day7 {

    public static void main(String[] args) throws IOException {

        var inputLines = Files.readAllLines(Paths.get("src/main/resources/day7.txt"));
        char[] symbols = new char[]{'+', '*', '|'};
        long total = 0;

        for (String line : inputLines) {

            String[] splitLine = line.split(":");
            long answer = Long.parseLong(splitLine[0]);
            String comboString = splitLine[1].trim();
            long spaces = comboString.length() - comboString.replace(" ", "").length();

            List<String> combinations = new ArrayList<>();
            long totalCombos = (long) Math.pow(symbols.length, spaces);
            for (int i = 0; i < totalCombos; i++) {
                StringBuilder combination = new StringBuilder();
                int temp = i;
                for (int j = 0; j < spaces; j++) {
                    combination.append(symbols[temp % symbols.length]);
                    temp = temp / symbols.length;
                }
                combinations.add(combination.toString());
            }

            for (String combination : combinations) {
                StringBuilder replacedComboString = new StringBuilder(comboString);
                int comboIndex = 0;
                for (int k = 0; k < replacedComboString.length(); k++) {
                    if (replacedComboString.charAt(k) == ' ') {
                        replacedComboString.setCharAt(k, combination.charAt(comboIndex));
                        comboIndex++;
                    }
                }

                long result = evaluateLeftToRight(replacedComboString.toString());

                if (result == answer) {
                    total += answer;
                    break;
                }

            }

        }
        System.out.println("Total: " + total);
    }

    private static long evaluateLeftToRight(String expression) {
        String[] tokens = expression.split("(?=[+*|])|(?<=[+*|])");

        long result = Long.parseLong(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            long nextNumber;

            if (operator.equals("|")) {
                String left = String.valueOf(result);
                String right = tokens[i + 1];
                result = Long.parseLong(left + right);
            } else {
                nextNumber = Long.parseLong(tokens[i + 1]);

                if (operator.equals("+")) {
                    result += nextNumber;
                } else if (operator.equals("*")) {
                    result *= nextNumber;
                }
            }
        }

        return result;
    }
}

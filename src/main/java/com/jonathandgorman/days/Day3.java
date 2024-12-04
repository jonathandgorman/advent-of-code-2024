package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public static void main(String[] args) {
        try {
            String input = Files.readString(Paths.get("src/main/resources/day3.txt"));
            int result = 0;

            // PART 1 Regex - String regex = "mul\\((\\d+),(\\d+)\\)";
            String regex = "mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            boolean mulEnabled = true;
            while (matcher.find()) {
                if (matcher.group().equals("do()")) mulEnabled = true;
                if (matcher.group().equals("don't()")) mulEnabled = false;
                if (mulEnabled && matcher.group(1) != null && matcher.group(2) != null) {
                    result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
                }
            }

            System.out.println("The combined total of mul instructions is " + result);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }
}

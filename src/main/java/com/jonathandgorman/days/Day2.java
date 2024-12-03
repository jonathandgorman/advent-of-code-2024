package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String[] args) {
        try {
            var input = Files.readAllLines(Paths.get("src/main/resources/day2.txt"));

            // PART 1
            var safeReportsOld = 0;
            for (String report : input) {
                if (isValidReport(report)) safeReportsOld++;
            }
            System.out.println("The total number of safe reports found is: " + safeReportsOld);

            // PART 2
            var safeReportsWithModifications = 0;
            for (String report : input) {
                if (isValidReportWithModifications(report)) safeReportsWithModifications++;
            }
            System.out.println("The total number of safe reports (with removal logic) found is: " + safeReportsWithModifications);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }

    static boolean isValidReport(String report) {
        var splitReportLevels = Arrays.stream(report.split(" "))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        boolean isAscending = true;
        for (int i = 0; i < splitReportLevels.size() - 1; i++) {
            if (splitReportLevels.get(i) < splitReportLevels.get(i + 1)) {
                isAscending = false;
                break;
            }
        }

        boolean isDescending = true;
        for (int i = 0; i < splitReportLevels.size() - 1; i++) {
            if (splitReportLevels.get(i) > splitReportLevels.get(i + 1)) {
                isDescending = false;
                break;
            }
        }

        boolean isValidStep = true;
        for (int i = 0; i < splitReportLevels.size() - 1; i++) {
            int diff = Math.abs(splitReportLevels.get(i) - splitReportLevels.get(i + 1));
            if (diff <= 0 || diff > 3) {
                isValidStep = false;
                break;
            }
        }

        return isValidStep && (isDescending || isAscending);
    }

    static boolean isValidReportWithModifications(String report) {
        var splitReportLevels = Arrays.stream(report.split(" "))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        if (isValidReportWithoutModification(splitReportLevels)) {
            return true;
        }

        for (int i = 0; i < splitReportLevels.size(); i++) {
            var modifiedReport = new ArrayList<>(splitReportLevels);
            modifiedReport.remove(i);
            if (isValidReportWithoutModification(modifiedReport)) {
                return true;
            }
        }

        return false;
    }

    static boolean isValidReportWithoutModification(ArrayList<Integer> levels) {
        boolean isAscending = true;
        for (int i = 0; i < levels.size() - 1; i++) {
            if (levels.get(i) < levels.get(i + 1)) {
                isAscending = false;
                break;
            }
        }

        boolean isDescending = true;
        for (int i = 0; i < levels.size() - 1; i++) {
            if (levels.get(i) > levels.get(i + 1)) {
                isDescending = false;
                break;
            }
        }

        boolean isValidStep = true;
        for (int i = 0; i < levels.size() - 1; i++) {
            int diff = Math.abs(levels.get(i) - levels.get(i + 1));
            if (diff <= 0 || diff > 3) {
                isValidStep = false;
                break;
            }
        }

        return isValidStep && (isDescending || isAscending);
    }
}

package com.jonathandgorman.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws IOException {
        
        var input = Files.readString(Paths.get("src/main/resources/day5.txt")).split("\\n\\n");
        var rules = input[0].split("\\n");
        var updates = input[1].split("\\n");

        var updatesList = Arrays.stream(updates)
                .map(u -> Arrays.stream(u.split(",")).mapToInt(Integer::parseInt).boxed().toList())
                .toList();

        Map<Integer, List<Integer>> groupedRules = Arrays.stream(rules)
                .map(s -> s.split("\\|"))
                .collect(Collectors.groupingBy(arr -> Integer.parseInt(arr[0]),
                        Collectors.mapping(
                                arr -> Integer.parseInt(arr[1]),
                                Collectors.toList()
                        )
                ));

        System.out.println("The sum of the middle value of each correct update is: " + processOrderedUpdates(updatesList, groupedRules));
        System.out.println("The sum of the middle value of each correct update is: " + processInvalidUpdates(updatesList, groupedRules));
  }

    private static Integer[] getNextFailedRule(List<Integer> update, Map<Integer, List<Integer>> groupedRules, Map<Integer, Integer> indexMap) {
        for (Integer entry : update) {
            if (groupedRules.containsKey(entry)) {
                var mustBeAheadList = groupedRules.get(entry);
                for (Integer aheadElement : mustBeAheadList) {
                    if (indexMap.containsKey(aheadElement)) {
                        if (indexMap.get(entry) > indexMap.get(aheadElement)) {
                            return new Integer[]{entry, aheadElement};
                        }
                    }
                }
            }
        }
        return null;
    }

    private static int processOrderedUpdates(List<List<Integer>> updatesList, Map<Integer, List<Integer>> groupedRules) {
        int middlePageSum = 0;

        for (List<Integer> update : updatesList) {
            Map<Integer, Integer> entryIndexMap = new HashMap<>();
            for (int i = 0; i < update.size(); i++) {
                entryIndexMap.put(update.get(i), i);
            }

            if (getNextFailedRule(update, groupedRules, entryIndexMap) == null) middlePageSum += update.get(update.size() / 2);
        }
        return middlePageSum;
    }

    private static int processInvalidUpdates(List<List<Integer>> updatesList, Map<Integer, List<Integer>> groupedRules) {
        int middlePageSum = 0;

        for (List<Integer> update : updatesList) {
            List<Integer> mutableUpdate = new ArrayList<>(update);
            Map<Integer, Integer> entryIndexMap = new HashMap<>();

            for (int i = 0; i < mutableUpdate.size(); i++) {
                entryIndexMap.put(mutableUpdate.get(i), i);
            }

            Integer[] failedRule = getNextFailedRule(mutableUpdate, groupedRules, entryIndexMap);
            if (failedRule != null) {
                while ((failedRule = getNextFailedRule(mutableUpdate, groupedRules, entryIndexMap)) != null) {

                    int indexX = entryIndexMap.get(failedRule[0]);
                    int indexY = entryIndexMap.get(failedRule[1]);

                    Integer temp = mutableUpdate.get(indexX);
                    mutableUpdate.set(indexX, mutableUpdate.get(indexY));
                    mutableUpdate.set(indexY, temp);

                    entryIndexMap.clear();
                    for (int i = 0; i < mutableUpdate.size(); i++) {
                        entryIndexMap.put(mutableUpdate.get(i), i);
                    }

                    if (getNextFailedRule(mutableUpdate, groupedRules, entryIndexMap) == null) {
                        break;
                    }
                }
                middlePageSum += mutableUpdate.get(mutableUpdate.size() / 2);
            }
        }

        return middlePageSum;
    }
}

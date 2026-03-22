package com.matias.bingo.utils;

import java.util.*;

public class BingoCardGenerator {
    
    private static final Random rand = new Random();
    
    public static List<Integer> generate() {
        while (true) {
            try {
                Integer[][] grid = new Integer[3][9];
                List<TreeSet<Integer>> columns = new ArrayList<>();
                for (int col = 0; col < 9; col++) {
                    columns.add(new TreeSet<>());
                }

                for (int c = 0; c < 9; c++) {
                    columns.get(c).add(generateNumberForColumn(c));
                }

                int totalPuestos = 9;
                while (totalPuestos < 15) {
                    int c = rand.nextInt(9);
                    if (columns.get(c).size() < 3) {
                        if (columns.get(c).add(generateNumberForColumn(c))) {
                            totalPuestos++;
                        }
                    }
                }

                distributeToRows(grid, columns);

                return flattenGrid(grid);
                
            } catch (Exception e) {
            	System.err.println("Card generation failed, retrying... Reason: " + e.getMessage());
            }
        }
    }

    private static void distributeToRows(Integer[][] grid, List<TreeSet<Integer>> columns) {
        for (int c = 0; c < 9; c++) {
            TreeSet<Integer> colNumbers = columns.get(c);
            int count = colNumbers.size();

            if (count == 1) {
                int r = findRandomRowWithSpace(grid);
                grid[r][c] = colNumbers.pollFirst();
            } 
            else if (count == 2) {
                List<Integer> rows = getTwoSortedRowsWithSpace(grid);
                grid[rows.get(0)][c] = colNumbers.pollFirst();
                grid[rows.get(1)][c] = colNumbers.pollFirst();
            } 
            else if (count == 3) {
                if (countRow(grid, 0) < 5 && countRow(grid, 1) < 5 && countRow(grid, 2) < 5) {
                    grid[0][c] = colNumbers.pollFirst();
                    grid[1][c] = colNumbers.pollFirst();
                    grid[2][c] = colNumbers.pollFirst();
                } else {
                    throw new RuntimeException("No space left for a 3-number column");
                }
            }
        }
    }

    private static int findRandomRowWithSpace(Integer[][] grid) {
        List<Integer> validRows = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            if (countRow(grid, r) < 5) validRows.add(r);
        }
        if (validRows.isEmpty()) throw new RuntimeException("No rows with available space");
        return validRows.get(rand.nextInt(validRows.size()));
    }

    private static List<Integer> getTwoSortedRowsWithSpace(Integer[][] grid) {
        List<Integer> validRows = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            if (countRow(grid, r) < 5) validRows.add(r);
        }
        if (validRows.size() < 2) throw new RuntimeException("Not enough rows with available space");
        
        Collections.shuffle(validRows);
        List<Integer> selected = new ArrayList<>(validRows.subList(0, 2));
        Collections.sort(selected);
        return selected;
    }

    private static int generateNumberForColumn(int col) {
        int min = (col == 0) ? 1 : col * 10;
        int max = (col == 8) ? 90 : col * 10 + 9;
        return rand.nextInt((max - min) + 1) + min;
    }

    private static long countRow(Integer[][] grid, int row) {
        return Arrays.stream(grid[row]).filter(Objects::nonNull).count();
    }

    private static List<Integer> flattenGrid(Integer[][] grid) {
        List<Integer> flatList = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 9; c++) {
                flatList.add(grid[r][c] == null ? 0 : grid[r][c]);
            }
        }
        return flatList;
    }
}
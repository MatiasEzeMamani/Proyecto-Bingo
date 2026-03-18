package com.matias.bingo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.TreeSet;

public class BingoCardGenerator {
	
	private static final Random rand = new Random();
	
	public static List<Integer> generate(){
		Integer[][] grid = new Integer[3][9];
		
		List<TreeSet<Integer>> columns = new ArrayList<>();
		for(int col = 0; col < 9; col++) {
			columns.add(new TreeSet<>());
		}
		
		for(int c=0; c < 9; c++) {
			columns.get(c).add(generateNumberForColumn(c));
		}
		
		int totalPuestos = 9;
		
		while(totalPuestos < 15) {
			int c = rand.nextInt(9);
			
			if(columns.get(c).size() < 3) {
				if(columns.get(c).add(generateNumberForColumn(c))) {
					totalPuestos++;
				}
			}
		}
		
		distributeToRows(grid, columns);
		
		return flattenGrid(grid);
	}
	
	
	private static void distributeToRows(Integer[][] grid, List<TreeSet<Integer>> columns) {
	    for (int c = 0; c < 9; c++) {
	        TreeSet<Integer> colNumbers = columns.get(c);
	        int count = colNumbers.size();

	        if (count == 1) {
	            int r = findRandomRowWithSpace(grid, c);
	            grid[r][c] = colNumbers.pollFirst();
	        } 
	        else if (count == 2) {
	            List<Integer> rows = getTwoSortedRowsWithSpace(grid, c);
	            grid[rows.get(0)][c] = colNumbers.pollFirst();
	            grid[rows.get(1)][c] = colNumbers.pollFirst();
	        } 
	        else if (count == 3) {
	            grid[0][c] = colNumbers.pollFirst();
	            grid[1][c] = colNumbers.pollFirst();
	            grid[2][c] = colNumbers.pollFirst();
	        }
	    }
	}
	
	private static int findRandomRowWithSpace(Integer[][] grid, int col) {
        List<Integer> validRows = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            if (countRow(grid, r) < 5) validRows.add(r);
        }
        return validRows.get(rand.nextInt(validRows.size()));
    }

    private static List<Integer> getTwoSortedRowsWithSpace(Integer[][] grid, int col) {
        List<Integer> validRows = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            if (countRow(grid, r) < 5) validRows.add(r);
        }
        Collections.shuffle(validRows);
        List<Integer> selected = validRows.subList(0, 2);
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
	
	private static List<Integer> flattenGrid(Integer[][] grid){
		List<Integer> flatList = new ArrayList<>();
		
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 9; c++) {
				flatList.add(grid[r][c] == null ? 0 : grid[r][c]);
			}
		}
		
		return flatList;
	}
}

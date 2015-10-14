package com;

import java.util.List;

public class Simulator {
	public static void main(String[] args) {

		// Field myField = new Field();
		Sweeper mineSweeper = new Sweeper();
		
		int j = 1;
			for (List<String> instr : mineSweeper.firingScript) {
				
				System.out.println("Step " + j +"\n");
					switch (instr.size()) {
					case 0:
						mineSweeper.mineField.printField();
						System.out.println("\n");
								mineSweeper.sink();
								mineSweeper.mineField.printField();
								System.out.println("\n");
								break;
						
					case 1:
						if(instr.get(0).equals("north") || instr.get(0).equals("south") ||instr.get(0).equals("east") ||instr.get(0).equals("west")) {
						mineSweeper.mineField.printField();
						System.out.println("\n"+ instr.get(0) + "\n");
						mineSweeper.navigateSweeper(instr.get(0));
						mineSweeper.sink();
						mineSweeper.mineField.printField();
						System.out.println("\n");
						} else {
							mineSweeper.mineField.printField();
							System.out.print("\n");
							System.out.println(instr.get(0) + "\n");
							mineSweeper.fire(instr.get(0));
							mineSweeper.sink();
							mineSweeper.mineField.printField();
							System.out.println("\n");
						}
						
						break;
					case 2 :
						mineSweeper.mineField.printField();
						System.out.println(instr + "\n");
						mineSweeper.fire(instr.get(0));
						mineSweeper.navigateSweeper(instr.get(1));
						mineSweeper.sink();
						mineSweeper.mineField.printField();
						System.out.println("\n");
						break;
						
					}
				j++;
				
			}
		
	//	mineSweeper.sink();

		/*System.out.println("Hhhhhhh\n");

		mineSweeper.mineField.printField();

		mineSweeper.navigateSweeper("north");
		mineSweeper.sink();
		System.out.println("\n");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		
		mineSweeper.fire("delta");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		
		
		mineSweeper.navigateSweeper( "south");
		mineSweeper.sink();
		System.out.println("\n");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		
		mineSweeper.navigateSweeper( "west");
		mineSweeper.sink();
		System.out.println("\n");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		
		
		mineSweeper.fire("gamma");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		
		mineSweeper.navigateSweeper("east");
		mineSweeper.sink();
		System.out.println("\n");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		*/
		/*mineSweeper.mineField.resizeFieldRows("south", "south");
		mineSweeper.sink();
		System.out.println("\n");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		
		mineSweeper.mineField.resizeFieldColumns("east", "east");
		System.out.println("\n");
		mineSweeper.mineField.printField();
		mineSweeper.sink();
		System.out.println("\n");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		
		
		mineSweeper.mineField.resizeFieldColumns("east", "west");
		mineSweeper.sink();
		System.out.println("\n");
		mineSweeper.mineField.printField();
		System.out.println("\n");
		
		mineSweeper.mineField.resizeFieldColumns("west", "west");
		mineSweeper.sink();
		System.out.println("\n");
		mineSweeper.mineField.printField();
		System.out.println("\n");*/
		
	}
}

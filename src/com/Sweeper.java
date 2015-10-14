package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class Sweeper {
	public List<List<String>> firingScript = new ArrayList<List<String>>();

	Field mineField ;
	int zCoord;


	public void navigateSweeper(String dir) {
		switch (dir) {
		case "east":
			mineField.resizeFieldColumns( "east");
			break;

		case "west":
			mineField.resizeFieldColumns( "west");
			break;

		case "north":
			mineField.resizeFieldRows( "north");
			break;

		case "south":
			mineField.resizeFieldRows( "south");
			break;

		}

	}

	public String resetMineAtLoc(int x, int y) {
		String mineDepthIndicator = mineField.field.get(y).get(x);
		mineField.field.get(y).set(x, ".");
		return mineDepthIndicator;
	}

	public List<Point> firingCoords (Point FieldSize, Point midPoint, String pattern) {
		List<Point>  fireAt = new ArrayList<Point>();
		int curLocX = (int) midPoint.getX();
		int curLocY = (int) midPoint.getY();
		
		int xSize = (int) FieldSize.getX();
		int ySize = (int) FieldSize.getY();
		
		switch (pattern) {
		case "alpha":
			if ((curLocX > 0 && curLocY >0) ) {fireAt.add(new Point(curLocX-1, curLocY-1));}
			if ((curLocX > 0 && (curLocY+1) < ySize) ) {fireAt.add(new Point(curLocX-1, curLocY+1));}
			if ((curLocX+1) < xSize && curLocY > 0)  {fireAt.add(new Point(curLocX+1, curLocY-1));}
			if ((curLocX+1) <xSize && (curLocY+1) < ySize)  {fireAt.add(new Point(curLocX+1, curLocY+1));}
			break;
		case "beta":
			if (curLocY>0)  {fireAt.add(new Point(curLocX, curLocY-1));}
			if (curLocX>0) {fireAt.add(new Point(curLocX-1, curLocY));}
			if ((curLocY+1) < ySize) {fireAt.add(new Point(curLocX, curLocY+1));}
			if ((curLocX+1) < xSize) {fireAt.add(new Point(curLocX+1, curLocY));}
			break;
		case "gamma":
			if (curLocX > 0 ) { fireAt.add(new Point(curLocX-1, curLocY));}
			fireAt.add(new Point(curLocX, curLocY));	
			if ((curLocX+1) < xSize) { fireAt.add(new Point(curLocX+1, curLocY));}
			break;
		case "delta":
			if (curLocY>0) {fireAt.add(new Point(curLocX, curLocY-1));}
			fireAt.add(new Point(curLocX, curLocY));	
			if ((curLocY+1) < ySize) { fireAt.add(new Point(curLocX, curLocY+1));}
			break;
		}
		return fireAt;
		
	}
	public void fire(String dir) {
		List<ArrayList<String>> fieldLayout = mineField.getFieldLayout();
		
		ArrayList<String> depth = mineField.getDepths();
		
		for ( Point p : firingCoords(mineField.fieldSize, mineField.fieldMidPoint, dir) ) {
			if (!fieldLayout.get((int)p.getY()).get((int)p.getX()).equals(".")) {
				depth.remove(resetMineAtLoc((int)p.getX(), (int)p.getY()));
			}
		}
	}

	public void printFiringScript() {
		for (List<String> instr : firingScript) {
			for (int i = 0; i < instr.size(); i++) {
				System.out.print(instr.get(i));
				System.out.print(" ");
			}
			System.out.print("\n");
		}
	}

	public void sink() {
		for (List<String> coords : mineField.field) {
			for (int i = 0; i < coords.size(); i++) {
				if (!coords.get(i).equals(".")) {
					char a = coords.get(i).charAt(0);
					if (a =='A'){ 
						a = 'z';
					} else {
						a--;
					}
					String dummy = "" + a;
					coords.set(i, dummy);
				}
			}
		}

		// Sinking depths array
	//	System.out.println(mineField.depths);
		for (int i = 0; i < mineField.depths.size(); i++) {
			char a = mineField.depths.get(i).charAt(0);
			if (a =='A'){ 
				a = 'z';
			} else {
				a--;
			}
			String dummy = "" + a;
			mineField.depths.set(i, dummy);
		}
		//System.out.println(mineField.depths);
	}

	public void readFiringScript() {

		String fileName = "Script.txt";
		try {
			File f = null;

			URL path = ClassLoader.getSystemResource(fileName);

			try {
				f = new File(path.toURI());
			} catch (Exception e) {
				System.out.println("Could not get file");
			}

			String line = null;

			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

			while ((line = bufferedReader.readLine()) != null) {
				String[] cmds = line.split(" ");
				List<String> instr = new ArrayList<String>(cmds.length);
				for (String cmd : cmds) {
					if(!cmd.isEmpty()) {
						instr.add(cmd);
					}
				}
				firingScript.add(instr);
			}

			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public Sweeper() {
		
		mineField = new Field();

		zCoord = 0;


		readFiringScript();
		//printFiringScript();
		mineField.readField();
		//mineField.printField();

	}

}

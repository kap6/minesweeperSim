package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.Point;

public class Field {
	List<ArrayList<String>> field;
	ArrayList<String> depths;
	ArrayList<Point> mines;

	Point fieldSize = new Point(0, 0);

	Point fieldMidPoint = new Point(0, 0);

	public Field() {
		field = new ArrayList<ArrayList<String>>();
		depths = new ArrayList<String>();
		mines = new ArrayList<Point>();
		
	}

	public List<ArrayList<String>> getFieldLayout() {
		return field;
	}
	
	public ArrayList<String> getDepths() {
		return depths;
	}

	public void updateMidPoint(Point fieldSize) {
		fieldMidPoint = new Point((int) Math.floor(fieldSize.getX() / 2), (int) Math.floor(fieldSize.getY() / 2));
	}

	public Point getFieldMidPoint() {
		return fieldMidPoint;
	}

	public void printField() {
		if (depths.size() >0) {
		for (List<String> coords : field) {
			for (int i = 0; i < coords.size(); i++) {
				System.out.print(coords.get(i));
			}
			System.out.print("\n");
		}
		}else {
				System.out.println(".\n");
		}
	}
	
	public String getFillerString(int length, char charToFill) {
		if (length > 0) {
			char[] array = new char[length];
			Arrays.fill(array, charToFill);
			return new String(array);
		}
		return "";
	}

	public ArrayList<String> getFiller(int size) {
		String filler = new String(getFillerString(size, '.'));
		String[] pos = filler.split("");

		ArrayList<String> coords = new ArrayList<String>();
		for (String point : pos) {
			if(!point.isEmpty()){
				coords.add(point);
			}
		}
		return coords;
	}

	public String twoColsAvailable(String dir) {
		String exists = "no";
		int offset =0;

		if (dir.equals("east")) {exists = "west";} else {exists = "east";}

		for (List<String> coords : field) {
			switch (dir) {
			case "east" :
				offset =0;
				break;
			case "west" :
				offset = coords.size() -2;
				break;
				
			}
			for (int i =  0; i < 2; i++) {
				if (!coords.get(offset+i).equals(".")) {
					exists="no";
					break;
				}
			}
			if (exists.equals("no")){ break;}
			
		}
		return exists;
	}
	
	public String twoRowsAvailable(String dir) {
		int offset =0;
		String exists = "no";

		switch (dir) {
		case "south" :
			offset =0;
			exists = "north";
			break;
		case "north" :
			offset = field.size()-2;
			exists = "south";
			break;
			
		}
		for (int i = offset ; i < offset+2; i++) {
			for(int j =0; j < field.get(i).size(); j++) {
				if (!field.get(i).get(j).equals(".")) {
					exists="no";
					break;
				}
			}
		}
		return exists;
	}
	
	
	public void resizeFieldRows( String curDir) {
		String whereAvailable = twoRowsAvailable(curDir);
		
		if (whereAvailable.equals("no")) {
			switch (curDir) {
			case "north":
				field.add(0, getFiller((int) fieldSize.getX()));
				field.add(0, getFiller((int) fieldSize.getX()));
				fieldSize = new Point((int) fieldSize.getX(), (int) (fieldSize.getY() + 2.0));

				break;

			case "south":
				field.add(field.size(), getFiller((int) fieldSize.getX()));
				field.add(field.size(), getFiller((int) fieldSize.getX()));
				fieldSize = new Point((int) fieldSize.getX(), (int) (fieldSize.getY() + 2.0));
				break;
			}

		} else {
			switch (whereAvailable) {
			case "north":
				field.remove(0);
				field.remove(0);
				fieldSize = new Point((int) fieldSize.getX(), (int) (fieldSize.getY() - 2.0));
				updateMidPoint(fieldSize);
				break;

			case "south":
				field.remove(field.size()-1);
				field.remove(field.size()-1);
				fieldSize = new Point((int) fieldSize.getX(), (int) (fieldSize.getY() - 2.0));
				break;
			}

		}
		updateMidPoint(fieldSize);
	}

	public void resizeFieldColumns( String curDir) {

		//if (prevDirToggle.equals(curDir) || prevDirToggle.isEmpty()) {
		String whereAvailable = twoColsAvailable(curDir);
		
		if (whereAvailable.equals("no")) {
			switch (curDir) {
			case "west":
				for (int i = 0; i < field.size(); i++) {
					field.get(i).add(0, ".");
					field.get(i).add(0, ".");
				}
				fieldSize = new Point((int) (fieldSize.getX() + 2.0), (int) (fieldSize.getY()));
				break;

			case "east":
				for (int i = 0; i < field.size(); i++) {
					int pos = field.get(i).size();
					field.get(i).add(pos, ".");
					field.get(i).add(pos, ".");
				}
				fieldSize = new Point((int) (fieldSize.getX() + 2.0), (int) (fieldSize.getY()));
				break;
			}

		} else {
			switch (whereAvailable) {
			case "west":
				for (int i = 0; i < field.size(); i++) {
					field.get(i).remove(0);
					field.get(i).remove(0);

				}
				fieldSize = new Point((int) (fieldSize.getX() - 2.0), (int) fieldSize.getY());
				break;

			case "east":
				for (int i = 0; i < field.size(); i++) {
					field.get(i).remove(field.get(i).size()-1);
					field.get(i).remove(field.get(i).size()-1);
				}
				fieldSize = new Point((int) (fieldSize.getX() - 2.0), (int) (fieldSize.getY()));
				break;
			}

		}
		updateMidPoint(fieldSize);

	}

	public void readField() {
		String fileName = "Layout.txt";
		try {
			File f = null;

			URL path = ClassLoader.getSystemResource(fileName);

			try {
				f = new File(path.toURI());
			} catch (Exception e) {
				System.out.println("Could not get file");
			}
			String line = null;

			int xSize = 0, ySize = 0;

			// FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

			while ((line = bufferedReader.readLine()) != null) {
				String[] pos = line.split("");

				xSize = pos.length-1;
				ySize++;

				ArrayList<String> coords = new ArrayList<String>();
				for (String point : pos) {
					if (!point.isEmpty()) {
					coords.add(point);
					}
					if (!point.isEmpty() && !point.equals(".")) {

						depths.add(point);
					}
				}
				field.add(coords);
			}

			bufferedReader.close();
			Collections.sort(depths);
			fieldSize = new Point(xSize, ySize);
			updateMidPoint(fieldSize);
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

}

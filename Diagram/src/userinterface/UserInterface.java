package userinterface;

import java.awt.Color;
import java.io.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import ecs100.*;


public class UserInterface {


	private int defaultSize = 50;
	private double defaultFontSize = 30;
	private double defaultLineSize = 1;
	private Color currentColour = Color.black;
	private Color currentFontColour = Color.black;

	private String currentShape = "box";
	private Shape selectedShape;

	public boolean fillShape = false;
	private boolean remove = false;	

	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private ArrayList<Position> tempLine = new ArrayList<Position>();

	public void addBox() {
		//selects button and sets currentShape to box
		UI.clearText();
		this.resetButtons();
		currentShape = "box";
		buttons.get(0).setBackground(Color.lightGray);
	}

	public void addCircle() {
		//selects button and sets currentShape to circle
		UI.clearText();
		this.resetButtons();
		currentShape = "circle";
		buttons.get(1).setBackground(Color.lightGray);
	}

	public void addLine() {
		//selects button and sets currentShape to line
		UI.clearText();
		this.resetButtons();
		UI.println("Click where you want the start and end points to be.");
		currentShape = "line";
		buttons.get(2).setBackground(Color.lightGray);
	}

	public void addText() {
		//selects button and sets currentShape to text
		UI.clearText();
		this.resetButtons();
		currentShape = "text";
		buttons.get(3).setBackground(Color.lightGray);
	}

	public void setColour() {
		//prompts user for colour. If a shape is selected, it changes the colour of that shape only. If no 
		//shape is selected, it changes the colour of any future shapes and sets the colour display
		//button to the colour.
		UI.clearText();
		Color colour = JColorChooser.showDialog(null, "", Color.white);
		if (selectedShape == null) {
			currentColour = colour;
		} else {
			selectedShape.setColour(colour);
			selectedShape = null;
		}
		buttons.get(7).setBackground(currentColour);
		this.drawShapes();
	}

	public void resetShapeColour() {
		currentColour = Color.black;
		buttons.get(7).setBackground(currentColour);
	}

	public void setSize() {
		//Asks user for size in pixels. If a shape is selected, it changes the size of that shape only. If no 
		//shape is selected, it changes the size of any future shapes.
		UI.clearText();
		UI.println("Please enter the desired size in pixels:");
		int newSize = UI.askInt("");
		if (selectedShape == null) {
			defaultSize = newSize;
			UI.clearText();
			UI.println("Default size changed.");
		} else {
			UI.clearText();
			selectedShape.setSize(newSize);
			selectedShape = null;
		}
		this.drawShapes();
	}

	public void setFontColour() {
		//Prompts user for a colour. If no shape is selected or the shape is not text, it sets the font
		//colour for any future text. If text is selected, it sets the font colour for that text only.
		UI.clearText();
		Color colour = JColorChooser.showDialog(null, "", Color.white);
		if (selectedShape == null) {
			currentFontColour = colour;
		} else if (selectedShape != null && selectedShape instanceof Text) {
			selectedShape.setColour(colour);
			selectedShape = null;
		}
		buttons.get(10).setBackground(currentFontColour);
		this.drawShapes();
	}

	public void resetFontColour() {
		currentFontColour = Color.black;
		buttons.get(10).setBackground(currentFontColour);
	}

	public void setFontSize() {
		//Asks user for a font size. If no shape is selected or the shape is not text, it sets the font
		//size for any future text. If text is selected, it sets the font size for that text only.
		UI.clearText();
		UI.println("Please enter the desired size in pixels: ");
		int newFontSize = UI.askInt("");
		if (selectedShape == null) {
			defaultFontSize = newFontSize;
			UI.clearText();
			UI.println("Default size changed.");
		} else if (selectedShape != null && selectedShape instanceof Text) {
			UI.clearText();
			selectedShape.setSize(newFontSize);
			selectedShape = null;
		}
		this.drawShapes();
	}

	//not implemented
	public void fillShape() {
		UI.clearText();
		if(selectedShape == null) {
			UI.println(null);
		} else {
			if (fillShape) {
				fillShape = false;
			} else {
				fillShape = true;
			}
		}
	}

	public void saveFile() {
		//saves each object in the shapes array to a file
		String type = null;
		String string = null;
		try {
			PrintStream out = new PrintStream(new File(UIFileChooser.save()));
			for (int i=0; i<shapes.size(); i++) {
				Shape s = shapes.get(i);
				if (s instanceof Circle) {
					type = "circle";
				} else if (s instanceof Box) {
					type = "rectangle";
				} else if (s instanceof Text) {
					type = "text";
					string = ((Text) s).getString();
				} else if (s instanceof Line) {
					type = "line";
				}
				out.println(type);
				if (type.equals("text")) {
					out.println(string);
				}
				out.println(s.getColour().toString());
				if (type.equals("line")) {
					out.println(s.getPosition().toString()); //gets start coordinate
					out.println(s.getEndPosition().toString());
				} else {
					out.println(s.getPosition().toString());
				}
				out.println(s.getSize());
			}
			out.close();
		} catch (Exception e) {}
	}

	public void loadFile() {
		//reads objects from a file and adds them to an array, the draws them to the graphics pane
		shapes.clear();
		UI.clearText();
		String string = null;
		try {
			Scanner scan = new Scanner(new File(UIFileChooser.open()));
			while (scan.hasNext()) {
				String type = scan.nextLine();
				if (type.equals("text")) {
					string = scan.nextLine();
				} 
				String colourLine = scan.nextLine();
				String[] rgbValues = colourLine.split("=|\\,|]");
				int red = Integer.parseInt(rgbValues[1]); //r value
				int green = Integer.parseInt(rgbValues[3]); //g value
				int blue = Integer.parseInt(rgbValues[5]); //b value
				Color rgb = new Color(red, green, blue);
				double x = scan.nextDouble();
				double y = scan.nextDouble();
				Position pos = new Position(x, y);
				Position end = null;
				if (type.equals("line")) {
					double a = scan.nextDouble();
					double b = scan.nextDouble();
					end = new Position(a, b);
				}
				double size = scan.nextDouble();
				scan.nextLine();
				if (type.contains("circle")) {
					Circle circ = new Circle(rgb, pos, size);
					shapes.add(circ);
				} else if (type.contains("rectangle")) {
					Box rect = new Box(rgb, pos, size);
					shapes.add(rect);
				} else if (type.contains("text")) {
					Text text = new Text(string, x, y, rgb);
					text.setSize(size);
					shapes.add(text);
				} else if (type.contains("line")) {
					Line line = new Line(pos, end);
					shapes.add(line);
				}
				this.drawShapes();
			}
			scan.close();

		} catch (Exception e) {}
	}

	public void remove() {
		//toggles 'remove mode' on (sets button red) or off (sets button white). When remove mode is on, 
		//any object clicked will be removed from the array and from the graphics output.
		UI.clearText();
		JButton removeButton = buttons.get(11);
		if (!remove) {
			removeButton.setBackground(Color.red);
			remove = true;
		} else {
			removeButton.setBackground(Color.white);
			remove = false;
		}
	}

	public void drawShapes() {
		//draws all objects in the shapes array
		UI.clearGraphics();
		for (Shape s : shapes) {
			s.drawShape();
		}
	}

	public void resetButtons() {
		//sets all buttons to white (except for the ones displaying the colour)
		for (int i=0; i<buttons.size()-1; i++) {
			if (i != 7 && i != 10) {
				buttons.get(i).setBackground(Color.white);
			}
		}
	}

	//When the user clicks something, if the remove toggle is on, it searches the array list and if the point clicked contains
	//a shape, it removes it from the array and redraws all shapes. If it isn't there, it gets drawn.
	//If the remove toggle is off, it iterates through the array and sets all shapes as 'unselected', then goes through the 
	//array list and if a shape has been clicked it adds it to the selectedShape variable and gives it a cyan outline to indicate
	//that it is selected.
	public void doMouse(String action, double x, double y) {
		Position click = new Position(x, y);

		if (action.equals("released")) {
			if (remove) {
				for (int i=0; i<shapes.size(); i++) {
					Shape s = shapes.get(i);
					if (s.isSelected(click)) {
						shapes.remove(i);
						this.drawShapes();
						return;
					}
				}
			} else if (!remove) {
				//sets all shapes to unselected
				for (int i=0; i<shapes.size(); i++) {
					Shape s = shapes.get(i);
					s.setIsSelected(false);
				}
				for (int i=0; i<shapes.size(); i++) {
					//sets selected shape to selected
					Shape s = shapes.get(i);
					if (s.isSelected(click)) {
						selectedShape = s;
						this.drawShapes();
						return;
					}
				}
				//adds shape to array and draws
				switch(currentShape) {
				case "box":
					Box newBox = new Box(currentColour, click, defaultSize);
					shapes.add(newBox);
					break;
				case "circle":
					Circle newCircle = new Circle(currentColour, click, defaultSize);
					shapes.add(newCircle);
					break;
				case "text":
					String string = UI.askString("Enter the text you wish to add: ");
					Text newText = new Text(string, click.getX(), click.getY(), currentFontColour);
					newText.setSize(defaultFontSize);
					shapes.add(newText);
					this.resetButtons();
					UI.clearText();
					currentShape = " ";
					break;
				case "line":
					Position linePos = new Position(click.getX(), click.getY());
					tempLine.add(linePos);
					if (tempLine.size()==2) {
						Position start = tempLine.get(0);
						Position end = tempLine.get(1);
						Line line = new Line(start, end);
						line.setColour(currentColour);
						line.setSize(defaultLineSize);
						shapes.add(line);
						tempLine.clear();
						this.resetButtons();
						UI.clearText();
						currentShape = " ";
					}
					break;
				default:
				}
			}			
		}

		if (action.equals("dragged")) {
			//move action
			for (int i=0; i<shapes.size(); i++) {
				Shape s = shapes.get(i);
				s.setIsSelected(false);
				if (s.isSelected(click)) {
					s.setPosition(click);
					shapes.remove(i);
					shapes.add(s);
					selectedShape = null;
				}
				//change size click
				//if user has clicked (dragged) and the shape is selected
				//check if they're clicking the corner with cornerCheck
				//if it returns true, run method enlarge
				//else move shape
				/*for (int i=0; i<shapes.size(); i++) {
					Shape s = shapes.get(i);
					if (cornerCheck(click, s) && s.isSelected(click)) {
						UI.println("corner check true");
					}
				}*/
			}
		}
		this.drawShapes();
	}

	//not implemented - used to check if a click on a shape is in the corner so it can resize the shape rather than move
	public boolean cornerCheck(Position click, Shape s) {
		Position shape = s.getPosition();
		double size = s.getSize();
		double x = shape.getX();
		double y = shape.getY();
		Position botRightCorner = new Position(x+size, y+size);
		double xDifference = Math.abs(click.getX() - botRightCorner.getX());
		double yDifference = Math.abs(click.getY() - botRightCorner.getY());
		if (xDifference <= size/2 && yDifference <= size/2) {
			return true;
		} else {
			return false;
		}
	}

	public UserInterface() {
		UI.initialise();
		UI.setDivider(0.6);
		UI.setWindowSize(1500, 900);
		UI.setMouseMotionListener(this::doMouse);
		buttons.add(UI.addButton("Draw box", this::addBox)); //0	
		buttons.add(UI.addButton("Draw circle", this::addCircle));	//1
		buttons.add(UI.addButton("Draw line", this::addLine)); //2
		buttons.add(UI.addButton("Add text", this::addText)); //3
		buttons.add(UI.addButton("", this::nothing));//4
		buttons.add(UI.addButton("Set shape size", this::setSize)); //5
		buttons.add(UI.addButton("Reset shape colour", this::resetShapeColour)); //6
		buttons.add(UI.addButton("", this::setColour));//7
		buttons.add(UI.addButton("Set font size", this::setFontSize));//8
		buttons.add(UI.addButton("Reset font colour", this::resetFontColour)); //9
		buttons.add(UI.addButton("", this::setFontColour)); //10
		//buttons.add(UI.addButton("Fill shape", this::fillShape));
		buttons.add(UI.addButton("Remove Mode", this::remove)); //11
		buttons.add(UI.addButton("Clear", this::clear)); //12
		buttons.add(UI.addButton("Save", this::saveFile)); //13
		buttons.add(UI.addButton("Load", this::loadFile)); //14
		buttons.add(UI.addButton("Quit", this::quit)); //15
		buttons.get(0).setBackground(Color.lightGray);
		for (int i=1; i<buttons.size(); i++) {
			buttons.get(i).setBackground(Color.white);
		}
		buttons.get(7).setBackground(currentFontColour);
		buttons.get(10).setBackground(currentColour);
		UI.println("Welcome to the Diagram Editor.\n");
		UI.println("<-- Click these buttons to select a shape, then draw over there -->");
		for (int i=0; i<6; i++) {
			UI.println("\n");
		}
		UI.println("<-- Click the coloured buttons to set the shape colour...");
		for (int i=0; i<3; i++) {
			UI.println("\n");
		}
		UI.println("<-- ... and the font colour.");
		UI.println("\n<-- If you want to remove an item, toggle Remove Mode.");
	}

	public void clear() {
		UI.clearGraphics();
		shapes.clear();
	}

	public void quit() {
		UI.quit();
	}

	//placeholder for non-implemented methods
	public void nothing() {
		UI.println("Not implemented.");
	}

	public static void main(String[] args) {
		new UserInterface();

	}

}

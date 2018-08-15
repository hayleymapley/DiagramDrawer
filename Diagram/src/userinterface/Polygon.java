package userinterface;

import java.awt.Color;

import ecs100.UI;

public class Polygon implements Shape {

	private Color colour;
	private Position position;
	private int numSides;
	private double size;
	private boolean isSelected;
	
	public Polygon(Color c, Position p, int sides, int s) {
		colour = c;
		position = p;
		setNumSides(sides);
		size = s;
	}
	
	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getNumSides() {
		return numSides;
	}
	
	public void setNumSides(int num) {
		numSides = num;
	}
	
	public double getSize() {
		return size;
	}

	public void setSize(double x) {
		size = x;
	}

	public boolean isSelected(Position click) {
		
		return false;
	}
	
	public void setIsSelected(boolean b) {
		isSelected = b;
	}

	public void drawShape() {
		UI.setColor(colour);
		//UI.fillPolygon(xPoints, yPoints, nPoints);
	}
	public void drawOutline() {
		
	}

	@Override
	public Position getEndPosition() {
		// TODO Auto-generated method stub
		return null;
	}	
}

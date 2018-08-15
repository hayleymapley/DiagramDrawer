package userinterface;

import java.awt.Color;

import ecs100.UI;

public class Box implements Shape {

	private Color colour = Color.black;
	private Position position;
	private double size;
	boolean isSelected;
	boolean isFillShape;

	public Box(Color c, Position p, double s) {
		colour = c;
		position = p;
		size = s;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color c) {
		colour = c;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position p) {
		position = p;
	}
	
	public Position getEndPosition() {
		return position;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double x) {
		size = x;
	}

	public boolean isSelected(Position click) {
		double xDifference = Math.abs(click.getX() - position.getX());
		double yDifference = Math.abs(click.getY() - position.getY());
		if (xDifference <= size/2 && yDifference <= size/2) {
			isSelected = true;
			return true;
		} else {
			isSelected = false;
			return false;
		}
	}
	
	public void setIsSelected(boolean b) {
		isSelected = b;
	}
	
	public void setIsFillShape(boolean b) {
		isFillShape = b;
	}
	
	public boolean getIsFillShape() {
		return isFillShape;
	}

	public void drawShape() {
		if (isSelected) {
			drawOutline();
		}
		UI.setColor(colour);
		UI.drawRect(position.getX()-(size/2), position.getY()-(size/2), size, size);
	}
	
	public void drawOutline() {
		UI.setColor(Color.cyan);
		UI.setLineWidth(2);
		UI.drawRect((position.getX()-(size/2))-1, (position.getY()-(size/2))-1, size+3, size+3);
		UI.setLineWidth(1);
	}
	
	public String toString() {
		return this.getColour().toString() + this.getPosition().toString() + this.getSize();
	}
	
}

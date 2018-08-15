package userinterface;

import java.awt.Color;

import ecs100.UI;

public class Circle implements Shape {

	private Color colour;
	private Position position;
	private double size;
	boolean isSelected;

	public Circle(Color c, Position p, double size) {
		colour = c;
		position = p;
		this.size = size;
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

	public double getSize() {
		return size;
	}

	public void setSize(double x) {
		this.size = x;
	}

	public boolean isSelected(Position click) {
		double sideA = position.getY() - click.getY();
		double sideB = position.getX() - click.getX();
		double sideC = Math.sqrt(sideA*sideA + sideB*sideB);

		if (sideC<=size/2) {
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

	public void drawShape() {
		if (isSelected) {
			this.drawOutline();
		}
		UI.setColor(colour);
		UI.drawOval(position.getX() - (size/2), position.getY() - (size/2), size, size);		
	}

	public void drawOutline() {
		UI.setColor(Color.cyan);
		UI.setLineWidth(2);
		UI.drawOval((position.getX()-(size/2))-1, (position.getY()-(size/2))-1, size+3, size+3);
		UI.setLineWidth(1);
	}
	
	public String toString() {
		return this.getColour().toString() + this.getPosition().toString() + this.getSize();
	}

	@Override
	public Position getEndPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}

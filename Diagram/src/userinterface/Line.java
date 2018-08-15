package userinterface;

import java.awt.Color;

import ecs100.UI;

public class Line implements Shape {

	private Position start; //start
	private Position end; //end
	private double lineWidth = 1;
	private Color colour;
	private boolean isSelected;

	public Line(Position x, Position y) {
		start = x;
		end = y;
	}

	public Position getStart() {
		return start;
	}
	public void setStart(Position x) {
		start = x;
	}

	public Position getEnd() {
		return end;
	}
	public void setEnd(Position y) {
		end = y;
	}

	public Color getColour() {
		return colour;
	}
	public void setColour(Color c) {
		colour = c;
	}

	public Position getPosition() {
		return start;
	}

	public void setPosition(Position position) {
		position = start;
	}
	
	public Position getEndPosition() {
		return end;
	}

	public double getSize() {
		return lineWidth;
	}

	public void setSize(double x) {
		lineWidth = x;
	}

	public boolean isSelected(Position click) {
		double xDifference = Math.abs(click.getX() - start.getX());
		double yDifference = Math.abs(click.getY() - start.getY());
		double xSize = end.getX()-start.getX()/2;
		double ySize = this.getSize() * 10;
		if (xDifference <= xSize && yDifference <= ySize) {
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
		UI.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	public void drawOutline() {
		UI.setColor(Color.cyan);
		UI.drawLine(start.getX()-1, start.getY()+1, end.getX()-1, end.getY()+1);
	}
}


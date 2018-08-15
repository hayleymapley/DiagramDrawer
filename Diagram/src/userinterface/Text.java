package userinterface;

import java.awt.Color;

import ecs100.UI;

public class Text implements Shape {
	private String string;
	private double left;
	private double baseline;
	private Position botLeft;
	private double fontSize;
	private Color fontColour;
	private boolean isSelected;
	
	public Text(String t, double l, double b, Color c) {
		string = t;
		left = l;
		baseline = b;
		botLeft = new Position(l,b);
		fontColour = c;
	}
	
	public String getString() {
		return string;
	}
	
	public double getLeft() {
		return left;
	}
	
	public double getBaseline() {
		return baseline;
	}
	
	public double getFontSize() {
		return fontSize;
	}

	public Color getColour() {
		return fontColour;
	}

	public void setColour(Color colour) {
		this.fontColour = colour;		
	}

	public Position getPosition() {
		return botLeft;
	}

	public void setPosition(Position position) {
		botLeft = position;
		left = position.getX();
		baseline = position.getY();
	}

	public double getSize() {
		return fontSize;
	}

	public void setSize(double x) {
		fontSize = x;
	}

	public boolean isSelected(Position click) {
		//double xDifference = Math.abs(click.getX() - botLeft.getX());
		//double yDifference = Math.abs(click.getY() - botLeft.getY());
		//is click x > left and < left + width
		boolean xInBounds = false;
		boolean yInBounds = false;
		if (click.getX() > left && click.getX() < (left + (string.length()*10))) {
			xInBounds = true;
		}
		//is click y < botleft and > y - height
		if (click.getY() < botLeft.getY() && click.getY() > (botLeft.getY() - fontSize)) {
			yInBounds = true;
		}
		if (xInBounds && yInBounds) {
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
		UI.setColor(fontColour);
		UI.setFontSize(fontSize);
		UI.drawString(this.getString(), this.getLeft()-5, this.getBaseline()+5);
	}

	public void drawOutline() {
		UI.setColor(Color.cyan);
		UI.setFontSize(this.getFontSize()+2);
		UI.drawString(this.getString(), this.getLeft()-6, this.getBaseline()+5);
		UI.setFontSize(this.getFontSize());
	}

	public Position getEndPosition() {
		return null;
	}
}

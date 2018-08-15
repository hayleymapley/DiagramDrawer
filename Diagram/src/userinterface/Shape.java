package userinterface;
import java.awt.Color;

public interface Shape {

	public Color getColour();
	public void setColour(Color colour);

	public Position getPosition();
	public void setPosition(Position position);
	
	public Position getEndPosition();
	
	public double getSize();
	public void setSize(double x);
	
	public boolean isSelected(Position click);
	public void setIsSelected(boolean b);
	
	public void drawShape();
	public void drawOutline();
	
}

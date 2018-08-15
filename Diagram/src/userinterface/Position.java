package userinterface;

public class Position {
	
	private double x, y;
	
	public double getX() {
		return x;
	}
	public void setX(double a) {
		x = a;
	}
	
	public double getY() {
		return y;
	}
	public void setY(double b) {
		y = b;
	}
	
	public Position(double a, double b) {
		x = a;
		y = b;
	}
	
	public String toString() {
		return this.getX() + " " + this.getY();
	}
}

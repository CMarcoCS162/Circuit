import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Node extends Draggable {

	public Node(double a, double b) {
		x = a;
		y = b;
	}
	
	double x;
	double y;
	ArrayList<Element> connected = new ArrayList<Element>();
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.black);
		Ellipse2D.Double circle = new Ellipse2D.Double(x-5, y-5, 10.0, 10.0);
		g2d.fill(circle);
	}
	
	// Check if mouse is over node
	public boolean clickedOn(double clickX, double clickY) {
		Ellipse2D circle = new Ellipse2D.Double(x-15, y-15, 30.0, 30.0);
		return circle.contains(clickX, clickY);
	}
	
	
	public void move(double deltaX, double deltaY) {
		x += deltaX;
		y += deltaY;
	}

	public void connect(Element e) {
		connected.add(e);
	}
}

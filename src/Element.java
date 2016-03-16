import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

// Any circuit element. Has voltage, current, and resistance which are NaN by default until solved for or input.
// Each Element is bordered by two nodes.
public abstract class Element extends Draggable {
	
	public Element(Node s, Node e, Main c, double h) {
		start = s;
		end = e;
		circuit = c;
		height = h;
		
		shape = getShape();
		at = getTransform();
		
		start.connect(this);
		end.connect(this);
		
		resistance = Double.NaN;
		voltage = Double.NaN;
		current = Double.NaN;
	}

	public double resistance;
	public double voltage;
	public double current;
	
	public Node start;
	public Node end;
	public double height;
	public Main circuit;
	
	public Shape shape;
	public Color color;
	
	public AffineTransform at;

	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
		
		String str = "";
		if(resistance != Double.NaN)
			str += ("R: " + Double.toString(resistance));
		if(current != Double.NaN)
			str += ("\nI: " + Double.toString(current));
		if(voltage != Double.NaN)
			str += ("\nV: " + Double.toString(voltage));
		g.drawString(str, (int)(start.x + end.y)/2, (int)((end.y+start.y)/2+20));
	}
	
	public boolean clickedOn(double clickX, double clickY) {
		return getShape().contains(clickX, clickY);
	}
		
	// Determine what shape the element has on the GUI to determine whether it has been clicked on.
	public Shape getShape() {
		Shape s = baseShape();
		at = getTransform();
		return at.createTransformedShape(s);
	}
	
	public Shape baseShape() {
		double width = Math.sqrt((start.x-end.x)*(start.x-end.x)+(start.y-end.y)*(start.y-end.y));
		return new Rectangle2D.Double((start.x+end.x)/2 - width/2, (start.y+end.y)/2 - height/2, width, height);
	}
	
	public Area getArea() {
		shape = getShape();
		return new Area(shape);
	}
	
	public AffineTransform getTransform() {
		AffineTransform a = new AffineTransform();
		a.rotate(getRotation()+Math.PI/2, (start.x+end.x)/2, (start.y+end.y)/2);
		return a;
	}
	
	public double getRotation() {
		return Math.atan2((start.y-end.y)/2, (end.x-start.x)/2);
	}
	
	public void move(double deltaX, double deltaY) {
		start.move(deltaX, deltaY);
		end.move(deltaX, deltaY);
	}
	
	public double getResistance() {return 0;}

	// Get whichever border node is not the input node.
	public Node other(Node n) {
		return start == n ? end : start;
	}
	
	
}

import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;

// Ideal Resistor
public class Resistor extends Element {

	public Resistor(Node s, Node e, Main c, double x) {
		super(s, e, c, 70.0);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawLine((int) start.x, (int) start.y, (int) (start.x*10/12.0+end.x/6.0), (int)(start.y*10/12.0+end.y/6.0));
		g.drawLine((int) end.x, (int) end.y, (int) (start.x/6.0+end.x*5/6.0), (int)(start.y/6.0+end.y*5/6.0));
		
		drawTriangle(1, g, 1);drawTriangle(3, g, 1);drawTriangle(5, g, 1);drawTriangle(7, g, 1);
		drawTriangle(0, g, -1);drawTriangle(2, g, -1);drawTriangle(4, g, -1);drawTriangle(6, g, -1);
		
		String str = "";
		if(resistance != Double.NaN)
			str += ("R: " + Double.toString(resistance));
		if(current != Double.NaN)
			str += (System.lineSeparator() + " I: " + Double.toString(current));
		if(voltage != Double.NaN)
			str += (System.lineSeparator() + " V: " + Double.toString(voltage));
		g.drawString(str, (int)(start.x + end.x)/2, (int)((end.y+start.y)/2-50));
	}

	private void drawTriangle(int i, Graphics g, int aux) {
		double auxX = Math.cos(-getRotation()-Math.PI/2)*30.0*aux;
		double auxY = Math.sin(-getRotation()-Math.PI/2)*30.0*aux;
		g.drawLine((int) (start.x*(12-(2+i))/12.0+end.x*(2+i)/12.0), (int) (start.y*(12-(2+i))/12.0+end.y*(2+i)/12.0), 
				(int) (start.x*(24-(5+2*i))/24.0+end.x*(5+2*i)/24.0+auxX), (int) (start.y*(24-(5+2*i))/24.0+end.y*(5+2*i)/24.0+auxY));
		g.drawLine((int) (start.x*(12-(3+i))/12.0+end.x*(3+i)/12.0), (int) (start.y*(12-(3+i))/12.0+end.y*(3+i)/12.0), 
				(int) (start.x*(24-(5+2*i))/24.0+end.x*(5+2*i)/24.0+auxX), (int) (start.y*(24-(5+2*i))/24.0+end.y*(5+2*i)/24.0+auxY));
		
	}
	
	public double getResistance() {return resistance;}
}

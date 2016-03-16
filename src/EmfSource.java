import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;

// Some sort of ideal EMF source with no resistance
public class EmfSource extends Element {

	public EmfSource(Node s, Node e, Main c, double x) {
		super(s, e, c, 70.0);
	}
	
	
	public void paint(Graphics g) {
		double auxX = Math.cos(-getRotation()-Math.PI/2);
		double auxY = Math.sin(-getRotation()-Math.PI/2);
		
		g.setColor(Color.black);
		g.drawLine((int) start.x, (int) start.y, (int) (start.x*4/7.0+end.x*3/7.0), (int)(start.y*4/7.0+end.y*3/7.0));
		g.drawLine((int) end.x, (int) end.y, (int) (start.x*3/7.0+end.x*4/7.0), (int)(start.y*3/7.0+end.y*4/7.0));
		
		g.drawLine((int) (start.x*5/9.0+end.x*4/9.0+auxX*25), (int)(start.y*5/9.0+end.y*4/9.0+auxY*25),
				(int) (start.x*5/9.0+end.x*4/9.0-auxX*25), (int)(start.y*5/9.0+end.y*4/9.0-auxY*25));
		g.drawLine((int) (start.x*4/9.0+end.x*5/9.0+auxX*10), (int)(start.y*4/9.0+end.y*5/9.0+auxY*10),
				(int) (start.x*4/9.0+end.x*5/9.0-auxX*10), (int)(start.y*4/9.0+end.y*5/9.0-auxY*10));
		
		String str = "";
		if(resistance != Double.NaN)
			str += ("R: " + Double.toString(resistance));
		if(current != Double.NaN)
			str += (System.lineSeparator() + " I: " + Double.toString(current));
		if(voltage != Double.NaN)
			str += (System.lineSeparator() + " V: " + Double.toString(voltage));
		g.drawString(str, (int)(start.x + end.x)/2, (int)((end.y+start.y)/2-50));
	}
	

}
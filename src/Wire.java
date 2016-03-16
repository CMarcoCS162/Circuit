import java.awt.Color;
import java.awt.Graphics;

// Boring old wire with no resistance
public class Wire extends Element {
	public Wire(Node s, Node e, Main c) {
		super(s, e, c, 20.0);
	}

	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
	}
}

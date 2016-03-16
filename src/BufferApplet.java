import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class BufferApplet extends Applet {

	private static final long serialVersionUID = 1L;

	Image offScreenBuffer;

	public void update(Graphics g) {
		Graphics gr;
		if (offScreenBuffer == null || (!(offScreenBuffer.getWidth(this) == getWidth()
				&& offScreenBuffer.getHeight(this) == getHeight()))) {
			offScreenBuffer = this.createImage(getWidth(), getHeight());
		}
		gr = offScreenBuffer.getGraphics();
		paint(gr);
		g.drawImage(offScreenBuffer, 0, 0, this);
	}
}

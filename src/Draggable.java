// Any class that can be dragged around by the mouse.
public abstract class Draggable {
	abstract boolean clickedOn(double clickX, double clickY);
	
	abstract void move(double deltaX, double deltaY);
}

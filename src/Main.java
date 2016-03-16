import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Jama.Matrix;

public class Main extends BufferApplet implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	public boolean eKeyPressed;
	public boolean rKeyPressed;
	public boolean qKeyPressed;
	public boolean leftPressed;
	public boolean rightPressed;

	private double oldX;
	private double oldY;
	private Draggable draggedItem;
	private Node startNode;

	public Scanner scanner;
	public Circuit circuit = new Circuit(this);

	public void init() {
		// Initialize code
		setSize(Cfg.width, Cfg.height);
		addKeyListener(this);
		addMouseListener(this);
		this.setFocusable(true);
		scanner = new Scanner(System.in);
	}

	public void start() {
		// Create a timer to run the update code 50 times per second
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				doStuff();

			}
		};
		Timer timer = new Timer(20, taskPerformer);
		timer.start();

		doStuff();

	}

	public void doStuff() {
		if (rightPressed) {		// Have the ability to drag items in the GUI
			double mouseX = getMouse().x;
			double mouseY = getMouse().y;
			draggedItem.move(mouseX - oldX, mouseY - oldY);
			oldX = mouseX;
			oldY = mouseY;
		}

		repaint();
	}

	public Point getMouse() {
		if(this.getMousePosition() == null)
				return new Point();
		else
			return this.getMousePosition();
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Cfg.width, Cfg.height);

		for (Element e : circuit.elements) {
			e.paint(g);
		}
		for (Node n : circuit.nodes) {
			n.paint(g);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Cfg.emfKey) {
			eKeyPressed = true;
		} else if (e.getKeyCode() == Cfg.resistorKey) {
			rKeyPressed = true;
		} else if (e.getKeyCode() == Cfg.removeKey) {
			qKeyPressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Cfg.emfKey) {
			eKeyPressed = false;
		} else if (e.getKeyCode() == Cfg.resistorKey) {
			rKeyPressed = false;
		} else if (e.getKeyCode() == Cfg.removeKey) {
			qKeyPressed = false;
		} else if (e.getKeyCode() == Cfg.calcKey)
			circuit.solve();

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void stop() {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		oldX = getMouse().x;
		oldY = getMouse().y;

		if (SwingUtilities.isRightMouseButton(e)) {		// Drag an item
			draggedItem = null;
			for (Element el : circuit.elements) {
				if (el.clickedOn(oldX, oldY))
					draggedItem = el;
			}
			for (Node n : circuit.nodes) {
				if (n.clickedOn(oldX, oldY))
					draggedItem = n;
			}

			if (draggedItem != null)
				rightPressed = true;
		} else if (SwingUtilities.isLeftMouseButton(e)) {		// Make start node for a new element
			leftPressed = true;

			startNode = null;
			for (Node n : circuit.nodes) {
				if (n.clickedOn(oldX, oldY))
					startNode = n;
			}

			if (startNode == null) {
				startNode = new Node(oldX, oldY);
				circuit.nodes.add(startNode);
				// }
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!leftPressed && !rightPressed)
			return;
		if (SwingUtilities.isRightMouseButton(e)) {		// Stop dragging things
			rightPressed = false;
		} else if (SwingUtilities.isLeftMouseButton(e)) {		// Create new element of some kind
			leftPressed = false;

			Node temp = null;
			for (Node n : circuit.nodes) {
				if (n.clickedOn(getMouse().x, getMouse().y))
					temp = n;
			}

			if (temp == null)
				temp = new Node(getMouse().x, getMouse().y);

			if (Math.abs(temp.x - startNode.x) + Math.abs(temp.y - startNode.y) > 50) {
				if (!circuit.nodes.contains(temp))
					circuit.nodes.add(temp);
			} else {
				circuit.nodes.remove(startNode);
				return;
			}

			if (eKeyPressed) {
				EmfSource current = new EmfSource(startNode, temp, this, 1);
				circuit.elements.add(current);
				repaint();
				System.out.println("Enter voltage: ");
				 current.voltage = scanner.nextInt();
			} else if (rKeyPressed) {
				Resistor current = new Resistor(startNode, temp, this, 1);
				circuit.elements.add(current);
				repaint();
				System.out.println("Enter resistance: ");
				 current.resistance = scanner.nextInt();
			} else if (qKeyPressed) {
				circuit.elements.remove(draggedItem);
				circuit.nodes.remove(draggedItem);
			} else {
				circuit.elements.add(new Wire(startNode, temp, this));
			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

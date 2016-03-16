import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Jama.Matrix;

// Stores a circuit. Merely a collection of nodes and elements.
public class Circuit {
	public ArrayList<Element> elements = new ArrayList<Element>();
	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	Main main;
	
	public Circuit(Main m) {
		main = m;
		elements = new ArrayList<Element>();
		nodes = new ArrayList<Node>();
	}
	
	// Solves for the voltage and current of each circuit element
	public void solve() {
		double[] B = new double[elements.size()*2];
		Arrays.fill(B, 0);
		
		double[][] A =  new double[B.length][B.length];
		for(int i=0; i< A.length; i++)
			for(int j=0; j< A[0].length; j++)
				A[i][j] = 0;
	
		int pointer = 0;
		boolean first = true;
		for(Node n: nodes) {
			if(first) {
				first = false;
				continue;
			}
			
			for(Element e: n.connected) {	// Kirchoff's First Law: current in = current out
				A[pointer][elements.indexOf(e)] =  e.start == n ? -1 : 1;
			}
			pointer++;
		}
		
		for(Element e: elements) {			// V = IR
			if(EmfSource.class.isInstance(e)) {
				B[pointer] = ((EmfSource)e).voltage;
			} else {
				A[pointer][elements.indexOf(e)] = e.getResistance();
			}
			
			A[pointer][elements.indexOf(e)+elements.size()] = 1;
			pointer++;
		}
		
		int loopStart = pointer;
		boolean solved = false;
		Matrix X = new Matrix(0, 0);
		
		do {
			pointer = loopStart;
			Random rand = new Random();
			
			while(pointer < B.length) {
				ArrayList<Node> loop = new ArrayList<Node>();
				Node start = nodes.get(rand.nextInt(nodes.size()));
				Node current = start;
				Element lastElement = null;
				ArrayList<Element> loopElements = new ArrayList<Element>();
				
				do {
					loop.add(current);
					Element el;
					do {
						el = current.connected.get(rand.nextInt(current.connected.size()));
					} while(el == lastElement);
					
					current = el.other(current);
					loopElements.add(el);
					lastElement = el;
				}while(!loop.contains(current));
				
				//Kirchoff's Second Law: V_Loop = 0
				for(int i=loop.indexOf(current); i<loopElements.size(); i++) {
					A[pointer][elements.indexOf(loopElements.get(i)) + elements.size()] = 1;
				}
				
				pointer++;
			}
		
			double[][] newB = new double[B.length][1];
			for(int i=0; i<B.length; i++)
				newB[i][0] = B[i];
		
			Matrix matrixA = (new Matrix(A));
			Matrix matrixB = new Matrix(newB);
			try {
				X = matrixA.solve(matrixB);
				solved = true;
			} catch(RuntimeException e) {
				solved = false;
			}
			
			for(Element e: elements) {
				if (X.get(elements.indexOf(e), 0) == 0)
					solved = false;
			}
		
		}while(!solved);
		
		// Print out results and set current and voltage for all elements
		for(Element e: elements) {
			System.out.println("Resistance: " + e.getResistance() + " Voltage: " + X.get(elements.indexOf(e)+elements.size(), 0) + " Current: " + X.get(elements.indexOf(e), 0));
			e.resistance = e.getResistance(); e.voltage = X.get(elements.indexOf(e)+elements.size(), 0); e.current=X.get(elements.indexOf(e), 0);
		}
	}
}

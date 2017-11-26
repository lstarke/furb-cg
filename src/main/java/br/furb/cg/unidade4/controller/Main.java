package br.furb.cg.unidade4.controller;

import java.awt.event.*;
import javax.media.opengl.*;
import br.furb.cg.unidade4.model.*;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	/// Objetos do OpenGL
	private OpenGL o;
	
	/// Mundo da cena
	private Mundo mundo;
	
	/// Especificacao de controladores
	private Controller2D cont2d;
	private Controller3D cont3d;
	
	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		o = new OpenGL(drawable);
		mundo = new Mundo(true);
		cont2d = new Controller2D(o, mundo);
		cont3d = new Controller3D(o, mundo);
	}
	
	// metodo definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// System.out.println(" --- reshape ---");
		o.gl.glViewport(0, 0, width, height);
		cont3d.reshape3D();
	}

	public void display(GLAutoDrawable arg0) {
		if (mundo.is2D())
			cont2d.cena2D();
		else
			cont3d.cena3D();
		
		o.gl.glFlush();
	}

	public void keyPressed(KeyEvent e) {
		if (mundo.is2D())
			cont2d.keyPressed(e);
		else
			cont3d.keyPressed(e);

		o.glDrawable.display();
	}

	// metodo definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibicao associado foi
	// alterado.
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
//		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
//		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
//		System.out.println(" --- keyTyped ---");
	}

	public void mouseDragged(MouseEvent e) {
		//System.out.println(" --- mouseDragged ---");
		if (mundo.is2D())
			cont2d.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
//		System.out.println(" --- mouseMoved ---");
		if (mundo.is2D())
			cont2d.mouseMoved(e);
	}

	public void mouseClicked(MouseEvent e) {
		//System.out.println(" --- mouseClicked ---");
		if (mundo.is2D())
			cont2d.mouseClicked(e);
	}

	public void mouseEntered(MouseEvent e) {
//		System.out.println(" --- mouseEntered ---");
	}

	public void mouseExited(MouseEvent e) {
//		System.out.println(" --- mouseExited ---");
	}

	public void mousePressed(MouseEvent e) {
		//System.out.println(" --- mousePressed ---");
		if (mundo.is2D())
			cont2d.mousePressed(e);

		o.glDrawable.display();
	}

	public void mouseReleased(MouseEvent e) {
//		System.out.println(" --- mouseReleased ---");
		if (mundo.is2D())
			cont2d.mouseReleased(e);
	}
}
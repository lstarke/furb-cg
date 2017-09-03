package br.com.furb.cg.unidade2.ex6;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import br.com.furb.cg.unidade2.ex4.Ponto4D;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private float ortho2D_minX = -400.0f, ortho2D_maxX =  400.0f, ortho2D_minY = -400.0f, ortho2D_maxY =  400.0f;
	private Ponto4D pto1 = null, pto2 = null, pto3 = null, pto4 = null, ptoSelecionado = null;
	private int xAntigo = 0, yAntigo = 0;

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espa�o de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f); // aqui muda a cor de fundo
		
		pto1 = new Ponto4D(-100.0, -100.0, 0.0, 1.0);
		pto2 = new Ponto4D(-100.0, 100.0, 0.0, 1.0);
		pto3 = new Ponto4D(100.0, 100.0, 0.0, 1.0);
		pto4 = new Ponto4D(100.0, -100.0, 0.0, 1.0);
		ptoSelecionado = pto1;
	}
	
	public void SRU() {
//		gl.glDisable(gl.GL_TEXTURE_2D);
//		gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
//		gl.glDisable(gl.GL_LIGHTING); //TODO: [D] FixMe: check if lighting and texture is enabled
		
		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin( GL.GL_LINES );
			gl.glVertex2f( -200.0f, 0.0f );
			gl.glVertex2f(  200.0f, 0.0f );
		gl.glEnd();
			
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin( GL.GL_LINES);
			gl.glVertex2f(  0.0f, -200.0f);
			gl.glVertex2f(  0.0f, 200.0f );
		gl.glEnd();
	}

	//exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D( ortho2D_minX,  ortho2D_maxX,  ortho2D_minY,  ortho2D_maxY);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		SRU();
		 
		// seu desenho ...
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(2.0f);
		gl.glPointSize(5.0f);
		
		// Guias
		gl.glBegin(GL.GL_LINE_STRIP);
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex2d(pto1.obterX(), pto1.obterY());
			gl.glVertex2d(pto2.obterX(), pto2.obterY());
			gl.glVertex2d(pto3.obterX(), pto3.obterY());
			gl.glVertex2d(pto4.obterX(), pto4.obterY());
		gl.glEnd();
		 
		// Pontos
		gl.glBegin(GL.GL_POINTS);
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex2d(ptoSelecionado.obterX(), ptoSelecionado.obterY());
		gl.glEnd();
		
		// Spline
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINE_STRIP);

		for (double t = 0.0; t <= 1.0; t += 0.09)
			gl.glVertex2d(bezierCubica(t, pto1.obterX(), pto2.obterX(), pto3.obterX(), pto4.obterX()), 
					      bezierCubica(t, pto1.obterY(), pto2.obterY(), pto3.obterY(), pto4.obterY()));

		gl.glEnd();
			
		gl.glFlush();
	}
	
	public double bezierCubica(double t, double b0, double b1, double b2, double b3) {
		double passo = 1 - t;
		return Math.pow(passo, 3) * b0 + 
				3 * t * Math.pow(passo, 2) * b1 + 
				3 * Math.pow(t, 2) * (passo) * b2 + 
				(Math.pow(t, 3) * b3);
	}
	
	public double RetornaX(double angulo, double raio) { 
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	} 

	public double RetornaY(double angulo, double raio) { 
		return (raio * Math.sin(Math.PI * angulo / 180.0)); 
	}

	public void keyPressed(KeyEvent e) {
//		System.out.println(" --- keyPressed ---");
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_1:
		case KeyEvent.VK_NUMPAD1:
			ptoSelecionado = pto1;
			break;
			
		case KeyEvent.VK_2:
		case KeyEvent.VK_NUMPAD2:
			ptoSelecionado = pto2;
			break;
			
		case KeyEvent.VK_3:
		case KeyEvent.VK_NUMPAD3:
			ptoSelecionado = pto3;
			break;

		case KeyEvent.VK_4:
		case KeyEvent.VK_NUMPAD4:
			ptoSelecionado = pto4;
			break;
		}
		
//		System.out.println(" --- Redesenha ao sair do callback ---");
		glDrawable.display();
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
//		System.out.println(" --- reshape ---");
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
//		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
//		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
//		System.out.println(" --- keyTyped ---");
	}

	public void mouseClicked(MouseEvent e) {
//		System.out.println(" --- mouseClicked ---");
	}

	public void mouseEntered(MouseEvent e) {
//		System.out.println(" --- mouseEntered ---");
	}

	public void mouseExited(MouseEvent e) {
//		System.out.println(" --- mouseExited ---");
	}

	public void mousePressed(MouseEvent e) {
//		System.out.println(" --- mousePressed ---");
		
		xAntigo = e.getX();
		yAntigo = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
//		System.out.println(" --- mouseReleased ---");
	}

	public void mouseDragged(MouseEvent e) {
//		System.out.println(" --- mouseDragged ---");
		
		int xDiferenca = e.getX() - xAntigo;
		int yDiferenca = e.getY() - yAntigo;
		ptoSelecionado.atribuirX(ptoSelecionado.obterX() + xDiferenca);
		ptoSelecionado.atribuirY(ptoSelecionado.obterY() - yDiferenca);
		xAntigo = e.getX();
		yAntigo = e.getY();

		glDrawable.display();
	}

	public void mouseMoved(MouseEvent e) {
//		System.out.println(" --- mouseMoved ---");
	}
}
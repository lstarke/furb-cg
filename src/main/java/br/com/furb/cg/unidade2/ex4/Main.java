package br.com.furb.cg.unidade2.ex4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private float ortho2D_minX = -400.0f, ortho2D_maxX =  400.0f, ortho2D_minY = -400.0f, ortho2D_maxY =  400.0f;
	private double xVar = 0f, yVar = 0f, angulo = 0f, raio = 0f;
	private Ponto4D pto1 = null, pto2 = null;
	private final double DESLOCAMENTO = 10.0; // podes-se utilizar o valor que achar interessante

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espa�o de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f); // aqui muda a cor de fundo
		
		// Incializar nossas variaveis
		angulo = 45;
		raio   = 100;
		pto1   = new Ponto4D(  0.0,   0.0, 0.0, 1.0);
		pto2   = new Ponto4D(200.0, 200.0, 0.0, 1.0);
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
		 pto1.atribuirX(xVar);
		 pto1.atribuirY(yVar);
		 pto2.atribuirX(RetornaX(angulo, raio) + xVar);
		 pto2.atribuirY(RetornaY(angulo, raio) + yVar);
			
		 gl.glColor3f(0.0f, 0.0f, 0.0f);
		 gl.glPointSize(2.0f);
		 
		 gl.glBegin(GL.GL_LINES);
			gl.glVertex2d(pto1.obterX(), pto1.obterY());
			gl.glVertex2d(pto2.obterX(), pto2.obterY());
		 gl.glEnd();
		 
		 gl.glBegin(GL.GL_POINTS);
			gl.glVertex2d(pto1.obterX(), pto1.obterY());
			gl.glVertex2d(pto2.obterX(), pto2.obterY());
		 gl.glEnd();
		 
		 gl.glFlush();
	}
	
	public double RetornaX(double angulo, double raio) { 
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	} 

	public double RetornaY(double angulo, double raio) { 
		return (raio * Math.sin(Math.PI * angulo / 180.0)); 
	}

	public void keyPressed(KeyEvent e) {
		//System.out.println(" --- keyPressed ---");
		
		switch (e.getKeyCode()) {
		
		    // Mover para Esquerda
			case KeyEvent.VK_Q:
				xVar -= DESLOCAMENTO;
				break;
				
			// Mover para Direita
			case KeyEvent.VK_W:
				xVar += DESLOCAMENTO;
				break;

		    // Diminuir Raio
			case KeyEvent.VK_A:
				raio -= DESLOCAMENTO;
				break;

			// Aumentar Raio
			case KeyEvent.VK_S:
				raio += DESLOCAMENTO;
				break;

			// Diminiur Angulo
			case KeyEvent.VK_Z:
				angulo -= DESLOCAMENTO;

				if (angulo < 0.0)
					angulo += 360.0;
				break;

			// Aumentar Angulo
			case KeyEvent.VK_X:
				angulo += DESLOCAMENTO;
				if (angulo > 360.0)
					angulo -= 360.0;
				break;
		}
		
		//System.out.println(" --- Redesenha ao sair do callback ---");
		glDrawable.display();
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		//System.out.println(" --- reshape ---");
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		//System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		//System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		//System.out.println(" --- keyTyped ---");
	}
}
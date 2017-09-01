package br.com.furb.cg.unidade2.ex2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private float ortho2D_minX = -400.0f, ortho2D_maxX =  400.0f, ortho2D_minY = -400.0f, ortho2D_maxY =  400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private final double DESLOCAMENTO = 20.0; // podes-se utilizar o valor que achar interessante

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espa�o de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f); // aqui muda a cor de fundo
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
		 gl.glPointSize(2.0f);
		 gl.glBegin(GL.GL_POINTS);
		 gl.glColor3f(0.0f, 0.0f, 1.0f);
		 	// 72 pontos
		 	for (int i = 0; i < 72; i++) {
		 		// raio = 100
		 		double x = this.RetornaX(i*5, 100); // 360/72 = 5
			 	double y = this.RetornaY(i*5, 100);
			 	gl.glVertex2d(x,y);				
			}		   	    
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
		//System.out.println(" --- keyPressed --- ");
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_I: // Zoom In
				if (ortho2D_maxX - ortho2D_minX > 100) {
					ortho2D_minX += DESLOCAMENTO;
					ortho2D_maxX -= DESLOCAMENTO;
					ortho2D_minY += DESLOCAMENTO;
					ortho2D_maxY -= DESLOCAMENTO;
				} else
					System.out.println("Limite maximo do Zoom!");
				break;

			case KeyEvent.VK_O: // Zoom Out
				if (ortho2D_maxX - ortho2D_minX < 2200) {
					ortho2D_minX -= DESLOCAMENTO;
					ortho2D_maxX += DESLOCAMENTO;
					ortho2D_minY -= DESLOCAMENTO;
					ortho2D_maxY += DESLOCAMENTO;
				} else
					System.out.println("Limite minimo do Zoom!");
				break;

			case KeyEvent.VK_E: // Pan E = Esquerda
				ortho2D_minX += DESLOCAMENTO;
				ortho2D_maxX += DESLOCAMENTO;
				break;

			case KeyEvent.VK_D: // Pan D = Direita
				ortho2D_minX -= DESLOCAMENTO;
				ortho2D_maxX -= DESLOCAMENTO;
				break;

			case KeyEvent.VK_C:	// Pan C = Cima
				ortho2D_minY -= DESLOCAMENTO;
				ortho2D_maxY -= DESLOCAMENTO;
				break;

			case KeyEvent.VK_B:	// Pan B = Baixo
				ortho2D_minY += DESLOCAMENTO;
				ortho2D_maxY += DESLOCAMENTO;
				break;
		}
		System.out.println("minX: " + ortho2D_minX  +
						   " maxX: " + ortho2D_maxX +
				           " minY: " + ortho2D_minY +
				           " maxY: " + ortho2D_maxY);
		
		//System.out.println(" --- Redesenha ao sair do callback --- ");
		glDrawable.display();
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		//System.out.println(" --- reshape --- ");
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		//System.out.println(" --- displayChanged --- ");
	}

	public void keyReleased(KeyEvent arg0) {
		//System.out.println(" --- keyReleased --- ");
	}

	public void keyTyped(KeyEvent arg0) {
		//System.out.println(" --- keyTyped --- ");
	}
}
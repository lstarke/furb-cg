package br.com.furb.cg.unidade2.ex3;

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

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espa�o de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // aqui muda a cor de fundo
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
		 gl.glPointSize(1.2f);
		 gl.glBegin(GL.GL_POINTS);
			 
			 int qtdePontos = 360;
			 double x = 0.0;
			 double y = 0.0;
			 
			 // primeiro circulo no primeiro quadrante
			 for (int i = 0; i < qtdePontos; i++) {		 	
			 	x = this.RetornaX(i*(qtdePontos/360), 100) + 100; 
			 	y = this.RetornaY(i*(qtdePontos/360), 100) + 100;
			 	gl.glVertex2d(x,y);				
			 }
			 
			// segundo circulo no primeiro quadrante
			for (int i = 0; i < qtdePontos; i++) {		 	
			 	x = this.RetornaX(i*(qtdePontos/360), 100) - 100; 
			 	y = this.RetornaY(i*(qtdePontos/360), 100) + 100;
			 	gl.glVertex2d(x,y);				
			}
			
			// terceiro circulo no terceiro e quarto quadrante
			for (int i = 0; i < qtdePontos; i++) {		 	
			 	x = this.RetornaX(i*(qtdePontos/360), 100); 
			 	y = this.RetornaY(i*(qtdePontos/360), 100) - 100;
				gl.glVertex2d(x,y);				
			}
			
		gl.glEnd();
		// este trecho de código desenha o triangulo
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);	
			// base do triangulo
		 	gl.glVertex2d(-100, 100);
		    gl.glVertex2d(100, 100);
		    // linha lado esquerdo
		    gl.glVertex2d(-100, 100);
		    gl.glVertex2d(0, -100);
		    // linha lado direito
		    gl.glVertex2d(100, 100);
		    gl.glVertex2d(0, -100);
		/*
		este código desenha o triango passando somente três vertices
		porem preenche com triangulo com uma cor    
		    
		gl.glBegin(GL.GL_TRIANGLES);
		 	gl.glVertex2d(-100, 100);
		 	gl.glVertex2d(100, 100);
		 	gl.glVertex2d(0, -100);	
		gl.glEnd();	
		*/
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
		System.out.println(" --- keyPressed ---");
		
		System.out.println(" --- Redesenha ao sair do callback ---");
		glDrawable.display();
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		System.out.println(" --- reshape ---");
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}
	
}

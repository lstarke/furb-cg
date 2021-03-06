package br.com.furb.cg.unidade2.ex5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ArrayBlockingQueue;

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
	private boolean v = false;
	private int primitiva = GL.GL_POINTS;
	private int i = 0;
	private int listaPrimitivas[] = {GL.GL_POINTS,
			                 GL.GL_LINES,
			                 GL.GL_LINE_LOOP,
			                 GL.GL_LINE_STRIP,
			                 GL.GL_TRIANGLES,
			                 GL.GL_TRIANGLE_FAN,
			                 GL.GL_TRIANGLE_STRIP,
			                 GL.GL_QUADS,
			                 GL.GL_QUAD_STRIP,
			                 GL.GL_POLYGON};
	

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
		 gl.glPointSize(5f);
		 gl.glLineWidth(5.0f);
		 
		  
		 
		 if (v) {			 
			 gl.glBegin(primitiva);			 
				 // primeiro quadrante
				 gl.glColor3f(0.0f, 1.0f, 0.0f);
		         gl.glVertex2i(200,200);
		         // quarto quadrante
		         gl.glColor3f(1.0f, 0.0f, 0.0f);
		         gl.glVertex2i(200,-200);
		         // terceiro quadrante
		         gl.glColor3f(1.0f, 0.0f, 1.0f);
		         gl.glVertex2i(-200,-200);            
		         // segundo quadrante
		      	 gl.glColor3f(0.0f, 0.0f, 1.0f);
		         gl.glVertex2i(-200,200);
		     gl.glEnd();
		     gl.glFlush();			
		}
		 
	}	

	public void keyPressed(KeyEvent e) {
		System.out.println(" --- keyPressed ---");		
		v = true;		
		// pega a primitiva na lista de primitivas
		this.primitiva = listaPrimitivas[i++];		
		// faz loop na lista de primitivas
		if (i == 9) {
			i = 0;
		}
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
		
		if (arg0.getKeyCode() == 32) {
			System.out.println("entrou apertando espaço");
			//v = true;
		}
		
	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}
	
}

package br.com.furb.cg.unidade3.ex1;
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

public class TelaGrafica implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Mundo mundo;
	private Caneta caneta;
	


//	private ObjetoGrafico objeto = new ObjetoGrafico();
	private ObjetoGrafico[] objetos = { 
			new ObjetoGrafico(),
			new ObjetoGrafico() };
	
	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		mundo = new Mundo();
		caneta = new Caneta();

		//for (byte i=0; i < objetos.length; i++) {
		//	objetos[i].atribuirGL(gl);
		//}
	}

	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLineWidth(1.0f);
		gl.glPointSize(1.0f);
		
		mundo.posicionaCamera(gl, glu);
		mundo.desenhaSRU(gl, glu);
		mundo.desenhaObjetos(gl, glu);
		
		//for (byte i=0; i < objetos.length; i++) {
		//	objetos[i].desenha();
		//}
		
		gl.glFlush();
	}
	
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_P:
			//objetos[0].exibeVertices();
			break;
		case KeyEvent.VK_M:
			objetos[0].exibeMatriz();
			break;

		case KeyEvent.VK_R:
			objetos[0].atribuirIdentidade();
			break;

		case KeyEvent.VK_RIGHT:
			objetos[0].translacaoXYZ(2.0,0.0,0.0);
			break;
		case KeyEvent.VK_LEFT:
			objetos[0].translacaoXYZ(-2.0,0.0,0.0);
			break;
		case KeyEvent.VK_UP:
			objetos[0].translacaoXYZ(0.0,2.0,0.0);
			break;
		case KeyEvent.VK_DOWN:
			objetos[0].translacaoXYZ(0.0,-2.0,0.0);
			break;

		case KeyEvent.VK_PAGE_UP:
			objetos[0].escalaXYZ(2.0,2.0);
			break;
		case KeyEvent.VK_PAGE_DOWN:
			objetos[0].escalaXYZ(0.5,0.5);
			break;

		case KeyEvent.VK_HOME:
//			objetos[0].RoracaoZ();
			break;

		case KeyEvent.VK_1:
			objetos[0].escalaXYZPtoFixo(0.5, new Ponto4D(-15.0,-15.0,0.0,0.0));
			break;
			
		case KeyEvent.VK_2:
			objetos[0].escalaXYZPtoFixo(2.0, new Ponto4D(-15.0,-15.0,0.0,0.0));
			break;
			
			case KeyEvent.VK_3:
				objetos[0].rotacaoZPtoFixo(10.0, new Ponto4D(-15.0,-15.0,0.0,0.0));
				break;
		}

		glDrawable.display();
	}

	// metodo definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	    gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
		// System.out.println(" --- reshape ---");
	}

	// metodo definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibicao associado foi
	// alterado.
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		// System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		// System.out.println(" --- keyTyped ---");
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		Ponto4D p = new Ponto4D(e.getX(), e.getY(), 0.0, 1.0);
		this.caneta.atualizaUltimoVertice(p);
		glDrawable.display();
	}

	public void mouseClicked(MouseEvent arg0) {
		
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		Ponto4D p = new Ponto4D(e.getX(), e.getY(), 0.0, 1.0);
		
		this.mundo.getListaObjetoGrafico().add(this.caneta.novoPonto(p));
		glDrawable.display();
		
		
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
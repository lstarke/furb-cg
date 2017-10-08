package br.com.furb.cg.unidade3.controller;

import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import br.com.furb.cg.unidade3.model.*;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	/// Indica o status se esta desenhando ou selecionando
	private boolean desenhando;
	private boolean selecionando;
	
	/// Mundo da cena
	private Mundo mundo;
	
	/// Desenha demais objetos graficos
	private Caneta caneta;
	
	/// Objeto graficos selecionado
	private ObjetoGrafico objetoSelecionado;
	
	// Ponto selecionado
	private Ponto4D pSelecionado;
	
	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		mundo = new Mundo();
		caneta = new Caneta();
		//caneta.setMundo(mundo);		
		
		desenhando = false;
		selecionando = false;
		objetoSelecionado = null;
		pSelecionado = null;
	}

	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLineWidth(1.0f);
		gl.glPointSize(1.0f);
		
		mundo.posicionaCamera(gl, glu);
		mundo.SRU(gl, glu);
		mundo.desenharObjetos(gl, glu);
		gl.glFlush();
	}
	
	public void keyPressed(KeyEvent e) {
		
		if (e.isControlDown()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_D:
				this.desenhando = true;
				this.caneta.setMundo(mundo);
				System.out.println("Pronto para desenhar.");
				break;
			case KeyEvent.VK_S:
				this.selecionando = true;
				this.desenhando = false;
				break;
			default:
				break;
			}			
		} else {
			
			switch (e.getKeyCode()) {	

			case KeyEvent.VK_ESCAPE:
				this.desenhando = false;
				this.caneta.finalizar(false);
				break;
			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_1:
				//	objetos[0].escalaXYZPtoFixo(0.5, new Ponto4D(-15.0,-15.0,0.0,0.0));
				break;
			
			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_2:
				//	objetos[0].escalaXYZPtoFixo(2.0, new Ponto4D(-15.0,-15.0,0.0,0.0));
				break;
			
			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_3:
				//	objetos[0].rotacaoZPtoFixo(10.0, new Ponto4D(-15.0,-15.0,0.0,0.0));
				break;
				
			// Camera Pan Baixo 
			// (deslocar para baixo)
			case KeyEvent.VK_B:
				mundo.getCamera().panBaixo();
				break;
				
			// Camera Pan Cima
			// (deslocar para cima)
			case KeyEvent.VK_C:
				mundo.getCamera().panCima();
				break;
				
			// Camera Pan Direita
			// (deslocar para direita)
			case KeyEvent.VK_D:
				mundo.getCamera().panDireita();
				break;
				
			// Camera Pan Esquerda
			// (deslocar para esquerda)
			case KeyEvent.VK_E:
				mundo.getCamera().panEsquerda();
				break;
				
			// Camera Zoom In
			case KeyEvent.VK_I:
				mundo.getCamera().zoomIn();
				break;
			
		    // No exemplo do professor apresenta a matriz transformada no console
			case KeyEvent.VK_M:
				//	objetos[0].exibeMatriz();
				break;
				
			// Camera Zoom Out
			case KeyEvent.VK_O:
				mundo.getCamera().zoomOut();
				break;
			
			// No exemplo do professor apresenta os vertices no console
			case KeyEvent.VK_P:
				//  objetos[0].exibeVertices();
				break;

			// No exemplo do professor atribuir a matriz identidade (reinicia a matriz)
			case KeyEvent.VK_R:
				//	objetos[0].atribuirIdentidade();
				break;

			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_RIGHT:
				//	objetos[0].translacaoXYZ(2.0,0.0,0.0);
				break;

			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_LEFT:
				//	objetos[0].translacaoXYZ(-2.0,0.0,0.0);
				break;

			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_UP:
				//	objetos[0].translacaoXYZ(0.0,2.0,0.0);
				break;

			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_DOWN:
				//	objetos[0].translacaoXYZ(0.0,-2.0,0.0);
				break;
				
			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_PAGE_UP:
				//	objetos[0].escalaXYZ(2.0,2.0);
				break;
			
			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_PAGE_DOWN:
				//	objetos[0].escalaXYZ(0.5,0.5);
				break;
	
			// Ver o que o exemplo do professor faz
			case KeyEvent.VK_HOME:
				// objetos[0].RoracaoZ();
				break;
			}
			
		}
		glDrawable.display();
	}

	// metodo definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// System.out.println(" --- reshape ---");
	    gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
	}

	// metodo definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibicao associado foi
	// alterado.
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}

	public void mouseDragged(MouseEvent e) {
		if (selecionando) {
			if (pSelecionado != null) {
				Ponto4D p = this.getPontoDeEventoMouse(e);
				this.pSelecionado.atribuirX(p.obterX());
				this.pSelecionado.atribuirY(p.obterY());
				glDrawable.display();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		
		if (desenhando) {
			Ponto4D novoPonto = this.getPontoDeEventoMouse(e);
			this.caneta.atualizarUltimoVertice(novoPonto);
			glDrawable.display();
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		System.out.println(" --- mouseClicked ---");
	}

	public void mouseEntered(MouseEvent arg0) {
		System.out.println(" --- mouseEntered ---");
	}

	public void mouseExited(MouseEvent arg0) {
		System.out.println(" --- mouseExited ---");
	}

	public void mousePressed(MouseEvent e) {
		Ponto4D p = this.getPontoDeEventoMouse(e);
		if (desenhando) {			
			this.caneta.inserirNovoPonto(p);
		} else {
			if (selecionando) {
				pSelecionado = this.mundo.selecionaPonto(p);				
			}
		}
		glDrawable.display();
	}

	public void mouseReleased(MouseEvent arg0) {
		System.out.println(" --- mouseReleased ---");
	}
	
	private Ponto4D getPontoDeEventoMouse(MouseEvent e)
	{		
		double xTela = e.getComponent().getWidth();
		double yTela = e.getComponent().getHeight();
		
		return mundo.getCamera().convertePontoTela(e.getX(), e.getY(), xTela, yTela);		
	}
}
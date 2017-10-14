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
	private Ponto4D ptoSelecionado;
	
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
		ptoSelecionado = null;
	}

	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLineWidth(1.0f);
		gl.glPointSize(1.0f);
		
		mundo.posicionarCamera(gl, glu);
		mundo.SRU(gl, glu);
		mundo.desenharObjetos(gl, glu);
		gl.glFlush();
	}
	
	public void keyPressed(KeyEvent e) {
		
		if (e.isControlDown()) {
			switch (e.getKeyCode()) {
			// Desenhar
			case KeyEvent.VK_D:
				this.desenhando = true;
				this.caneta.setObjetosGraficos(mundo.getObjetosGraficos());
				System.out.println("Pronto para desenhar.");
				break;
				
			// Selecionar
			case KeyEvent.VK_S:
				this.selecionando = true;
				this.desenhando = false;
				break;
			}			
		} else {
			
			switch (e.getKeyCode()) {
				// Concluir o desenho do poligono (objeto grafico)
				case KeyEvent.VK_ESCAPE:
					this.desenhando = false;
					this.caneta.finalizar(false);
					this.ptoSelecionado = null;
					this.objetoSelecionado = null;
					break;

				// Excluir o ponto selecionado
				case KeyEvent.VK_DELETE:
					if (this.ptoSelecionado != null && this.selecionando)
						this.objetoSelecionado.removerVerticeSelecionado();
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
				
			    // Apresentar a matriz transformada do objeto selecionado no console
				case KeyEvent.VK_M:
					if (objetoSelecionado == null)
						this.objetoSelecionado = mundo.getObjetosGraficos().getUltimo();
					
					this.objetoSelecionado.exibirMatriz();
					break;
					
				// Camera Zoom Out
				case KeyEvent.VK_O:
					this.mundo.getCamera().zoomOut();
					break;
				
				// Apresentar os vertices(pontos) do objeto selecionado no console
				case KeyEvent.VK_P:
					if (objetoSelecionado == null)
						this.objetoSelecionado = mundo.getObjetosGraficos().getUltimo();
					
					this.objetoSelecionado.exibirVertices();
					break;
	
				// No exemplo do professor atribuir a matriz identidade (reinicia a matriz)
				case KeyEvent.VK_R:
					//	objetos[0].atribuirIdentidade();
					break;
	
				// Mover o objeto grafico selecionado para direita
				case KeyEvent.VK_RIGHT:
					if (objetoSelecionado == null)
						this.objetoSelecionado = mundo.getObjetosGraficos().getUltimo();
					
					this.objetoSelecionado.mover(2f, 0f);
					break;
	
				// Mover o objeto grafico selecionado para esquerda
				case KeyEvent.VK_LEFT:
					if (objetoSelecionado == null)
						this.objetoSelecionado = mundo.getObjetosGraficos().getUltimo();
					
					this.objetoSelecionado.mover(-2f, 0f);
					break;
	
				// Mover o objeto grafico selecionado para cima
				case KeyEvent.VK_UP:
					if (objetoSelecionado == null)
						this.objetoSelecionado = mundo.getObjetosGraficos().getUltimo();
					
					this.objetoSelecionado.mover(0f, 2f);
					break;
	
				// Mover o objeto grafico selecionado para baixo
				case KeyEvent.VK_DOWN:
					if (objetoSelecionado == null)
						this.objetoSelecionado = mundo.getObjetosGraficos().getUltimo();
					
					this.objetoSelecionado.mover(0f, -2f);
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
			if (ptoSelecionado != null) {
				Ponto4D p = this.getPontoCliqueMouse(e);
				this.ptoSelecionado.atribuirX(p.obterX());
				this.ptoSelecionado.atribuirY(p.obterY());
				glDrawable.display();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		
		if (desenhando) {
			Ponto4D novoPonto = this.getPontoCliqueMouse(e);
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
		Ponto4D p = this.getPontoCliqueMouse(e);
		if (desenhando)			
			this.caneta.inserirNovoPonto(p);
		else
			if (selecionando) {
				ptoSelecionado = this.mundo.selecionarPonto(p);
				objetoSelecionado = this.mundo.selecionarObjeto(p);
				this.caneta.setObjeto(objetoSelecionado);
				System.out.println(p.toString());
			}

		glDrawable.display();
	}

	public void mouseReleased(MouseEvent arg0) {
		System.out.println(" --- mouseReleased ---");
		if (this.objetoSelecionado != null) {
			this.objetoSelecionado.setPontosBbox();
			glDrawable.display();
		}
		
	}
	
	/**
	 * Informar em qual ponto da tela (Frame) se refe o clique do mouse
	 */
	private Ponto4D getPontoCliqueMouse(MouseEvent e)
	{	
		/*
		 Congela local (X e Y) absoluto (tela real) e do frame
		 para converter o local real de clique do mouse para a tela do programa.
		 
		 Sem congelar nao funciona
		 
		 Onde:
		   - e.getX() = X absoluto (X real) do clique do mouse;
		   - e.getY() = Y absoluto (Y real) do clique do mouse;
		   - e.getComponent().getWidth() = X referente ao componente clicado (neste caso, frame);
		   - e.getComponent().getHeight() = Y referente ao componente clicado (neste caso, frame);
		*/
		return converterPontoCliqueMouse(e.getX(), e.getY(), e.getComponent().getWidth(), e.getComponent().getHeight());
	}
	
	/**
	 * Converter o x, y do clique do mouse (absoluto) para ponto na tela do programa
	 */
	public Ponto4D converterPontoCliqueMouse(double xAbsoluto, double yAbsoluto, double xFrame, double yFrame)
	{
        // Encontrar a diferenca entre ortho e frame e transforma em indice de conversao de escala
		double indiceConvX = mundo.getCamera().getTamX() / xFrame;
		double indiceConvY = mundo.getCamera().getTamY() / yFrame;

		// Encontrar em que ponto do frame se refere o ponto real (absolunto) do clique do mouse  
		double xNovoPto = ((xAbsoluto * indiceConvX) + mundo.getCamera().getXmin());
		double yNovoPto = ((yAbsoluto * indiceConvY) + mundo.getCamera().getYmin()) * -1;
		
		return new Ponto4D(xNovoPto, yNovoPto, 0.0, 1.0);
	}
}
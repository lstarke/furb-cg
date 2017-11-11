package br.furb.cg.unidade4.controller;

import java.awt.Color;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.swing.JColorChooser;

import br.furb.cg.unidade4.model.*;
import br.furb.cg.unidade4.model.auxiliar.AlgoritmoDeSelecao;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	/// Mundo da cena
	private Mundo mundo;
	
	/// Desenha demais objetos graficos
	private Caneta caneta;
	
	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		mundo = new Mundo();
		caneta = new Caneta();
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
					mundo.setDesenhando(true);
					this.caneta.setObjetosGraficos(mundo.getObjetosGraficos());
					System.out.println("Pronto para desenhar.");
					break;
					
				// Selecionar
				case KeyEvent.VK_S:
					mundo.setDesenhando(false);
					System.out.println("Pronto para selecionar.");
					break;	
			}
		} else {
			
			switch (e.getKeyCode()) {				
				// Concluir o desenho do poligono (objeto grafico)
				case KeyEvent.VK_ESCAPE:
					if (this.mundo.isDesenhando()) {
						mundo.setDesenhando(false);
						this.caneta.finalizar(false);
					}
					break;

				// Excluir o ponto selecionado
				case KeyEvent.VK_DELETE:
					if (mundo.isSelecionando() && mundo.hasVerticeSelecionado())
						mundo.removerVerticeObjeto();
					break;
					
				// Camera Pan Baixo 
				// (deslocar camera para baixo)
				case KeyEvent.VK_B:
					if (mundo.isSelecionando())
						mundo.getCamera().panBaixo();
					break;
					
				// Camera Pan Cima
				// (deslocar camera para cima)
				case KeyEvent.VK_C:
					if (mundo.isSelecionando())
						mundo.getCamera().panCima();
					break;
					
				// Camera Pan Direita
				// (deslocar camera para direita)
				case KeyEvent.VK_D:
					if (mundo.isSelecionando())
						mundo.getCamera().panDireita();
					break;
					
				// Camera Pan Esquerda
				// (deslocar camera para esquerda)
				case KeyEvent.VK_E:
					if (mundo.isSelecionando())
						mundo.getCamera().panEsquerda();
					break;
					
				// Camera Zoom In
				case KeyEvent.VK_I:
					if (mundo.isSelecionando())
						mundo.getCamera().zoomIn();
					break;
					
				// Camera Zoom Out
				case KeyEvent.VK_O:
					if (mundo.isSelecionando())
						this.mundo.getCamera().zoomOut();
					break;
	
				// Rotacionar objeto grafico selecionado
				case KeyEvent.VK_R:
					mundo.rotacionarObjeto();
					break;
	
				// Mover o objeto grafico selecionado para direita
				case KeyEvent.VK_RIGHT:
					mundo.moverObjeto(2f, 0f);
					break;
	
				// Mover o objeto grafico selecionado para esquerda
				case KeyEvent.VK_LEFT:
					mundo.moverObjeto(-2f, 0f);
					break;
	
				// Mover o objeto grafico selecionado para cima
				case KeyEvent.VK_UP:
					mundo.moverObjeto(0f, 2f);
					break;
	
				// Mover o objeto grafico selecionado para baixo
				case KeyEvent.VK_DOWN:
					mundo.moverObjeto(0f, -2f);
					break;
					
				// Ampliar escala do objeto selecionado
				case KeyEvent.VK_PAGE_UP:
					mundo.escalonarObjeto(1.5, false);
					break;
				
				// Reduzir escala do objeto selecionado
				case KeyEvent.VK_PAGE_DOWN:
					mundo.escalonarObjeto(0.5, false);
					break;
					
				// Aumetar objeto selecionado
				case KeyEvent.VK_ADD:
				case KeyEvent.VK_EQUALS:
					mundo.escalonarObjeto(2f, true);
					break;
					
				// Reduzir objeto selecionado
				case KeyEvent.VK_MINUS:
					mundo.escalonarObjeto(0.5, true);
					break;
					
				// Duplicar objeto selecionado
				case KeyEvent.VK_F1:
					if (mundo.isSelecionando() && mundo.hasObjetoSelecionado()) {
						this.caneta.setObjeto(mundo.getObjetoSelecionado());
						this.caneta.setObjetosGraficos(mundo.getObjetosGraficos());
						this.caneta.duplicarObjeto();
					}
					break;
					
				// Alterar cor do poligono selecionado
				case KeyEvent.VK_F2:
					if (mundo.isSelecionando()) {
						Color corEscolhida = JColorChooser.showDialog(null, "Altere a cor do poligono selecionado", Color.BLACK);
						mundo.getObjetoSelecionado().setCor(corEscolhida);
					}
					break;
					
				// Apresentar a matriz transformada do objeto selecionado no console 
				case KeyEvent.VK_F10:
					mundo.exibirMatrizObjeto();
					break;
					
				// Apresentar os vertices(pontos) do objeto selecionado no console
				case KeyEvent.VK_F11:
					mundo.exibirVerticesObjeto();
					break;
					
				// Apresentar os pontos (vertices e centro) da bound box do objeto selecionado
				case KeyEvent.VK_F12:
					mundo.exibirBboxObjeto();
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
//		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
//		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
//		System.out.println(" --- keyTyped ---");
	}

	public void mouseDragged(MouseEvent e) {
		if (mundo.isSelecionando() && mundo.hasVerticeSelecionado()) {
			Ponto4D p = this.getPontoCliqueMouse(e);
			mundo.getVerticeSelecionado().atribuirX(p.obterX());
			mundo.getVerticeSelecionado().atribuirY(p.obterY());
			glDrawable.display();
		}
	}

	public void mouseMoved(MouseEvent e) {
//		System.out.println(" --- mouseMoved ---");
		if (this.mundo != null && mundo.isDesenhando()) {
			Ponto4D novoPonto = this.getPontoCliqueMouse(e);
			this.caneta.atualizarUltimoVertice(novoPonto);
			glDrawable.display();
		}
	}

	public void mouseClicked(MouseEvent e) {
		//System.out.println(" --- mouseClicked ---");
		Ponto4D p = this.getPontoCliqueMouse(e);
		if (this.mundo != null && this.mundo.isSelecionando()) {
			this.mundo.selecionarVertice(p);
			this.mundo.selecionarObjeto(p);			
		}
		
	}

	public void mouseEntered(MouseEvent arg0) {
//		System.out.println(" --- mouseEntered ---");
	}

	public void mouseExited(MouseEvent arg0) {
//		System.out.println(" --- mouseExited ---");
	}

	public void mousePressed(MouseEvent e) {
		Ponto4D p = this.getPontoCliqueMouse(e);
		
		if (mundo.isDesenhando())
			this.caneta.inserirNovoPonto(p);
		else {
			boolean estaDentro = false;
			this.mundo.selecionarVertice(p);
			this.mundo.selecionarObjeto(p);
			
			if (mundo.hasObjetoSelecionado())
				estaDentro = AlgoritmoDeSelecao.pontoEmPoligono(mundo.getObjetoSelecionado(), p);
			
			if (estaDentro)
				this.caneta.setObjeto(mundo.getObjetoSelecionado());
			else
				this.mundo.selecionarObjeto(null);
			
			System.out.println("Clicou dentro do poligono? " + estaDentro);
		}

		glDrawable.display();
	}

	public void mouseReleased(MouseEvent arg0) {
//		System.out.println(" --- mouseReleased ---");
		if (mundo.hasObjetoSelecionado()) {
			mundo.calcularBoundBox();
			glDrawable.display();
		}
	}
	
	/**
	 * Informar em qual ponto da tela (Frame) se refere o clique do mouse
	 * @param MouseEvent
	 * @return Ponto4D
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
	 * @param double xAbsoluto
	 * @param double yAbsoluto
	 * @param double xFrame
	 * @param double yFrame
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
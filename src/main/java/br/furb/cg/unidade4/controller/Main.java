package br.furb.cg.unidade4.controller;

import java.awt.Color;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.GLUT;
import javax.swing.JColorChooser;
import br.furb.cg.unidade4.model.*;
import br.furb.cg.unidade4.model.auxiliar.AlgoritmoDeSelecao;
import br.furb.cg.unidade4.model.auxiliar.ListaObjetosGraficos;
import br.furb.cg.unidade4.model.d3.Caneta3D;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;
	
	// Temporario para aparecer 3D
	private final float posLight[] = { 10f, 10f, 10.0f, 0.0f };
	private boolean chaveCena2D;
	
	/// Mundo da cena
	private Mundo mundo;
	
	/// Desenha demais objetos graficos
	private Caneta caneta;
	
	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));
		
		// Diretivas gerais
	    gl.glEnable(GL.GL_CULL_FACE); // Diretiva que indica para desenhar apenas uma face do cubo
	    gl.glEnable(GL.GL_DEPTH_TEST);
		
		mundo = new Mundo();
		caneta = new Caneta();
		chaveCena2D = false;
	}
	
	// metodo definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// System.out.println(" --- reshape ---");
		gl.glViewport(0, 0, width, height);
	}

	public void display(GLAutoDrawable arg0) {
		if (chaveCena2D)
			cena2D();
		else
			cena3D();
		
		gl.glFlush();
	}
	
	public void cena2D() {
		gl.glClearColor(1f, 1f, 1f, 1f);
		
		//gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLineWidth(1.0f);
		gl.glPointSize(1.0f);
		
		mundo.posicionarCamera(gl, glu);
		mundo.SRU(gl, glu);
		mundo.desenharObjetos(gl, glu);
	}
	
	public void cena3D() {
		gl.glClearColor(0f, 0f, 0f, 0f);
	    
	    // Diretiva de limpeza de tela 3D
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		// Diretivas 3D
		gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
		//gl.glViewport(0, 0, width, height);
	    glu.gluPerspective(60, 1, 0.1, 400);    

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(10f, 10f, 10f, 
					  0f, 0f, 0f, 
					  0f, 1f, 0f);
		
		// Iluminacao ambiente
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
	    gl.glEnable(GL.GL_LIGHT0);

	    // Desenhar as linhas
	    mundo.Sru3D(gl);
	
	    // Desenhar um cubo de forma fixa
	    Caneta3D.desenhaCuboFaces(gl);
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

				case KeyEvent.VK_L:
					System.out.println("Qtde objetos no mundo: " + this.mundo.getObjetosGraficos().size());
					for (int i = 0; i < this.mundo.getObjetosGraficos().size(); i++) {
						System.out.println("Objeto " + i + " tem " + this.mundo.getObjetosGraficos().get(i).getVertices().size() + " vertices." );						
					}
					break;

				case KeyEvent.VK_C:
					int qtdeObjetosMundo = this.mundo.getObjetosGraficos().size();					
					if (qtdeObjetosMundo == 2 ) {
						if(this.verificaQtdeVerticesIguais(this.mundo.getObjetosGraficos())) {							
							//--percorrendo lista de vertices de cada objeto							
							for (int j = 0; j < this.mundo.getObjetosGraficos().get(0).getVertices().size(); j++) {
								ObjetoGrafico novoObjeto = new ObjetoGrafico(GL.GL_LINE_STRIP);
								Ponto4D verticeA = this.mundo.getObjetosGraficos().get(0).getVertices().get(j);									
								Ponto4D verticeB = this.mundo.getObjetosGraficos().get(1).getVertices().get(j);
								novoObjeto.addVertice(verticeA.obterX(), verticeA.obterY());
								this.mundo.getObjetosGraficos().add(novoObjeto);
								novoObjeto.addVertice(verticeB.obterX(), verticeB.obterY());
								this.mundo.getObjetosGraficos().add(novoObjeto);
							}
						}
					}
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
					
				case KeyEvent.VK_0:
					mundo.setDesenhando(true);
					this.caneta.setObjetosGraficos(mundo.getObjetosGraficos());
					
					this.caneta.desenharQuadrado2d();
					
					mundo.setDesenhando(false);
					this.caneta.finalizar(false);
					break;
					
				// Troca da Cena 2D para 3D
				case KeyEvent.VK_3:
					chaveCena2D = !chaveCena2D;
					break;
			}
		}

		glDrawable.display();
	}

	private boolean verificaQtdeVerticesIguais(ListaObjetosGraficos objetosGraficos) {
		ObjetoGrafico objeto1 = objetosGraficos.get(0);
		ObjetoGrafico objeto2 = objetosGraficos.get(0);
		if (objeto1.getVertices().size() == objeto2.getVertices().size()) {
			return true;
		}
		return false;
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
		if (chaveCena2D) {
			if (mundo.isSelecionando() && mundo.hasVerticeSelecionado()) {
				Ponto4D p = this.getPontoCliqueMouse(e);
				mundo.getVerticeSelecionado().atribuirX(p.obterX());
				mundo.getVerticeSelecionado().atribuirY(p.obterY());
				glDrawable.display();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
//		System.out.println(" --- mouseMoved ---");
		if (chaveCena2D) {
			if (this.mundo != null && mundo.isDesenhando()) {
				Ponto4D novoPonto = this.getPontoCliqueMouse(e);
				this.caneta.atualizarUltimoVertice(novoPonto);
				glDrawable.display();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		//System.out.println(" --- mouseClicked ---");
		if (chaveCena2D) {
			Ponto4D p = this.getPontoCliqueMouse(e);
			if (this.mundo != null && this.mundo.isSelecionando()) {
				this.mundo.selecionarVertice(p);
				this.mundo.selecionarObjeto(p);			
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
//		System.out.println(" --- mouseEntered ---");
	}

	public void mouseExited(MouseEvent arg0) {
//		System.out.println(" --- mouseExited ---");
	}

	public void mousePressed(MouseEvent e) {
		//System.out.println(" --- mousePressed ---");
		if (chaveCena2D) {
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
		}

		glDrawable.display();
	}

	public void mouseReleased(MouseEvent arg0) {
//		System.out.println(" --- mouseReleased ---");
		if (chaveCena2D) {
			if (mundo.hasObjetoSelecionado()) {
				mundo.calcularBoundBox();
				glDrawable.display();
			}
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
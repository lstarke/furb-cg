package br.furb.cg.unidade4.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.JColorChooser;
import br.furb.cg.unidade4.model.Caneta;
import br.furb.cg.unidade4.model.Mundo;
import br.furb.cg.unidade4.model.ObjetoGrafico;
import br.furb.cg.unidade4.model.Ponto4D;
import br.furb.cg.unidade4.model.auxiliar.AlgoritmoDeSelecao;
import br.furb.cg.unidade4.model.auxiliar.ListaObjetosGraficos;

public class Controller2D {
	// OpenGl drawable (vindo do main)
	private GLAutoDrawable glDrawable;
	
	/// Mundo da cena (vindo do main)
	private Mundo mundo;
	
	/// Desenha demais objetos graficos (vindo do main)
	private Caneta caneta;
	
	public Controller2D(GLAutoDrawable drawable, Mundo mundo, Caneta caneta) {
		this.glDrawable = drawable;
		this.mundo = mundo;
		this.caneta = caneta;
	}
	
	public void keyPressed(KeyEvent e) {
		
		if (e.isControlDown()) {
			switch (e.getKeyCode()) {
				// Desenhar
				case KeyEvent.VK_D:
					mundo.setDesenhando(true);
					caneta.setObjetosGraficos(mundo.getObjetosGraficos());
					System.out.println("Pronto para desenhar.");
					break;
					
				// Selecionar
				case KeyEvent.VK_S:
					mundo.setDesenhando(false);
					System.out.println("Pronto para selecionar.");
					break;

				case KeyEvent.VK_L:
					System.out.println("Qtde objetos no mundo: " + mundo.getObjetosGraficos().size());
					for (int i = 0; i < mundo.getObjetosGraficos().size(); i++) {
						System.out.println("Objeto " + i + " tem " + mundo.getObjetosGraficos().get(i).getVertices().size() + " vertices." );						
					}
					break;

				case KeyEvent.VK_C:
					int qtdeObjetosMundo = mundo.getObjetosGraficos().size();					
					if (qtdeObjetosMundo == 2 ) {
						if(this.verificaQtdeVerticesIguais(mundo.getObjetosGraficos())) {							
							//--percorrendo lista de vertices de cada objeto							
							for (int j = 0; j < mundo.getObjetosGraficos().get(0).getVertices().size(); j++) {
								ObjetoGrafico novoObjeto = new ObjetoGrafico(GL.GL_LINE_STRIP);
								Ponto4D verticeA = mundo.getObjetosGraficos().get(0).getVertices().get(j);									
								Ponto4D verticeB = mundo.getObjetosGraficos().get(1).getVertices().get(j);
								novoObjeto.addVertice(verticeA.obterX(), verticeA.obterY());
								mundo.getObjetosGraficos().add(novoObjeto);
								novoObjeto.addVertice(verticeB.obterX(), verticeB.obterY());
								mundo.getObjetosGraficos().add(novoObjeto);
							}
						}
					}
					break;
			}
		} else {

			switch (e.getKeyCode()) {				
				// Concluir o desenho do poligono (objeto grafico)
				case KeyEvent.VK_ESCAPE:
					if (mundo.isDesenhando()) {
						mundo.setDesenhando(false);
						caneta.finalizar(false);
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
						mundo.getCamera().zoomOut();
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
						caneta.setObjeto(mundo.getObjetoSelecionado());
						caneta.setObjetosGraficos(mundo.getObjetosGraficos());
						caneta.duplicarObjeto();
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
					caneta.setObjetosGraficos(mundo.getObjetosGraficos());
					
					caneta.desenharQuadrado2d();
					
					mundo.setDesenhando(false);
					caneta.finalizar(false);
					break;
					
				// Troca da Cena 2D para 3D
				case KeyEvent.VK_3:
					mundo.set3D();
					break;
			}
		}

		//glDrawable.display();
	}

	public void keyReleased(KeyEvent arg0) {
		//...
	}

	public void keyTyped(KeyEvent arg0) {
		//...
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
		if (mundo != null && mundo.isDesenhando()) {
			Ponto4D novoPonto = this.getPontoCliqueMouse(e);
			caneta.atualizarUltimoVertice(novoPonto);
			glDrawable.display();
		}
	}

	public void mouseClicked(MouseEvent e) {
		Ponto4D p = this.getPontoCliqueMouse(e);
		if (mundo != null && mundo.isSelecionando()) {
			mundo.selecionarVertice(p);
			mundo.selecionarObjeto(p);			
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		//...
	}

	public void mouseExited(MouseEvent arg0) {
		//...
	}

	public void mousePressed(MouseEvent e) {
		Ponto4D p = this.getPontoCliqueMouse(e);
		
		if (mundo.isDesenhando())
			caneta.inserirNovoPonto(p);
		else {
			boolean estaDentro = false;
			mundo.selecionarVertice(p);
			mundo.selecionarObjeto(p);
			
			if (mundo.hasObjetoSelecionado())
				estaDentro = AlgoritmoDeSelecao.pontoEmPoligono(mundo.getObjetoSelecionado(), p);
			
			if (estaDentro)
				caneta.setObjeto(mundo.getObjetoSelecionado());
			else
				mundo.selecionarObjeto(null);
			
			System.out.println("Clicou dentro do poligono? " + estaDentro);
		}

//		glDrawable.display();
	}

	public void mouseReleased(MouseEvent arg0) {
		if (mundo.hasObjetoSelecionado()) {
			mundo.calcularBoundBox();
			glDrawable.display();
		}
	}
	
	private boolean verificaQtdeVerticesIguais(ListaObjetosGraficos objetosGraficos) {
		ObjetoGrafico objeto1 = objetosGraficos.get(0);
		ObjetoGrafico objeto2 = objetosGraficos.get(0);
		
		return objeto1.getVertices().size() == objeto2.getVertices().size();
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
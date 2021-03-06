package br.furb.cg.unidade4.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.media.opengl.GL;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import br.furb.cg.unidade4.model.Mundo;
import br.furb.cg.unidade4.model.d3.Camera3D;
import br.furb.cg.unidade4.model.d3.Caneta3D;
import br.furb.cg.unidade4.model.d3.Iluminacao;
import br.furb.cg.unidade4.model.d3.Textura;
import br.furb.cg.unidade4.util.Arquivo;

public class Controller3D {
	private OpenGL o;
	
	// Meus objetos
	private Camera3D camera;
	//private Textura textura;
	private Iluminacao luz;
	private Mundo mundo;
	
	// Meus parametros
	private boolean chaveCubo = true;
    private boolean chaveEixos = true;
    private boolean chaveLuz = true;
    private boolean usaLuz   = true;
    private boolean chaveCam = false;
    private boolean primeiraChamada = true;
	
	public Controller3D(OpenGL o, Mundo mundo) {
		this.o = o;
		
		this.mundo = mundo;
		this.luz = new Iluminacao(o.gl);
		this.camera = new Camera3D(o.gl, o.glu);
		//this.textura = new Textura(o.gl);
	}
	
	public void reshape3D() {
		float far = (mundo.getTamX() + mundo.getTamY() + mundo.getTamZ()) * 2;
		camera.especificar(60, 0.1, far);
		
		if (mundo.hasObjetoSelecionado()) {
			luz.setPos((float) mundo.getObjetoSelecionado().getBbox().getCentro().obterX(), 
					   (float) mundo.getTamY() * 3f, 
					   (float) mundo.getObjetoSelecionado().getBbox().getCentro().obterZ());

			camera.olharPara(350f, 350f, 350f,
					         (float) mundo.getObjetoSelecionado().getBbox().getCentro().obterX(),
					         (float) mundo.getObjetoSelecionado().getBbox().getCentro().obterY(),
					         (float) mundo.getObjetoSelecionado().getBbox().getCentro().obterZ());

			mundo.getObjetoSelecionado().setCor(Color.RED);
		} else
			camera.olharPara(mundo.getTamX(), mundo.getTamY(), mundo.getTamZ(), 0, 0, 0);
	}
	
	public void cena3D() {
		if (primeiraChamada) {
			reshape3D();
			primeiraChamada = false;
		}
		
		o.gl.glClearColor(0f, 0f, 0f, 0f);
	    
	    // Diretiva de limpeza de tela 3D
		o.gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		// Diretivas gerais
		//o.gl.glEnable(GL.GL_CULL_FACE); // Diretiva que indica para desenhar apenas uma face do cubo
		o.gl.glEnable(GL.GL_DEPTH_TEST);
		
		// Camera livre: chaveCam = true;
		if (chaveCam)
			camera.posicionar();
		
		// Acender/Apagar luz
		luz.setEnable(chaveLuz);

		// Visualizar/Esconder eixos
		if (chaveEixos)
			mundo.Sru3d(o.gl);

		// Visualizar/Esconder cubo
		if (chaveCubo) {
			mundo.desenharObjetos(o.gl, o.glu);
		}
	}
	
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
			// Visualizar/Esconder eixos
			case KeyEvent.VK_F1:
				chaveEixos = !chaveEixos;
				break;

			// Visualizar/Esconder cubo
			case KeyEvent.VK_F2:
				chaveCubo = !chaveCubo;
				break;

			// Acender/Apagar luz
			case KeyEvent.VK_F3:
				if (usaLuz) {
					chaveLuz = !chaveLuz;
					System.out.println("Luz acesa: " + chaveLuz);
				}
				break;

			// Travar camera
			case KeyEvent.VK_F4:
				chaveCam = !chaveCam;
				System.out.println("Camera 3d ativa? " + chaveCam);
				break;

			// Trocar a cor do cubo
			case KeyEvent.VK_F5:
				if (mundo.isSelecionando()) {
					Color corEscolhida = JColorChooser.showDialog(null, "Altere a cor do poligono selecionado", Color.RED);
					mundo.getObjetoSelecionado().setCor(corEscolhida);
				}
				break;

			// Escolher e aplicar textura 2D
//			case KeyEvent.VK_F6:
//				JFileChooser chooser = new JFileChooser();
//			    chooser.setMultiSelectionEnabled(false);
//			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//			    	 cuboSimples = false;
//			    	 System.out.println(chooser.getSelectedFile().getAbsolutePath());
//			    	 this.textura.carregarImagem(0, chooser.getSelectedFile().getAbsolutePath());
//			    } else
//			    	cuboSimples = true;
//
//				break;

			// Gravar em arquivo texto
			case KeyEvent.VK_F7:
				if (this.mundo.getObjetoSelecionado() != null && ! this.mundo.is2D()) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save File");
					
					if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						camera.gerarArquivo3d();
						StringBuilder sbArquivo = new StringBuilder("===== Camera - Inicio =====\n");
						sbArquivo.append(camera.getStrArquivo());
						
						mundo.getObjetoSelecionado().gerarArquivo3d();
						sbArquivo.append("\n===== Objeto - Inicio =====\n");
						sbArquivo.append(mundo.getObjetoSelecionado().getStrArquivo());

						Arquivo.gravar(jfc.getSelectedFile().getAbsolutePath(), sbArquivo.toString());
					}
				}
				break;

			// Apresentar os vertices(pontos) do objeto selecionado no console
			case KeyEvent.VK_F8:
				mundo.exibirVerticesObjeto();
				break;

			// Apresentar os pontos (vertices e centro) da bound box do objeto selecionado
			case KeyEvent.VK_F9:
				mundo.exibirBboxObjeto();
				break;

			// local da camera
			case KeyEvent.VK_F10:
				System.out.println(camera);
				break;

			case KeyEvent.VK_F11:
				// Faz nada ainda...
				break;

			case KeyEvent.VK_F12:
				if (mundo.hasObjetoSelecionado()) {
					usaLuz = !usaLuz;
					mundo.getObjetoSelecionado().setPercebeLuz(usaLuz);
					System.out.println("Utilizar luz: " + usaLuz);
					
					if (usaLuz)
						System.out.println("Luz acesa: " + chaveLuz);
				}
				break;

			// Zoom in (aproximar camera) ou escalora objeto
			case KeyEvent.VK_EQUALS:
			case KeyEvent.VK_ADD:
				if (chaveCam)
					camera.zoomIn();
				else
					mundo.escalonarObjeto3d(1.5, true);
				break;

			// Zoom out (afastar camera) ou escalora objeto
			case KeyEvent.VK_MINUS:
				if (chaveCam)
					camera.zoomOut();
				else
					mundo.escalonarObjeto3d(0.5, true);
				break;

			// Rotacionar camera para baixo
			// Aparentemente o objeto gira para o lado contrario
			// Transladar objeto pelo eixo Z (aumentar)
			case KeyEvent.VK_DOWN:
				if (chaveCam)
					camera.baixo();
				else
					mundo.moverObjeto3d(0f, 0f, 10f);
				break;

			// Rotacionar camera para esquerda
			// Aparentemente o objeto gira para o lado contrario
			// Transladar objeto pelo eixo X (diminuir)
			case KeyEvent.VK_LEFT:
				if (chaveCam)
					camera.esquerda();
				else
					mundo.moverObjeto3d(-10f, 0f, 0f);
				break;

			// Rotacionar camera para direita
			// Aparentemente o objeto gira para o lado contrario
			// Transladar objeto pelo eixo X (aumentar)
			case KeyEvent.VK_RIGHT:
				if (chaveCam)
					camera.direita();
				else
					mundo.moverObjeto3d(10f, 0f, 0f);
				break;

			// Rotacionar cmaer para cima
			// Aparentemente o objeto gira para o lado contrario
			// Transladar objeto pelo eixo Z (diminuir)
			case KeyEvent.VK_UP:
				if (chaveCam)
					camera.cima();
				else
					mundo.moverObjeto3d(0f, 0f, -10f);
				break;
				
			// Transladar objeto pelo eixo Y (aumentar)
			case KeyEvent.VK_PAGE_UP:
				if (!chaveCam)
					mundo.moverObjeto3d(0f, 10f, 0f);
				break;
				
			// Transladar objeto pelo eixo Y (diminuir)
			case KeyEvent.VK_PAGE_DOWN:
				if (!chaveCam)
					mundo.moverObjeto3d(0f, -10f, 0f);
				break;

			// Rotacao 3d em X
			case KeyEvent.VK_X:
				if (!chaveCam)
					mundo.rotacionarObjeto3dx();
				break;

			// Rotacao 3d em Y
			case KeyEvent.VK_Y:
				if (!chaveCam)
					mundo.rotacionarObjeto3dy();
				break;

			// Rotacao 3d em Z
			case KeyEvent.VK_Z:
				if (!chaveCam)
					mundo.rotacionarObjeto3dz();
				break;
		}
	}
}
package br.com.furb.cg.unidade2.ex7;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ArrayBlockingQueue;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private float ortho2D_minX = -400.0f, ortho2D_maxX =  400.0f, ortho2D_minY = -400.0f, ortho2D_maxY =  400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private int cor = 0;
	private double posXOrigem = 200;
	private double posYOrigem = 200;
	private double posXClicado;	
	private double posYClicado;
	private double posX = 200;
	private double posY = 200;
	private double raioCMenor = 50;
	private double raioCMaior = 150;
	// calcula as coordenadas no bbox e armazena e vetor de 2 posições 0 = X e 1 = Y
	private double[] bbox45  = this.coordenadasBoundBox(45);
	private double[] bbox135 = this.coordenadasBoundBox(135);
	private double[] bbox225 = this.coordenadasBoundBox(225);
	private double[] bbox315 = this.coordenadasBoundBox(315);
	

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
		 //gl.glLineWidth(5.0f);	
		 
		 // seu desenho ...
		 gl.glColor3f(0.0f, 0.0f, 0.0f);
		 gl.glPointSize(2.0f);
		 gl.glBegin(GL.GL_POINTS);
		 gl.glColor3f(0.0f, 0.0f, 1.0f);		 	
		 
			double x = 0;
			double y = 0;	
	
			gl.glVertex2d(this.posX, this.posY);
			
			// circulo menor
		 	for (int i = 0; i < 360; i++) {		 		 
		 		x = this.RetornaX(i, this.raioCMenor, this.posX); 
			 	y = this.RetornaY(i, this.raioCMenor, this.posY);
			 	gl.glVertex2d(x,y);				
			}
		 	
		 	// circulo maior
		 	for (int i = 0; i < 360; i++) {		 		 
		 		x = this.RetornaX(i, this.raioCMaior, 200); 
			 	y = this.RetornaY(i, this.raioCMaior, 200);
			 	gl.glVertex2d(x,y);				
			}		 	
		 	
		 gl.glEnd();
	
		 // cria o bbox
		 gl.glBegin(GL.GL_LINES);
		 
		 	switch (this.cor) {
			case 0: 
				gl.glColor3f(0.0f, 0.0f, 0.0f);
				break;
			case 1: 
				gl.glColor3f(0.0f, 1.0f, 0.0f);
				break;
			case 2:
				gl.glColor3f(1.0f, 0.0f, 0.0f);
				break;
			default:
				break;
			}		 
		 	
			gl.glVertex2d(bbox45[0], bbox45[1]);
			gl.glVertex2d(bbox135[0], bbox135[1]);
			
			gl.glVertex2d(bbox135[0], bbox135[1]);
			gl.glVertex2d(bbox225[0], bbox225[1]);
			
			gl.glVertex2d(bbox225[0], bbox225[1]);
			gl.glVertex2d(bbox315[0], bbox315[1]);
			
			gl.glVertex2d(bbox315[0], bbox315[1]);
			gl.glVertex2d(bbox45[0], bbox45[1]);
		 gl.glEnd();
		 
		 gl.glFlush(); 	 
	}
	
	public double RetornaX(double angulo, double raio, double posX) { 
		return (raio * Math.cos(Math.PI * angulo / 180.0) + posX);
	} 

	public double RetornaY(double angulo, double raio, double posY) { 
		return (raio * Math.sin(Math.PI * angulo / 180.0) + posY); 
	}
	
	public double[] coordenadasBoundBox(double angulo) {		
		double[] coordenadas = {this.RetornaX(angulo, 150, 200), 
				                this.RetornaY(angulo, 150, 200)};
		return coordenadas;
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

	public void mouseDragged(MouseEvent e) {
		//System.out.println("mouseDragged X=" + this.posX + " Y=" + this.posY);		
		this.posX += e.getX() - this.posXClicado; 
		this.posY -= e.getY() - this.posYClicado;  
		this.posXClicado = e.getX();
		this.posYClicado = e.getY();
		
		// aqui somei o raio junto, assim o algoritmo ja entende quando a borda 
		// do circulo passa do limite no bbox e não somente quando o ponto central		
		double xAtualCirculo = this.posX + this.raioCMenor;	
		
		// este if testa se o circulo está fora da bbox		
		if ((this.posX + this.raioCMenor > bbox45[0])  || 
			(this.posX - this.raioCMenor < bbox135[0]) ||
		    (this.posY + this.raioCMenor > bbox45[1])  ||
		    (this.posY - this.raioCMenor < bbox315[1])) {
			
			this.cor = 2;
			
			//se sim, calcula distancia euclidiana
			double d = Math.pow((200 - this.posX), 2) + 
					   Math.pow((200 - this.posY), 2);			
			
			if (d <= Math.pow(this.raioCMaior, 2)) {
				this.cor = 2; // vermelho
			} else {
				this.cor = 1; // verde
			}			
		} else {
			this.cor = 0; // preto
		}
		glDrawable.display();
	}

	public void mouseMoved(MouseEvent e) {
		//System.out.println("mouseMoved X=" + e.getX() + "Y=" + e.getY());		
	}

	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked X=" + e.getX() + "Y=" + e.getY());
	}

	public void mouseEntered(MouseEvent e) {
		//System.out.println("mouseEntered");		
	}

	public void mouseExited(MouseEvent e) {
		//System.out.println("mouseExited");		
	}

	public void mousePressed(MouseEvent e) {
		// pega a posição inicial do mouse
		this.posXClicado = e.getX();
		this.posYClicado = e.getY();
		
	}

	public void mouseReleased(MouseEvent e) {
		// aqui volta para a posição original quando solta o botão do mouse 	
		this.posX = this.posXOrigem;
		this.posY = this.posYOrigem;
		this.cor = 0; // preto
		glDrawable.display();
		
	}
	
}

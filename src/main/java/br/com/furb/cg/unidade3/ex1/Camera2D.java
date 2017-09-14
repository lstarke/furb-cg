package br.com.furb.cg.unidade3.ex1;

public class Camera2D {
	
	private final double DESLOCAMENTO = 20.0; // podes-se utilizar o valor que achar interessante
	private float ortho2D_minX = -400.0f, ortho2D_maxX =  400.0f, ortho2D_minY = -400.0f, ortho2D_maxY =  400.0f;
	
	public float getXmin() {
		return ortho2D_minX;
	}
	
	public float getXmax() {
		return ortho2D_maxX;
	}
	
	public float getYmin() {
		return ortho2D_minY;
	}
	
	public float getYmax() {
		return ortho2D_maxY;
	}

	/**
	 * Zoom In da camera
	 * valor de movimento fixo
	 */
	public void zoomIn() {
		if (ortho2D_maxX - ortho2D_minX > 100) {
			ortho2D_minX += DESLOCAMENTO;
			ortho2D_maxX -= DESLOCAMENTO;
			ortho2D_minY += DESLOCAMENTO;
			ortho2D_maxY -= DESLOCAMENTO;
		} else
			System.out.println("Limite maximo do Zoom!");
	}
	
	/**
	 * Zoom Out da camera
	 * valor de movimento fixo
	 */
	public void zoomOut() {
		if (ortho2D_maxX - ortho2D_minX < 2200) {
			ortho2D_minX -= DESLOCAMENTO;
			ortho2D_maxX += DESLOCAMENTO;
			ortho2D_minY -= DESLOCAMENTO;
			ortho2D_maxY += DESLOCAMENTO;
		} else
			System.out.println("Limite minimo do Zoom!");
	}
	
	/**
	 * Movimentar a camera para baixo
	 * valor de movimento fixo
	 */
	public void panBaixo() {
		ortho2D_minY += DESLOCAMENTO;
		ortho2D_maxY += DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para cima
	 * valor de movimento fixo
	 */
	public void panCima() {
		ortho2D_minY -= DESLOCAMENTO;
		ortho2D_maxY -= DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para a direita
	 * valor de movimento fixo
	 */
	public void panDireita() {
		ortho2D_minX -= DESLOCAMENTO;
		ortho2D_maxX -= DESLOCAMENTO;
	}
	
	/**
	 * Movimentar a camera para a esquerda
	 * valor de movimento fixo
	 */
	public void panEsquerda() {
		ortho2D_minX += DESLOCAMENTO;
		ortho2D_maxX += DESLOCAMENTO;
	}
}
package br.furb.cg.unidade4.model.d3;

import javax.media.opengl.GL;

public class Iluminacao {

	private GL gl;
	private float posLight[] = { 0f, 0f, 0f, 0f };

	public Iluminacao(GL gl) {
		// ja inicializa com tudo zerado
		this.gl = gl;
		this.setEnable(false);
	}
	
	public Iluminacao(GL gl, float corR, float corG, float corB, float alfa) {
		this.gl = gl;
		this.setIluminacao(corR, corG, corB, alfa);
		this.setEnable(false);
	}
	
	public float[] getIluminacao() {
		float iluminacao[] = {this.posLight[0], 
		                      this.posLight[1],
		                      this.posLight[2],
		                      this.posLight[3]};

		return iluminacao;
	}
	
	public void setIluminacao(float corR, float corG, float corB, float alfa) {
		this.posLight[0] = corR;
		this.posLight[1] = corG;
		this.posLight[2] = corB;
		this.posLight[3] = alfa;
	}
	
	public void setEnable(boolean enable) {
		if (enable)
			gl.glEnable(GL.GL_LIGHT0);
		else
			gl.glDisable(GL.GL_LIGHT0);
	}
	
	public void poscionar() {
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
	}
}
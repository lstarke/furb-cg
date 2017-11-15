package br.furb.cg.unidade4.model.d3;

import javax.media.opengl.GL;

public class Caneta3D {
	
	private static float corRed[] = { 1.0f, 0.0f, 0.0f, 1.0f };

	// Desenha o cubo por face
	// Neste caso cada face eh um objeto diferente
	// Cadas face pode receber cores e transformacaoes diferente das outras
	public static void desenhaCuboFaces(GL gl)
	{
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corRed, 0);  // iluminacao
		gl.glEnable(GL.GL_LIGHTING);

		gl.glBegin (GL.GL_QUADS );
			// Front Face
			gl.glNormal3f(0,0,1); // desenhar a face, uma sera vista e a outra nao, chamado "vetor normal" x,y,z
								  // o numero indica para qual lado a o vetor esta apontando e qual lado sera visto
			//gl.glColor3f(1,0,0);
			gl.glVertex3f(-1.0f, -1.0f,  1.0f); // todos os Zs sao iguais e apontam para o lado positivo
			gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glVertex3f(-1.0f,  1.0f,  1.0f);
			
			// Back Face
			gl.glNormal3f(0,0,-1);
			//glColor3f(0,1,0);
			gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glVertex3f(-1.0f,  1.0f, -1.0f);
			gl.glVertex3f( 1.0f,  1.0f, -1.0f);
			gl.glVertex3f( 1.0f, -1.0f, -1.0f);
			
			// Top Face
			gl.glNormal3f(0,1,0);
			gl.glVertex3f(-1.0f,  1.0f, -1.0f);
			gl.glVertex3f(-1.0f,  1.0f,  1.0f);
			gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glVertex3f( 1.0f,  1.0f, -1.0f);
			
			// Bottom Face
			gl.glNormal3f(0,-1,0);
			gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glVertex3f( 1.0f, -1.0f, -1.0f);
			gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			gl.glVertex3f(-1.0f, -1.0f,  1.0f);
			
			// Right face
			gl.glNormal3f(1,0,0);
			gl.glVertex3f( 1.0f, -1.0f, -1.0f);
			gl.glVertex3f( 1.0f,  1.0f, -1.0f);
			gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			
			// Left Face
			gl.glNormal3f(-1,0,0);
			gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glVertex3f(-1.0f, -1.0f,  1.0f);
			gl.glVertex3f(-1.0f,  1.0f,  1.0f);
			gl.glVertex3f(-1.0f,  1.0f, -1.0f);
		gl.glEnd();

		gl.glDisable(GL.GL_LIGHTING);
	}
	
	public static void drawAxis(GL gl) {
		// eixo X - Red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glEnd();

		// eixo Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glEnd();

		// eixo Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 10.0f);
		gl.glEnd();
	}
}
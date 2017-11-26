package br.furb.cg.unidade4.model.d3;

import java.awt.Color;
import javax.media.opengl.GL;

public class Caneta3D {
	
	private static float corLuz[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	
	// Obedecer a cor escolhida pelo usuario
	//gl.glColor3ub((byte)cor.getRed(), (byte)cor.getGreen(), (byte)cor.getBlue());
	public static void setCorCubo(Color cor) {
		corLuz[0] = cor.getRed()   / 255f;
		corLuz[1] = cor.getGreen() / 255f;
		corLuz[2] = cor.getBlue()  / 255f;
		corLuz[3] = cor.getAlpha() / 255f;
	}

	// Desenha o cubo por face
	// Neste caso cada face eh um objeto diferente
	// Cadas face pode receber cores e transformacaoes diferente das outras
	public static void desenhaCuboFaces(GL gl)
	{
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corLuz, 0);  // iluminacao
	    
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
	
	public static void desenharCuboTextura(GL gl, Textura textura, int idxTextura) {
		// Primeiro habilita uso de textura
		gl.glEnable(GL.GL_TEXTURE_2D);
		
		// Especifica qual e a textura corrente pelo identificador
		gl.glBindTexture(GL.GL_TEXTURE_2D, textura.getIdTextura(idxTextura));
		
		// Envio da textura para OpenGL
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, textura.getLargura(idxTextura), textura.getAltura(idxTextura), 0, GL.GL_BGR, GL.GL_UNSIGNED_BYTE, textura.getBuffer(idxTextura));
		desenharCuboTexturaInterno(gl);
		
		// Desabilita uso de textura
		gl.glDisable(GL.GL_TEXTURE_2D);
	}

	private static void desenharCuboTexturaInterno(GL gl) {
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glBegin (GL.GL_QUADS );
			// Especifica a coordenada de textura para cada vertice
			// Face frontal
			gl.glNormal3f(0.0f, 0.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);

			// Face posterior
			gl.glNormal3f(0.0f, 0.0f, 1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);

			// Face superior
			gl.glNormal3f(0.0f, 1.0f, 0.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);

			// Face inferior
			gl.glNormal3f(0.0f, -1.0f, 0.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);

			// Face lateral direita
			gl.glNormal3f(1.0f, 0.0f, 0.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			
			// Face lateral esquerda
			gl.glNormal3f(-1.0f, 0.0f, 0.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
		gl.glEnd();
	}
}
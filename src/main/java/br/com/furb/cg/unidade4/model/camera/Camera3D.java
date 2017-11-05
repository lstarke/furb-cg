package br.furb.cg.unidade4.model.camera;

/**
 * Camera da cena grafica
 * 
 * Preparada para trabalhar em cenas 3D
 * Comporta camera sintetica Ortogonal, Frustum e Perspectiva
 */

public class Camera3D {
	private TipoProjecao tiProjecao;
	private ICamera3D camera;
	
	public Camera3D(TipoProjecao tiProjecao) {
		this.tiProjecao = tiProjecao;
	}
	
	public TipoProjecao getTipoProjecao() {
		return this.tiProjecao;
	}
	
// ==================================================
// Como trabalhar com as 3 cameras ?
// ==================================================
	
	public CameraOrtho getOrtho() {
		return (CameraOrtho) this.camera;
	}
	
	public CameraFrustum getFrustum() {
		return (CameraFrustum) this.camera;
	}
	
	public CameraPerspective getPerspective() {
		return (CameraPerspective) this.camera;
	}
}
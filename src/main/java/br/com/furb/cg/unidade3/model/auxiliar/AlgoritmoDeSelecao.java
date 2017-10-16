package br.com.furb.cg.unidade3.model.auxiliar;

import java.util.List;
import br.furb.cg.unidade3.model.ObjetoGrafico;
import br.furb.cg.unidade3.model.Ponto4D;

public class AlgoritmoDeSelecao {
	
	public static boolean pontoEmPoligono(ObjetoGrafico objeto, Ponto4D pontoClicado) {
		
		List<Ponto4D> vertices = objeto.getVertices();
		int n = 0;
		
		for (int i = 0; i < vertices.size(); i++) {
			
			Ponto4D p1 = vertices.get(i);
			Ponto4D p2 = vertices.get((i+1) % vertices.size());						
			
			if (p1.obterY() != p2.obterY()) {				
				Ponto4D interseccao = getPontoInterseccao(p1, p2, pontoClicado.obterY());				
				if (interseccao != null) {					
					if (interseccao.obterX() == pontoClicado.obterX()) {
						return false;						
					} else {						
						if (interseccao.obterX() > pontoClicado.obterX() &&
							interseccao.obterY() > Math.min(p1.obterY(), p2.obterY()) &&
							interseccao.obterY() <= Math.max(p1.obterY(), p2.obterY())) {						
							n++;						
						}						
					}					
				}				
			} else {				
				if (pontoClicado.obterY() == p1.obterY() && 
					pontoClicado.obterX() >= Math.min(p1.obterX(), p2.obterX()) &&
					pontoClicado.obterX() <= Math.max(p1.obterX(), p2.obterX())) {
					return false;
				}				
			}			
		}

		return !((n%2) == 0);			
	}
	
	private static Ponto4D getPontoInterseccao(Ponto4D p1, Ponto4D p2, double scanline) {
		
		double ti = (scanline - p1.obterY()) / (p2.obterY() - p1.obterY());
		
		if (ti >= 0 && ti <= 1) {
			double x = p1.obterX() + ((p2.obterX() - p1.obterX()) * ti);
			return new Ponto4D(x, scanline, 0.0, 0.0);
		}
		
		return null;
	}
}
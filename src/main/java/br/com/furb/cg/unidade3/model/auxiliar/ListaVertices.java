package br.furb.cg.unidade3.model.auxiliar;

/**
 * Encapsulamento da lista de verices do objeto grafico
 * Util para trabalhar com o objeto grafico
 */

import java.util.ArrayList;
import java.util.List;

import br.furb.cg.unidade3.model.Ponto4D;

public class ListaVertices {
	
	private List<Ponto4D> vertices;
	private Ponto4D selecionado;
	
	/**
	 * Construtor
	 */
	public ListaVertices() {
		this.vertices = new ArrayList<Ponto4D>();
		this.selecionado = null;
	}
	
	/**
	 * Adicionar vertice no objeto grafico/poligono
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Ponto4D add(double x, double y)
	{
		return null;
	}
	
	/**
	 * Retornar o primeiro vertice da lista de vertices do objeto grafico/poligono
	 */
	public Ponto4D getPrimeiro()
	{
		if (vertices.size() > 0)
			return vertices.get(0);
		else
			return null;
	}
	
	/**
	 * Retornar o ultimo vertice adicionado no objeto grafico/poligono
	 */
	public Ponto4D getUltimo()
	{	
		int idxUltimo = getUltimoIndice();
		
		if (idxUltimo > -1)
			return vertices.get(idxUltimo);
		
		return null;
	}
	
	/**
	 * Retornar o indice do ultimo vertice adicionado no objeto grafico/poligono
	 */
	public int getUltimoIndice()
	{
		return vertices.size() - 1;
	}
	
	/**
	 * Indicar o numero/quantidade de vertices no objeto gradico/poligono
	 */
	public int size()
	{
		return vertices.size();
	}
	
	/**
	 * Remover o vertice selecionado
	 */
	public boolean removerSelecionado()
	{
		if (selecionado != null)
		{
			vertices.remove(selecionado);
			selecionado = null;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Remover ultimo vertice do objeto grafico/poligono
	 */
	public void removerUltimo()
	{
		int idxUltimo = getUltimoIndice();
		
		if (idxUltimo > -1)
			vertices.remove(idxUltimo);
	}
	
	/**
	 * Imprimir vertices no console
	 */
	public void exibir()
	{
		for (int i = 0; i < vertices.size(); i++)
			System.out.println(String.format("Vertice nro %d: %s", i, vertices.get(i).toString()));
	}
	
	/**
	 * Retorna o ponto mais perto do vertice mais perto
	 * 
	 * @param pontoComparado
	 * @return pontoMaisPertoDoVerticeMaisPerto
	 */
	public Ponto4D getPontoVerticeMaisPerto(Ponto4D pontoComparado)
	{	
		for (Ponto4D v : vertices)
			if (v.estaPerto(pontoComparado))
				return v;
		
		return null;
	}
}
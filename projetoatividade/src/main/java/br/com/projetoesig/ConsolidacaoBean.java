package br.com.projetoesig;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.dao.DaoConsolidacao;
import br.com.entidades.Pessoa;

@ManagedBean(name = "consolidacaoBean")
public class ConsolidacaoBean {
	
	private DaoConsolidacao daoPessoa = new DaoConsolidacao();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	

	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}	
	

	
}

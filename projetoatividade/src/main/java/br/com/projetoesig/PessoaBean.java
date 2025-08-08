package br.com.projetoesig;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.dao.DaoConsolidacao;
import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import br.com.entidades.PessoaSalarioConsolidado;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	
	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private DaoGeneric<PessoaSalarioConsolidado> daoConsolidacao = new DaoGeneric<PessoaSalarioConsolidado>();
	private DaoConsolidacao daoConsolidacaoSalarios = new DaoConsolidacao();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private List<Object[]> salariosConsolidado = new ArrayList<Object[]>();
	private List<PessoaSalarioConsolidado> salariosFinal = new ArrayList<PessoaSalarioConsolidado>();
	
	
	public String salvar() {
		pessoa = daoGeneric.merge(pessoa);
		carregarPessoas();
		
		return "";
	}
	
	public String novo() {
		pessoa = new Pessoa();
		carregarConsolidado();
		
		return "";
	}
	
	public String remove() {
		daoGeneric.deletePorID(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		
		return "";
	}
	
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}
	
	public String carregarConsolidado() {
		salariosConsolidado = daoConsolidacaoSalarios.consolidarSalarios();
		
		return "";
	}
	
	public String carregarFinal() {
		daoConsolidacao.getListConsolidado(PessoaSalarioConsolidado.class);
		
		
		return "";
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Object[]> getSalariosConsolidado() {
		return salariosConsolidado;
	}

	

	
	
}

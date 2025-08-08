package br.com.projetoesig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.dao.DaoGeneric;
import br.com.entidades.Cargo;
import br.com.entidades.CargoVencimentos;
import br.com.entidades.Pessoa;
import br.com.entidades.PessoaSalarioConsolidado;
import br.com.entidades.Vencimentos;

@ManagedBean(name = "pessoaBean2")
public class PessoaBean2 {

	private Pessoa pessoa = new Pessoa();
	private Cargo cargo = new Cargo();
	private Vencimentos vencimentos = new Vencimentos();
	private CargoVencimentos cargoVencimentos = new CargoVencimentos();
	private PessoaSalarioConsolidado consolidado = new PessoaSalarioConsolidado();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private DaoGeneric<Cargo> daoCargo = new DaoGeneric<Cargo>();
	private DaoGeneric<Vencimentos> daoVencimentos = new DaoGeneric<Vencimentos>();
	private DaoGeneric<CargoVencimentos> daoCargoVencimentos = new DaoGeneric<CargoVencimentos>();

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private List<Cargo> cargos = new ArrayList<Cargo>();
	private List<Vencimentos> listVencimentos = new ArrayList<Vencimentos>();
	private List<CargoVencimentos> listCargoVencimentos = new ArrayList<CargoVencimentos>();
	private List<PessoaSalarioConsolidado> pessoaSalarioConsolidados = new ArrayList<PessoaSalarioConsolidado>();
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public String Salvar() {
		daoGeneric.salvar(pessoa);
		
		return "";
	}
	
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}
	
	public void calcularSalarios() {
		carregarPessoas();
		cargos = daoCargo.getListEntity(Cargo.class);
		listVencimentos = daoVencimentos.getListEntity(Vencimentos.class);
		listCargoVencimentos = daoCargoVencimentos.getListEntity(CargoVencimentos.class);
		
		for (Pessoa pessoa : pessoas) {
			consolidado.setPessoa(pessoa);
			
			// Calcular o salário de acordo com o tipo (CREDITO ou DEBITO)
			
		}
		
	}
	
	// Método para calcular os salários
	public void calculaSalarios() {
		
	        // Recuperando os dados das 4 tabelas utilizando JPQL
	        String jpql = "SELECT p.id AS pessoaId, p.nome AS nomePessoa, c.nome AS nomeCargo, v.tipo AS tipo, v.valor AS valor " +
	                      "FROM Pessoa p " +
	                      "JOIN p.cargo c " +
	                      "JOIN c.cargoVencimentos cv " +
	                      "JOIN cv.vencimentos v";

	        // Executando a consulta
	        List<Object[]> results = entityManager.createQuery(jpql, Object[].class).getResultList();

	        // Mapa para armazenar os salários por pessoa
	        Map<Integer, Double> salarios = new HashMap<>();
	        Map<Integer, String> nomePessoas = new HashMap<>();
	        Map<Integer, String> nomeCargos = new HashMap<>();

	        // Processando os resultados da consulta
	        for (Object[] row : results) {
	            Integer pessoaId = (Integer) row[0];
	            String nomePessoa = (String) row[1];
	            String nomeCargo = (String) row[2];
	            String tipo = (String) row[3];
	            Double valor = (Double) row[4];

	            // Armazenando os nomes para facilitar a inserção posterior
	            nomePessoas.put(pessoaId, nomePessoa);
	            nomeCargos.put(pessoaId, nomeCargo);

	            // Calculando o salário de acordo com o tipo (CREDITO ou DEBITO)
	            if (tipo.equals("CREDITO")) {
	                salarios.put(pessoaId, salarios.getOrDefault(pessoaId, 0.0) + valor);
	            } else if (tipo.equals("DEBITO")) {
	                salarios.put(pessoaId, salarios.getOrDefault(pessoaId, 0.0) - valor);
	            }
	        }

	        // Inserindo ou atualizando a tabela pessoa_salario_consolidado
	        for (Integer pessoaId : salarios.keySet()) {
	            Double salario = salarios.get(pessoaId);
	            String nomePessoa = nomePessoas.get(pessoaId);
	            String nomeCargo = nomeCargos.get(pessoaId);

	            // Verificando se a pessoa já existe na tabela pessoa_salario_consolidado
	            PessoaSalarioConsolidado salarioConsolidado = entityManager.find(PessoaSalarioConsolidado.class, pessoaId);
	            if (salarioConsolidado == null) {
	                // Se não existir, cria um novo registro
	                salarioConsolidado = new PessoaSalarioConsolidado(pessoaId, nomePessoa, nomeCargo, salario);
	                entityManager.persist(salarioConsolidado);
	            } else {
	                // Se já existir, atualiza o salário
	                salarioConsolidado.setSalario(salario);
	                entityManager.merge(salarioConsolidado);
	            }
	        }
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
}

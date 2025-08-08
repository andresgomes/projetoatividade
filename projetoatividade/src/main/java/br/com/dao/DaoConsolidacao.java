package br.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.PessoaSalarioConsolidado;
import br.com.jpautil.JPAUtil;

public class DaoConsolidacao{
	
	
	public List<Object[]> consolidarSalarios() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		
		String jpql = "SELECT p.id AS pessoaId, p.nome AS nomePessoa, c.nome AS nomeCargo, v.tipo AS tipo, v.valor AS valor " +
				"FROM Pessoa p " +
				"JOIN p.cargo c " +
				"JOIN CargoVencimentos cv on cv.cargo = c " +
				"JOIN cv.vencimentos v";
	
		
		List<Object[]> results = entityManager.createQuery(jpql, Object[].class).getResultList();
		
		Map<Integer, Double> salarios = new HashMap<>();
		Map<Integer, String> nomePessoas = new HashMap<>();
	    Map<Integer, String> nomeCargos = new HashMap<>();
	    
	    //Tratando os resultados da consulta
	    for(Object[] row : results) {
	    	Integer pessoaId = (Integer) row[0];
	    	String nomePessoa = (String) row[1];
	    	String nomeCargo = (String) row[2];
	    	String tipo = (String) row [3];
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
	            PessoaSalarioConsolidado salarioConsolidadoComparador = entityManager.find(PessoaSalarioConsolidado.class, pessoaId);
	            if (salarioConsolidadoComparador == null) {
	                // Se não existir, cria um novo registro
	                PessoaSalarioConsolidado salarioConsolidado = new PessoaSalarioConsolidado(pessoaId, nomePessoa, nomeCargo, salario);
	                entityManager.persist(salarioConsolidado);
	            } else {
	                // Se já existir, atualiza o salário
	            	
	                salarioConsolidadoComparador.setSalario(salario);
	                entityManager.merge(salarioConsolidadoComparador);
	            }
	        }
            	    
		
		

		
		entityTransaction.commit();
		entityManager.close();
		
		return results;
	}
	
	
	
	

}

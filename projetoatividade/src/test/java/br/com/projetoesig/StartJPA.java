package br.com.projetoesig;

import javax.persistence.Persistence;

public class StartJPA {
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("projetoatividade");
	}
}

package br.com.gabrielDias.desafioAgiBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendedor {

	private String cpf;
	
	private String nome;
	
	private Double salario;
	
}

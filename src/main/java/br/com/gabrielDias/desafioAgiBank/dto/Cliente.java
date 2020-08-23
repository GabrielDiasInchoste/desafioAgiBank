package br.com.gabrielDias.desafioAgiBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	private String cnpj;
	
	private String nome;
	
	private String areaNegocio;
	
}

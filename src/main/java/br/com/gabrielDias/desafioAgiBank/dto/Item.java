package br.com.gabrielDias.desafioAgiBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	private Integer id;
	
	private Integer quantidade;
	
	private Double preco;
	
}

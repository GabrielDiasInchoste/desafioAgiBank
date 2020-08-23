package br.com.gabrielDias.desafioAgiBank.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

	private String id;
	
	private List<Item> items;
	
	private String nomeVendedor;
	
}

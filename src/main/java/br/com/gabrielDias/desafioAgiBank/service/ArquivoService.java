package br.com.gabrielDias.desafioAgiBank.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import br.com.gabrielDias.desafioAgiBank.contants.Constantes;
import br.com.gabrielDias.desafioAgiBank.dto.Cliente;
import br.com.gabrielDias.desafioAgiBank.dto.Item;
import br.com.gabrielDias.desafioAgiBank.dto.Venda;
import br.com.gabrielDias.desafioAgiBank.dto.Vendedor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArquivoService {

	public void verificarArquivo() {
		log.info("ArquivoService.verificarArquivo - Start");

		List<File> listarArquivos = listarArquivos();
		List<Vendedor> venderores = new ArrayList<>();
		List<Cliente> clientes = new ArrayList<>();
		List<Venda> vendas = new ArrayList<>();

		listarArquivos.forEach(arquivo -> {
			try {

				FileReader fileReader = new FileReader(arquivo);
				BufferedReader reader = new BufferedReader(fileReader);
				Stream<String> linhas = reader.lines();

				linhas.forEach(linha -> criarArquivo(venderores, clientes, vendas, linha));
				Files.write(Paths
						.get(System.getenv(Constantes.HOMEPATH).concat(Constantes.DATA_OUTPUT).concat("Done_").concat(arquivo.getName())),
						criarResposta(venderores, clientes, vendas).getBytes());
				venderores.clear();
				clientes.clear();
				vendas.clear();
				reader.close();
			} catch (IOException e) {
				log.error("Erro ao verificar arquivo - Erro: {}", e.getMessage(), e);
			}
		});
	log.info("ArquivoService.verificarArquivo - End");

	}

	private void criarArquivo(List<Vendedor> venderores, List<Cliente> clientes, List<Venda> vendas,String linha) {
		
		String[] splitFileLine = linha.split(Constantes.SEPARATOR);
		if (splitFileLine[0].equals(Constantes.VENDEDOR_ID) && splitFileLine.length >= 4) {
			venderores.add(new Vendedor(splitFileLine[1], splitFileLine[2], Double.parseDouble(splitFileLine[3])));
		} else if (splitFileLine[0].equals(Constantes.CLIENTE_ID) && splitFileLine.length >= 4) {
			clientes.add(new Cliente(splitFileLine[1], splitFileLine[2], splitFileLine[3]));
		} else if (splitFileLine[0].equals(Constantes.VENDA_ID) && splitFileLine.length >= 4) {

			String replace = splitFileLine[2].replace(Constantes.ARRAY_START, "");
			replace = replace.replace(Constantes.ARRAY_END, "");
			String[] stringItems = replace.split(Constantes.SEPARATOR_ARRAY_ITEMS);

			List<Item> items = new ArrayList<>();
			for (String stringItem : stringItems) {
				String[] splitItem = stringItem.split(Constantes.SEPARATOR_OBJECT_ITEM);
				items.add(new Item(Integer.valueOf(splitItem[0]), Integer.valueOf(splitItem[1]),
						Double.valueOf(splitItem[2])));
			}
			vendas.add(new Venda(splitFileLine[1], items, splitFileLine[3]));

		}
	}

	public List<File> listarArquivos() {

		File diretorio = new File(System.getenv(Constantes.HOMEPATH).concat(Constantes.DATA_INPUT));
		if (diretorio.exists() && diretorio.isDirectory()) {
			FileFilter filter = arquivo -> arquivo.getName().endsWith(".txt");
			return Arrays.asList(diretorio.listFiles(filter));
		} else {
			log.error("Diretorio nao encontrado");
		}
		return new ArrayList<>();
	}

	private String criarResposta(List<Vendedor> vendedores, List<Cliente> clientes, List<Venda> vendas) {
		String quantidadeCliente = "Quantidade de clientes no arquivo de entrada: " + clientes.size();
		String quantidadeVendedor = "Quantidade de vendedor no arquivo de entrada: " + vendedores.size();
		Map<String, Double> collect = vendas.stream().collect(Collectors.toMap(a -> a.getId(),
				venda -> venda.getItems().stream().mapToDouble(item -> item.getPreco()).sum()));
		String vendaId = collect.entrySet().stream()
				.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		String vendaMaisCara = "ID da venda mais cara: " + vendaId;
		String piorVendedor = "O pior vendedor: " + "";
		return quantidadeCliente + "\n" + quantidadeVendedor + "\n" + vendaMaisCara + "\n" + piorVendedor;
	}
	
	
}

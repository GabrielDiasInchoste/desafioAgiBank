package br.com.gabrielDias.desafioAgiBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class Scheduler {
	@Autowired
	private ArquivoService arquivoService;

	@Scheduled(fixedDelay = 60000, zone = "America/Sao_Paulo")
	public void verificar() {
		arquivoService.verificarArquivo();
	}

}

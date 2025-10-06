package br.edu.infnet.boletoapi.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.infnet.boletoapi.clients.config.FeriadosFeignClientConfig;
import br.edu.infnet.boletoapi.model.domain.Feriado;

@FeignClient(name = "feriadosClient", url = "${url.api.feriados}", configuration = FeriadosFeignClientConfig.class)
public interface FeriadosFeignClient {

    @GetMapping("/{ano}")
    List<Feriado> obterFeriados(@PathVariable("ano") String ano);

}

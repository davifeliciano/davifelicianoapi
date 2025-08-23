package br.edu.infnet.davifelicianoapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.davifelicianoapi.model.domain.Boleto;
import br.edu.infnet.davifelicianoapi.model.service.BoletoService;

@RestController
@RequestMapping("/api/boletos")
public class BoletoController {

    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Boleto>> obterTodos() {
        List<Boleto> boletos = boletoService.obterTodos();

        if (boletos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(boletos);
    }
}

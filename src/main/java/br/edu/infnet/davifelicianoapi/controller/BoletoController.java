package br.edu.infnet.davifelicianoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.davifelicianoapi.model.domain.Boleto;
import br.edu.infnet.davifelicianoapi.model.service.BoletoService;
import jakarta.validation.Valid;

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
        return ResponseEntity.ok(boletos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Boleto> obterPorId(@PathVariable Integer id) {
        Boleto boleto = boletoService.obterPorId(id);
        return ResponseEntity.ok(boleto);
    }

    @PostMapping
    public ResponseEntity<Boleto> incluir(@Valid @RequestBody Boleto boleto) {
        Boleto boletoCriado = boletoService.incluir(boleto);
        return ResponseEntity.status(HttpStatus.CREATED).body(boletoCriado);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Boleto> alterar(@PathVariable Integer id, @Valid @RequestBody Boleto boleto) {
        Boleto boletoAtualizado = boletoService.alterar(id, boleto);
        return ResponseEntity.ok(boletoAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        boletoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/pagar")
    public ResponseEntity<Boleto> pagar(@PathVariable Integer id) {
        Boleto boletoPago = boletoService.pagar(id);
        return ResponseEntity.ok(boletoPago);
    }

}

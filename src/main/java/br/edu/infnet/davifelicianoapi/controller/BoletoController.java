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
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoJaPagoException;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInexistenteException;
import br.edu.infnet.davifelicianoapi.model.exceptions.BoletoInvalidoException;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<Boleto> obterPorId(@PathVariable Integer id) {
        try {
            Boleto boleto = boletoService.obterPorId(id);
            return ResponseEntity.ok(boleto);
        } catch (BoletoJaPagoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Boleto> incluir(@RequestBody Boleto boleto) {
        try {
            Boleto boletoCriado = boletoService.incluir(boleto);
            return ResponseEntity.status(HttpStatus.CREATED).body(boletoCriado);
        } catch (BoletoInvalidoException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Boleto> alterar(@PathVariable Integer id, @RequestBody Boleto boleto) {
        try {
            Boleto boletoAtualizado = boletoService.alterar(id, boleto);
            return ResponseEntity.ok(boletoAtualizado);
        } catch (BoletoJaPagoException e) {
            return ResponseEntity.notFound().build();
        } catch (BoletoInvalidoException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            boletoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (BoletoJaPagoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{id}/pagar")
    public ResponseEntity<Boleto> pagar(@PathVariable Integer id) {
        try {
            Boleto boletoPago = boletoService.pagar(id);
            return ResponseEntity.ok(boletoPago);
        } catch (BoletoInexistenteException e) {
            return ResponseEntity.notFound().build();
        } catch (BoletoJaPagoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

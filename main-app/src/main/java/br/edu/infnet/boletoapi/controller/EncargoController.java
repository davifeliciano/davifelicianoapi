package br.edu.infnet.boletoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.boletoapi.model.domain.Encargo;
import br.edu.infnet.boletoapi.dtos.EncargoRequestComBoletoIdDTO;
import br.edu.infnet.boletoapi.service.BoletoService;
import br.edu.infnet.boletoapi.service.EncargoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/encargos")
public class EncargoController {

    private final EncargoService encargoService;
    private final BoletoService boletoService;

    public EncargoController(EncargoService encargoService, BoletoService boletoService) {
        this.encargoService = encargoService;
        this.boletoService = boletoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Encargo>> obterTodos() {
        List<Encargo> encargos = encargoService.obterTodos();
        return ResponseEntity.ok(encargos);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Encargo> obterPorId(@PathVariable("id") Integer id) {
        Encargo encargo = encargoService.obterPorId(id);
        return ResponseEntity.ok(encargo);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Encargo> incluir(@Valid @RequestBody EncargoRequestComBoletoIdDTO encargoRequest) {
        Encargo encargo = encargoRequest.toEncargo();
        encargo.setBoleto(boletoService.obterPorId(encargoRequest.getBoletoId()));
        Encargo encargoCriado = encargoService.incluir(encargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(encargoCriado);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Encargo> alterar(@PathVariable("id") Integer id,
            @Valid @RequestBody EncargoRequestComBoletoIdDTO encargoRequest) {
        Encargo encargo = encargoRequest.toEncargo();
        encargo.setBoleto(boletoService.obterPorId(encargoRequest.getBoletoId()));
        Encargo encargoAtualizado = encargoService.alterar(id, encargo);
        return ResponseEntity.ok(encargoAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
        encargoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}

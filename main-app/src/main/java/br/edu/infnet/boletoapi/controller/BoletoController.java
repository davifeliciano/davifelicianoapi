package br.edu.infnet.boletoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.boletoapi.model.domain.Boleto;
import br.edu.infnet.boletoapi.model.domain.EncargoProjetado;
import br.edu.infnet.boletoapi.model.domain.Pagamento;
import br.edu.infnet.boletoapi.dtos.PagamentoResponseSemBoletoDTO;
import br.edu.infnet.boletoapi.service.BoletoService;
import br.edu.infnet.boletoapi.service.PagamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/boletos")
public class BoletoController {

    private final BoletoService boletoService;
    private final PagamentoService pagamentoService;

    public BoletoController(BoletoService boletoService, PagamentoService pagamentoService) {
        this.boletoService = boletoService;
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Iterable<Boleto>> obterTodos(@RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        if (from == null && to == null) {
            List<Boleto> boletos = boletoService.obterTodos();
            return ResponseEntity.ok(boletos);
        }

        List<Boleto> boletos = boletoService.obterPorDataVencimento(from, to);
        return ResponseEntity.ok(boletos);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Boleto> obterPorId(@PathVariable("id") Integer id) {
        Boleto boleto = boletoService.obterPorId(id);
        return ResponseEntity.ok(boleto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boleto> incluir(@Valid @RequestBody Boleto boleto) {
        Boleto boletoCriado = boletoService.incluir(boleto);
        return ResponseEntity.status(HttpStatus.CREATED).body(boletoCriado);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boleto> alterar(@PathVariable("id") Integer id, @Valid @RequestBody Boleto boleto) {
        Boleto boletoAtualizado = boletoService.alterar(id, boleto);
        return ResponseEntity.ok(boletoAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
        boletoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/pagar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Boleto> pagar(@PathVariable("id") Integer id) {
        Boleto boletoPago = boletoService.pagar(id);
        return ResponseEntity.ok(boletoPago);
    }

    @GetMapping(value = "/{id}/pagamentos")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<PagamentoResponseSemBoletoDTO>> obterPagamentos(@PathVariable("id") Integer id) {
        List<Pagamento> pagamentos = pagamentoService.obterPorBoletoId(id);
        List<PagamentoResponseSemBoletoDTO> pagamentosSemBoleto = pagamentos.stream()
                .map(PagamentoResponseSemBoletoDTO::fromPagamento)
                .toList();

        return ResponseEntity.ok(pagamentosSemBoleto);
    }

    @GetMapping(value = "/{id}/encargos")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<EncargoProjetado> obterEncargosProjetados(@PathVariable("id") Integer id) {
        EncargoProjetado encargoProjetado = boletoService.calcularEncargosProjetadosPorId(id);
        return ResponseEntity.ok(encargoProjetado);
    }

}

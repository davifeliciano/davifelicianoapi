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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.boletoapi.model.domain.Pagamento;
import br.edu.infnet.boletoapi.dtos.PagamentoRequestComBoletoIdDTO;
import br.edu.infnet.boletoapi.service.BoletoService;
import br.edu.infnet.boletoapi.service.PagamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {
    private final PagamentoService pagamentoService;
    private final BoletoService boletoService;

    public PagamentoController(PagamentoService pagamentoService, BoletoService boletoService) {
        this.pagamentoService = pagamentoService;
        this.boletoService = boletoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Iterable<Pagamento>> obterTodos(@RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        if (from == null && to == null) {
            List<Pagamento> pagamentos = pagamentoService.obterTodos();
            return ResponseEntity.ok(pagamentos);
        }

        List<Pagamento> pagamentos = pagamentoService.obterPorDataPagamento(from, to);
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Pagamento> obterPorId(@PathVariable("id") Integer id) {
        Pagamento pagamento = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamento);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pagamento> incluir(@Valid @RequestBody PagamentoRequestComBoletoIdDTO pagamentoRequest) {
        Pagamento pagamento = pagamentoRequest.toPagamento();
        pagamento.setBoleto(boletoService.obterPorId(pagamentoRequest.getBoletoId()));
        Pagamento pagamentoCriado = pagamentoService.incluir(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoCriado);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pagamento> alterar(@PathVariable("id") Integer id,
            @Valid @RequestBody PagamentoRequestComBoletoIdDTO pagamentoRequest) {
        Pagamento pagamento = pagamentoRequest.toPagamento();
        pagamento.setBoleto(boletoService.obterPorId(pagamentoRequest.getBoletoId()));
        Pagamento pagamentoAtualizado = pagamentoService.alterar(id, pagamento);
        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
        pagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}

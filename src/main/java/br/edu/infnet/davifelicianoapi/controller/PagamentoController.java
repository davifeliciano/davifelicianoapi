package br.edu.infnet.davifelicianoapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.davifelicianoapi.model.domain.Pagamento;
import br.edu.infnet.davifelicianoapi.model.dtos.PagamentoRequestComBoletoIdDTO;
import br.edu.infnet.davifelicianoapi.model.service.BoletoService;
import br.edu.infnet.davifelicianoapi.model.service.PagamentoService;
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
    public ResponseEntity<Pagamento> obterPorId(@PathVariable Integer id) {
        Pagamento pagamento = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamento);
    }

    @PostMapping
    public ResponseEntity<Pagamento> incluir(@Valid @RequestBody PagamentoRequestComBoletoIdDTO pagamentoRequest) {
        Pagamento pagamento = pagamentoRequest.toPagamento();
        pagamento.setBoleto(boletoService.obterPorId(pagamentoRequest.getBoletoId()));
        Pagamento pagamentoCriado = pagamentoService.incluir(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoCriado);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pagamento> alterar(@PathVariable Integer id,
            @Valid @RequestBody PagamentoRequestComBoletoIdDTO pagamentoRequest) {
        Pagamento pagamento = pagamentoRequest.toPagamento();
        pagamento.setBoleto(boletoService.obterPorId(pagamentoRequest.getBoletoId()));
        Pagamento pagamentoAtualizado = pagamentoService.alterar(id, pagamento);
        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        pagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}

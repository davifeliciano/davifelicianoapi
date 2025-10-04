package br.edu.infnet.davifelicianoapi.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.davifelicianoapi.model.domain.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    @Find
    List<Pagamento> findByBoletoId(Integer boletoId);

    @Find
    List<Pagamento> findByDataPagamentoBetween(LocalDate valueOf, LocalDate valueOf2);

    @Find
    List<Pagamento> findByDataPagamentoGreaterThanEqual(LocalDate valueOf);

    @Find
    List<Pagamento> findByDataPagamentoLessThanEqual(LocalDate valueOf);

}

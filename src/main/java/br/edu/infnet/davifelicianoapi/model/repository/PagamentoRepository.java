package br.edu.infnet.davifelicianoapi.model.repository;

import java.sql.Date;
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
    List<Pagamento> findByDataPagamentoBetween(Date valueOf, Date valueOf2);

    @Find
    List<Pagamento> findByDataPagamentoGreaterThanEqual(Date valueOf);

    @Find
    List<Pagamento> findByDataPagamentoLessThanEqual(Date valueOf);

}

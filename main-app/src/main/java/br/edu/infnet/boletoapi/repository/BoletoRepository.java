package br.edu.infnet.boletoapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.boletoapi.model.domain.Boleto;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

    @Find
    List<Boleto> findByDataVencimentoBetween(LocalDate valueOf, LocalDate valueOf2);

    @Find
    List<Boleto> findByDataVencimentoGreaterThanEqual(LocalDate valueOf);

    @Find
    List<Boleto> findByDataVencimentoLessThanEqual(LocalDate valueOf);

}

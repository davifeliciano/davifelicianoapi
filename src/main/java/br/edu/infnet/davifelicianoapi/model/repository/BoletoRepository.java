package br.edu.infnet.davifelicianoapi.model.repository;

import java.sql.Date;
import java.util.List;

import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.davifelicianoapi.model.domain.Boleto;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

    @Find
    List<Boleto> findByDataVencimentoBetween(Date valueOf, Date valueOf2);

    @Find
    List<Boleto> findByDataVencimentoGreaterThanEqual(Date valueOf);

    @Find
    List<Boleto> findByDataVencimentoLessThanEqual(Date valueOf);

}

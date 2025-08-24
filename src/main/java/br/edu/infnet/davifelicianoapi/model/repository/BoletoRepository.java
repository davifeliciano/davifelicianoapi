package br.edu.infnet.davifelicianoapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.davifelicianoapi.model.domain.Boleto;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

}

package br.edu.infnet.boletoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.boletoapi.model.domain.Encargo;

@Repository
public interface EncargoRepository extends JpaRepository<Encargo, Integer> {

}

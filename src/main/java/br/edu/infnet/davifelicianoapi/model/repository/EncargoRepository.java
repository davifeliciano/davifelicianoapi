package br.edu.infnet.davifelicianoapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.davifelicianoapi.model.domain.Encargo;

@Repository
public interface EncargoRepository extends JpaRepository<Encargo, Integer> {

}

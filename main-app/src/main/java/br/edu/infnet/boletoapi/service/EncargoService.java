package br.edu.infnet.boletoapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.boletoapi.model.domain.Encargo;
import br.edu.infnet.boletoapi.model.domain.exceptions.EncargoInexistenteException;
import br.edu.infnet.boletoapi.model.domain.exceptions.EncargoInvalidoException;
import br.edu.infnet.boletoapi.repository.EncargoRepository;
import jakarta.transaction.Transactional;

@Service
public class EncargoService implements CrudService<Encargo, Integer> {

    private final EncargoRepository encargosRepository;

    public EncargoService(EncargoRepository encargosRepository) {
        this.encargosRepository = encargosRepository;
    }

    @Override
    @Transactional
    public Encargo incluir(Encargo encargo) throws EncargoInvalidoException {
        if (encargo.getId() != null) {
            throw new EncargoInvalidoException("ID deve ser nulo ao incluir um novo encargo");
        }

        encargosRepository.save(encargo);
        return encargo;
    }

    @Override
    @Transactional
    public Encargo alterar(Integer id, Encargo encargo) throws EncargoInexistenteException {
        if (!encargosRepository.existsById(id)) {
            throw new EncargoInexistenteException("Encargo não encontrado com id " + id);
        }

        encargo.setId(id);
        return encargosRepository.save(encargo);
    }

    @Override
    @Transactional
    public void excluir(Integer id) throws EncargoInexistenteException {
        Encargo encargo = encargosRepository.findById(id)
                .orElseThrow(() -> new EncargoInexistenteException("Encargo não encontrado com id " + id));

        encargo.getBoleto().setEncargo(null);
        encargo.setBoleto(null);
        encargosRepository.deleteById(id);
        encargosRepository.flush();
    }

    @Override
    public Encargo obterPorId(Integer id) throws EncargoInexistenteException {
        Encargo encargo = encargosRepository.findById(id)
                .orElseThrow(() -> new EncargoInexistenteException("Encargo não encontrado com id " + id));

        return encargo;
    }

    @Override
    public List<Encargo> obterTodos() {
        return encargosRepository.findAll();
    }

}

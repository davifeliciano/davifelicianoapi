package br.edu.infnet.davifelicianoapi.model.service;

public interface CrudService<T, ID> {
    T incluir(T entity);

    void excluir(ID id);

    T obterPorId(ID id);

    Iterable<T> obterTodos();
}

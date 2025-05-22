/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repositories.impl;


import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author dubalaguilar
 */
public abstract class  BaseRepositoryImpl<T, ID> implements Repository <T, ID>{
    protected final List<T> items = new ArrayList<>();

    @Override
    public boolean add(T item) {
        if (item == null || exists(getId(item))) {
            return false;
        }
        items.add(item);
        return true;
    }

    @Override
    public boolean remove(ID id) {
        return items.removeIf(item -> getId(item).equals(id));
    }

    @Override
    public Optional<T> findById(ID id) {
        return items.stream()
                .filter(item -> getId(item).equals(id))
                .findFirst();
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(items); // Retorna copia para evitar modificaciones externas
    }

    @Override
    public boolean exists(ID id) {
        return items.stream().anyMatch(item -> getId(item).equals(id));
    }

    // Método abstracto para obtener el ID de un item
    protected abstract ID getId(T item);
}

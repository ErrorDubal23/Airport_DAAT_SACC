/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.repositories.impl;


import java.util.List;
import java.util.Optional;

/**
 *
 * @author dubalaguilar
 */
public interface Repository<T, ID> {
    boolean add(T item);
    boolean remove(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();
    boolean exists(ID id);
}

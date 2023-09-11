/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author s_vre
 */
public interface Repository<T> {
    
    //prosljeđuje se instanca klase npr. movie
    
    int create(T object) throws Exception;

    void createAll(List<T> objects) throws Exception;

    void update(int id, T data) throws Exception;

    void delete(int id, T object) throws Exception;
    
    void deleteAll() throws Exception;

    Optional<T> select(int id, T object) throws Exception;

    List<T> selectAll(T object) throws Exception;
    
    int checkUser(String name, String password) throws Exception;
    
}

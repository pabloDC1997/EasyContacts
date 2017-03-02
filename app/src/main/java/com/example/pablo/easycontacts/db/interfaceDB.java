package com.example.pablo.easycontacts.db;

import java.util.List;

/**
 * Created by Pablo on 26/01/2017.
 */

public interface interfaceDB<T>{
    List<T> select(T obj);
    boolean insert(T obj);
    boolean update(T oldObj, T newObj);
    boolean delete(T obj);
    boolean deleteAll( );
}

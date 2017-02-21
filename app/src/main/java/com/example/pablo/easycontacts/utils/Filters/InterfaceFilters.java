package com.example.pablo.easycontacts.utils.Filters;

import java.util.List;

/**
 * Created by Pablo on 18/02/2017.
 */

public interface InterfaceFilters <T>{
    List<T> filterByName(List<T> list, String name);
}

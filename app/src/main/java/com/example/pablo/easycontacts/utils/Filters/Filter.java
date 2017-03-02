package com.example.pablo.easycontacts.utils.Filters;

import com.example.pablo.easycontacts.Models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 18/02/2017.
 */

public class Filter implements InterfaceFilters<Contact> {

    /**
     * @param list
     * @param name
     * @return Lista de contatos filtrados pelo {@name}
     */
    @Override
    public List<Contact> filterByName(List<Contact> list, String name) {
        List<Contact> responseList = new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            String nameByObj = list.get(i).getName();
            if (name.contains(nameByObj)){
                responseList.add(list.get(i));
            }
        }
        return responseList;
    }
}

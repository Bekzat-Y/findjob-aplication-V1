package com.findjob.findjobbackend.dto.request;

import com.findjob.findjobbackend.model.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditCompany {
    private String name;
    private String avatar;
    private String description;
    private String address;
    private Integer employeeQuantity;
    private City city;
    private String linkMap;
    private String phone;


}

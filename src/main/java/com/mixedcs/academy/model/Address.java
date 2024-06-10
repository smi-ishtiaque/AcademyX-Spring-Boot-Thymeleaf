package com.mixedcs.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Address extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int addressId;

    @NotBlank(message="Address-1 Must Not Be Blank")
    @Size(min=5, message="Address-1 Must Be At Least 5 Characters Long!")
    private String address1;

    private String address2;

    @NotBlank(message="City Must Not Be Blank")
    @Size(min=5, message="City Must Be At Least 5 Characters Long!")
    private String city;

    @NotBlank(message="State Must Not Be Blank")
    @Size(min=5, message="State Must Be At Least 5 Characters Long!")
    private String state;

    @NotBlank(message="Zip Code Must Not Be Blank")
    @Pattern(regexp="(^$|[0-9]{4})",message = "Zip Code Must Be 4 Digits")
    private int zipCode;

}

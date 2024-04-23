package com.axara.backend.wire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String address;
    private String city;
    private String departament;
    private String postalCode;
    private String phone;
}

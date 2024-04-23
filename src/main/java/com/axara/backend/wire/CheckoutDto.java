package com.axara.backend.wire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {
    private Long id;
    private Long cartId;
    private AddressDto address;
}

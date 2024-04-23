package com.axara.backend.adapter;

import com.axara.backend.models.Address;
import com.axara.backend.wire.AddressDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressAdapterTest {

    @Autowired
    private AddressAdapter addressAdapter;

    @Test
    public void testModelToWire(){
        Address address = Address.builder()
                .id(1L)
                .address("Cra 34 # 32 - 65")
                .city("Bucaramanga")
                .departament("Santander")
                .postalCode("680001")
                .phone("3016667436")
                .build();

        AddressDto addressDto = addressAdapter.modelToWire(address);

        AddressDto expectedAddress = AddressDto.builder()
                .id(1L)
                .address("Cra 34 # 32 - 65")
                .city("Bucaramanga")
                .departament("Santander")
                .postalCode("680001")
                .phone("3016667436")
                .build();

        assertEquals(expectedAddress, addressDto);
    }
}

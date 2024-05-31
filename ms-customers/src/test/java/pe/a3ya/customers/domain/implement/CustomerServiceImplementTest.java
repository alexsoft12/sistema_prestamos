package pe.a3ya.customers.domain.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;
import pe.a3ya.mscustomers.domain.implement.CustomerServiceImplement;
import pe.a3ya.mscustomers.domain.ports.out.CustomerServiceOut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplementTest {

    @Mock
    private CustomerServiceOut customerServiceOut;

    @InjectMocks
    private CustomerServiceImplement customerServiceImplement;

    private CustomerRequest customerRequest;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerRequest = new CustomerRequest();
        customerRequest.setDocumentNumber("78996589");
        customerRequest.setEmail("example@domain.com");
        customerRequest.setPhone("987654321");
        customerRequest.setBirthDate(LocalDate.parse("1990-01-01"));

        customerDto = CustomerDto.builder()
                .id(1L)
                .documentNumber("78996589")
                .email("example@domain.com")
                .phone("987654321")
                .dateBirth(LocalDate.parse("1990-01-01"))
                .build();
    }

    @Test
    void testCreateCustomer() {
        when(customerServiceOut.save(any(CustomerRequest.class))).thenReturn(customerDto);
        CustomerDto result = customerServiceImplement.save(customerRequest);
        verify(customerServiceOut, times(1)).save(any(CustomerRequest.class));
        assertNotNull(result);
        assertEquals(customerDto.getId(), result.getId());
        assertEquals(customerDto.getDocumentNumber(), result.getDocumentNumber());
    }

    @Test
    void testGetAllCustomers() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerDtoList.add(customerDto);
        when(customerServiceOut.getAll()).thenReturn(customerDtoList);
        List<CustomerDto> result = customerServiceImplement.getAll();
        verify(customerServiceOut, times(1)).getAll();
        assertNotNull(result);
        assertEquals(customerDtoList.size(), result.size());
    }

    @Test
    void testGetCustomerById() {
        when(customerServiceOut.getById(1L)).thenReturn(java.util.Optional.of(customerDto));
        CustomerDto result = customerServiceImplement.getById(1L).get();
        verify(customerServiceOut, times(1)).getById(1L);
        assertNotNull(result);
        assertEquals(customerDto.getId(), result.getId());
        assertEquals(customerDto.getDocumentNumber(), result.getDocumentNumber());
    }

    @Test
    void testUpdateCustomer() {
        when(customerServiceOut.update(1L, customerRequest)).thenReturn(customerDto);
        CustomerDto result = customerServiceImplement.update(1L, customerRequest);
        verify(customerServiceOut, times(1)).update(1L, customerRequest);
        assertNotNull(result);
        assertEquals(customerDto.getId(), result.getId());
        assertEquals(customerDto.getDocumentNumber(), result.getDocumentNumber());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerServiceOut).delete(1L);
        customerServiceImplement.delete(1L);
        verify(customerServiceOut, times(1)).delete(1L);
    }
}

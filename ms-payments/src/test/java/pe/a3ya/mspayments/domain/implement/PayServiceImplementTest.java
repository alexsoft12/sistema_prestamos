package pe.a3ya.mspayments.domain.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;
import pe.a3ya.mspayments.domain.ports.out.PayServiceOut;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayServiceImplementTest {

    @Mock
    private PayServiceOut payServiceOut;

    @InjectMocks
    private PayServiceImplement payServiceImplement;
    private PayRequest payRequest;
    private PayDto payDto;

    @BeforeEach
    void setUp() {
        payRequest = new PayRequest();
        payRequest.setInstallmentsId(1L);
        payRequest.setModality("moda1");
        payRequest.setMethod("Plin");
        payRequest.setAmount(460.20);

        payDto = PayDto.builder()
                .id(1L)
                .installmentsId(1L)
                .day(Timestamp.valueOf(LocalDateTime.now()))
                .modality("moda1")
                .method("Plin")
                .amount(460.20)
                .build();
    }

    @Test
    void testCreatePay() {
        when(payServiceOut.save(any(PayRequest.class))).thenReturn(payDto);
        PayDto result = payServiceImplement.save(payRequest);
        verify(payServiceOut, times(1)).save(any(PayRequest.class));
        assertNotNull(result);
        assertEquals(payDto.getId(), result.getId());
        assertEquals(payDto.getInstallmentsId(), result.getInstallmentsId());
    }

    @Test
    void testGetPay() {
        when(payServiceOut.getById(anyLong())).thenReturn(payDto);
        PayDto result = payServiceImplement.getById(1L);
        verify(payServiceOut, times(1)).getById(anyLong());
        assertNotNull(result);
        assertEquals(payDto.getId(), result.getId());
        assertEquals(payDto.getInstallmentsId(), result.getInstallmentsId());
    }

    @Test
    void testUpdatePay() {
        when(payServiceOut.update(anyLong(), any(PayRequest.class))).thenReturn(payDto);
        PayDto result = payServiceImplement.update(1L, payRequest);
        verify(payServiceOut, times(1)).update(anyLong(), any(PayRequest.class));
        assertNotNull(result);
        assertEquals(payDto.getId(), result.getId());
        assertEquals(payDto.getInstallmentsId(), result.getInstallmentsId());
    }

    @Test
    void testDeletePay() {
        doNothing().when(payServiceOut).delete(anyLong());
        payServiceImplement.delete(1L);
        verify(payServiceOut, times(1)).delete(anyLong());
    }
}

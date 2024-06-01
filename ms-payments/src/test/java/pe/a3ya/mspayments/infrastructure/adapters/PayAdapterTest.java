package pe.a3ya.mspayments.infrastructure.adapters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;
import pe.a3ya.mspayments.infrastructure.dao.PayRepository;
import pe.a3ya.mspayments.infrastructure.entities.PayEntity;

@ExtendWith(MockitoExtension.class)
public class PayAdapterTest {

    @Mock
    private PayRepository payRepository;

    @Mock
    private SecurityValidator securityValidator;

    @InjectMocks
    private PayAdapter payAdapter;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        request.addHeader("Authorization", "Bearer token");
    }

    @Test
    void testSave() {
        PayRequest payRequest = new PayRequest();
        payRequest.setInstallmentsId(1L);
        payRequest.setModality("modality");
        payRequest.setMethod("method");
        payRequest.setAmount(100);

        Mockito.doNothing().when(securityValidator).validateSecurity();
        when(payRepository.save(any(PayEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PayDto result = payAdapter.save(payRequest);

        assertNotNull(result);
        verify(payRepository).save(any(PayEntity.class));
    }

    @Test
    void testGetById() {
        PayEntity payEntity = new PayEntity();
        payEntity.setId(1L);

        Mockito.doNothing().when(securityValidator).validateSecurity();
        when(payRepository.findById(1L)).thenReturn(Optional.of(payEntity));

        PayDto result = payAdapter.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAll() {
        PayEntity payEntity1 = new PayEntity();
        PayEntity payEntity2 = new PayEntity();

        Mockito.doNothing().when(securityValidator).validateSecurity();
        when(payRepository.findAll()).thenReturn(Arrays.asList(payEntity1, payEntity2));

        List<PayDto> result = payAdapter.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testUpdate() {
        PayRequest payRequest = new PayRequest();
        payRequest.setInstallmentsId(1L);
        payRequest.setModality("modality");
        payRequest.setMethod("method");
        payRequest.setAmount(100);

        PayEntity payEntity = new PayEntity();
        payEntity.setId(1L);

        Mockito.doNothing().when(securityValidator).validateSecurity();
        when(payRepository.findById(1L)).thenReturn(Optional.of(payEntity));
        when(payRepository.save(any(PayEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PayDto result = payAdapter.update(1L, payRequest);

        assertNotNull(result);
        verify(payRepository).save(any(PayEntity.class));
    }

    @Test
    void testDelete() {
        PayEntity payEntity = new PayEntity();
        payEntity.setId(1L);

        Mockito.doNothing().when(securityValidator).validateSecurity();
        when(payRepository.findById(1L)).thenReturn(Optional.of(payEntity));

        payAdapter.delete(1L);

        verify(payRepository).save(any(PayEntity.class));
    }

    @Test
    void testInvalidToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        request.addHeader("Authorization", "Bearer invalid_token");

        PayRequest payRequest = new PayRequest();
        payRequest.setInstallmentsId(1L);
        payRequest.setModality("modality");
        payRequest.setMethod("method");
        payRequest.setAmount(100);

        assertThrows(RuntimeException.class, () -> payAdapter.save(payRequest));
    }
}

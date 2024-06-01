package pe.a3ya.mspayments.infrastructure.mappers;

import org.junit.jupiter.api.Test;
import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.infrastructure.entities.PayEntity;
import pe.a3ya.mspayments.infrastructure.mapers.PayMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PayMapperTest {
    @Test
    void testFromEntityToDto() {
        PayEntity payEntity = new PayEntity();
        payEntity.setId(1L);
        payEntity.setInstallmentsId(1L);
        payEntity.setModality("moda1");
        payEntity.setDay(new Timestamp(System.currentTimeMillis()));
        payEntity.setMethod("Plin");
        payEntity.setAmount(460.20);

        PayDto payDto = PayMapper.fromEntityToDto(payEntity);
        assertNotNull(payDto);
        assertEquals(payEntity.getId(), payDto.getId());
        assertEquals(payEntity.getInstallmentsId(), payDto.getInstallmentsId());
        assertEquals(payEntity.getModality(), payDto.getModality());
        assertEquals(payEntity.getDay(), payDto.getDay());
        assertEquals(payEntity.getMethod(), payDto.getMethod());
        assertEquals(payEntity.getAmount(), payDto.getAmount());
    }

    @Test
    void testFromEntityToDtoList() {
        List<PayEntity> payEntities = new ArrayList<>();
        PayEntity payEntity = new PayEntity();
        payEntity.setId(1L);
        payEntity.setInstallmentsId(1L);
        payEntity.setModality("moda1");
        payEntity.setDay(new Timestamp(System.currentTimeMillis()));
        payEntity.setMethod("Plin");
        payEntity.setAmount(460.20);
        payEntities.add(payEntity);
        List<PayDto> payDtos = PayMapper.fromEntityToDtoList(payEntities);
        assertNotNull(payDtos);
        assertEquals(payEntities.size(), payDtos.size());

    }
}

package pe.a3ya.mspayments.application.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;
import pe.a3ya.mspayments.domain.ports.in.PayServiceIn;

import java.util.List;

@RestController
@RequestMapping("api/v1/pay")
@AllArgsConstructor
public class PayController {
    private final PayServiceIn payServiceIn;

    @PostMapping
    public ResponseEntity<PayDto> pay(@RequestBody PayRequest payRequest) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(payServiceIn.save(payRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayDto> getById(@PathVariable Long id) {
        try {
            PayDto payDto = payServiceIn.getById(id);
            if (payDto == null) {
                return ResponseEntity.
                        status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(payDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<PayDto>> getAll() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(payServiceIn.getAll());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayDto> updatePay(@PathVariable Long id, @RequestBody PayRequest payRequest) {
        try {
            PayDto payDto = payServiceIn.update(id, payRequest);
            if (payDto == null) {
                return ResponseEntity.
                        status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(payDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePay(@PathVariable Long id) {
        try {
            payServiceIn.delete(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

}

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
@AllArgsConstructor
@RequestMapping("api/v1/pay")
public class PayController {
    private final PayServiceIn payServiceIn;

    @PostMapping
    public ResponseEntity<PayDto> pay(@RequestBody PayRequest payRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(payServiceIn.save(payRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayDto> getById(@PathVariable Long id) {
        PayDto payDto = payServiceIn.getById(id);
        if (payDto == null) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(payDto);
    }
    @GetMapping
    public ResponseEntity<List<PayDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(payServiceIn.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayDto> updatePay (@PathVariable Long id, @RequestBody PayRequest payRequest) {
        PayDto payDto = payServiceIn.update(id, payRequest);
        if (payDto == null) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(payDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePay(@PathVariable Long id) {
        payServiceIn.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

}

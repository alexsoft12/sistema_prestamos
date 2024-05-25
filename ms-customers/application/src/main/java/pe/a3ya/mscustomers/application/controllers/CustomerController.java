package pe.a3ya.mscustomers.application.controllers;

import org.springframework.web.bind.annotation.*;
import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;
import pe.a3ya.mscustomers.domain.ports.in.CustomerServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerServiceIn customerServiceIn;

    @PostMapping
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerServiceIn.save(customerRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Long id) {
        CustomerDto customerDto = customerServiceIn.getById(id).orElse(null);
        if (customerDto != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(customerDto);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerServiceIn.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        CustomerDto customerDto = customerServiceIn.update(id, customerRequest);
        if (customerDto != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(customerDto);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerServiceIn.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}

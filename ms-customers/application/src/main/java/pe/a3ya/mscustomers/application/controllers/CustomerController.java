package pe.a3ya.mscustomers.application.controllers;
import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;
import pe.a3ya.mscustomers.domain.ports.in.CustomerServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

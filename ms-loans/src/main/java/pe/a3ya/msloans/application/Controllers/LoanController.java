package pe.a3ya.msloans.application.Controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.ports.in.LoanServiceIn;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/loans")
public class LoanController {

    private final LoanServiceIn loanServiceIn;

    @PostMapping
    public ResponseEntity<LoanDto> register(@Valid @RequestBody LoanRequest loanRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanServiceIn.save(loanRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoanById(@PathVariable Long id) {
        LoanDto loanDto = loanServiceIn.getById(id);
        if (loanDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanDto);
    }

    @GetMapping
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanServiceIn.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDto> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanRequest loanRequest) {
        LoanDto loanDto = loanServiceIn.update(id, loanRequest);
        if (loanDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanServiceIn.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}

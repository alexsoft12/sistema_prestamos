package pe.a3ya.msloans.application.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.a3ya.msloans.domain.aggregates.dto.GuarantiesDto;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.ports.in.LoanServiceIn;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/loans")
@Tag(
        name = "loans maintenance api",
        description = "Api that contains all the end points to maintain the loan entity"
)
public class LoanController {

    private final LoanServiceIn loanServiceIn;

    @PostMapping
    @Operation(
            summary = "Loan register",
            description = "To use the endpoint, you must send a LoanRequest object, which will be saved in the database.",
            parameters = {
                    @Parameter(name="customerId", description="ID customer"),
                    @Parameter(name="amount", description="loan amount requested by the client"),
                    @Parameter(name="paymentMethod", description="payment method requested by the client"),
                    @Parameter(name="paymentType", description="payment type requested by the client"),
                    @Parameter(name="contractDate", description="Date the contract was signed"),
                    @Parameter(name="startDate", description="Contract start date"),
                    @Parameter(name="endDate", description="Contract end date"),
                    @Parameter(name="interestRate", description="Interest rate applied to the loan"),
                    @Parameter(name="status", description="Current status of the loan"),
                    @Parameter(name="term", description="Term of the loan"),
                    @Parameter(name="fee", description="Applicable fee or charge for the loan"),
                    @Parameter(name = "guaranties",
                            description = "List of customer guarantees",
                            schema = @Schema(
                                    type = "array",
                                    implementation = GuarantiesDto.class))
            }
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "customer created successfully",
            content = {@Content(mediaType = "appication/json",
                    schema = @Schema(implementation = LoanRequest.class
                    ))}
    )})
    public ResponseEntity<LoanDto> register(@Valid @RequestBody LoanRequest loanRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanServiceIn.save(loanRequest));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Search loans by ID",
            description = "This endpoint will bring all the loan registered in the database."
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "customer created successfully",
            content = {@Content(mediaType = "appication/json",
                    schema = @Schema(implementation = LoanDto.class
                    ))}
    )})
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
    @Operation(
            summary = "List all Loans",
            description = "This endpoint will bring all the loans registered in the database."
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "customer created successfully",
            content = {@Content(mediaType = "appication/json",
                    schema = @Schema(implementation = LoanDto.class
                    ))}
    )})
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanServiceIn.getAll());
    }


    @PutMapping("/{id}")
    @PostMapping
    @Operation(
            summary = "Update loans by id ",
            description = "To use the endpoint, you must send a loan object along with its id, which will update the fields in the database..",
            parameters = {
                    @Parameter(name="customerId", description="ID customer"),
                    @Parameter(name="amount", description="loan amount requested by the client"),
                    @Parameter(name="paymentMethod", description="payment method requested by the client"),
                    @Parameter(name="paymentType", description="payment type requested by the client"),
                    @Parameter(name="contractDate", description="Date the contract was signed"),
                    @Parameter(name="startDate", description="Contract start date"),
                    @Parameter(name="endDate", description="Contract end date"),
                    @Parameter(name="interestRate", description="Interest rate applied to the loan"),
                    @Parameter(name="status", description="Current status of the loan"),
                    @Parameter(name="term", description="Term of the loan"),
                    @Parameter(name="fee", description="Applicable fee or charge for the loan"),
                    @Parameter(name = "guaranties",
                            description = "List of customer guarantees",
                            schema = @Schema(
                                    type = "array",
                                    implementation = GuarantiesDto.class))
            }
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "loan update successfully",
            content = {@Content(mediaType = "appication/json",
                    schema = @Schema(implementation = LoanRequest.class
                    ))}
    )})
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
    @Operation(
            summary = "Delete loan by id",
            description = "To use this endpoint, you must send a loan id, which will be deleted from the database after validation."
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "loan delete successfully",
            content = {@Content(mediaType = "appication/json",
                    schema = @Schema(implementation = LoanDto.class
                    ))}
    )})
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanServiceIn.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}

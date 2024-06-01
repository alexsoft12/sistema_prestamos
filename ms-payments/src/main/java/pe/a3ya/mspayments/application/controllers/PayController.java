package pe.a3ya.mspayments.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "pay maintenance api",
        description = "Api that contains all the end points to maintain the pay entity"
)
public class PayController {
    private final PayServiceIn payServiceIn;

    @PostMapping
    @Operation(
            summary = "register Payment",
            description = "To use the endpoint, you must send a PayRequest object, which will be saved in the database.",
            parameters = {
                    @Parameter(name = "installmentsId", description = "Identification intallments id"),
                    @Parameter(name = "modality", description = "customer payment modality"),
                    @Parameter(name = "method", description = "customer payment methods."),
                    @Parameter(name = "amount", description = "The customer's date of birth."),

            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "payment created successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = PayRequest.class))})
    })
    public ResponseEntity<PayDto> pay(@RequestBody PayRequest payRequest) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(payServiceIn.save(payRequest));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Search payment by ID",
            description = "To use the endpoint, you must send a payment ID, which will query the database after validation.",
            parameters = {
                    @Parameter(name = "id", description = "payment id"),
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "payment found successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = PayDto.class))})
    })
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
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @GetMapping
    @Operation(
            summary = "List all payments",
            description = "This endpoint will bring all the payment registered in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers found successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = PayDto.class))})
    })
    public ResponseEntity<List<PayDto>> getAll() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(payServiceIn.getAll());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "UpDATE Payment",
            description = "To use the endpoint, you must send a PayRequest object, which will be saved in the database.",
            parameters = {
                    @Parameter(name = "installmentsId", description = "Identification intallments id"),
                    @Parameter(name = "modality", description = "customer payment modality"),
                    @Parameter(name = "method", description = "customer payment methods."),
                    @Parameter(name = "amount", description = "The customer's date of birth."),

            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "payment created successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = PayRequest.class))})
    })
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
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @Operation(
            summary = "Delete customer by id",
            description = "To use this endpoint, you must send a pay id, which will be deleted from the database after validation.",
            parameters = {
                    @Parameter(name = "id", description = "pay id")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pay deleted successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = PayDto.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePay(@PathVariable Long id) {
        try {
            payServiceIn.delete(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

}

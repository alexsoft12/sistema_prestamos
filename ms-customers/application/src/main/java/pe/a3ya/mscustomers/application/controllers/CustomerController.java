package pe.a3ya.mscustomers.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pe.a3ya.mscustomers.domain.aggregates.dto.AddressDto;
import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;
import pe.a3ya.mscustomers.domain.ports.in.CustomerServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/customers")
@Tag(
        name= "client maintenance api",
        description = "Api that contains all the end points to maintain the client entity"
)
public class CustomerController {
    private final CustomerServiceIn customerServiceIn;

    @PostMapping
    @Operation(
                summary = "register customer",
                description = "To use the endpoint, you must send a CustomerRequest object, which will be saved in the database.",
                parameters = {
                        @Parameter(name="documentNumber",description = "Identification document number"),
                        @Parameter(name="email",description = "The client's email address used for communication and notifications"),
                        @Parameter(name="phone",description = "The client's phone number, for contact purposes."),
                        @Parameter(name="birthDate",description = "The customer's date of birth."),
                        @Parameter(
                                name = "addresses",
                                description = "List of customer's addresses",
                                schema = @Schema(
                                        type= "array",
                                        implementation = AddressDto.class
                                )
                        )
                })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer created successfully",
                content = {@Content(mediaType = "appication/json",
                        schema = @Schema(implementation = CustomerRequest.class))})
    })
    public ResponseEntity<CustomerDto> register(@Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerServiceIn.save(customerRequest));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Search customer by ID",
            description = "To use the endpoint, you must send a customer ID, which will query the database after validation.",
            parameters = {
                    @Parameter(name = "id",description = "customer id"),
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = CustomerDto.class))})
    })
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
    @Operation(
            summary = "List all customers",
            description = "This endpoint will bring all the customers registered in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers found successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = CustomerDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerServiceIn.getAll());
    }
    @Operation(
                summary = "Update customer by id",
                description = "To use the endpoint, you must send a customer object along with its id, which will update the fields in the database.",
                parameters = {
                        @Parameter(name="documentNumber",description = "Identification document number"),
                        @Parameter(name="email",description = "The client's email address used for communication and notifications"),
                        @Parameter(name="phone",description = "The client's phone number, for contact purposes."),
                        @Parameter(name="birthDate",description = "The customer's date of birth."),
                        @Parameter(
                                name = "addresses",
                                description = "List of customer's addresses",
                                schema = @Schema(
                                        type= "array",
                                        implementation = AddressDto.class
                                )
                            )
                })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = CustomerRequest.class))})
    })
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
    @Operation(
            summary = "Delete customer by id",
            description = "To use this endpoint, you must send a customer id, which will be deleted from the database after validation.",
            parameters = {
                    @Parameter(name = "id",description = "customer id")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company deleted successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = CustomerDto.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerServiceIn.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

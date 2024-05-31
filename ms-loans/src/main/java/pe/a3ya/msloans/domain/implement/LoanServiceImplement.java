package pe.a3ya.msloans.domain.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.aggregates.requests.PaymentInstallmentRequest;
import pe.a3ya.msloans.domain.ports.in.LoanServiceIn;
import pe.a3ya.msloans.domain.ports.out.LoanServiceOut;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanServiceImplement implements LoanServiceIn {
    private final LoanServiceOut loanServiceOut;


    @Override
    public LoanDto save(LoanRequest loanRequest) {
        calculateLoan(loanRequest);
        generatePaymentInstallment(loanRequest);

        return loanServiceOut.save(loanRequest);
    }

    @Override
    public LoanDto getById(Long id) {
        return loanServiceOut.getById(id);
    }

    @Override
    public List<LoanDto> getAll() {
        return loanServiceOut.getAll();
    }

    @Override
    public LoanDto update(Long id, LoanRequest loanRequest) {
        calculateLoan(loanRequest);
        generatePaymentInstallment(loanRequest);
        return loanServiceOut.update(id, loanRequest);
    }

    @Override
    public void delete(Long id) {
        loanServiceOut.delete(id);
    }

    private static void calculateLoan(LoanRequest loanRequest) {
        int term = loanRequest.getTerm();
        double amount = loanRequest.getAmount();
        double interestRate = loanRequest.getInterestRate();
        // amount = 1000.00, interestRate = 0.1, term = 4
        double fee = (amount * interestRate) / (1 - Math.pow(1 + interestRate, -term));
        loanRequest.setFee(fee);

        switch (loanRequest.getPaymentMethod()) {
            case "monthly":
                loanRequest.setStartDate(loanRequest.getContractDate().plusMonths(1));
                loanRequest.setEndDate(loanRequest.getContractDate().plusMonths(term));
                break;
            case "weekly":
                loanRequest.setStartDate(loanRequest.getContractDate().plusWeeks(1));
                loanRequest.setEndDate(loanRequest.getContractDate().plusWeeks(term));
                break;
            case "biweekly":
                loanRequest.setStartDate(loanRequest.getContractDate().plusWeeks(2));
                loanRequest.setEndDate(loanRequest.getContractDate().plusWeeks(term * 2L));
                break;
            case "daily":
                loanRequest.setStartDate(loanRequest.getContractDate().plusDays(1));
                loanRequest.setEndDate(loanRequest.getContractDate().plusDays(term));
                break;
        }
    }

    private static void generatePaymentInstallment(LoanRequest loanRequest) {
        List<PaymentInstallmentRequest> paymentInstallments = new ArrayList<>();
        for (int i = 0; i < loanRequest.getTerm(); i++) {
            PaymentInstallmentRequest paymentInstallmentRequest = new PaymentInstallmentRequest();
            switch (loanRequest.getPaymentMethod()) {
                case "monthly" -> {
                    paymentInstallmentRequest.setStartDate(loanRequest.getContractDate().plusDays(15).plusMonths(i));
                    paymentInstallmentRequest.setEndDate(loanRequest.getContractDate().plusDays(30).plusMonths(i));
                }
                case "weekly" -> {
                    paymentInstallmentRequest.setStartDate(loanRequest.getContractDate().plusDays(4).plusWeeks(i));
                    paymentInstallmentRequest.setEndDate(loanRequest.getContractDate().plusDays(7).plusWeeks(i));
                }
                case "biweekly" -> {
                    paymentInstallmentRequest.setStartDate(loanRequest.getContractDate().plusDays(8).plusWeeks(i));
                    paymentInstallmentRequest.setEndDate(loanRequest.getContractDate().plusDays(15).plusWeeks(i));
                }
                case "daily" -> {
                    paymentInstallmentRequest.setStartDate(loanRequest.getContractDate().plusDays(1).plusDays(i));
                    paymentInstallmentRequest.setEndDate(loanRequest.getContractDate().plusDays(1).plusDays(i));
                }
            }
            paymentInstallmentRequest.setAmount(loanRequest.getFee());
            paymentInstallmentRequest.setStatus("pending");
            paymentInstallments.add(paymentInstallmentRequest);
        }
        loanRequest.setPaymentInstallments(paymentInstallments);
    }
}

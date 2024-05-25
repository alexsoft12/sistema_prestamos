package pe.a3ya.mscustomers.infraestructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import pe.a3ya.mscustomers.domain.aggregates.constants.Constant;
import pe.a3ya.mscustomers.domain.aggregates.dto.ReniecDto;

@FeignClient(name = "apis-net", url = Constant.APIS_NET_URL + "reniec/")
public interface ApisNetReniecClient {

        @GetMapping("dni")
        ReniecDto getReniecInfo(
                @RequestParam("numero") String ruc,
                @RequestHeader("Authorization") String token
        );
}

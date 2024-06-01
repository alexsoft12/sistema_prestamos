package pe.a3ya.mscustomers.domain.aggregates.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    public static final String APIS_NET_URL = "https://api.apis.net.pe/v2/";
    public static final String REDIS_KEY_GETCUSTOMER="MS:REGISTER:CUSTOMER:";
}

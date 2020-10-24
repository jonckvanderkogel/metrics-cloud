package com.bullet.metricscloud.domain;

import java.util.List;
import java.util.Map;

public enum Category {
    COUNTRY,OS,MOBILE_PROVIDER,APP_VERSION,URL,USER_TYPE,AUTHORIZED;

    public static Map<Category, List<String>> valueMap = Map.of(
            COUNTRY, List.of("Netherlands", "Belgium", "Luxemburg", "Austria", "Germany"),
            OS, List.of("Android", "iOS"),
            MOBILE_PROVIDER, List.of("KPN", "Vodafone", "T-Mobile", "Tele2", "Simpel"),
            APP_VERSION, List.of("1.0", "1.1", "1.2", "1.2.1", "1.3", "1.4", "1.4.1", "1.4.2", "2.0", "2.1", "2.2", "2.3"),
            URL, List.of("/payments/prepare", "/payments/execute", "/loans", "/loans/{id}", "/loans/amortize", "/mortgages",
                    "/mortgages/{id}", "/creditcards", "/creditcards/pay/prepare", "/creditcards/pay/execute"),
            USER_TYPE, List.of("retail", "business"),
            AUTHORIZED, List.of("true", "false"));
}

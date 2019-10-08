package com.revolut.realtimerates.util;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Constants {
    public static final String CURRENCY_RATES = "currency-rates";
    static final char[] PHRASE = {'R', 'A', 'T', 'E', '1'};
    static final HashMap<String, String> KNOWN_CURRENCIES = new HashMap<String, String>() {{
        put("USD","United States dollar");
        put("AUD","Australian dollar");
        put("CHF","Swiss franc");
        put("RUB","Russian ruble");
        put("CNY","Chinese yuan");
        put("EUR","Euro");
        put("SEK","Swedish krona");
        put("SGD","Singapore dollar");
        put("THB","Thai baht");
        put("TRY","Turkish lira");
        put("BGN","Bulgarian lev");
        put("BRL","Brazilian real");
        put("CAD","Canadian dollar");
        put("CZK","Czech koruna");
        put("DKK","Danish Krone");
        put("GBP","Pound sterling");
        put("HKD","Hong Kong dollar");
        put("RON","Romanian leu");
        put("HRK","Croatian kuna");
        put("HUF","Hungarian forint");
        put("JPY","Japanese yen");
        put("KRW","South Korean won");
        put("MXN","Mexican peso");
        put("MYR","Malaysian ringgit");
        put("NOK","Norwegian krone");
        put("NZD","New Zealand dollar");
        put("IDR","Indonesian rupiah");
        put("ILS","Israeli new shekel");
        put("INR","Indian rupee");
        put("ISK","Icelandic krona");
        put("PHP","Philippine peso");
        put("PLN","Polish złoty");
        put("ZAR","South African rand");
    }};
    private static final String CURRENCY_FORMAT = "###,###,###.##";
    static final DecimalFormat CURRENCY_FORMATTER = new DecimalFormat(Constants.CURRENCY_FORMAT);
    public static final String REGEX_RULE = "[^\\d.]";
    public static final String DIVIDER = ".";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
}

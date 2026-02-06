package org.demo.money;


import module java.base;
import module org.javamoney.moneta;

public class MoneyTool {

    public static final CurrencyUnit rmb = Monetary.getCurrency(Locale.CHINA);

    final static MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(
            AmountFormatQueryBuilder.of(Locale.CHINA).build());

    public static String formatPrice(Money money) {
        return format.format(money);
    }
}

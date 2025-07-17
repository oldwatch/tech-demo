package org.demo.money;

import org.javamoney.moneta.Money;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class MoneyTool {

    final static MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(
            AmountFormatQueryBuilder.of(Locale.CHINA).build());

    public static String formatPrice(Money money) {
        return format.format(money);
    }
}

import org.javamoney.moneta.Money;
import org.javamoney.moneta.RoundedMoney;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryContext;
import javax.money.MonetaryContextBuilder;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryFormats;
import java.math.RoundingMode;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMoney {

    private static final Logger log = LoggerFactory.getLogger(TestMoney.class);

    private static final CurrencyUnit rmb = Monetary.getCurrency(Locale.CHINA);


    @Test
    public void testContext() {
        var context1 =
                MonetaryContextBuilder.of(RoundedMoney.class)
                        .setPrecision(0)
                        .set(RoundingMode.HALF_DOWN)
                        .build();

        var summary1 = doCompute(context1);

        var context2 =
                MonetaryContextBuilder.of(RoundedMoney.class)
                        .setPrecision(0)
                        .set(RoundingMode.CEILING)
                        .build();

        var summary2 = doCompute(context2);

        assertTrue(summary1.isEqualTo(summary2));
    }

    public Money doCompute(MonetaryContext context) {

        var m1 = Money.of(125.35, rmb, context);
        log.info(" rounded mode:{}", m1.getContext());

        var m2 = Money.of(5.55, rmb, context);

        var summary = m1.multiply(3.8).multiply(0.8).add(m2);

        var format = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(Locale.CHINA).build());

        log.info("summary:{}", format.format(summary));

        return summary;
    }

}

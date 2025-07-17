package org.demo.money;

import org.javamoney.moneta.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;

@ReadingConverter
public class DecimalToMoneyConvert implements Converter<BigDecimal, Money> {
    @Override
    public Money convert(BigDecimal source) {
        return Money.of(source, MoneyTool.rmb);
    }
}

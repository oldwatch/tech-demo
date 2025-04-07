package org.demo.money;

import org.javamoney.moneta.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.math.BigDecimal;

@WritingConverter
public class MoneyToDecimalConvert implements Converter<Money, BigDecimal> {

    @Override
    public BigDecimal convert(Money source) {
        return new BigDecimal(source.toString());
    }
}

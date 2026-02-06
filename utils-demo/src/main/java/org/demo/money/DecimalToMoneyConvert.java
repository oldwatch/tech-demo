package org.demo.money;

import module java.base;
import module org.javamoney.moneta;
import module spring.data.commons;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

@ReadingConverter
public class DecimalToMoneyConvert implements ConverterFactory<BigDecimal, Money> {

    @Override
    public <T extends Money> Converter<BigDecimal, ? extends T> getConverter(Class<T> targetType) {
        return (source -> (T) Money.of(source, MoneyTool.rmb));
    }
}

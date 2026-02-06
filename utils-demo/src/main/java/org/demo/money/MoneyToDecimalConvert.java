package org.demo.money;


import module java.base;
import module org.javamoney.moneta;
import module spring.data.commons;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

@WritingConverter
public class MoneyToDecimalConvert implements ConverterFactory<Money, BigDecimal> {

    @Override
    public <T extends BigDecimal> Converter<Money, ? extends T> getConverter(Class<T> targetType) {
        return source -> (T) (new BigDecimal(source.toString()));
    }


}

package com.demo.grpc.service;

import io.r2dbc.spi.ConnectionFactory;
import org.demo.money.DecimalToMoneyConvert;
import org.demo.money.MoneyToDecimalConvert;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConnectFactory extends AbstractR2dbcConfiguration {

    @Override
    public ConnectionFactory connectionFactory() {
        // Configure your ConnectionFactory (e.g., H2ConnectionFactory, PostgresqlConnectionFactory)
        return null; // Replace with actual connection factory
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new MoneyToDecimalConvert());
        converters.add(new DecimalToMoneyConvert());
        return converters;
    }
}

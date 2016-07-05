package com.scottlogic.gradtrader.price.source;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;
import com.scottlogic.gradtrader.pair.Pair;
import com.scottlogic.gradtrader.price.PriceException;

public class FakePriceSourceFactory implements PriceSourceFactory {

    @Inject
    private GradTraderConfiguration configuration;

    private Map<String, PriceSource> priceSources = new LinkedHashMap<String, PriceSource>();

    public PriceSource getPriceSource(String pairName) throws PriceException {
        PriceSource priceSource = priceSources.get(pairName);
        if (priceSource == null) {
            if (!configuration.getValidPairs().containsKey(pairName)) {
                throw new PriceException("Invalid pair");
            }
            long testPrice;
            try {
                Map<String, Long> testPrices = configuration.getTestPrices();
                testPrice = testPrices.get(pairName);
            } catch (Exception e) {
                throw new PriceException("Missing currency pair configuration");
            }
            Map<String, Pair> validPairs = configuration.getValidPairs();
            Pair pair = validPairs.get(pairName);
            int priceDecimals = pair.getDecimals();
            long pointSize = (long) Math.pow(10, priceDecimals);
            priceSource = new FakePriceSource(pairName, testPrice, 20000L,
                    2 * pointSize / 100L, 2 * pointSize / 100L);
            priceSources.put(pairName, priceSource);
        }
        return priceSource;

    }

}

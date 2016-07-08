package com.scottlogic.gradtrader.price.source;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import com.google.inject.Inject;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;
import com.scottlogic.gradtrader.pair.Pair;
import com.scottlogic.gradtrader.price.PriceException;

public class FakePriceSourceFactory implements PriceSourceFactory {

    @Inject
    private GradTraderConfiguration configuration;

    private Map<String, PriceSource> priceSources = new LinkedHashMap<>();

    private Random random = new Random(System.currentTimeMillis());

    public PriceSource getPriceSource(String pairId) throws PriceException {
        PriceSource priceSource = priceSources.get(pairId);
        if (priceSource == null) {
            if (!configuration.getValidPairs().containsKey(pairId)) {
                throw new PriceException("Invalid pair");
            }
            long testPrice;
            try {
                Map<String, Long> testPrices = configuration.getTestPrices();
                testPrice = testPrices.get(pairId);
            } catch (Exception e) {
                throw new PriceException("Missing currency pair configuration");
            }
            Map<String, Pair> validPairs = configuration.getValidPairs();
            Pair pair = validPairs.get(pairId);
            int priceDecimals = pair.getDecimals();
            long pointSize = (long) Math.pow(10, priceDecimals);
            long range = 2 * pointSize / (500 + random.nextInt(1500));
            long period = (15000 + random.nextInt(15000));
            long spread = 2 * pointSize / (500 + random.nextInt(1500));
            priceSource = new FakePriceSource(pairId, testPrice, period, range, spread);
            priceSources.put(pairId, priceSource);
        }
        return priceSource;

    }

}

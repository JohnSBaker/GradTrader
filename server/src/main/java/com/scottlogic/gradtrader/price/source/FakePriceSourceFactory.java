package com.scottlogic.gradtrader.price.source;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;
import com.scottlogic.gradtrader.price.PriceException;

public class FakePriceSourceFactory implements PriceSourceFactory {

    private final Map<String, PriceSource> priceSources = new LinkedHashMap<>();
    private final Random random = new Random(System.currentTimeMillis());
    @Inject
    private GradTraderConfiguration configuration;

    public PriceSource getPriceSource(final String pairId) throws PriceException {
        PriceSource priceSource = priceSources.get(pairId);
        if (priceSource == null) {
            if (!configuration.getValidPairs().containsKey(pairId)) {
                throw new PriceException("Invalid pair");
            }
            final Map<String, PriceConfiguration> priceConfigurations = configuration.getPriceConfigurations();
            if (!priceConfigurations.containsKey(pairId)) {
                throw new PriceException("Missing currency pair configuration");
            }
            final PriceConfiguration priceConfiguration = priceConfigurations.get(pairId);

            final long basePrice = priceConfiguration.getBasePrice();

            final int frequencyMillis = priceConfiguration.getBroadcastFrequencyMillis();
            final int randomisedFrequencyMillis = frequencyMillis / 2 + random.nextInt(frequencyMillis);

            final FakePriceSource.Period[] spreadPeriods = new FakePriceSource.Period[] {
                    new FakePriceSource.SpreadPeriod(getRandom(TimeUnit.SECONDS, 5, 120), getRandom(20, 150),
                            FakePriceSource.Function.SIN),
                    new FakePriceSource.SpreadPeriod(getRandom(TimeUnit.HOURS, 2, 24), getRandom(10, 150),
                            FakePriceSource.Function.SIN) };

            final FakePriceSource.Function longTermFunction = random.nextBoolean() ? FakePriceSource.Function.SIN
                    : FakePriceSource.Function.COS;
            final FakePriceSource.Period[] periods = new FakePriceSource.Period[] {
                    new FakePriceSource.Period(getRandom(TimeUnit.SECONDS, 30, 90), getRandom(10, 100),
                            FakePriceSource.Function.SIN),
                    new FakePriceSource.Period(getRandom(TimeUnit.MINUTES, 30, 90), getRandom(10, 100),
                            FakePriceSource.Function.COS),
                    new FakePriceSource.Period(getRandom(TimeUnit.HOURS, 6, 72), getRandom(100, 200),
                            FakePriceSource.Function.SIN),
                    new FakePriceSource.Period(getRandom(TimeUnit.HOURS, 6, 72), getRandom(100, 200),
                            FakePriceSource.Function.COS),
                    new FakePriceSource.Period(getRandom(TimeUnit.DAYS, 2, 30), getRandom(50, 400),
                            FakePriceSource.Function.SIN),
                    new FakePriceSource.Period(getRandom(TimeUnit.DAYS, 2, 30), getRandom(50, 400),
                            FakePriceSource.Function.COS),
                    new FakePriceSource.Period(getRandom(TimeUnit.DAYS, 24, 224), getRandom(1000, 1000),
                            longTermFunction) };

            priceSource = new FakePriceSource(pairId, randomisedFrequencyMillis, basePrice, periods, spreadPeriods);
            priceSources.put(pairId, priceSource);
        }
        return priceSource;
    }

    long getRandom(final TimeUnit timeUnit, final long duration, final long randomDuration) {
        return getRandom(timeUnit.toMillis(duration), timeUnit.toMillis(randomDuration));
    }

    long getRandom(final long base, final long random) {
        return base + (long) (this.random.nextDouble() * random);
    }
}

package com.scottlogic.gradtrader.price.source;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scottlogic.gradtrader.price.Price;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.history.PriceHistory;

public class FakePriceSource implements PriceSource {
    public static enum Function {
        NONE, SIN, COS
    }

    private enum Extreme {
        LOW, HIGH
    }

    public static class Period {

        private final long range;
        private final long periodMillis;
        private final Function function;

        public Period(final long periodMillis, final long range, final Function function) {
            this.periodMillis = periodMillis;
            this.range = range;
            this.function = function;
        }

        protected double getAngle(final double ratio) {
            return ratio * Math.PI * 2;
        }

        protected double getRatio(final long time) {
            return (time % periodMillis) / (double) periodMillis;
        }

        protected double applyFunction(final double ratio) {
            final double angle = getAngle(ratio);
            switch (function) {
            case SIN:
                return Math.sin(angle);
            case COS:
                return Math.cos(angle);
            default:
                return ratio;
            }
        }

        public long process(final long time) {
            final double ratio = getRatio(time);
            return processRatio(ratio);
        }

        public long processRatio(final double ratio) {
            return (long) (applyFunction(ratio) * range);
        }

        public double getExtremeRatio(final Extreme extreme) {
            switch (function) {
            case SIN:
                switch (extreme) {
                case HIGH:
                    return 0.25;
                case LOW:
                    return 0.75;
                }
            case COS:
                switch (extreme) {
                case HIGH:
                    return 0;
                case LOW:
                    return 0.5;
                }
            default:
                return 0;
            }
        }

        public long getExtreme(final Extreme extreme, final long from, final long to) {
            return getExtreme(extreme, getExtremeRatio(extreme), from, to);
        }

        public long getExtreme(final Extreme extreme, final double extremeRatio, final long from, final long to) {
            if (to - from > periodMillis) {
                return processRatio(extremeRatio);
            } else {
                final double fromRatio = getRatio(from);
                final double toRatio = getRatio(to);
                double extremeRatioMin = (1 + (extremeRatio - 0.25)) % 1;
                double extremeRatioMax = (1 + (extremeRatio + 0.25)) % 1;
                if (fromRatio > extremeRatioMin && fromRatio < extremeRatioMax && toRatio >= extremeRatio) {
                    return processRatio(extremeRatio);
                } else {
                    switch (extreme) {
                    case HIGH:
                        return Math.max(processRatio(fromRatio), processRatio(toRatio));
                    case LOW:
                        return Math.min(processRatio(fromRatio), processRatio(toRatio));
                    default:
                        return 0;
                    }
                }
            }
        }
    }

    public static class SpreadPeriod extends Period {
        public SpreadPeriod(final long periodMillis, final long range, final Function function) {
            super(periodMillis, range, function);
        }

        @Override
        protected double getAngle(final double ratio) {
            return ratio * Math.PI;
        }

        @Override
        public long process(final long time) {
            return 1 + super.process(time);
        }
    }

    private final Logger logger = LoggerFactory.getLogger(FakePriceSource.class);

    private final String pairId;
    private final int frequencyMillis;
    private final long base;
    private final Period[] periods;
    private final Period[] spreadPeriods;

    public FakePriceSource(final String pairId, final int frequencyMillis, final long base, final Period[] periods,
            final Period[] spreadPeriods) {
        this.pairId = pairId;
        this.frequencyMillis = frequencyMillis;
        this.base = base;
        this.periods = periods;
        this.spreadPeriods = spreadPeriods;
        logger.debug("Fake price source for {}: base {}", pairId, base);
        for (final Period period : periods) {
            logger.debug("\tPeriod {}, Range {}", period.periodMillis, period.range);
        }
        for (final Period period : spreadPeriods) {
            logger.debug("\tSpread Period {}, Range {}", period.periodMillis, period.range);
        }
    }

    public int getFrequencyMillis() {
        return frequencyMillis;
    }

    public Price getPrice() {
        return getPrice(System.currentTimeMillis());
    }

    public Price getPrice(final long time) {
        final long mid = getMid(time);
        final long spread = processPeriods(spreadPeriods, time);
        return new Price(pairId, time, mid - spread / 2, mid + spread / 2);
    }

    int getTick(final long time) {
        return (int) (time / frequencyMillis);
    }

    long roundToTick(final long time) {
        return Math.round(time / (double) frequencyMillis) * frequencyMillis;
    }

    private long getMid(final long time) {
        return base + processPeriods(periods, time);
    }

    private long processPeriods(final Period[] periods, final long time) {
        long value = 0;
        final long roundedToTickTime = roundToTick(time);
        for (final Period period : periods) {
            value += period.process(roundedToTickTime);
        }
        return value;
    }

    private long getExtreme(final Extreme extreme, final long from, final long to) {
        // This is approximate as this doesn't take into account the
        // interleaving of the curves
        long value = base;
        final long roundedToTickFrom = roundToTick(from);
        final long roundedToTickTo = roundToTick(to);
        for (final Period period : periods) {
            value += period.getExtreme(extreme, roundedToTickFrom, roundedToTickTo);
        }
        return value;
    }

    public PriceHistory getPriceHistory(final long from, final long to) throws PriceException {
        if (to - from < frequencyMillis) {
            throw new PriceException("Invalid price history resolution");
        }
        final long timestamp = (from + to) / 2;
        final long open = getMid(from);
        final long close = getMid(to);

        final long low = getExtreme(Extreme.LOW, from, to);
        final long high = getExtreme(Extreme.HIGH, from, to);
        return new PriceHistory(timestamp, open, close, low, high);
    }

    public Collection<PriceHistory> getPriceHistory(final long from, final long to, final long resolution)
            throws PriceException {
        final List<PriceHistory> priceHistories = new LinkedList<>();
        for (long currentFrom = from; currentFrom < to; currentFrom += resolution) {
            priceHistories.add(getPriceHistory(currentFrom, currentFrom + resolution));
        }
        return priceHistories;
    }
}

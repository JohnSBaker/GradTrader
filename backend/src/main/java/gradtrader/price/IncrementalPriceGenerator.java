package gradtrader.price;

import com.google.common.util.concurrent.AtomicDouble;


public class IncrementalPriceGenerator implements PriceGenerator{

  private final double min;
  private final double max;
  private final double halfSpread;
  private AtomicDouble delta;

  private AtomicDouble current;

  public IncrementalPriceGenerator(double min, double max, double delta, double spread){
    this.min = min;
    this.max = max;
    this.halfSpread = spread / 2.0;
    this.delta = new AtomicDouble(delta);
    this.current = new AtomicDouble(min);
  }

  public synchronized Price generate(){
    double next = current.get() + delta.get();
    if (next < min || next > max){
      delta.set(delta.get() * -1);
      next = current.get() + delta.get();
    }
    current.addAndGet(delta.get());
    return new Price(current.get() - halfSpread, current.get() + halfSpread);
  }

}

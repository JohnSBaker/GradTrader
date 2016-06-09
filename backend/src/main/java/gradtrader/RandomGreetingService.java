package gradtrader;

import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class RandomGreetingService implements GreetingService{

  private String[] greetings = {"Hello","Bonjour","Ciao","Guten Tag","Buenos Dias"};
  private final String template = "%s %s";
  private Random random = new Random();

  public String createGreeting(String recipient){

    int idx = random.nextInt(greetings.length);
    String greeting = greetings[idx];
    return String.format(template, greeting, recipient);
  }
}

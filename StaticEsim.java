// Jussin aivojumppaa...
public class StaticEsim {
  private int x = 0;

  public static int kasvata(StaticEsim tämä) {
    return ++tämä.x;
  }

  public int pienennä() {
    return --this.x;
  }

  public static void main(String[] argh) {
    StaticEsim tämä = new StaticEsim();
    System.out.println(kasvata(tämä));
    System.out.println(tämä.pienennä());
  }
}

// Jussin aivojumppaa...
public class StaticEsim {
  private int x = 0;

  public static int kasvata(StaticEsim t�m�) {
    return ++t�m�.x;
  }

  public int pienenn�() {
    return --this.x;
  }

  public static void main(String[] argh) {
    StaticEsim t�m� = new StaticEsim();
    System.out.println(kasvata(t�m�));
    System.out.println(t�m�.pienenn�());
  }
}

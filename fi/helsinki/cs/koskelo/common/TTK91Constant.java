
/**
 * Luokka Koskelon käyttämien vakioiden yhtenäiseksi sijaintipaikaksi
 */

public final class TTK91Constant {

  // common.TTK91TaskOptions
	public final static int COMPARE_TO_SIMULATED = 0; 
	public final static int COMPARE_TO_STATIC = 1;
  public final static int MAX_COMMANDS = 10000; 

  // common.TTK91TaskCriteria
  public final int INVALID = -1; // Alustamaton vertailu.
  public final int LESS = 0; // <
  public final int LESSEQ = 1; // <=
  public final int GREATER = 2; // >
  public final int GREATEREQ = 3; // >=
  public final int EQUAL = 4; // =
  public final int NOTEQUAL = 5; // !=
  public final int NOTCOMPARABLE = 6; // Tulosteita varten joissa
                                   // ei ole loogista operaattoria

}

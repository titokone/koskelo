import javax.servlet.*;
import javax.servlet.http.*;

/*
 * Apuluokka TaskDefinitionControlleria varten. Koostaa annetusta datasta
 * TaskDTO-olion. TTK91Taskparser saa parametrin‰ PostParameterParserin
 * sek‰ HttpSession. PostParameterParser tarjoaa post-kutsun mukana
 * tulleet palaute-kent‰t ja HttpSessiosta saadaan TTK91TaskOptions-olio
 * joka sis‰lt‰‰ kaiken muun teht‰v‰n m‰‰rittelyn.
 *
 * Luokka saa tiet‰‰ TTK91TaskOptionsin metodien avulla montako kriteerin
 * palautetta PostParameterParser tarjoaa (tarkistamalla ko. kriteereiden
 * taulukon koon esim. getMemoryCriterias.size()).
 *
 * Postin mukana tulleet kent‰t on nimetty juoksevasti
 * (ks. luokka SyntaxChecker)
 * samalla tavalla. Kun TTK91TaskParser tiet‰‰ palautteiden m‰‰r‰n, niin
 * se voi liitt‰‰ ne oikeisiin kriteereihin.
 *
 */
public class TTK91TaskParser {

	private static final String EXAMPLE_CODE
		= "exampleCode";
	private static final String TASK_DESCRIPTION
		= "taskDescription";
	private static final String REGISTER_FEEDBACK_POSITIVE
		= "registerFeedbackPositive";
	private static final String REGISTER_FEEDBACK_NEGATIVE
		= "registerFeedbackNegative";
	private static final String MEMORY_FEEDBACK_POSITIVE
		= "memoryFeedbackPositive";
	private static final String MEMORY_FEEDBACK_NEGATIVE
		= "memoryFeedbackNegative";
	//Jne.

	public static TaskDTO assembleStaticTTK91Task(
			PostParameterParser post,
			HttpSession session
			) {

		TaskDTO newTask = new TaskDTO();
		TTK91TaskOptions taskData
			= (TTK91TaskOptions)session.getAttribute(
					"fi.helsinki.cs.koskelo.common."+
					"TTK91TaskOptions"
					);

		newTask.set( EXAMPLE_CODE, taskData.getExampleCode() );
		newTask.set( TASK_DESCRIPTION, taskData.getTaskDescription() );
		//Jne.
		/*
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   newTask.set(, );
		   */

		String regpos = post.getStringParameter(REGISTER_FEEDBACK_POSITIVE);
		newTask.set(REGISTER_FEEDBACK_POSITIVE, regpos);
		String regneg = post.getStringParameter(REGISTER_FEEDBACK_NEGATIVE);
		newTask.set(_FEEDBACK_POSITIVE, regneg);
		String mempos = post.getStringParameter(MEMORY_FEEDBACK_POSITIVE);
		newTask.set(MEMORY_FEEDBACK_POSITIVE, mempos);
		String memneg = post.getStringParameter(MEMORY_FEEDBACK_NEGATIVE);
		newTask.set(MEMORY_FEEDBACK_NEGATIVE, memneg);
		//Jne.

		return newTask;

	}//assembleStaticTTK91Task

}//class

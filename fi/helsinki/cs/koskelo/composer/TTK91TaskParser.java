import javax.servlet.*;
import javax.servlet.http.*;
import fi.helsinki.cs.koskelo.common.*;

package fi.helsinki.cs.koskelo.composer;

public class TTK91TaskParser {
	/*
	   Ei, seuraavista huolimatta tämä luokka ei ole mikään pikkuvarasto.
	   Kentät ovat tässä vain parametrinvälitystä helpottamassa.
	   Vähän kömpelöä, mutta kukin taapertaa omalla tyylillään jne.
	 */
	private PostParameterParser post;
	private TTK91TaskOptions tops;
	private TaskDTO newTask;

	private static final String SPOT
		= "fi.helsinki.cs.koskelo.common.TTK91TaskOptions";

	/**
	 * Konstruktori luo sisäiseen tiedonvälitykseen tarkoitetun
	 * olion ja estää luokan ulkopuoliset ilmentymäpyynnöt.
	 */
	private TTK91TaskParser(
			PostParameterParser post,
			HttpSession session
			) {
		this.post = post;
		this.tops = (TTK91TaskOptions)session.getAttribute(SPOT);
		this.newTask = new TaskDTO();
	}

	public static TaskDTO assembleFillInTTK91Task(
			PostParameterParser post,
			HttpSession session
			) {
		TTK91TaskParser turf // (lue turf suomeksi hiekkalaatikoksi)
			= new TTK91TaskParser(post, session);
		turf.assembleCommon();
		turf.assembleFillIn();
		return turf.newTask;
	}

	public static TaskDTO assembleStaticTTK91Task(
			PostParameterParser post,
			HttpSession session
			) {
		TTK91TaskParser turf
			= new TTK91TaskParser(post, session);
		turf.assembleCommon();
		turf.assembleStatic();
		return turf.newTask;
	}

	public static TaskDTO assembleDynamicTTK91Task(
			PostParameterParser post,
			HttpSession session
			) {
		TTK91TaskParser turf
			= new TTK91TaskParser(post, session);
		turf.assembleCommon();
		turf.assembleDynamic();
		return turf.newTask;
	}

	private void assembleCommon() {
		newTask.set("acceptedSize", tops.getAcceptedSize());
		newTask.set("compareMethod", tops.getCompareMethod());
		newTask.set("exampleCode", tops.getExampleCode);
		newTask.set("fileOutputCriterias", tops.getFileOutputCriterias());
		newTask.set("forbiddenCommands", tops.getForbiddenCommands());
		newTask.set("hiddenInput", tops.getHiddenInput());
		newTask.set("maxCommands", tops.getMaxCommands());
		newTask.set("memoryCriterias", tops.getMemoryCriterias());
		newTask.set("memoryReferences", tops.getMemoryReferences()); // ?
		newTask.set("optimalSize", tops.getOptimalSize());
		newTask.set("publicInput", tops.getPublicInput());
		newTask.set("registerCriterias", tops.getRegisterCriterias());
		newTask.set("requiredCommands", tops.getRequiredCommands());
		newTask.set("screenOutputCriterias",
				tops.getScreenOutputCriterias());
		newTask.set("taskDescription", tops.getTaskDescription());
		newTask.set("taskFeedback", tops.getTaskFeedback); // ?

		// ?:lliset  näyttävät puuttuvan TTK91TaskOptionsista...

		// FIXME: kuuluisikohan jokin ylläolevista set-kutsuista
		// alla oleviin metodeihin?
	}

	private void assembleDynamic() {
	}

	private void assembleStatic() {
	}

	private void assembleFillIn() {
	}

	// Hmm... PostParameterParser postillakin piti kai tehdä jotakin?
}

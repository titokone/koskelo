package fi.helsinki.cs.koskelo.composer;
//TODO: JAVADOCIT JA KOMMENTIT
//by Harri Tuomikoski, 17.11.2004

import javax.servlet.*;
import javax.servlet.http.*;
import fi.helsinki.cs.koskelo.common.*;
import fi.hy.eassari.taskdefinition.util.*;
import fi.hy.eassari.taskdefinition.util.PostParameterParser;
import fi.hy.eassari.taskdefinition.util.datastructures.*;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;

public class TTK91TaskParser {

 private static final String OPTIONS_KEY = "fi.helsinki.cs.koskelo.common.TTK91TaskOptions";

 private static final String REGISTER_VALUES  = "registerValues";
 private static final String MEMORY_VALUES = "memoryValues";
 private static final String SCREEN_OUTPUT = "screenOutput";
 private static final String FILE_OUTPUT = "fileOutput";

 private static final String EXAMPLE_CODE = "exampleCode";
 private static final String TASK_DESCRIPTION = "taskDescription";
 private static final String PUBLIC_INPUT = "publicInput";
 private static final String HIDDEN_INPUT = "hiddenInput";
 private static final String COMPARE_METHOD = "compareMethod";
 private static final String MAX_COMMANDS = "maxCommands";
 private static final String ACCEPTED_SIZE = "acceptedSize";
 private static final String OPTIMAL_SIZE = "optimalSize";
 private static final String MEMORY_REFERENCES = "memoryReferences";
 private static final String REQUIRED_COMMANDS = "requiredCommands";
 private static final String FORBIDDEN_COMMANDS = "forbiddenCommands";

 private static final String REGISTER_FEEDBACK_POSITIVE = "registerFeedbackPositive";
 private static final String REGISTER_FEEDBACK_NEGATIVE = "registerFeedbackNegative";
 private static final String REGISTER_QUALITY_FEEDBACK = "registerQualityFeedback";
 private static final String MEMORY_FEEDBACK_POSITIVE = "memoryFeedbackPositive";
 private static final String MEMORY_FEEDBACK_NEGATIVE = "memoryFeedbackNegative";
 private static final String MEMORY_QUALITY_FEEDBACK = "memoryQualityFeedback";
 private static final String SCREEN_OUTPUT_FEEDBACK_POSITIVE = "screenOutputFeedbackPositive";
 private static final String SCREEN_OUTPUT_FEEDBACK_NEGATIVE = "screenOutputFeedbackNegative";
 private static final String SCREEN_OUTPUT_QUALITY_FEEDBACK = "screenOutputQualityFeedback";
 private static final String FILEOUTPUT_FEEDBACK_POSITIVE = "fileOutputFeedbackPositive";
 private static final String FILEOUTPUT_FEEDBACK_NEGATIVE = "fileOutputFeedbackNegative";
 private static final String FILE_OUTPUT_QUALITY_FEEDBACK = "fileOutputQualityFeedback";
 private static final String REQUIRED_COMMANDS_FEEDBACK_POSITIVE = "requiredCommandsFeedbackPositive";
 private static final String REQUIRED_COMMANDS_FEEDBACK_NEGATIVE = "requiredCommandsFeedbackNegative";
 private static final String REQUIRED_COMMANDS_QUALITY_FEEDBACK = "requiredCommandsQualityFeedback";
 private static final String FORBIDDEN_COMMANDS_FEEDBACK_POSITIVE = "forbiddenCommandsFeedbackPositive";
 private static final String FORBIDDEN_COMMANDS_FEEDBACK_NEGATIVE = "forbiddenCommandsFeedbackNegative";
 private static final String FORBIDDEN_COMMANDS_QUALITY_FEEDBACK = "forbiddenCommandsQualityFeedback";
 private static final String ACCEPTED_SIZE_FEEDBACK_POSITIVE = "acceptedSizeFeedbackPositive";
 private static final String ACCEPTED_SIZE_FEEDBACK_NEGATIVE = "acceptedSizeFeedbackNegative";
 private static final String OPTIMAL_SIZE_FEEDBACK_POSITIVE = "optimalSizeFeedbackPositive";
 private static final String OPTIMAL_SIZE_FEEDBACK_NEGATIVE = "optimalSizeFeedbackNegative";
 private static final String MEMORY_REFERENCES_FEEDBACK_POSITIVE = "memoryReferencesFeedbackPositive";
 private static final String MEMORY_REFERENCES_FEEDBACK_NEGATIVE = "memoryReferencesFeedbackNegative";

 public static TaskDTO assembleStaticTTK91Task(
		PostParameterParser post,
		HttpSession session) {

   return assemble(post, session);

 }//assembleStaticTTK91Task

 public static TaskDTO assembleFillInTTK91Task(
	 	PostParameterParser post,
		HttpSession session) {

  return assemble(post, session);

 }//assembleFillInTTK91Task

 private static TaskDTO assemble(
		 PostParameterParser post,
		 HttpSession session) {

  TTK91TaskOptions options;
  options = (TTK91TaskOptions)session.getAttribute(OPTIONS_KEY);

  Object temp = session.getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO"); //KAATUU
  if(temp == null) {return null;} //FIXME KAATUU TÄSSÄ

  TaskDTO newTask = null;
  newTask = (TaskDTO)temp;


  TTK91TaskCriteria[] rc = options.getRegisterCriterias();
  newTask.set( REGISTER_VALUES, concatCriterias(rc) );

  TTK91TaskCriteria[] mc = options.getMemoryCriterias();
  newTask.set( MEMORY_VALUES, concatCriterias(mc) );

  TTK91TaskCriteria[] soc = options.getScreenOutputCriterias();
  newTask.set( SCREEN_OUTPUT, concatCriterias(soc) );

  TTK91TaskCriteria[] foc = options.getFileOutputCriterias();
  newTask.set( FILE_OUTPUT, concatCriterias(foc) );

  String code = options.getExampleCode();
  newTask.set( EXAMPLE_CODE, code );

  String taskdesc = options.getTaskDescription();
  newTask.set( TASK_DESCRIPTION, taskdesc );

  int[] publicinput = options.getPublicInput();
  newTask.set( PUBLIC_INPUT, concatInput(publicinput) );

  int[] hiddeninput = options.getHiddenInput();
  newTask.set( HIDDEN_INPUT, concatInput(hiddeninput) );

  int compmethod = options.getCompareMethod();
  newTask.set( COMPARE_METHOD, ("" + compmethod ) );

  int maxcom = options.getMaxCommands();
  newTask.set( MAX_COMMANDS, ( "" + maxcom ) );

  int acceptsize = options.getAcceptedSize();
  newTask.set( ACCEPTED_SIZE, ( "" + acceptsize ) );

  String  optsize = "" + options.getOptimalSize();
  newTask.set( OPTIMAL_SIZE, ( "" + optsize ) );

  TTK91TaskCriteria memrefs = options.getMemRefCriteria();
  if(memrefs != null) {
   newTask.set( MEMORY_REFERENCES, memrefs.toString() );
  }//if

  String[] reqcomm = options.getRequiredCommands();
  if(reqcomm != null) {
   newTask.set( REQUIRED_COMMANDS, concatCommands(reqcomm) );
  }//if

  String[] forbcomm = options.getForbiddenCommands();
  if(forbcomm != null) {
   newTask.set( FORBIDDEN_COMMANDS, concatCommands(forbcomm) );
  }//if
  
  newTask.set( REGISTER_FEEDBACK_POSITIVE,
	       post.getStringParameter(REGISTER_FEEDBACK_POSITIVE) );
  newTask.set( REGISTER_FEEDBACK_NEGATIVE,
	       post.getStringParameter(REGISTER_FEEDBACK_NEGATIVE) );
  newTask.set( MEMORY_FEEDBACK_POSITIVE,
	       post.getStringParameter(MEMORY_FEEDBACK_POSITIVE) );
  newTask.set( MEMORY_FEEDBACK_NEGATIVE,
	       post.getStringParameter(MEMORY_FEEDBACK_NEGATIVE) );  
  newTask.set( SCREEN_OUTPUT_FEEDBACK_POSITIVE,
	       post.getStringParameter(SCREEN_OUTPUT_FEEDBACK_POSITIVE) );
  newTask.set( SCREEN_OUTPUT_FEEDBACK_NEGATIVE,
	       post.getStringParameter(SCREEN_OUTPUT_FEEDBACK_NEGATIVE) );
  newTask.set( FILEOUTPUT_FEEDBACK_POSITIVE,
	       post.getStringParameter(FILEOUTPUT_FEEDBACK_POSITIVE) );
  newTask.set( FILEOUTPUT_FEEDBACK_NEGATIVE,
	       post.getStringParameter(FILEOUTPUT_FEEDBACK_NEGATIVE) );
  newTask.set( REQUIRED_COMMANDS_FEEDBACK_POSITIVE,
	       post.getStringParameter(REQUIRED_COMMANDS_FEEDBACK_POSITIVE) );
  newTask.set( REQUIRED_COMMANDS_FEEDBACK_NEGATIVE,
	       post.getStringParameter(REQUIRED_COMMANDS_FEEDBACK_NEGATIVE) );  
  newTask.set( FORBIDDEN_COMMANDS_FEEDBACK_POSITIVE,
	       post.getStringParameter(FORBIDDEN_COMMANDS_FEEDBACK_POSITIVE) );
  newTask.set( FORBIDDEN_COMMANDS_FEEDBACK_NEGATIVE,
	       post.getStringParameter(FORBIDDEN_COMMANDS_FEEDBACK_NEGATIVE) );
  newTask.set( ACCEPTED_SIZE_FEEDBACK_POSITIVE,
	       post.getStringParameter(ACCEPTED_SIZE_FEEDBACK_POSITIVE) );
  newTask.set( ACCEPTED_SIZE_FEEDBACK_NEGATIVE,
	       post.getStringParameter(ACCEPTED_SIZE_FEEDBACK_NEGATIVE) );
  newTask.set( OPTIMAL_SIZE_FEEDBACK_POSITIVE,
	       post.getStringParameter(OPTIMAL_SIZE_FEEDBACK_POSITIVE) );
  newTask.set( OPTIMAL_SIZE_FEEDBACK_NEGATIVE,
	       post.getStringParameter(OPTIMAL_SIZE_FEEDBACK_NEGATIVE) );  
  newTask.set( MEMORY_REFERENCES_FEEDBACK_POSITIVE,
	       post.getStringParameter(MEMORY_REFERENCES_FEEDBACK_POSITIVE) );
  newTask.set( MEMORY_REFERENCES_FEEDBACK_NEGATIVE,
	       post.getStringParameter(MEMORY_REFERENCES_FEEDBACK_NEGATIVE) );
  newTask.set( REGISTER_QUALITY_FEEDBACK,
	       post.getStringParameter(REGISTER_QUALITY_FEEDBACK) );
  newTask.set( MEMORY_QUALITY_FEEDBACK,
	       post.getStringParameter(MEMORY_QUALITY_FEEDBACK) );
  newTask.set( SCREEN_OUTPUT_QUALITY_FEEDBACK,
	       post.getStringParameter(SCREEN_OUTPUT_QUALITY_FEEDBACK) );
  newTask.set( FILE_OUTPUT_QUALITY_FEEDBACK,
	       post.getStringParameter(FILE_OUTPUT_QUALITY_FEEDBACK) );
  newTask.set( REQUIRED_COMMANDS_QUALITY_FEEDBACK,
	       post.getStringParameter(REQUIRED_COMMANDS_QUALITY_FEEDBACK) );
  newTask.set( FORBIDDEN_COMMANDS_QUALITY_FEEDBACK,
	       post.getStringParameter(FORBIDDEN_COMMANDS_QUALITY_FEEDBACK) );

  return newTask;

 }//assemble

 private static String concatCriterias(TTK91TaskCriteria[] criterias) {

  String criteriaString = "";

  for(int i = 0; i<criterias.length;++i) {

      criteriaString += criterias[i].toString();

  }//for

  return criteriaString;

 }//concatCriterias

 /*
 private static String concatCriterias(int[][] criterias) {

     StringBuffer sb = new StringBuffer();

     if(criterias != null) {
	 for(int i = 0; i < criterias.length; i++) {
	     // voidaan olettaa että taulukon koko
	     // on kaksi, sillä outputteille joille
	     // tätä käytetään niiden jälkimmäisessä taulukossa
	     // on aina kaksi alkiota. Jos ei ole niin jossain
	     // aikaisemmin on tehty virhe.
	     // FIXME järkevämpi toteutus kuin int[][] output
	     // eille
	     sb.append("(");
	     sb.append(criterias[i][0]);
	     sb.append(",");
	     sb.append(criterias[i][1]);
	     sb.append(")");
	     sb.append(";");
	 }//for
     }//if

     return sb.toString();

 }//concatCriterias
*/
 
 private static String concatCommands(String[] commands) {

  String commandString = "";

  for(int i = 0; i<commands.length;++i) {

      commandString += commands[i];

  }//for

  return commandString;

 }//concatCriterias

 private static String concatInput(int[] input){

  String inputString = "";

  if(input == null) {return inputString;}

  inputString = "" + input[0];

  for(int i = 1; i < input.length; ++i) {

      inputString += "," + input[i];

  }//for

  return inputString;
 
 }//concatInput

}//class

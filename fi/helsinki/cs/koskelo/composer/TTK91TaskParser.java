package fi.helsinki.cs.koskelo.composer;
//TODO: JAVADOCIT JA KOMMENTIT
//by Harri Tuomikoski, 17.11.2004

import javax.servlet.*;
import javax.servlet.http.*;
import fi.helsinki.cs.koskelo.common.*;
import fi.hy.eassari.taskdefinition.util.*;

public class TTK91TaskParser {

 private final String OPTIONS_KEY = "fi.helsinki.cs.koskelo.common.TTK91TaskOptions";
 private TTK91TaskOptions options;

 private final String REGISTER_VALUES  = "registerValues";
 private final String MEMORY_VALUES = "memoryValues";
 private final String SCREEN_OUTPUT = "screenOutput";
 private final String FILE_OUTPUT = "fileOutput";

 private final String EXAMPLE_CODE = "exampleCode";
 private final String TASK_DESCRIPTION = "taskDescription";
 private final String PUBLIC_INPUT = "publicInput";
 private final String HIDDEN_INPUT = "hiddenInput";
 private final String COMPARE_METHOD = "compareMethod";
 private final String MAX_COMMANDS = "maxCommands";
 private final String ACCEPTED_SIZE = "acceptedSize";
 private final String OPTIMAL_SIZE = "optimalSize";
 private final String MEMORY_REFERENCES = "memoryReferences";
 private final String REQUIRED_COMMANDS = "requiredCommands";
 private final String FORBIDDEN_COMMANDS = "forbiddenCommands";

 private final String REGISTER_FEEDBACK_POSITIVE = "registerFeedbackPositive";
 private final String REGISTER_FEEDBACK_NEGATIVE = "registerFeedbackNegative";
 private final String REGISTER_QUALITY_FEEDBACK = "registerQualityFeedback";
 private final String MEMORY_FEEDBACK_POSITIVE = "memoryFeedbackPositive";
 private final String MEMORY_FEEDBACK_NEGATIVE = "memoryFeedbackNegative";
 private final String MEMORY_QUALITY_FEEDBACK = "memoryQualityFeedback";
 private final String SCREEN_OUTPUT_FEEDBACK_POSITIVE = "screenOutputFeedbackPositive";
 private final String SCREEN_OUTPUT_FEEDBACK_NEGATIVE = "screenOutputFeedbackNegative";
 private final String SCREEN_OUTPUT_QUALITY_FEEDBACK = "screenOutputQualityFeedback";
 private final String FILEOUTPUT_FEEDBACK_POSITIVE = "fileOutputFeedbackPositive";
 private final String FILEOUTPUT_FEEDBACK_NEGATIVE = "fileOutputFeedbackNegative";
 private final String FILE_OUTPUT_QUALITY_FEEDBACK = "fileOutputQualityFeedback";
 private final String REQUIRED_COMMANDS_FEEDBACK_POSITIVE = "requiredCommandsFeedbackPositive";
 private final String REQUIRED_COMMANDS_FEEDBACK_NEGATIVE = "requiredCommandsFeedbackNegative";
 private final String REQUIRED_COMMANDS_QUALITY_FEEDBACK = "requiredCommandsQualityFeedback";
 private final String FORBIDDEN_COMMANDS_FEEDBACK_POSITIVE = "forbiddenCommandsFeedbackPositive";
 private final String FORBIDDEN_COMMANDS_FEEDBACK_NEGATIVE = "forbiddenCommandsFeedbackNegative";
 private final String FORBIDDEN_COMMANDS_QUALITY_FEEDBACK = "forbiddenCommandsQualityFeedback";
 private final String ACCEPTED_SIZE_FEEDBACK_POSITIVE = "acceptedSizeFeedbackPositive";
 private final String ACCEPTED_SIZE_FEEDBACK_NEGATIVE = "acceptedSizeFeedbackNegative";
 private final String OPTIMAL_SIZE_FEEDBACK_POSITIVE = "optimalSizeFeedbackPositive";
 private final String OPTIMAL_SIZE_FEEDBACK_NEGATIVE = "optimalSizeFeedbackNegative";
 private final String MEMORY_REFERENCES_FEEDBACK_POSITIVE = "memoryReferencesFeedbackPositive";
 private final String MEMORY_REFERENCES_FEEDBACK_NEGATIVE = "memoryReferencesFeedbackNegative";

 public TaskDTO assembleStaticTTK91Task(
		PostParameterParser post,
		HttpSession session) {

  this.options = (TTK91TaskOptions)session.getAttribute(OPTIONS_KEY);
  TaskDTO newTask = new TaskDTO();

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
  newTask.set( PUBLIC_INPUT, publicinput );

  int[] hiddeninput = options.getHiddenInput();
  newTask.set( HIDDEN_INPUT, hiddeninput );

  int compmethod = options.getCompareMethod();
  newTask.set( COMPARE_METHOD, ("" + compmethod ) );

  int maxcom = options.getMaxCommands();
  newTask.set( MAX_COMMANDS, ( "" + maxcom ) );

  int acceptsize = options.getAcceptedSize();
  newTask.set( ACCEPTED_SIZE, ( "" + acceptsize ) );

  String  optsize = "" + options.getOptimalSize();
  newTask.set( OPTIMAL_SIZE, ( "" + optsize ) );

  /*FIXME: Poista kommenteista KUN TaskOptionsiin lisätty tämä metodi
  TTK91TaskCriteria memrefs = options.getMemoryReferences();
  newTask.set( MEMORY_REFERENCES, memrefs.toString() );
  */

  String[] reqcomm = options.getRequiredCommands();
  newTask.set( REQUIRED_COMMANDS, concatCommands(reqcomm) );

  String[] forbcomm = options.getForbiddenCommands();
  newTask.set( FORBIDDEN_COMMANDS, concatCommands(forbcomm) );

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

 }//assembleStaticTTK91Task

 private String concatCriterias(TTK91TaskCriteria[] criterias) {

  String criteriaString = "";

  for(int i = 0; i<criterias.length;++i) {

      criteriaString += criterias[i].toString();

  }//for

  return criteriaString;

 }//concatCriterias

 private String concatCommands(String[] commands) {

  String commandString = "";

  for(int i = 0; i<commands.length;++i) {

      commandString += commands[i];

  }//for

  return commandString;

 }//concatCriterias

}//class

import javax.servlet.*;
import javax.servlet.http.*;

public class TTK91TaskParser {

    private final String EXAMPLE_CODE = "exampleCode";
    private final String TASK_DESCRIPTION = "taskDescription";
    private final String REGISTER_FEEDBACK_POSITIVE = "registerFeedbackPositive";
    private final String REGISTER_FEEDBACK_NEGATIVE = "registerFeedbackNegative";
    private final String MEMORY_FEEDBACK_POSITIVE = "memoryFeedbackPositive";
    private final String MEMORY_FEEDBACK_NEGATIVE = "memoryFeedbackNegative";
    //Jne.

 public TaskDTO assembleStaticTTK91Task(
  PostParameterParser post, HttpSession session) {

   TaskDTO newTask = new TaskDTO();
   TTK91TaskOptions taskData = (TTK91TaskOptions)
                               session.getAttribute(
                               "fi.helsinki.cs.koskelo.common."+
                               "TTK91TaskOptions");

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

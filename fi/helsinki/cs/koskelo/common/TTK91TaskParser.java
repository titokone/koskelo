import javax.servlet.*;
import javax.servlet.http.*;

public class TTK91TaskParser {

 public TaskDTO assembleStaticTTK91Task(
  PostParameterParser post, HttpSession session) {

   TaskDTO newTask = new TaskDTO();
   TTK91TaskOptions taskData = (TTK91TaskOptions)
                               session.getAttribute(
                               "fi.helsinki.cs.koskelo.common."+
                               "TTK91TaskOptions");
   

   return newTask;

 }//assembleStaticTTK91Task

}//class

package fi.hy.eassari.taskdefinition.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.hy.eassari.showtask.trainer.TaskBase;
import fi.hy.eassari.taskdefinition.exception.TaskProcessingException;
import fi.hy.eassari.taskdefinition.util.AllTasksDataBase;
import fi.hy.eassari.taskdefinition.util.Authorization;
import fi.hy.eassari.taskdefinition.util.BlanksParser;
import fi.hy.eassari.taskdefinition.util.Events;
import fi.hy.eassari.taskdefinition.util.TaskDataBase;
import fi.hy.eassari.taskdefinition.util.PostParameterParser;
import fi.hy.eassari.taskdefinition.util.TaskTypes;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;
import fi.hy.eassari.taskdefinition.util.datastructures.TeacherSession;

import fi.helsinki.cs.koskelo.composer.*;

/**
 * A controller servlet for taskdefinition view and operation control.
 * @author Vesa-Matti Mäkinen
 */
public class TaskDefinitionController extends HttpServlet {

	TaskBase cache = null;
	AllTasksDataBase db = null;
	TaskDataBase taskDb = null; 
	/**
	 * Initialize the taskbase object for multilanguage values
	*/
	public void init(ServletConfig config) throws ServletException {
		String conFile = config.getServletContext().getInitParameter("confile");
		conFile = config.getServletContext().getRealPath(conFile);
		 	 
		try {
		   Properties p = new Properties();
		   p.load(new FileInputStream(conFile));
		   String dbDriver   = (String) p.get("dbDriver");
		   String dbServer   = (String) p.get("dbServer");
		   String dbUser     = (String) p.get("dbUser");
		   String dbPassword = (String) p.get("dbPassword");
			
		   db = new AllTasksDataBase(dbDriver, dbServer, dbUser, dbPassword);
		   taskDb = new TaskDataBase(dbDriver, dbServer, dbUser, dbPassword);		
		   cache = new TaskBase(dbDriver,dbServer,dbUser,dbPassword);
		}
		catch (Exception e) {
			throw new ServletException("Problems with configuration file " + conFile + ": " + e.getMessage());
		}
	}
	/**
	 * Analyze the request and perform required operations.
	 * Finally, redirect to the next view. 
 	*/
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		// parse post parameters
		PostParameterParser post = new PostParameterParser(request);
		int event = post.getEvent();

		// set event to request - needed by the next jsp-view	
		request.setAttribute("event", new Integer(event));
		request.setAttribute("fi.hy.eassari.showtask.trainer.TaskBase", cache);
				
		TaskDTO task;
		java.util.Collection tasks;

		try {
			// check that the session is valid and alive
			HttpSession session = request.getSession(false); 
			if (event == Events.LOGIN) {
				// should check username and password, not implemented yet
				session = request.getSession(true); // create new session
				TeacherSession userData = new TeacherSession(post.getStringParameter("userid"), post.getStringParameter("language"));	
				session.setAttribute("fi.hy.taskdefinition.util.datastructures.TeacherSession", userData);
			// get tasklist	
				tasks = db.getAllAuthorTasks(userData.getUserId(), userData.getSelectedLanguageId());
				session.setAttribute("java.util.Collection", tasks);
				request.getRequestDispatcher("/jsp/tasks.jsp").forward(request,response);
				return;
			}
			if (session == null) { // session expired, redirect to login page
				request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
				return;
			}
	
			// session valid
			// get user, language and task info
	   	    TeacherSession userData = (TeacherSession)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TeacherSession");
	   	    String userId = userData.getUserId();
	   	    String displayLanguage = userData.getSelectedLanguageId();
			String taskId = post.getTaskid();
					
			// check that the current user is allowed to control this task
			if (taskId.length() > 0) { // not a new task
				if (Authorization.hasPermissions(userId, taskId) == false) { // no permission -> redirect to error page
					System.out.println("Logged user '"+userId+"' did not have required permissions to control task: "+taskId);
					request.getRequestDispatcher("/jsp/error.jsp").forward(request,response);
					return;
				}
			}
			
			// decide what to do, and call approriate worker services
			switch(event) {
			  case Events.LIST_TASKS:
				  tasks = db.getAllAuthorTasks(userId, displayLanguage);
				  session.setAttribute("java.util.Collection", tasks);
				  request.getRequestDispatcher("/jsp/tasks.jsp").forward(request,response);
				  break;
			  case Events.NEW_TASK:
			  	  session.removeAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
				  request.getRequestDispatcher("/jsp/tasksettings.jsp").forward(request,response);
				  break;
			  case Events.EDIT_TASK:
			  	  task = taskDb.loadData(taskId, post.getStringParameter("editlanguage"));
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
				  request.getRequestDispatcher("/jsp/tasksettings.jsp").forward(request,response);
				  break;
			  case Events.EDIT_TASK_AS_NEW:
			  	  task = taskDb.loadData(taskId, post.getStringParameter("editlanguage"));
			  	  task.setSaveAsNew(true);
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
				  request.getRequestDispatcher("/jsp/tasksettings.jsp").forward(request,response);
				  break;
			  case Events.EDIT_TASK_AS_NEW_LANG:
				  task = taskDb.loadData(taskId, post.getStringParameter("editlanguage"));
				  task.setSaveAsNewLanguage(true);
				  task.setOldLanguage(post.getStringParameter("editlanguage"));
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
			  	  request.getRequestDispatcher("/jsp/tasksettings.jsp").forward(request,response);
				  break;
			  case Events.SUBMIT_TASK:
				  String taskType = post.getTaskType();
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", assembleTask(post, session));	
				  if (taskType.equals(TaskTypes.OPTION_TASK)) {
					  request.getRequestDispatcher("/jsp/option.jsp").forward(request,response);						
				  }
				  if (taskType.equals(TaskTypes.ORDERING_TASK)) {
					  request.getRequestDispatcher("/jsp/ordering1.jsp").forward(request,response);						
				  }
	  			  if (taskType.equals(TaskTypes.BLANKFILL_TASK)) {
	  			  	  request.getRequestDispatcher("/jsp/blankfill1.jsp").forward(request,response);						
				  }
				  //ADDED by HT 19.11.2004
				  if( taskType.equals(TaskTypes.STATIC_TTK91) ) {
	  			  	  request.getRequestDispatcher("/jsp/StaticTTK91Composer.jsp").forward(request,response);
				  }//if
				  if( taskType.equals(TaskTypes.FILLIN_TTK91) ) {
	  			  	  request.getRequestDispatcher("/jsp/FillInTTK91Composer.jsp").forward(request,response);
				  }//if
			      break;
			  case Events.DELETE_TASK:
			  	  taskDb.deleteTaskData(taskId);
			  	  session.removeAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
	 			  tasks = db.getAllAuthorTasks(userId, displayLanguage);
				  session.setAttribute("java.util.Collection", tasks);
				  request.getRequestDispatcher("/jsp/tasks.jsp").forward(request,response);
				  break;
			  case Events.OPTION_TASK_COMPOSE:
			      session.removeAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
				  request.getRequestDispatcher("/jsp/option.jsp").forward(request,response);
				  break;
			  case Events.OPTION_TASK_EDIT:
				  task = (TaskDTO)taskDb.loadData(taskId, post.getLanguage());
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
				  request.getRequestDispatcher("/jsp/option.jsp").forward(request,response);
				  break;
			  case Events.OPTION_TASK_COMPOSE_ADD_OPTIONS:
				  task = assembleOptionTask(post, session);
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
				  request.getRequestDispatcher("/jsp/option.jsp").forward(request,response);
				  break;
			  case Events.OPTION_TASK_SUBMIT:
				  task = assembleOptionTask(post, session);
				  taskDb.saveData(task);	
				  tasks = db.getAllAuthorTasks(userId, displayLanguage);
				  session.setAttribute("java.util.Collection", tasks);
				  request.getRequestDispatcher("/jsp/tasks.jsp").forward(request,response);
				  break;
			  case Events.BLANKFILL_TEXT_COMPOSE:
			      session.removeAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
				  request.getRequestDispatcher("/jsp/blankfill1.jsp").forward(request,response);
				  break;
			  case Events.BLANKFILL_TEXT_EDIT:
				  task = (TaskDTO)taskDb.loadData(taskId, post.getLanguage());
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
				  request.getRequestDispatcher("/jsp/blankfill1.jsp").forward(request,response);
				  break;
			  case Events.BLANKFILL_TEXT_SUBMIT:
				  task = assembleBlankFillTaskText(post, session);
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
				  request.getRequestDispatcher("/jsp/blankfill2.jsp").forward(request,response);
				  break;
			  case Events.BLANKFILL_FEEDBACK_COMPOSE:
				  request.getRequestDispatcher("/jsp/blankfill2.jsp").forward(request,response);
				  break;
			  case Events.BLANKFILL_FEEDBACK_EDIT:
				  request.getRequestDispatcher("/jsp/blankfill2.jsp").forward(request,response);
				  break;
			  case Events.BLANKFILL_FEEDBACK_SUBMIT:
			  	  task = assembleBlankFillTaskFeedback(post, session);
				  taskDb.saveData(task);
				  tasks = db.getAllAuthorTasks(userId, displayLanguage);
				  session.setAttribute("java.util.Collection", tasks);
				  request.getRequestDispatcher("/jsp/tasks.jsp").forward(request,response);
				  break;
			  case Events.ORDERING_OPTIONS_COMPOSE:
			      session.removeAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
				  request.getRequestDispatcher("/jsp/ordering1.jsp").forward(request,response);
				  break;
	  		  case Events.ORDERING_OPTIONS_EDIT:
				  task = (TaskDTO)taskDb.loadData(taskId, post.getLanguage());
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
				  request.getRequestDispatcher("/jsp/ordering1.jsp").forward(request,response);
				  break;
			  case Events.ORDERING_OPTIONS_COMPOSE_ADD_OPTIONS:
				  task = assembleOrderingTaskObjects(post, session);
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);		      
				  request.getRequestDispatcher("/jsp/ordering1.jsp").forward(request,response);
				  break;
			  case Events.ORDERING_OPTIONS_SUBMIT:
			  	  task = assembleOrderingTaskObjects(post, session);
				  session.setAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO", task);
				  request.getRequestDispatcher("/jsp/ordering2.jsp").forward(request,response);
				  break;
			  case Events.ORDERING_ORDER_COMPOSE:
				  request.getRequestDispatcher("/jsp/ordering2.jsp").forward(request,response);
				  break;
			  case Events.ORDERING_ORDER_SUBMIT:
			  	  task = assembleOrderingTaskOrder(post, session);
				  taskDb.saveData(task);
				  tasks = db.getAllAuthorTasks(userId, displayLanguage);
				  session.setAttribute("java.util.Collection", tasks);
				  request.getRequestDispatcher("/jsp/tasks.jsp").forward(request,response);
				  break;


               		  /*ADDED by HT, Koskelo-projekti, 08.11.2004
			  *Modified by EN
			  *Assemble a statitc TTK91 task
			  */

			  case Events.STATIC_TTK91_SUBMIT:

				  task = TTK91TaskParser.assembleStaticTTK91Task(post, session);
				  if(task != null) {
				   taskDb.saveData(task);
				   tasks = db.getAllAuthorTasks(userId, displayLanguage);
				   session.setAttribute("java.util.Collection", tasks);
				  }//if
				   request.getRequestDispatcher("/jsp/tasks.jsp").forward(request,response);
				  break;
			  case Events.FILLIN_TTK91_SUBMIT:
				  task = TTK91TaskParser.assembleFillInTTK91Task(post, session);
				  taskDb.saveData(task);
				  tasks = db.getAllAuthorTasks(userId, displayLanguage);
				  session.setAttribute("java.util.Collection", tasks);
				  request.getRequestDispatcher("/jsp/tasks.jsp").forward(request,response);
				  break;
			  default:
				  response.sendRedirect("http://www.helsinki.fi/cgi-bin/dump-all");
				  break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in TaskDefinitionServlet: "+e);
			request.getRequestDispatcher("/jsp/error.jsp").forward(request,response);
		}
	}	
	/**
	 * Assemble a new task-datastructure. Tasktype spesific data will be added later.
	 * @param post A PostParameter object containing the POST-data of this request.
	 * @param session A HttpSession object containing the data in this user session.
	 * @return TaskDTO the datastructure containing the new task's data.
	 */
	private TaskDTO createTask(PostParameterParser post, HttpSession session) {
		TeacherSession userData = (TeacherSession)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TeacherSession");
		TaskDTO task = new TaskDTO();
		task.setAuthor(userData.getUserId());
		task.setDateCreated(new Date());
		task.setTaskId(""); // leave blank -> new task
		return task;
	}
	/**
	 * Add general task data to the task datastructure held in the session.
	 * @param post A PostParameter object containing the POST-data of this request.
	 * @param session A HttpSession object containing the data in this user session.
	 * @param request the HttpRequest associated with this post.
	 * @param response the HttpResponse associated with this post. 
	 * @return TaskDTO the datastructure filled with general task data from the request.
	 */
	private TaskDTO assembleTask(PostParameterParser post, HttpSession session) throws TaskProcessingException {
		TaskDTO task = null;  
		try {
			task = (TaskDTO)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
			if (task == null) // no task found from session - create new task
				task = createTask(post, session);
				
			// set values to dto	
			task.setCutOffValue(post.getIntParameter("cutofvalue"));
			task.set("negativefeedback", post.getStringParameter("negativefeedback"));
			task.set("positivefeedback", post.getStringParameter("positivefeedback"));
			task.setBooleanImmediateFeedback(""+post.getIntParameter("immediatefeedback"));
			task.setLanguage(post.getLanguage());
			task.setNumberOfTries(post.getIntParameter("numberoftries"));
			task.setBooleanShouldKnowStudent(""+post.getIntParameter("loginrequired"));
			task.setBooleanShouldRegisterTry(""+post.getIntParameter("registertry"));
			task.setBooleanShouldStoreAnswer(""+post.getIntParameter("saveanswer"));
			task.set("task", post.getStringParameter("task"));
			task.setTaskType(post.getTaskType());
			task.setTaskName(post.getTaskName());
		} catch(Exception ex1) { // error while processing task from session
			throw new TaskProcessingException("Couldn't create a TaskDTO object for general task data: "+ex1);
		}		
		return task;
	}	
	/**
	 * Process option task data and update the data structure according to the request.
	 * @param post A PostParameter object containing the POST-data of this request.
	 * @param session A HttpSession object containing the data in this user session.
	 * @return TaskDTO the datastructure filled with option task specific data from the request.
	 */
	private TaskDTO assembleOptionTask(PostParameterParser post, HttpSession session) throws TaskProcessingException {
		TaskDTO task = null;  
		try {
			task = (TaskDTO)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
			int numberOfOptions = post.getIntParameter("numberofoptions");
			String[] options = post.getStringArrayParameter("option", numberOfOptions);
			String[] isSelected = post.getStringArrayParameter("isselected", numberOfOptions);
			String[] positiveFeedback = post.getStringArrayParameter("positivefeedback", numberOfOptions);
			String[] negativeFeedback = post.getStringArrayParameter("negativefeedback", numberOfOptions);
	
			int nextIndex = 1;
			for (int i=1; i<options.length; ++i) {
				if (options[i].trim().equals("")) // skip blank objects
					continue;
				task.set("option"+nextIndex, options[i]);
				task.set("isselected"+nextIndex, isSelected[i]);
				task.set("positivefeedback"+nextIndex, positiveFeedback[i]);
				task.set("negativefeedback"+nextIndex, negativeFeedback[i]);
				++nextIndex;
			}	
			// clear old values from taskDTO
			task.eraseParameterSequence("option", nextIndex);
			task.eraseParameterSequence("isselected", nextIndex);
			task.eraseParameterSequence("positivefeedback", nextIndex);
			task.eraseParameterSequence("negativefeedback", nextIndex);
		} catch(Exception ex1) { // error while processing task from session
			throw new TaskProcessingException("Couldn't create a TaskDTO object for option task: "+ex1);
		}	
		return task;
	}
	/**
	 * Process blankfill task data (view 1/2) and update the data structure according to the request.
	 * @param post A PostParameter object containing the POST-data of this request.
	 * @param session A HttpSession object containing the data in this user session.	 
	 * @return TaskDTO the datastructure filled with blankfill task specific text data from the request.
	 */
	private TaskDTO assembleBlankFillTaskText(PostParameterParser post, HttpSession session) throws TaskProcessingException {
		TaskDTO task = null;  
		try {
			task = (TaskDTO)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
			// erase old data
			task.eraseParameterSequence("blank", 1);
			task.eraseParameterSequence("before", 1);
			task.eraseParameterSequence("after", 1);

			String text = post.getStringParameter("text");
			BlanksParser parser = new BlanksParser();
			task.set("text", text);
			task = parser.parse(task);
		} catch(Exception ex1) { // error while processing task from session
			throw new TaskProcessingException("Couldn't create a TaskDTO object for blankfill task: "+ex1);
		}		
		return task;
	}
	/**
	 * Process blankfill task data (view 2/2) and update the data structure according to the request.
	 * @param post A PostParameter object containing the POST-data of this request.
	 * @param session A HttpSession object containing the data in this user session.	 
	 * @return TaskDTO the datastructure filled with blankfill task specific feedback data from the request.
	 */
	private TaskDTO assembleBlankFillTaskFeedback(PostParameterParser post, HttpSession session) throws TaskProcessingException {
		TaskDTO task = null;  
		try {
			task = (TaskDTO)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
			String[] positiveFeedback = post.getStringArrayParameter("positivefeedback", post.getIntParameter("numberofblankspots"));
			String[] negativeFeedback = post.getStringArrayParameter("negativefeedback", post.getIntParameter("numberofblankspots"));
			for (int i=1; i<positiveFeedback.length; ++i) {
				task.set("positivefeedback"+i, positiveFeedback[i]);
				task.set("negativefeedback"+i, negativeFeedback[i]);
			}
			// clear old values from taskDTO
			task.eraseParameterSequence("positivefeedback", positiveFeedback.length);
			task.eraseParameterSequence("negativefeedback", positiveFeedback.length);
		} catch(Exception ex1) { // error while processing task from session
			throw new TaskProcessingException("Couldn't create a TaskDTO object for blankfill task: "+ex1);
		}					
		return task;
	}	
	/**
	 * Process ordering task data (view 1/2) and update the data structure according to the request.
	 * @param post A PostParameter object containing the POST-data of this request.
	 * @param session A HttpSession object containing the data in this user session.
	 * @return TaskDTO the datastructure filled with ordering task specific orderable object data from the request.
	 */
	private TaskDTO assembleOrderingTaskObjects(PostParameterParser post, HttpSession session) throws TaskProcessingException {
		TaskDTO task = null;  
		try {
			task = (TaskDTO)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
			int numberOfObjects = post.getIntParameter("numberofoptions");
			
			String[] objects = post.getStringArrayParameter("object", numberOfObjects);
			String[] correctFeedback = post.getStringArrayParameter("correctfeedback", numberOfObjects);
			String[] tooEarlyFeedback = post.getStringArrayParameter("tooearlyfeedback", numberOfObjects);
			String[] tooLateFeedback = post.getStringArrayParameter("toolatefeedback", numberOfObjects);
			
			int nextIndex = 1;
			for (int i=1; i<objects.length; ++i) {
				if (objects[i].trim().equals("")) // skip blank objects
					continue;
				task.set("object"+nextIndex, objects[i]);
				task.set("correctfeedback"+nextIndex, correctFeedback[i]);
				task.set("tooearlyfeedback"+nextIndex, tooEarlyFeedback[i]);
				task.set("toolatefeedback"+nextIndex, tooLateFeedback[i]);
				++nextIndex;
			}	
			// clear old values from taskDTO
			task.eraseParameterSequence("object", nextIndex);
			task.eraseParameterSequence("correctfeedback", nextIndex);
			task.eraseParameterSequence("tooearlyfeedback", nextIndex);
			task.eraseParameterSequence("toolatefeedback", nextIndex);
		} catch(Exception ex1) { // error while processing task from session
			throw new TaskProcessingException("Couldn't create a TaskDTO object for ordering task: "+ex1);
		}			
		return task;
	}	
	/**
	 * Process ordering task data (view 2/2) and update the data structure according to the request.
	 * @param post A PostParameter object containing the POST-data of this request.
	 * @param session A HttpSession object containing the data in this user session.
	 * @return TaskDTO the datastructure filled with blankfill task specific data from the request.
	 */
	private TaskDTO assembleOrderingTaskOrder(PostParameterParser post, HttpSession session) throws TaskProcessingException {
		TaskDTO task = null;  
		try {
			task = (TaskDTO)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
			int[] objectPlaces = post.getIntArrayParameter("placeofobject", post.getIntParameter("numberofoptions"));
			for (int i=1; i<objectPlaces.length; ++i)
				task.set("placeofobject"+i, ""+objectPlaces[i]);
				
			// clear old values from taskDTO
			task.eraseParameterSequence("placeofobject", objectPlaces.length);
		} catch(Exception ex1) { // error while processing task from session
			throw new TaskProcessingException("Couldn't create a TaskDTO object for ordering task: "+ex1);
		}	
		return task;
	}
}

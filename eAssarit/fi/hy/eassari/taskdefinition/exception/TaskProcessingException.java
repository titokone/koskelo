package fi.hy.eassari.taskdefinition.exception;

/**
 * An exception class for exceptions occurred while building tasks from request data.
 * @author Vesa-Matti Mäkinen
 */
public class TaskProcessingException extends Exception {

	/**
	 * Creates a new TaskProcessingException object with an empty message.
	 */
	public TaskProcessingException() {
		super();
	}

	/**
	 * Creates a new TaskProcessingException object with the given message.
	 */
	public TaskProcessingException(String errorMessage) {
		super(errorMessage);
	}
}

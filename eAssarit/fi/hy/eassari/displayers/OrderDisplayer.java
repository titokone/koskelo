package fi.hy.eassari.displayers;



import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * Class to generate option task presentations.
 *
 * @author  Olli-Pekka Ruuskanen (by modifying Vesa-Matti Mäkinen's impelentation)
 * @version 22.4.2004
 */

public class OrderDisplayer extends CommonDisplayer {


	/**
	 * Generates the actual representation of the task in HTML.
	 *
	 * @param  initVal 	contains the default answer.
	 * @param  params  	contains the task specific generated parametervalues.
	 * 				    The format of the string is Displayer specific but xml is recommended.
	 * @param  hiddens 	contains hidden http parameters to be included in the form.
	 * @param  allowTry specifies whether answerbutton should be included or not.
	 * @return String
	 * @throws CacheException If there are problems using the system cache.
	 */

	public String getSetting(String [] initVal, String params, String hiddens,  boolean allowTry)
							throws CacheException {

		int          counter       = 0;
		int          attrcount     = 0;
		String       attrvalue     = null;
		String       targetServlet = "Answer2.do2";
		String       css           = "http://www.cs.helsinki.fi/group/assari/css/assari.css";
		String       buttontext    = cache.getAttribute("T", taskid, "submitbutton", language);
		StringBuffer setting       = new StringBuffer();

		try {
			attrcount = cache.attribCount (taskid, "object", language);
		}
		catch (Exception ex) {;}

		hiddens += "<input type=\"hidden\" name=\"tasktype\" value=\"orderingtask\">";
		hiddens += "<input type=\"hidden\" name=\"optioncount\" value=" + attrcount +">";
		
			setting.append (

			"<?xml version=\"1.0\"	encoding=\"iso-8859-1\"?>                             \n"           +
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
			"<html xmlns=\"http://www.w3.org/1999/xhtml\">                                \n"           +
			"<head>                                                                       \n\t"         +
				"<title>Ordering assignment</title>                                       \n\n\t"       +
				"<link href=\"" + css + "\" rel=\"stylesheet\" type=\"text/css\"\">       \n\n\t"       +
				// Links to external JavaScript files:
				"<script language=\"JavaScript\" src=\"http://www.cs.helsinki.fi/group/assari/dynapi/js/dynlayer.js\"></script>    \n\t" +
				"<script language=\"JavaScript\" src=\"http://www.cs.helsinki.fi/group/assari/dynapi/js/mouseevents.js\"></script> \n\t" +
				"<script language=\"JavaScript\" src=\"http://www.cs.helsinki.fi/group/assari/dynapi/js/drag.js\"></script>        \n\t" +
				"<script language=\"JavaScript\">                                         \n\n\t"       +

				// layout parameters
				"var topMargin  = 200                                                     \n\t"         +
				"var leftMargin = 50                                                      \n\t"         +
				"var orderingObjectHeight = 50                                            \n\t"         +
				"var orderingObjectVerticalSpacing = 20                                   \n\t"         +
				"var numberOfObjects  = " + attrcount + "                                 \n\t"         +

				// object handling:
				"var positions = new Array(numberOfObjects)                               \n\n\t"       +

				"function init() {                                                        \n\n\t\t"     +

					// initialize DynLayers
					"DynLayerInit()                                                       \n\n\t\t");

					// add the draggable layers to the drag object
					String list = "";
					for (int index = 1; index <= attrcount; index++){
						if (index < attrcount)
							list += "option" + index + ", ";
						else
							list += "option" + index;
					};

					setting.append(
					"drag.add(" + list + ")                                               \n\n\t\t"     +

					"drag.onDragStart = dragStart                                         \n\t\t"       +
					"drag.onDragMove  = dragMove                                          \n\t\t"       +
					"drag.onDragEnd   = dragEnd                                           \n\n\t\t"     +

					"initMouseEvents()                                                    \n\n\t\t");

					// initialize the array reflecting the order of the objects
					for (int index = 0; index < attrcount; index++){
						String entry = "positions[" + index + "] = option" + (index+1);
						setting.append (entry +                                          "\n\t\t");
					};


					// move objects to initial positions and redraw
					setting.append (                                                     "\n\t\t"       +
					"moveObjects()                                                        \n\t"         +
				"}                                                                        \n\n\t"       +

				"function dragStart(x,y) {                                                \n\t\t"       +
					"return false                                                         \n\t"         +
				"}                                                                        \n\n\t"       +

				"function dragMove(x,y) {                                                 \n\t"         +
				"}                                                                        \n\n\t"       +

				"function dragEnd(x,y) {                                                  \n\t\t"       +
					// reorder and redraw objects
					"moveObjects()                                                        \n\t"         +
				"}                                                                        \n\n\t"       +


				// moves all objects to their correct positions according to the positions-array
				"function moveObjects() {                                                 \n\n\t\t"     +

					// order positions table (ascending order) according to objects y-coordinates
					"for (var i = 0; i < numberOfObjects-1; i++) {                        \n\t\t\t"     +
						"for (var j = i+1; j < numberOfObjects; j++) {                    \n\t\t\t\t"   +
							"if (positions[i].y > positions[j].y) {                       \n\t\t\t\t\t" +
								"var tmp = positions[i]                                   \n\t\t\t\t\t" +
								"positions[i] = positions[j]                              \n\t\t\t\t\t" +
								"positions[j] = tmp                                       \n\t\t\t\t"   +
							"}                                                            \n\t\t\t"     +
						"}                                                                \n\t\t"       +
					"}                                                                    \n\n\t\t"     +

					// redraw objects to correct positions
					"var currentY = topMargin                                             \n\n\t\t"     +
					"for (var i = 0; i<numberOfObjects; i++) {                            \n\t\t\t"     +
						"positions[i].moveTo(leftMargin, currentY)                        \n\t\t\t"     +
						"currentY += orderingObjectHeight + orderingObjectVerticalSpacing \n\t\t"       +
					"}                                                                    \n\t\t"       +

					"resolveObjectPositions()                                             \n\t"         +
				"}                                                                        \n\n\t"       +

				"function resolveObjectPositions() {                                      \n\n\t\t"     +
					"for (var i = 0; i < numberOfObjects; i++) {                          \n\t\t") ;
							for (int j = 1; j <= attrcount; j++) {
								setting.append(
								"\t if (positions[i].id == 'option"+j+"Div') {            \n\t\t\t\t"   +
									"document.ordering.posof"+j+".value = ''+(i+1)        \n\t\t\t\t"   +
									"continue                                             \n\t\t\t"     +
								"}                                                        \n\t\t");
							};

					setting.append(
					"}                                                                    \n\t"         +
				"}                                                                        \n\n\t"       +

				"function getObjectHeight() {                                             \n\t\t"       +
					"return orderingObjectHeight                                          \n\t"         +
				"}                                                                        \n\n\t"       +

				"</script>                                                                \n\n\t"       +

				"<style type=\"text/css\">                                                \n\t\t"       +
					"<!--                                                                 \n\t\t");

						for (int index = 1; index <= attrcount; index++) {
							setting.append (
							"\t #option" + index + "Div {position:absolute; left:0; top:0; height:50} \n\t\t");
						};

					setting.append (
					"->                                                                   \n\t"         +
				"</style>                                                                 \n\n\t"       +


				"<link href=\"http://www.cs.helsinki.fi/group/assari/eAssarit/eAssariWeb/jsp/assari.css/assari.css\" rel=\"stylesheet\" type=\"text/css\"/>  \n"   +
			"</head>                                                                      \n\n"         +

			"<body onLoad=\"init()\" class=\"body\">                                      \n\t"         +
				"<p class=\"assignment\">" + cache.getAttribute("T", taskid, "task", language) + "</p> \n\n\t" +
				"<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"helpnote\"> \n\t\t" +
					"<tr>                                                                 \n\t\t\t"     +
						"<td><p>" + cache.getAttribute("D", "OrderDisplayer", "helpline", language)+ "</p></td> \n\t\t" +
					"</tr>                                                                \n\t"         +
				"</table>                                                                 \n\n\t"       +

				"<p align=\"left\">                                                       \n\t");



				//setting.append("<form name=\"ordering\" action=\"http://www.helsinki.fi/cgi-bin/dump-all\" method=\"post\"> \n\t\t");
				
				setting.append(
				"<form name=\"ordering\" action=" + targetServlet + " method=\"post\"> \n\t\t");
					if(allowTry){
						setting.append (super.getButton());
					}
					setting.append (
					"<p class=\"assignment\"><strong>                                     \n\t\t");
						for (int index = 1; index <= attrcount; index++) {
							setting.append ("<div id=\"option"+index+"Div\" class=\"orderingblock\"><p>" + cache.getAttribute ("T", taskid, "object" + index, language) +"</p></div> \n\t\t\t");
							setting.append ("<input name=\"posof" + index + "\" type=\"hidden\" value=\"\" > \n\t\t");
						}
						setting.append (hiddens);
					setting.append (
					"</p>                                                                 \n\t"         +
				"</form>\n\n" +
			"</body>                                                                      \n"           +
		"</html>                                                                          \n");

	return new String(setting);
	}
}

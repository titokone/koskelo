<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>


   <HEAD>


      <TITLE> Ohtutesti </TITLE>
      <LINK REL="stylesheet" TYPE="text/css" HREF="http://www.cs.helsinki.fi/group/assari/css/assari.css" TITLE="ohtu">
      <META NAME="description" CONTENT="Ohtu-projekti">
      <META NAME=keywords CONTENT="Helsingin yliopisto, TKTL, ohtu-projekti">


      <STYLE TYPE="text/css"; text-align: "justify">
         P { font-size: 90%; }
         TABLE { font-size: 90% }
      </STYLE>


   </HEAD>




   <BODY>


      <CENTER>
	<BR>
        <H2> - Esityskomponenttien testisivu - </H2>
        <BR><HR><BR><BR>


		<BR>
      	<FORM NAME="displayerselectionform" ACTION="<%=request.getContextPath()%>/eAssari/displayers" METHOD="Post">
      	   <TABLE BORDER="0">
      	      <TR><INPUT TYPE="hidden" NAME="sid" VALUE="studentX1"></TR>
      	      <TR><INPUT TYPE="hidden" NAME="cid" VALUE="courseX1"></TR>
      	      <TR><INPUT TYPE="hidden" NAME="mid" VALUE="moduleX1"></TR>
      	      <TR><TD> Tehtävätunnus: </TD>
      	          <TD><SELECT NAME="tno">
				        <OPTION> 101 </OPTION>
				        <OPTION> 102 </OPTION>
				        <OPTION> 103 </OPTION>
				        <OPTION> 104 </OPTION>
				        <OPTION> 105 </OPTION>
      		      	  </SELECT>
      		      </TD>
      	      </TR>
      	      <TR><TD> Esityskieli: </TD>
      		      <TD><SELECT NAME="lang">
      		      		<OPTION> EN </OPTION>
      		      		<OPTION> FI </OPTION>
      		      		<OPTION> SE </OPTION>
      		      	   </SELECT>
      	          </TD>
      	      </TR>
      	      <TR>
      	      	<TH COLSPAN="2">
      	      	   <INPUT TYPE="submit" VALUE="             Valitse             ">
      	      	</TH>
      	  	  </TR>
      	  </TABLE>
      	</FORM>
      </CENTER>
   </BODY>
</HTML>

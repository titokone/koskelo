<html>
<head>
<title>Staattisen tehtävän koostaminen</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<h1>Staattisen tehtävän luonti</h1>
<form method="post" action="LAITA TÄHÄN KOHDE" name="">
  <p>Tehtävänanto: <br>
    <textarea name="taskDescription" cols="100" rows="4"></textarea>
  </p>
  <p>Opiskelijalle näytettävät syötteet:<br>
    <input type="text" name="publicInput" size="50" value="(1, 2, 3)">
  </p>
  <p>Salaiset syötteet:<br>
    <input type="text" name="hiddenInput" size="50" value="(4, 5, 6)">
  </p>
  <p>Ohjelman malliratkaisu: <br>
    <textarea name="exampleCode" cols="50" rows="15"></textarea>
  </p>
  <p>Ohjelma saa sisältää enintään 
    <input type="text" name="maxSize" size="10" maxlength="4">
    käskyä (silmukka esto).<br>
    Ratkaisun on oltava alle 
    <input type="text" name="acceptedSize" size="10" maxlength="4">
    käskyä jotta se hyväksytään.<br>
    Ohjelman ihannekoko on alle 
    <input type="text" name="optimalSize" size="10" maxlength="4">
    käskyä. </p>
  <p> 
    <input type="radio" name="compareMethod" value="radiobutton">
    Verrataan malliratkaisun suorituksen perusteella</p>
  <p> 
    <input type="radio" name="compareMethod" value="radiobutton" checked>
    Vertailu valmiisiin kriteereihin</p>

      <p>Ohjelmassa vaaditut käskyt:<br>
    <textarea name="requiredCommands" rows="4" cols="50">(V, ADD);
(K, JUMP);</textarea>
  </p>

      <p>Ohjelmassa kielletyt käskyt:<br>
    <textarea name="forbiddenCommands" rows="4" cols="50"></textarea>
  </p>

  <p>Rekisterien sisältö:<br>
    <textarea name="registerValues" cols="50" rows="10">(L,R1 = 100);
(R4 &lt; 10);
(R2 &gt; 1);</textarea>
  </p>
  <p>Muistipaikkojen ja muuttujien sisältö:<br>
    <textarea name="memoryValues" cols="50" rows="10">(muuttuja1  &lt; 20);
(L,muuttuja2 = 10);</textarea>
    <br>
    Muistiviitteiden määrä: 
    <input type="text" name="memoryReferences" size="10" maxlength="8" value="&lt; 40">
  </p>
  <p>Tulosteet näytölle:<br>
    <textarea name="screenOutput" cols="50" rows="5">(1, 20);
(2, 30);
(3, 1);
(4, 5);</textarea>
  </p>
  <p>Tulosteet tiedostoon:<br>
    <textarea name="fileOutput" cols="50" rows="5"></textarea>
  </p>
  <p> 
    <input type="submit" name="Submit" value="Tehtävän luonti osa 2">
</form>
</body>
</html>

<html>
<head>
<title>Staattisen teht�v�n koostaminen</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<h1>Staattisen teht�v�n luonti</h1>
<form method="post" action="LAITA T�H�N KOHDE" name="">
  <p>Teht�v�nanto: <br>
    <textarea name="taskDescription" cols="100" rows="4"></textarea>
  </p>
  <p>Opiskelijalle n�ytett�v�t sy�tteet:<br>
    <input type="text" name="publicInput" size="50" value="(1, 2, 3)">
  </p>
  <p>Salaiset sy�tteet:<br>
    <input type="text" name="hiddenInput" size="50" value="(4, 5, 6)">
  </p>
  <p>Ohjelman malliratkaisu: <br>
    <textarea name="exampleCode" cols="50" rows="15"></textarea>
  </p>
  <p>Ohjelma saa sis�lt�� enint��n 
    <input type="text" name="maxSize" size="10" maxlength="4">
    k�sky� (silmukka esto).<br>
    Ratkaisun on oltava alle 
    <input type="text" name="acceptedSize" size="10" maxlength="4">
    k�sky� jotta se hyv�ksyt��n.<br>
    Ohjelman ihannekoko on alle 
    <input type="text" name="optimalSize" size="10" maxlength="4">
    k�sky�. </p>
  <p> 
    <input type="radio" name="compareMethod" value="radiobutton">
    Verrataan malliratkaisun suorituksen perusteella</p>
  <p> 
    <input type="radio" name="compareMethod" value="radiobutton" checked>
    Vertailu valmiisiin kriteereihin</p>

      <p>Ohjelmassa vaaditut k�skyt:<br>
    <textarea name="requiredCommands" rows="4" cols="50">(V, ADD);
(K, JUMP);</textarea>
  </p>

      <p>Ohjelmassa kielletyt k�skyt:<br>
    <textarea name="forbiddenCommands" rows="4" cols="50"></textarea>
  </p>

  <p>Rekisterien sis�lt�:<br>
    <textarea name="registerValues" cols="50" rows="10">(L,R1 = 100);
(R4 &lt; 10);
(R2 &gt; 1);</textarea>
  </p>
  <p>Muistipaikkojen ja muuttujien sis�lt�:<br>
    <textarea name="memoryValues" cols="50" rows="10">(muuttuja1  &lt; 20);
(L,muuttuja2 = 10);</textarea>
    <br>
    Muistiviitteiden m��r�: 
    <input type="text" name="memoryReferences" size="10" maxlength="8" value="&lt; 40">
  </p>
  <p>Tulosteet n�yt�lle:<br>
    <textarea name="screenOutput" cols="50" rows="5">(1, 20);
(2, 30);
(3, 1);
(4, 5);</textarea>
  </p>
  <p>Tulosteet tiedostoon:<br>
    <textarea name="fileOutput" cols="50" rows="5"></textarea>
  </p>
  <p> 
    <input type="submit" name="Submit" value="Teht�v�n luonti osa 2">
</form>
</body>
</html>

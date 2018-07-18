<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link href="<c:url value='/static/css/greske.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/style.css' />" rel="stylesheet"></link>
</head>
<body>
	<div id="sadrzaj">
	
	<c:if test="${!uspelo && pokusanoKupiti}">
		<div id="greske">
			<div> Ne mozete dodati isti proizvod u korpu. </div>
		</div>
	</c:if>
	
	<c:if test="${!uspelo && pokusanoAdmin}">
		<div id="greske">
			<div class="divGreska"> Admin ne moze da dodaje proizvode u korpu. </div>
		</div>
	</c:if>
	
	
	<center> <img height="300" width="350" src="getimage/${proizvod.idPro}"/> </center> <br>
	<center>
	<div class="tekstProizvoda">
		Ime: ${proizvod.ime} <br>
		Cena: ${proizvod.cena} din <br>
    	Opis: ${proizvod.opis} <br>
    	Kolicina: ${proizvod.kolicina} <br>
    </div>         
    </center>
	<c:if test="${proizvod.kolicina != 0 }" >
		<center> <a href="/ApotekaWeb/proizvodi/dodajUKorpu?idP=${proizvod.idPro}"> <button class="btnTabela1" type="submit"> Dodaj u korpu </button> </a> </center>
	</c:if>

	</div>

</body>
</html>
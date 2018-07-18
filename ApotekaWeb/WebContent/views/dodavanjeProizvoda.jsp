<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link href="<c:url value='/static/css/greske' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/style.css' />" rel="stylesheet"></link>
</head>
<body>
	<a href="/ApotekaWeb/admin/adminovaStranica"> <button class="btnTabela2" type="submit"> Nazad </button> </a>

	<center> <h1> Unos proizvoda </h1> </center>
	<c:if test="${!uspelo && pokusano}">
		Dodavanje proizvoda nije uspelo. Mozete pokusati ponovo.
	</c:if>
	

	<center>
	<form:form  modelAttribute="proizvodI" action="/ApotekaWeb/admin/dodavanjeProizvoda1" method="POST" 
		  enctype="multipart/form-data">
		Ime:  <form:input type="text" name="ime" path="ime"/>  <br>
		Cena: <form:input type="text" name="cena" path="cena"/> <br>
		Kolicina <form:input type="text" name="kolicina" path="kolicina"/> <br>
		Opis <form:input type="text" name="opis" path="opis"/> <br>
		Kategorija: <select name="kat">
						<option disabled selected value> -- select an option -- </option>
						<c:forEach var="k" items="${kategorije}">
							<option value="${k.idKat}"> ${k.naziv} </option>
						</c:forEach> 
					</select> <br>
		<form:input type="file" path="slika"/> <br>
		<input type="submit" value="Dodaj"> <br>
	</form:form>
	</center>
</body>
</html>
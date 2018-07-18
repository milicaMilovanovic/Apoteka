<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link href="<c:url value='/static/css/greske.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/style.css' />" rel="stylesheet"></link>
</head>
<body>

	<a href="/ApotekaWeb/admin/adminovaStranica"> <button class="btnTabela2" type="submit"> Nazad </button> </a>

	<c:if test="${!uspelo && pokusano}">
		<div id="greske">
			<div> Dodavanje kategorije nije uspelo. Mozete pokusati ponovo. </div>
		</div>
	</c:if>
	 

	Dodavanje kategorije
	<form action="/ApotekaWeb/admin/dodavanjeKategorije1" method="POST">
		Naziv: <input type="text" name="naziv">
		Dodajte proizvode:
		<select name="selectProizvoda" multiple="multiple">
			<option disabled selected value> -- select an option -- </option>
			<c:forEach var="p" items="${proizvodiBezKategorije}">
				<option value="${p.idPro}"> ${p.ime} </option>
			</c:forEach>
		</select>
		
		<input type="submit" value="Dodaj">
	</form>

</body>
</html>
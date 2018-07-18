<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link href="<c:url value='/static/css/login.css' />" rel="stylesheet"></link>
</head>
<body>
	<div id="sadrzaj">
	
	<c:if test="${nemaParam && pokusanoReg1}">
	<div id="greske">
		<div> Niste popunili sva polja za registraciju. </div>
	</div>
	</c:if>
	
	<c:if test="${!uspelo && pokusanoReg}">
	<div id="greske">
		<div> Username vec postoji. Molimo vas izaberite drugi. </div>
	</div>
	</c:if>
	
	<c:if test="${nadjenUser && postojiReg}">
	<div id="greske">
		<div> Doslo je do greske. Molimo Vas pokusajte ponovo. </div>
	</div>
	</c:if>
	
	<center><h2>Registracija</h2></center>	
	<center>
    <div class="kontejner">
	
		<form action="/ApotekaWeb/prijava/registracija" method="POST">
				Korisnicko ime: <input type="text" name="username">
				E-mail: <input type="text" name="email">
				Ime: <input type="text" name="ime">
				Prezime: <input type="text" name="prezime">
				Sifra: <input type="password" name="password">
				<input type="submit" value="Registruj se"> 
		</form>
	</div>
	</center>
	</div>
</body>
</html>
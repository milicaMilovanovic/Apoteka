<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link href="<c:url value='/static/css/style.css' />" rel="stylesheet"></link>
</head>
<body>
	
	<c:if test="${empty sveKategorije}">
		<h2> Nema kategorija trenutno. </h2>
	</c:if>
	<br>
	<br>
	<center>
	<div>
	<c:forEach var="kat" items="${sveKategorije}">
		<a href="/ApotekaWeb/proizvodi/prikaziKategoriju?idK=${kat.idKat}"> <button class="btnTabela1" type="submit"> ${kat.naziv} </button> </a>
	</c:forEach>
	</div>
	</center>
</body>
</html>
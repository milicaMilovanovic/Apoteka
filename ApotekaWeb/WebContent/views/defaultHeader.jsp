<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link href="<c:url value='/static/css/header.css' />" rel="stylesheet"></link>
</head>
<body>

	<div id='slika'>
		<div id="site-title"> <span> Apoteka </span> </div>
	</div>
	
	<div id='cssmenu'>
		<ul>
			<!--  class='active' -->
   			<li><a href="/ApotekaWeb/proizvodi/prikazPocetne"><span>Pocetna</span></a></li>
   			<li><a href="/ApotekaWeb/proizvodi/stranicaKategorije"><span>Kategorije</span></a></li>
   			<li><a href='#'><span>O nama</span></a></li>
   			<li class='last'><a href='#'><span>Kontakt</span></a></li>
   			<li style="float:right"> <a href="/ApotekaWeb/prijava/logout"> <span>Log out</span></a> </li>
   			<c:if test="${!sessionScope.korisnikAdmin}">
   				<li style="float:right"> <a href="/ApotekaWeb/proizvodi/prikaziKorpu"> <span>Korpa</span></a> </li>
   			</c:if>
   			<c:if test="${sessionScope.korisnikAdmin}">
   				<li style="float:right"> <a href="/ApotekaWeb/admin/adminovaStranica"> <span>Admin</span></a> </li>
   			</c:if>
		</ul>
	</div>
</body>
</html>
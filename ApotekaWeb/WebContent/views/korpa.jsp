<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link href="<c:url value='/static/css/pocetna.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/greske.css' />" rel="stylesheet"></link>
	
</head>
<body>
	
	<c:if test="${pokusanoKupiti && !dostupno }">
	<div id="greske">
		<div> Proizvoda vise nema na stanju. Pokusajte za par dana. </div>
	</div>
	</c:if>
	
	<c:if test="${empty proizvodi }">
		<h2> Trenutno nemate proizvoda u korpi. </h2>
	</c:if>
	
	<center> <a href= "/ApotekaWeb/proizvodi/obrisiSveIzKorpe"> <button class="btnPrikazi" type="submit"> Obrisi sve iz korpe </button> </a> </center>
	
	<c:if test="${!empty proizvodi }">
	
	
		<center>
	<table>
	
		<c:forEach var="pro" items="${proizvodi}" varStatus="theCount">
			<c:if test="${theCount.count % 3 == 0}" >
				<tr>
			</c:if> 
				<td>
					<center> <img height="180" width="210" src="/ApotekaWeb/proizvodi/getimage/${pro.idPro}"/> </center> <br>
					<center> ${pro.ime} ${pro.cena} din</center>
				<center>    <a href= "/ApotekaWeb/proizvodi/kupiProizvod?idP=${pro.idPro}"> <button class="btnPrikazi" type="submit"> Kupi </button> </a> 
				    <a href= "/ApotekaWeb/proizvodi/obrisiJednogIzKorpe?idP=${pro.idPro}"> <button class="btnPrikazi" type="submit"> Obrisi </button> </a>	 </center>
				</td>
			<c:if test="${(theCount.count+1) % 3 == 0}" >
				</tr>
			</c:if>
		</c:forEach>
		
	</table>	
	</center>
		
	</c:if>
	
</body>
</html>
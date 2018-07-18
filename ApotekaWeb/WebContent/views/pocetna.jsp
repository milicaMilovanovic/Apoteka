<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link href="<c:url value='/static/css/pocetna.css' />" rel="stylesheet"></link>

</head>
<body>
	
	<c:if test="${imaKategorija}" >
		<center> <h1> ${imeKategorija} </h1> </center>
	</c:if>
	
	<c:if test="${!imaKategorija}" >
		<center> <h1> Svi proizvodi </h1> </center>
		<center>
		<div class="sortCombo">	
			<form action="/ApotekaWeb/proizvodi/sortiraj" method="GET">
				<select name="kriterijum">
					<option disabled selected value> -- izaberite sort -- </option>
					<option value="cena"> po ceni </option>
					<option value="leksikografski"> po imenu </option>
					<option value="kolicina"> po kolicini </option>
				</select>
				<center> <input type="submit" value="Sortiraj"> </center>
			</form> 
		</div>
		</center>
	</c:if>


	<center>
	<table>
	
		<c:forEach var="pro" items="${proizvodi}" varStatus="theCount">
			<c:if test="${theCount.index % 3 == 0}" >
				<tr>
			</c:if> 
				<td>
					<center> <img height="180" width="210" src="/ApotekaWeb/proizvodi/getimage/${pro.idPro}"/> </center> <br>
					<center> ${pro.ime} ${pro.cena} din</center>
					<center> <a href= "/ApotekaWeb/proizvodi/prikazJednog?idP=${pro.idPro}"> <button class="btnPrikazi" type="submit"> Prikazi detaljnije </button> </a> </center>
				</td>
			<c:if test="${(theCount.index+1) % 3 == 0}" >
				</tr>
			</c:if>
		</c:forEach>
		
	</table>	
	</center>
	
</body>
</html>
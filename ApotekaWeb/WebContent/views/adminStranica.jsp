<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	
	<link href="<c:url value='/static/css/style.css' />" rel="stylesheet"></link>
</head>
<body>
   
   	<center> <h1>Izvestaji</h1> </center>
   	
   	<h3> Proizvodi sa kolicinom manjom od unete </h3>
   	<form action="/ApotekaWeb/reports/ProizvodiManjeKolicine.pdf" method="POST">
		Kolicina: <input type="text" name="kolicina">
		<input type="submit" value="Prikazi">
	</form> <br>
	<h3> Korisnici sa nepraznom korpom </h3>
	<form action="/ApotekaWeb/reports/KorisniciNeprazneKorpe.pdf" method="POST">
   		<input type="submit" value="Prikazi">
	</form> <br> <br>
   
	<center> <h1>Proizvodi</h1> </center>
	
	<c:if test="${empty sviProizvodi}">
		Nema proizvoda.
	</c:if>
	<c:if test="${!empty sviProizvodi}">
		
		<table id="t01">
			<tr>
				<th> <center> Id </center> </th>
				<th> <center> Ime </center> </th>
				<th> <center> Cena </center> </th>
				<th> <center> Kolicina </center> </th>
				<th> <center> Kategorija </center> </th>
				<th> <center> Obrisi </center> </th>
			</tr>
			<c:forEach var="p" items="${sviProizvodi}">
				<tr> 
					<td width="5%"> <center> ${p.idPro} </center> </td>
					<td> ${p.ime} </td>
					<td> ${p.cena} </td>
					<td> ${p.kolicina} </td>
					<c:if test="${empty p.kategorija.naziv}">
						<td> <center> / </center> </td>
					</c:if>
					<c:if test="${!empty p.kategorija.naziv}">
						<td> ${p.kategorija.naziv} </td>
					</c:if>
					<td> <center> <a href="/ApotekaWeb/admin/obrisiProizvod?idP=${p.idPro}">  <button class="btnTabela" type="submit"> Obrisi </button> </a> </center> </td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<!-- <a href="/ApotekaWeb/admin/dodajProizvod"> Dodaj proizvod </a> -->
	<center> <a href="/ApotekaWeb/admin/dodajProizvod"><button class="btn btn-default" type="submit"> Dodaj proizvod </button></a><br> </center>
		
	<center> <h1> Kategorije </h1> </center>
	<c:if test="${empty sveKategorije}">
		Nema kategorija.
	</c:if>
	<c:if test="${!empty sveKategorije}">
		<table id="t01" >
			<tr>
				<th> <center> Id </center> </th>
				<th> <center> Naziv </center> </th>
				<th> <center> Obrisi </center> </th>
			</tr>
			<c:forEach var="k" items="${sveKategorije}">
				<tr> 
					<td width="5%"> <center> ${k.idKat} </center> </td>
					<td> ${k.naziv}</td>
					<td width="30%"> <center> <a href="/ApotekaWeb/admin/obrisiKategoriju?idK=${k.idKat}"> <button class="btnTabela" type="submit"> Obrisi </button> </a> </center> </td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<center> <a href="/ApotekaWeb/admin/dodajKategoriju"><button class="btn btn-default" type="submit"> Dodaj kategoriju </button></a><br> </center>
	<!-- <a href="/ApotekaWeb/admin/dodajKategoriju"> Dodaj kategoriju </a> -->
</body>
</html>
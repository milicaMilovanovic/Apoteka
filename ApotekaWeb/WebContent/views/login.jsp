<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>
	<link href="<c:url value='/static/css/login.css' />" rel="stylesheet"></link>
</head>
<body>

	<%-- <c:url var="loginUrl" value="/loginS" />  --%>  <!-- security -->

	<c:if test="${loginFailed && pokusanLogin}" >
	<div id="greske">
		<div> Niste uneli dobru sifru ili username. </div>
	</div>
	</c:if>
	
	<c:if test="${nijeUneto && pokusanLogin}" >  
	<div id="greske">
		<div> Niste popunili sva polja za login. </div>
	</div>
	</c:if>
	
	<div class="kontejnerLog">
		<h2> Login </h2>
		<form action="/ApotekaWeb/prijava/login" method="POST">
		<%-- <form action="${loginUrl}" method="post"> --%>   <!-- security -->
			Korisnicko ime: <input type="text" name="username"> <br>
			Sifra: <input type="password" name="password"> <br>
			<input type="submit" value="Login">
			
			<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 	 --%>	<!-- security -->
		</form>
	</div>
	
	<div class="zaReg">
		<h2> Registracija </h2>	
	    <h4> Ukoliko jos uvek nemate nalog registrujte se klikom na link ispod! <h4>
		<a href="/ApotekaWeb/views/registracija.jsp"> <button class="btn btn-default" type="submit"> Registruj se </button> </a>
	</div>	
	
</body>
</html>



			
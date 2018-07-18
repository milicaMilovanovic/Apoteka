<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>
	<title><tiles:getAsString name="title" /></title>
	<link href="<c:url value='/static/css/header.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/footer.css' />" rel="stylesheet"></link>
</head>
 
<body>
		<header id="header" >
			<tiles:insertAttribute name="header" />
		</header>
	
		<section id="site-content" >
			<tiles:insertAttribute name="body" />
		</section>
		
		<footer id="footer" >
			<tiles:insertAttribute name="footer" />
		</footer>
</body>
</html>
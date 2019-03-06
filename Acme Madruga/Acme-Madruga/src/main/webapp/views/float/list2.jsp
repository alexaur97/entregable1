<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="floats" id="floaat"
	requestURI="${requestURI }">
	<display:column titleKey="float.picture" > <img src="${brotherhood.picture}" alt="floatLogo" height="200" width="350"/></display:column>
	<display:column titleKey="float.title" property="title" />
	<display:column titleKey="float.edit">
		<a href="brotherhood/float/edit.do?floatId=${floaat.id}">
		<spring:message code="float.edit" />
		</a>
		</display:column>
		<display:column titleKey="float.show">
		<a href="brotherhood/float/show.do?floatId=${floaat.id}">
		<spring:message code="float.show" />
		</a>
	</display:column>
</display:table>


  <acme:cancel url="/brotherhood/float/create.do" code="float.create"/>  
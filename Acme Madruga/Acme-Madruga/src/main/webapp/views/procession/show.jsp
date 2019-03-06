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
<br/><spring:message code="procession.title"/>: <jstl:out value="${procession.title}"></jstl:out>
<br/><spring:message code="procession.description"/>: <jstl:out value="${procession.description}"></jstl:out>
<br/><spring:message code="procession.moment"/>: <jstl:out value="${procession.moment}"></jstl:out>
<br/><spring:message code="procession.mode"/>: <jstl:out value="${procession.mode}"></jstl:out>
<br/><spring:message code="procession.brotherhood"/>: <jstl:out value="${procession.brotherhood.title}"></jstl:out>

<br/><spring:message code="procession.floats"/>
:<display:table name="floats" id="float"
	requestURI="${requestURI }">
	<display:column titleKey="procession.title" property="title" />

	
</display:table>


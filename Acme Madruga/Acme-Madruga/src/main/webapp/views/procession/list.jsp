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

<display:table name="processions" id="procession"
	requestURI="${requestURI}">
	<display:column titleKey="procession.title"  property="title" />
	<display:column titleKey="procession.description" property="description" />
	<display:column titleKey="procession.moment" property="moment" />
	<display:column titleKey="procession.ticker" property="ticker" />
	<display:column titleKey="procession.mode" property="mode" />
	<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey="procession.edit">
		<a href="brotherhood/procession/edit.do?processionId=${procession.id}">
		<spring:message code="procession.edit" />
		</a>
		</display:column>
		</security:authorize>
		
	<display:column titleKey="procession.show">
		<a href="brotherhood/procession/show.do?processionId=${procession.id}">
		<spring:message code="procession.show" />
		</a>
	</display:column>
</display:table>
		<security:authorize access="hasRole('BROTHERHOOD')">
		<acme:cancel url="/brotherhood/procession/create.do" code="procession.create"/>
		</security:authorize>

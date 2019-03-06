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

<br/><img src="${float.pictures}" alt="MemberPhoto" height="200" width="350"/>

<br/><img src="${member.photo}" alt="MemberPhoto" height="200" width="350"/>
<br/><spring:message code="member.name"/>: <jstl:out value="${member.name}"></jstl:out>
<br/><spring:message code="member.middleName"/>: <jstl:out value="${member.middleName}"></jstl:out>
<br/><spring:message code="member.surName"/>: <jstl:out value="${member.surname}"></jstl:out>
<br/><spring:message code="member.email"/>: <jstl:out value="${member.email}"></jstl:out>
<br/><spring:message code="member.address"/>: <jstl:out value="${member.address}"></jstl:out>

<br/><spring:message code="member.enrolment"/>:
<display:table name="enrolments" id="enrolment"
	requestURI="${requestURI }">
	<display:column titleKey="member.brotherhood" property="brotherhood.name" />
	<display:column titleKey="member.moment" property="moment" />
	
</display:table>

<br/><spring:message code="member.dropOut"/>:
<display:table name="dropOuts" id="dropOut"
	requestURI="${requestURI }">
	<display:column titleKey="member.brotherhood" property="brotherhood.name" />
	<display:column titleKey="member.moment" property="moment" />
	
</display:table>

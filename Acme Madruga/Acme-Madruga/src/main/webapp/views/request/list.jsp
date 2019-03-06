<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('MEMBER')">
	<acme:cancel url="/request/member/create.do" code="request.create"/>
</security:authorize>	

<h3>
	<spring:message code="request.acceptedRequests" />
</h3>
<display:table name="acceptedRequests" id="acceptedRequest"
	requestURI="${requestURI }">
	<display:column style="color:green" titleKey="request.status" property="status" />
	<display:column style="color:green" titleKey="request.procession"
		property="procession.title" />
	<display:column style="color:green" titleKey="request.row" property="row" />
	<display:column style="color:green" titleKey="request.column" property="column" />
</display:table>

<h3>
	<spring:message code="request.pendingRequests" />
</h3>
<display:table name="pendingRequests" id="pendingRequest"
	requestURI="${requestURI }">
	<display:column style="color:grey" titleKey="request.status" property="status" />
	<display:column style="color:grey" titleKey="request.procession"
		property="procession.title" />
	<security:authorize access="hasRole('MEMBER')">
	<display:column titleKey="request.delete">
		<acme:cancel url="/request/member/delete.do?requestId=${pendingRequest.id}"
			code="request.delete" />
	</display:column>
	</security:authorize>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey="request.accept">
		<acme:cancel url="/request/brotherhood/accept.do?requestId=${pendingRequest.id}"
			code="request.accept" />
	</display:column>
	<display:column titleKey="request.reject">
		<acme:cancel url="/request/brotherhood/reject.do?requestId=${pendingRequest.id}"
			code="request.reject" />
	</display:column>
	</security:authorize>
</display:table>

<h3>
	<spring:message code="request.rejectedRequests" />
</h3>
<display:table name="rejectedRequests" id="rejectedRequest"
	requestURI="${requestURI }">
	<display:column style="color:orange" titleKey="request.status" property="status" />
	<display:column style="color:orange" titleKey="request.procession"
		property="procession.title" />
</display:table>



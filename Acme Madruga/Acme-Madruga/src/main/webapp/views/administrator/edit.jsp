<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="administrator/edit.do"
	modelAttribute="memberAdminRegisterForm" method="POST">

	<acme:textbox code="administrator.name" path="name" />
	<acme:textbox code="administrator.middleName" path="middleName" />
	<acme:textbox code="administrator.surName" path="surName" />
	<acme:textbox code="administrator.photo" path="photo" />
	<acme:textbox code="administrator.email" path="email" />
	<acme:textbox code="administrator.phone" path="phone" />
	<acme:textbox code="administrator.address" path="address" />
	<acme:textbox code="administrator.username" path="username" />
	<acme:password code="administrator.password" path="password" />
	<acme:password code="administrator.confirmPassword"
		path="confirmPassword" />
	<spring:message code="administrator.check" />
	<form:checkbox path="terms" />
	<form:errors path="terms" cssClass="error" />
	<br />

	<acme:submit name="save" code="administrator.save" />
	<acme:cancel url="/#" code="administrator.cancel" />

</form:form>

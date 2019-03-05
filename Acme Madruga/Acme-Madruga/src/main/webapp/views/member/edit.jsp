<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%> 

<form:form action="member/edit.do" modelAttribute="reg">

<acme:textbox code="member.name" path="name"/>
<acme:textbox code="member.middleName" path="middleName"/>
<acme:textbox code="member.surName" path="surName"/>
<acme:textbox code="member.photo" path="photo"/>
<acme:textbox code="member.email" path="email"/>
<acme:textbox code="member.phone" path="phone"/>
<acme:textbox code="member.address" path="address"/>
<acme:textbox code="member.username" path="username"/>
<acme:password code="member.password" path="password"/>
<acme:password code="member.confirmPassword" path="confirmPassword"/>
<spring:message code="member.check"/><form:checkbox path="terms"/>
<br/>

<acme:submit name="save" code="member.save"/>
<acme:cancel url="/#" code="member.cancel"/>

</form:form>
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

<form:form action="actor/edit.do" modelAttribute="actorEditForm" method="POST">

<acme:textbox code="actor.name" path="name"/>
<acme:textbox code="actor.middleName" path="middleName"/>
<acme:textbox code="actor.surName" path="surname"/>
<acme:textbox code="actor.photo" path="photo"/>
<acme:textbox code="actor.email" path="email"/>
<acme:textbox code="actor.phone" path="phoneNumber"/>
<acme:textbox code="actor.address" path="address"/>
<br/>
<acme:submit name="save" code="actor.save"/>
</form:form>
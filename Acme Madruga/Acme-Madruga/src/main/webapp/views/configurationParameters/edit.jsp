<%--
 * action-2.jsp
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

<form:form modelAttribute="configurationParameters"
	action="configurationParameters/administrator/edit.do">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="name"><spring:message code="config.name"/></form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br/>
	
	<form:label path="banner"><spring:message code="config.banner"/></form:label>
	<form:input path="banner"/>
	<form:errors cssClass="error" path="banner"/>
	<br/>
	<form:label path="sysMessage"><spring:message code="config.sysMessage"/></form:label>
	<form:input path="sysMessage"/>
	<form:errors cssClass="error" path="sysMessage"/>
	<br/>
	<form:label path="sysMessageEs"><spring:message code="config.sysMessageEs"/></form:label>
	<form:input path="sysMessageEs"/>
	<form:errors cssClass="error" path="sysMessageEs"/>
	<br/>
	<form:label path="countryCode"><spring:message code="config.countryCode"/></form:label>
	<form:input path="countryCode"/>
	<form:errors cssClass="error" path="countryCode"/>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="config.save"/>"/>
	<input type="button" name="cancel" value="<spring:message code="config.cancel"/>" onclick="javascript: relativeRedir('#')"/>
	
</form:form>

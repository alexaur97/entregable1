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

<spring:message code="positions.all" var="positions"/>

<div class="container">
<div class="row">
<div class="col-xs-12 col-sm-12 col-md-12">
<div class="table-responsive">	

<display:table name="positions" id="row" class="table" requestURI="${resquestURI}">
	<display:column titleKey="position.name" property="name"/>	
	<display:column titleKey="position.nameEs" property="nameEs"/>	
	<display:column titleKey="position.edit">
		<a href="position/administrator/edit.do?positionId=${row.id}">
		<spring:message code="position.edit" />
		</a>
	</display:column>
</display:table>

</div>
</div>
</div>
		<acme:cancel url="/position/administrator/create.do" code="position.create"/>
</div>
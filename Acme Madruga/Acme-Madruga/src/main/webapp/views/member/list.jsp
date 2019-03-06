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

<display:table name="members" id="member"
	requestURI="${requestURI }">
	<display:column titleKey="member.name" property="name" />
	<display:column titleKey="member.middleName" property="middleName" />
	<display:column titleKey="member.surName" property="surname" />
	<display:column titleKey="member.profile">
		<acme:cancel url="/member/brotherhood/profile.do?memberId=${member.id}" code="member.profile" />
	</display:column>
	<display:column titleKey="member.remove">
		<acme:cancel url="/dropOut/brotherhood/create.do?memberId=${member.id}" code="member.remove" />
	</display:column>
</display:table>
		<acme:cancel url="/enrolment/brotherhood/create.do" code="member.enrol" />


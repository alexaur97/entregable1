<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" width="450" alt="Acme Madruga Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configurationParameters/administrator/edit.do"><spring:message code="master.page.administrator.config" /></a></li>

					<li><a href="position/administrator/list.do"><spring:message code="master.page.administrator.position" /></a></li>

					<li><a href="stats/administrator/display.do"><spring:message code="master.page.administrator.stats" /></a></li>

				</ul>
			</li>
		</security:authorize>


		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="brotherhood/list.do"><spring:message code="master.page.brotherhoods" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="brotherhood/create.do"><spring:message code="master.page.signup.brotherhood" /></a></li>
			<li><a class="fNiv" href="member/create.do"><spring:message code="master.page.signup.member" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
					<li><a class="fNiv"><spring:message	code="master.page.brotherhoods" /></a>
				<ul>
					<li class="arrow"></li>
			<li><a href="brotherhood/list.do"><spring:message code="master.page.allbrotherhoods" /></a></li>

		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a href="member/brotherhood/list.do"><spring:message code="master.page.mymembers" /></a></li>
			<li><a href="brotherhood/procession/list.do"><spring:message code="master.page.processions" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('MEMBER')">
			<li><a href="brotherhood/member/myList.do"><spring:message code="master.page.mybrotherhoods" /></a></li>
		</security:authorize>				</ul>
			</li>

		<security:authorize access="hasRole('MEMBER')">
			<li><a href="request/member/list.do"><spring:message code="master.page.requests" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a href="brotherhood/procession/list.do"><spring:message code="master.page.processions" /></a></li>
		</security:authorize>

			<li>
				<a class="fNiv">
					<spring:message code="master.page.profile" />
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

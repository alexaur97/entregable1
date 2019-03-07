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


<spring:message code="stats.members.brotherhood.average"/>: <jstl:out value="${membersPerBrotherhood[0][0]}"/>
<br/>
<spring:message code="stats.members.brotherhood.min"/>: <jstl:out value="${membersPerBrotherhood[0][1]}"/>
<br/>
<spring:message code="stats.members.brotherhood.max"/>: <jstl:out value="${membersPerBrotherhood[0][2]}"/>
<br/>
<spring:message code="stats.members.brotherhood.stddev"/>: <jstl:out value="${membersPerBrotherhood[0][3]}"/>
<br/>

<h4><spring:message code="stats.largest.brotherhoods"/>:</h4>
<ol>
<jstl:forEach items="${largestBrotherhoods}" var="brotherhood">
<li><jstl:out value="${brotherhood.title}"/></li>
</jstl:forEach>
</ol>

<h4><spring:message code="stats.smallest.brotherhoods"/>:</h4>
<ol>
<jstl:forEach items="${smallestBrotherhoods}" var="brotherhood">
<li><jstl:out value="${brotherhood.title}"/></li>
</jstl:forEach>
</ol>

<h4><spring:message code="stats.request.ratio"/>:</h4>
<spring:message code="stats.approved.request.ratio"/>: <jstl:out value="${approvedRatio}"/>
<br/>
<spring:message code="stats.pending.request.ratio"/>: <jstl:out value="${pendingRatio}"/>
<br/>
<spring:message code="stats.rejected.request.ratio"/>: <jstl:out value="${rejectedRatio}"/>
<br/>
<h4><spring:message code="stats.soon"/>:</h4>
<ul>
<jstl:forEach items="${soon}" var="procession">
<li><jstl:out value="${procession.title}"/></li>
</jstl:forEach>
</ul>
<h4><spring:message code="stats.members.ten.percent"/>:</h4>
<ul>
<jstl:forEach items="${members}" var="member">
<li><jstl:out value="${member.name}"/> <jstl:out value="${member.surname}"/></li>
</jstl:forEach>
</ul>

<body>
	<div class="container" style="width: 75%;">
		<div
			style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"
			class="chartjs-size-monitor">
			<div class="chartjs-size-monitor-expand"
				style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
				<div
					style="position: absolute; width: 1000000px; height: 1000000px; left: 0; top: 0"></div>
			</div>
			<div class="chartjs-size-monitor-shrink"
				style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
				<div
					style="position: absolute; width: 200%; height: 200%; left: 0; top: 0"></div>
			</div>
		</div>
		<canvas id="myChart"
			style="display: block; width: 935px; height: 467px;" width="935"
			height="467" class="chartjs-render-monitor"></canvas>
	</div>

</body>


<script>
var president = "${president}";
var vicePresident = "$(vicePresident)";
var secretary = "$(secretary)";

	var ctx = document.getElementById('myChart').getContext('2d');
	var myChart = new Chart(ctx, {
		type : 'bar',
		data : {
			labels : [
					"Long", "Short", "All"
			],
			datasets : [
				{
					label : 'Shouts',
					data : [president, vicePresident, secretary],
					backgroundColor : [
						'rgba(255, 99, 132, 0.2)','rgba(255, 99, 132, 0.2)','rgba(255, 99, 132, 0.2)'
					],
					borderColor : [
						'rgba(255,99,132,1)','rgba(255, 99, 132, 1)','rgba(255, 99, 132, 1)','rgba(255, 99, 132, 1)'
					],
					borderWidth : 1
				}
			]
		},
		options : {
			scales : {
				yAxes : [
					{
						ticks : {
							beginAtZero : true
						}
					}
				]
			}
		}
	});

	Chart.scaleService.updateScaleDefaults('bar', {
		ticks : {
			min : 0
		}
	});
</script>
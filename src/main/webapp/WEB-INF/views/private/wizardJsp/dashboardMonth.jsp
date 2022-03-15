<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>
<%@include file="../common-alert.jsp"%>

<c:if test="${onlyMonth  == 'All'}"><c:set var="month" value="JAN to DEC"/></c:if>
  <c:if test="${onlyMonth  == '01'}"><c:set var="month" value="January"/></c:if>
  <c:if test="${onlyMonth  == '02'}"><c:set var="month" value="February"/></c:if>
  <c:if test="${onlyMonth  == '03'}"><c:set var="month" value="March"/></c:if>
  <c:if test="${onlyMonth  == '04'}"><c:set var="month" value="April"/></c:if>
  <c:if test="${onlyMonth  == '05'}"><c:set var="month" value="May"/></c:if>
  <c:if test="${onlyMonth  == '06'}"><c:set var="month" value="June"/></c:if>
  <c:if test="${onlyMonth  == '07'}"><c:set var="month" value="July"/></c:if>
  <c:if test="${onlyMonth  == '08'}"><c:set var="month" value="August"/></c:if>
  <c:if test="${onlyMonth  == '09'}"><c:set var="month" value="September"/></c:if>
  <c:if test="${onlyMonth  == '10'}"><c:set var="month" value="October"/></c:if>
  <c:if test="${onlyMonth  == '11'}"><c:set var="month" value="November"/></c:if>
  <c:if test="${onlyMonth  == '12'}"><c:set var="month" value="December"/></c:if>


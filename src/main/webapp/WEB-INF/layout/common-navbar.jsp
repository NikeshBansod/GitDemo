<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">GSTN</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="<spring:url value="/home" />">HOME</a></li>
            <li><a href="#about">ABOUT</a></li>
            <li><a href="#faq">FAQ</a></li>
            <li><a href="#help">HELP</a></li>
            <li><a href="#contact">CONTACT</a></li>
          </ul>
          
          <ul class="nav navbar-nav navbar-right">
			<li><a href="<spring:url value="#" />">Welcome ${userName }</a></li>
			<li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Settings<span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="<spring:url value="/profileManagement" />">Profile Management</a></li>
	            <li><a href="#">Invoice Management</a></li>
	            <li><a href="#">ASP Management</a></li>
	          </ul>
	        </li>
		    <li><a href="<spring:url value="/logout" />">LOGOUT</a></li>
		           
		  </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
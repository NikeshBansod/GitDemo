<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<script type="text/javascript" src="<spring:url value="/resources/js/eWayBill/addEwaybill.js" />"></script>

 <header class="insidehead" id="generateInvoiceDefaultPageHeader">
            <a href="<spring:url value="/home#eway_management"/>" class="btn-back"><i class="fa fa-angle-left"></i></a>
            <a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg" />" class="img-responsive"> <span1> Bill Lite </span1></a>
 </header>
   <main>
   <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
            <section class="block generateInvoice">
            
             	<div class="container" id='loadingmessage' style='display:none;' align="center">
				  <img src='<spring:url value="/resources/images/loading.gif"/>'/>
				</div>
           
                <div class="container" id="addInvoiceDiv">
                      <div class="card">
                      <span><center><h4><b> Generate E-way Bill </b></h4></center></span>
                      
                       <div class="radio-inline text-left" style="margin-top:10px">
                           
                            <div class="rdio rdio-success">
                                <input type="radio" name="invoiceFor" value="Invoice" id="radio2" checked="checked">
                                <label for="radio2"  style="margin-left: 2px">Through Invoice number</label>
                            </div>
                            <div class="rdio rdio-success">
                                <input type="radio" name="invoiceFor" value="Generic" id="radio1" >
                                <label for="radio1"  style="margin-left: 2px">Generic E-way Bill</label>
                            </div>
                      </div>
                     <br/>
                    </div>
                   
                    <div class="btns">
                        <button id="submitId"  class="btn btn-info" style="margin:5px 0 0 0" value="">Go</button>
                    </div>
                </div>
                
            </section>

        </main>


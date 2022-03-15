
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp" %> 
<%@include file="../common-alert.jsp" %>



<%--  <header class="insidehead">
	<a href="<spring:url value="javascript:redirectToPreviousPage()"/>" class="btn-back"><i class="fa fa-angle-left"></i></a>
	<a class="logoText" href="home#invoice"><img src="<spring:url value="/resources/images/home/gst_logo.jpg"/>" class="img-responsive"> <span1> Bill Lite </span1></a>
</header>  --%>

<div class="container">
<div class="loader"></div>
 <div class="brd-wrap">
             <a href="<spring:url value="javascript:redirectToPreviousPage()"/>"><strong>GST return filing</strong></a>  &raquo;<strong>  GSTR3B Return Filing</strong>
        </div>
	<%-- <div class="container" id='loadingmessage' style='display:none;' align="center">
		<img src='<spring:url value="./resources/images/loading.gif"/>'/>
	</div> --%>
	<div class="card" id="wholePrepareform">
		<div class="row">
			<div class="col-md-12">
				<h4 class="page_heading">
					<b>Prepare GSTR-3B</b>
					<span>${gstinId}</span>
				</h4>	
				<!-- <div  class="row text-right">
					<a class="downToLast" href="#" onclick="scrollSmoothToBottom()" id="optionsDiv" class="btn-back"><i class="fa fa-angle-double-down"></i></a>
				</div> -->
				<div class="panel-group" id="ewaybill-form">					
					<div class="panel">
						<div class="panel-heading" role="tab" id="headingdiv1">
						  <h4 class="panel-title">
							<div class="btn-group">
								  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										Select <span class="caret"></span>
								  </button>
								  <ul class="dropdown-menu" >
										<li><a href="#" style="color:black;" data-id="">Select</a></li>
									  	<li><a href="#" style="color:black;" data-id="3_1">3.1 Details of Outward Supplies and Inward Supplies liable to Reverse Charge</a></li>
										<li><a href="#" style="color:black;" data-id="3_2">3.2 Details of Inter-State Supplies made to Unregistered Persons, Composition Taxable Persons and UIN holders</a></li>
										<li><a href="#" style="color:black;" data-id="4_0">4 Eligible ITC</a></li>
										<li><a href="#" style="color:black;" data-id="5_0">5 Values of Exempt, Nil-Rated and Non-GST Inward Supplies</a></li>
										<li><a href="#" style="color:black;" data-id="6_1">6.1 Interest Payable</a></li>
								  </ul>
							</div>
						  </h4>
						</div>
						<div class="panel-body">
							<div class="row rowData" id="3_1">	<!-- id="threeDotOne" -->
								<div class="col-md-12">
									<h2 class="title_blue_brd"><i class="fa fa-hand-o-right"></i> Net Supplies (a+b+c+d+e)</h2>
									<table class="table">
										<tr>
											<td class="w55">Total Taxable Value</td> 
											<td class="text-right">(₹)	 <label id="3_1_totaltaxablevalue">0.00</label></td>
										</tr>
										<tr>
											<td class="w55">Integrated Tax Amount</td> 
											<td class="text-right">(₹)	 <label id="3_1_itaxamt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Central Tax Amount</td> 
											<td class="text-right">(₹)	 <label id="3_1_ctaxamt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">State/UT Tax Amount</td> 
											<td class="text-right">(₹)	 <label id="3_1_staxamt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Cess Amount</td> 
											<td class="text-right">(₹)	 <label id="3_1_csamt">0.00</label></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(a) Outward Taxable Supplies (Other than Zero Rated, Nil Rated, and Exempted)
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Total Taxable Value</td> 
											<td class="text-right"><input type="text" id="outward_nonzero_totamt" value="0.00" class="form-control text-right netSupTotAmt" /></td>
										</tr>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="outward_nonzero_iamt" value="0.00" class="form-control text-right netSupIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="outward_nonzero_camt" value="0.00" class="form-control text-right netSupCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="outward_nonzero_samt" value="0.00" class="form-control text-right netSupSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="outward_nonzero_csamt" value="0.00" class="form-control text-right netSupCsAmt"/></td>
										</tr>
									</table>
								</div>
								
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(b) Outward Taxable Supplies (Zero Rated)
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Total Taxable Value</td> 
											<td class="text-right"><input type="text" id="outward_zero_totamt" value="0.00" class="form-control text-right netSupTotAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="outward_zero_iamt" value="0.00" class="form-control text-right netSupIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="outward_zero_camt"  value="0.00" class="form-control text-right netSupCAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="outward_zero_samt"  value="0.00" class="form-control text-right netSupSAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="outward_zero_csamt" value="0.00" class="form-control text-right netSupCsAmt"/></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(c) Other Outward Supplies (Nil Rated, Exempted)
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Total Taxable Value</td> 
											<td class="text-right"><input type="text" id="otheroutward_totamt" value="0.00" class="form-control text-right netSupTotAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="otheroutward_iamt"  value="0.00" class="form-control text-right netSupIAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="otheroutward_camt"  value="0.00" class="form-control text-right netSupCAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="otheroutward_samt"  value="0.00" class="form-control text-right netSupSAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><!-- <input type="text" id="otheroutward_csamt"  value="0.00" class="form-control text-right netSupCsAmt"/> --></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(d) Inward Supplies (Liable to Reverse Charge)
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Total Taxable Value</td> 
											<td class="text-right"><input type="text" id="inward_totamt" value="0.00" class="form-control text-right netSupTotAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="inward_iamt" value="0.00" class="form-control text-right netSupIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="inward_camt" value="0.00" class="form-control text-right netSupCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="inward_samt" value="0.00" class="form-control text-right netSupSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="inward_csamt" value="0.00" class="form-control text-right netSupCsAmt"/></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(e) Non-GST Outward Supplies
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Total Taxable Value</td> 
											<td class="text-right"><input type="text" id="nongstoutward_totamt" value="0.00" class="form-control text-right netSupTotAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="nongstoutward_iamt"  value="0.00" class="form-control text-right netSupIAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="nongstoutward_camt"  value="0.00" class="form-control text-right netSupCAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="nongstoutward_samt"  value="0.00" class="form-control text-right netSupSAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><!-- <input type="text" id="nongstoutward_csamt"  value="0.00" class="form-control text-right netSupCsAmt"/> --></td>
										</tr>
									</table>
								</div>
							</div>							
				<!-- -------------------------------------------------------------------------------------------------------------------------------------- -->
							<div class="row rowData" id="3_2">		 <!-- id="threeDotTwo" -->
								<div class="col-md-12" id="3_2_unregisteredTable">
									<table class="table table-bordered" id="">
										<thead>
											<tr class="info">
												<th colspan="2">
													Supplies made to Unregistered Persons
												</th>
											</tr>
										</thead>
										<tbody id="0">
												<input type="hidden" id="index_3_2_unregisteredTable_0" name="index_3_2_unregisteredTable_0" value="0" />
											<tr>
												<td class="w55">Total Taxable Value</td> 
												<td class="text-right">(₹)	 <label id="3_2_unreg_totaltaxablevalue">0.00</label></td>
											</tr>
											<tr>
												<td class="w55 ">Total Integrated Tax Amount</td> 
												<td class="text-right">(₹)	 <label id="3_2_unreg_itaxamt">0.00</label></td>
											</tr>
											<tr>
												<td class="w55 active">Place of Supply (State/ UT)</td> 
												<td class="text-right">
													<select class="form-control interstateUnregPos" id="interstate_unreg_pos_0"/></select>
												</td>
											</tr>
											<tr>
												<td class="w55 active">Total Taxable Value</td> 
												<td class="text-right"><input type="text" id="interstate_unreg_totamt_0" value="0.00" class="form-control text-right interstateUnregTotAmt"/></td>
											</tr>
											<tr>
												<td class="w55 active">Total Integrated Tax Amount</td> 
												<td class="text-right"><input type="text" id="interstate_unreg_iamt_0" value="0.00" class="form-control text-right interstateUnregIAmt"/></td>
											</tr>
										</tbody>
									</table>
									
									<center>
										<button type="button" class="btn btn-primary addTbodyRow" data-tid="3_2_unregisteredTable" data-unregposid="interstate_unreg_pos_" data-unregtotamtid="interstate_unreg_totamt_" data-unregiamtid="interstate_unreg_iamt_" ><i class="fa fa-plus"></i></button>										
										<button type="button" id="removelastRowUnregTab" class="btn btn-danger removelastRow" data-tid="3_2_unregisteredTable"><i class="fa fa-trash-o"></i></button>									
									</center>
									<hr/>
									<i class="clearfix"></i>
								</div>
								<div class="col-md-12" id="3_2_compositionTable">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													Supplies made to Composition Taxable Persons
												</th>
											</tr>
										</thead>
										<tbody id="0">
												<input type="hidden" id="index_3_2_compositionTable_0" name="index_3_2_compositionTable_0" value="0" />
											<tr>
												<td class="w55">Total Taxable Value</td> 
												<td class="text-right">(₹)	 <label id="3_2_compositn_totaltaxablevalue">0.00</label></td>
											</tr>
											<tr>
												<td class="w55 ">Total Integrated Tax Amount</td> 
												<td class="text-right">(₹)	 <label id="3_2_compositn_itaxamt">0.00</label></td>
											</tr>
											<tr>
												<td class="w55 active">Place of Supply (State/ UT)</td> 
												<td class="text-right">
													<select class="form-control interstateCompositnPos" id="interstate_compositn_pos_0"/></select>
												</td>
											</tr>
											<tr>
												<td class="w55 active">Total Taxable Value</td> 
												<td class="text-right"><input type="text" id="interstate_compositn_totamt_0" value="0.00" class="form-control text-right interstateCompositnTotAmt"/></td>
											</tr>
											<tr>
												<td class="w55 active">Total Integrated Tax Amount</td> 
												<td class="text-right"><input type="text" id="interstate_compositn_iamt_0" value="0.00" class="form-control text-right interstateCompositnIAmt"/></td>
											</tr>
										</tbody>
									</table>
									
									<center>
										<button type="button" class="btn btn-primary addTbodyRow" data-tid="3_2_compositionTable" data-compositnposid="interstate_compositn_pos_" data-compositntotamtid="interstate_compositn_totamt_" data-compositniamtid="interstate_compositn_iamt_" ><i class="fa fa-plus"></i></button>										
										<button type="button" id="removelastRowCompTab" class="btn btn-danger removelastRow" data-tid="3_2_compositionTable"><i class="fa fa-trash-o"></i></button>									
									</center>
									<hr/>
									<i class="clearfix"></i>
								</div>
								<div class="col-md-12" id="3_2_uinTable">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													Supplies made to UIN holders
												</th>
											</tr>
										</thead>
										<tbody id="0">
												<input type="hidden" id="index_3_2_uinTable_0" name="index_3_2_uinTable_0" value="0" />
											<tr>
												<td class="w55">Total Taxable Value</td> 
												<td class="text-right">(₹)	 <label id="3_2_uin_totaltaxablevalue">0.00</label></td>
											</tr>
											<tr>
												<td class="w55 ">Total Integrated Tax Amount</td> 
												<td class="text-right">(₹)	 <label id="3_2_uin_itaxamt">0.00</label></td>
											</tr>
											<tr>
												<td class="w55 active">Place of Supply (State/ UT)</td> 
												<td class="text-right">
													<select class="form-control interstateUinPos" id="interstate_uin_pos_0"/></select>
												</td>
											</tr>
											<tr>
												<td class="w55 active">Total Taxable Value</td> 
												<td class="text-right"><input type="text" id="interstate_uin_totamt_0" value="0.00" class="form-control text-right interstateUinTotAmt"/></td>
											</tr>
											<tr>
												<td class="w55 active">Total Integrated Tax Amount</td> 
												<td class="text-right"><input type="text" id="interstate_uin_iamt_0" value="0.00" class="form-control text-right interstateUinIAmt"/></td>
											</tr>
										</tbody>
									</table>
									<center>
										<button type="button" class="btn btn-primary addTbodyRow" data-tid="3_2_uinTable" data-uinposid="interstate_uin_pos_" data-uintotamtid="interstate_uin_totamt_" data-uiniamtid="interstate_uin_iamt_" ><i class="fa fa-plus"></i></button>										
										<button type="button" id="removelastRowUinTab" class="btn btn-danger removelastRow" data-tid="3_2_uinTable"><i class="fa fa-trash-o"></i></button>									
									</center>
									<hr/>
									<i class="clearfix"></i>
								</div>
							</div>
				<!-- -------------------------------------------------------------------------------------------------------------------------------------- -->
				
							<div class="row rowData"  id="4_0">	<!-- id="four" -->
								<div class="col-md-12">
									<h2 class="title_blue_brd"><i class="fa fa-hand-o-right"></i> (A) ITC Available (whether in full or part)</h2>
									<table class="table">
										<tr>
											<td class="w55">Integrated Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_available_iamt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Central Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_available_camt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">State/UT Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_available_samt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Cess Amount</td> 
											<td class="text-right">(₹)	<label id="itc_available_csamt">0.00</label></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(1) Import of Goods
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_goods_iamt" value="0.00" class="form-control text-right itcAvailableIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="itcavailable_goods_camt"  value="0.00" class="form-control text-right itcAvailableCAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="itcavailable_goods_samt"  value="0.00" class="form-control text-right itcAvailableSAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_goods_csamt" value="0.00" class="form-control text-right itcAvailableCsAmt"/></td>
										</tr>
									</table>
								</div>								
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(2) Import of Services
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_services_iamt" value="0.00" class="form-control text-right itcAvailableIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="itcavailable_services_camt"  value="0.00" class="form-control text-right itcAvailableCAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><!-- <input type="text" id="itcavailable_services_samt"  value="0.00" class="form-control text-right itcAvailableSAmt"/> --></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_services_csamt" value="0.00" class="form-control text-right itcAvailableCsAmt"/></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(3) Inward Supplies liable to Reverse Charge (other than 1 & 2 above)
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_inwardreverse_iamt" value="0.00" class="form-control text-right itcAvailableIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_inwardreverse_camt" value="0.00" class="form-control text-right itcAvailableCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_inwardreverse_samt" value="0.00" class="form-control text-right itcAvailableSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_inwardreverse_csamt" value="0.00" class="form-control text-right itcAvailableCsAmt"/></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(4) Inward Supplies from ISD
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_inwardisd_iamt" value="0.00" class="form-control text-right itcAvailableIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_inwardisd_camt" value="0.00" class="form-control text-right itcAvailableCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_inwardisd_samt" value="0.00" class="form-control text-right itcAvailableSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_inwardisd_csamt" value="0.00" class="form-control text-right itcAvailableCsAmt"/></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(5) All other ITC
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_allotheritc_iamt" value="0.00" class="form-control text-right itcAvailableIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_allotheritc_camt" value="0.00" class="form-control text-right itcAvailableCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_allotheritc_samt" value="0.00" class="form-control text-right itcAvailableSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcavailable_allotheritc_csamt" value="0.00" class="form-control text-right itcAvailableCsAmt"/></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<h2 class="title_blue_brd"><i class="fa fa-hand-o-right"></i> (B) ITC Reversed</h2>
									<table class="table">
										<tr>
											<td class="w55">Integrated Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_reserved_iamt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Central Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_reserved_camt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">State/UT Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_reserved_samt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Cess Amount</td> 
											<td class="text-right">(₹)	<label id="itc_reserved_csamt">0.00</label></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(1) As Per Rule 42 & 43 of CGST Rules
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcreversed_4243cgstrules_iamt" value="0.00" class="form-control text-right itcReservedIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcreversed_4243cgstrules_camt" value="0.00" class="form-control text-right itcReservedCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcreversed_4243cgstrules_samt" value="0.00" class="form-control text-right itcReservedSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcreversed_4243cgstrules_csamt" value="0.00" class="form-control text-right itcReservedCsAmt"/></td>
										</tr>
									</table>
								</div>								
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(2) Others
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcreversed_others_iamt" value="0.00" class="form-control text-right itcReservedIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcreversed_others_camt" value="0.00" class="form-control text-right itcReservedCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcreversed_others_samt" value="0.00" class="form-control text-right itcReservedSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcreversed_others_csamt" value="0.00" class="form-control text-right itcReservedCsAmt"/></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<h2 class="title_blue_brd"><i class="fa fa-hand-o-right"></i> (C) Net ITC Available (A) - (B)</h2>
									<table class="table">
										<tr>
											<td class="w55">Integrated Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_net_iamt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Central Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_net_camt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">State/UT Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itc_net_samt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Cess Amount</td> 
											<td class="text-right">(₹)	<label id="itc_net_csamt">0.00</label></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<h2 class="title_blue_brd"><i class="fa fa-hand-o-right"></i> (D) Ineligible ITC</h2>
									<table class="table">
										<tr>
											<td class="w55">Integrated Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itcineligible_itc_iamt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Central Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itcineligible_itc_camt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">State/UT Tax Amount</td> 
											<td class="text-right">(₹)	<label id="itcineligible_itc_samt">0.00</label></td>
										</tr>
										<tr>
											<td class="Central Tax Amount">Cess Amount</td> 
											<td class="text-right">(₹)	<label id="itcineligible_itc_csamt">0.00</label></td>
										</tr>
									</table>
								</div>
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(1) As Per Section 17(5)
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcineligible_section17_iamt" value="0.00" class="form-control text-right itcIneligibleIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcineligible_section17_camt" value="0.00" class="form-control text-right itcIneligibleCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcineligible_section17_samt" value="0.00" class="form-control text-right itcIneligibleSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcineligible_section17_csamt" value="0.00" class="form-control text-right itcIneligibleCsAmt"/></td>
										</tr>
									</table>
								</div>								
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													(2) Others
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcineligible_others_iamt" value="0.00" class="form-control text-right itcIneligibleIAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcineligible_others_camt" value="0.00" class="form-control text-right itcIneligibleCAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="itcineligible_others_samt" value="0.00" class="form-control text-right itcIneligibleSAmt"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="itcineligible_others_csamt" value="0.00" class="form-control text-right itcIneligibleCsAmt"/></td>
										</tr>
									</table>
								</div>
							</div>
				<!-- -------------------------------------------------------------------------------------------------------------------------------------- -->
				
							<div class="row rowData" id="5_0">	 <!-- id="five" -->
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													From a Supplier under Composition Scheme, Exempt and Nil Rated Supply
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Inter-State Supplies</td> 
											<td class="text-right"><input type="text" id="inwardsup_exemptnil_inter" value="0.00" class="form-control text-right"/></td>
										</tr>
										<tr>
											<td class="w55 active">Intra-State Supplies</td> 
											<td class="text-right"><input type="text" id="inwardsup_exemptnil_intra" value="0.00" class="form-control text-right"/></td>
										</tr>
									</table>
								</div>
								
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													Non-GST Supply
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Inter-State Supplies</td> 
											<td class="text-right"><input type="text" id="inwardsup_nongst_inter" value="0.00" class="form-control text-right"/></td>
										</tr>
										<tr>
											<td class="w55 active">Intra-State Supplies</td> 
											<td class="text-right"><input type="text" id="inwardsup_nongst_intra" value="0.00" class="form-control text-right"/></td>
										</tr>
									</table>
								</div>
							</div>
				<!-- -------------------------------------------------------------------------------------------------------------------------------------- -->
				
							<div class="row rowData" id="6_1">		 <!-- id="sixDotOne" -->
								<div class="col-md-12">
									<table class="table table-bordered">
										<thead>
											<tr class="info">
												<th colspan="2">
													Interest
												</th>
											</tr>
										</thead>
										<tr>
											<td class="w55 active">Integrated Tax Amount</td> 
											<td class="text-right"><input type="text" id="interest_iamt" value="0.00" class="form-control text-right"/></td>
										</tr>
										<tr>
											<td class="w55 active">Central Tax Amount</td> 
											<td class="text-right"><input type="text" id="interest_camt" value="0.00" class="form-control text-right"/></td>
										</tr>
										<tr>
											<td class="w55 active">State/UT Tax Amount</td> 
											<td class="text-right"><input type="text" id="interest_samt" value="0.00" class="form-control text-right"/></td>
										</tr>
										<tr>
											<td class="w55 active">Cess Amount</td> 
											<td class="text-right"><input type="text" id="interest_csamt" value="0.00" class="form-control text-right"/></td>
										</tr>
									</table>
								</div>
							</div>								
							<!-- <div  class="row text-right">
								<a class="downToUp" href="#" onclick="scrollSmoothToTop()" class="btn-back"><i class="fa fa-angle-double-up"></i></a>
							</div> -->				
							<center>
								<button type="button" class="btn btn-primary" id="savePrepareGstr3b">Save <i class="fa fa-save"></i></button>
							</center>
						</div>
						<i class="clearfix"></i>
					</div>	
					<input type="hidden" id="appCode" name="appCode" value="${appCode}">
					<input type="hidden" id="gstinId" name="gstinId" value="${gstinId}">
					<input type="hidden" id="financialPeriod" name="financialPeriod" value="${financialPeriod}">
					<input type="hidden" id="userId" name="userId" value="${userId}">			
					<input type="hidden" id="loggedInFrom" name="loggedInFrom" value="${loggedInFrom}">
	   				<input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
	  				<input type="hidden" id="counter3_2_unreg" value="0" />
	  				<input type="hidden" id="counter3_2_compositn" value="0" />
	  				<input type="hidden" id="counter3_2_uin" value="0" />
				</div>
			</div>
		</div>
	</div>	
</div>

<form name="goBackToAspOnErrorFromGstr3b" method="post">
    <input type="hidden" id="gstinId" name="gstinId" value="">
    <input type="hidden" id="financialPeriod" name="financialPeriod" value="">
    <input type="hidden" id="gstrType" name="gstrType" value="">
    <input type="hidden" id="_csrf_token" name="_csrf_token" value="${_csrf_token}" />
</form> 

<script src="<spring:url value="/resources/js/gstr3b/preparejs/commonMethodGstr3b.js" />"></script>
<script src="<spring:url value="/resources/js/gstr3b/preparejs/prepareGstr.js" />"></script>
<script src="<spring:url value="/resources/js/gstr3b/preparejs/saveGstr3b.js" />"></script>
<script src="<spring:url value="./resources/js/pageScroll/pageScrollCode.js" />"></script>
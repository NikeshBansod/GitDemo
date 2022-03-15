<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../layout/taglib.jsp"%>

<c:if test="${not empty response}">
	<script type="text/javascript">
		bootbox.alert('${response}', function() {
			var respStatus = '${status}';
			if (respStatus == 'SUCCESS') {
				window.location.href = "./wHome";
			}
		});
	</script>
</c:if>



<div class="loader"></div>

<section class="block">
	<form:form modelAttribute="userMaster" method="post"
		id="updateUserMasterFormId" action="./updateUser">
		<div class="container">


			<input type="hidden" id="_csrf_token" name="_csrf_token"
				value="${_csrf_token}" />

			<div id="editPageContainer">
				<!-- <div class="brd-wrap">
                      <strong><a href="./home">Home</a></strong> »  <strong>My Profile</strong>
                                             
 </div> -->
				<div class="page-title" id="listheader">
					<a href="<spring:url value="/home#account_management"/>" class="back"><i
						class="fa fa-chevron-left"></i></a>My Profile
				</div>
				<div class="form-wrap">




					<div class="row">
						<div class="form-group">
							<div class="col-md-4">
								<label for="">PAN<span> *</span> <input type="text"
									disabled="true"
									value="${userMasterObj.organizationMaster.panNumber }"
									class="form-control" /> <form:hidden
										path="organizationMaster.panNumber" id="pan-number"
										maxlength="10"
										value="${userMasterObj.organizationMaster.panNumber }" />
								</label> <span class="text-danger cust-error" id="pan-number-req">This
									field is required and should be in the correct format.</span>
							</div>

							<div class="col-md-4">
								<label for="">Firm Name<span> *</span> <form:input
										path="organizationMaster.orgName" id="org-name"
										maxlength="100"
										value="${userMasterObj.organizationMaster.orgName }"
										class="form-control" />
								</label>

							</div>


							<div class="col-md-4">
								<label for="">Firm Type <span> *</span> <input
									type="hidden" id="orgTypeHidden"
									value="${userMasterObj.organizationMaster.orgType }" /> <form:select
										id="orgType" path="organizationMaster.orgType"
										value="${userMasterObj.organizationMaster.orgType }"
										class="form-control" style="height: 42px;"></form:select>
								</label>

							</div>


							<div class="col-md-4" style="display: none;" id="divOtherOrgType"
								style="padding-bottom: 0px;padding-top: 0px;width: 362px;height: 42px;">
								<label for="">Specify Firm Type <span> *</span> <input
									type="hidden" id="orgTypeHidden"
									value="${userMasterObj.organizationMaster.orgType }" /> <form:input
										path="organizationMaster.otherOrgType" maxlength="200"
										id="otherOrgType"
										value="${userMasterObj.organizationMaster.otherOrgType}"
										class="form-control" />
								</label>

							</div>
						</div>



					</div>



					<div class="row">


						<div class="form-group">
							<div class="col-md-4">
								<label for="">Email Id<span> *</span> <input type="text"
									required="required" name="defaultEmailId" id="defaultEmailId"
									maxlength="100" value="${userMasterObj.defaultEmailId }"
									class="form-control">
								</label> <span class="text-danger cust-error" id="cust-email-format">This
									field should be in a correct format</span>

							</div>

							<div class="col-md-4">
								<label for="">Aadhar No.for e-Sign <form:input
										path="secUserAadhaarNo" id="secUserAadhaarNo"
										value="${userMasterObj.secUserAadhaarNo }"
										class="form-control" maxlength="12" />
									<!-- name ="aadharNoForEsign" -->
								</label> <span class="text-danger cust-error" id="aadharNo-req">This
									field should be 12 digits.</span>

							</div>

							<div class="col-md-4">
								<label for="">Mobile No. (This will be your User Id) <span>
										*</span> <form:input path="contactNo" readonly="true" maxlength="10"
										value="${userMasterObj.contactNo }" class="form-control" />
								</label> <span class="text-danger cust-error" id="contactNo-req">This
									field should be 10 digits</span>
							</div>
						</div>


					</div>



					<div class="row">

						<div class="form-group">
							<div class="col-md-4">
								<label for="">CIN (Corporate Identity Number) <form:input
										path="organizationMaster.cin" id="cin" maxlength="21"
										value="${userMasterObj.organizationMaster.cin}"
										class="form-control" />
									<!-- name ="aadharNoForEsign" -->
								</label> <span class="text-danger cust-error" id="cust-cin-format">CIN
									should be in proper format</span>
							</div>
						</div>





					</div>

					<div class="row" id="divTermsConditions">
						<div class="form-group">
							<div class="col-md-4">
								<span></span> <span><a id="termsConditions" href="#">
										Click here to read terms & conditions </a></span>
							</div>
						</div>
					</div>

					<div class="row">


						<span> <img alt="No Logo Uploaded"
							onerror="this.src='${pageContext.request.contextPath}/resources/images/no_image_available.jpg';"
							width="65" height="50" class="img-responsive"
							src="${pageContext.request.contextPath}/wGetOrgLogo">

						</span>


					</div>
					<form:hidden path="id" value="${userMasterObj.id }" />
					<form:hidden path="userId" value="${userMasterObj.userId }" />
					<form:hidden path="userRole" value="${userMasterObj.userRole }" />
					<form:hidden path="password" value="ZZZZZZZZZ1HHHfHHH#@H" />
					<!-- pattern regex in entity does not allow encrypted value -->
					<form:hidden path="organizationMaster.id"
						value="${userMasterObj.organizationMaster.id }" />
					<form:hidden path="status" value="${userMasterObj.status }" />
					<form:hidden path="organizationMaster.createdOn"
						value="${userMasterObj.organizationMaster.createdOn }" />
					<form:hidden path="organizationMaster.createdBy"
						value="${userMasterObj.organizationMaster.createdBy }" />
					<form:hidden path="createdOn" value="${userMasterObj.createdOn }" />
					<form:hidden path="createdBy" value="${userMasterObj.createdBy }" />
					<input type="hidden" id="termsConditionsFlagHidden"
						name="organizationMaster.termsConditionsFlagHidden"
						value="${userMasterObj.organizationMaster.termsConditionsFlagHidden}" />
					<form:hidden path="uniqueSequence"
						value="${userMasterObj.uniqueSequence }" />
					<input type="hidden" id="logoUploadFlag"
						name="organizationMaster.logoUploadFlag"
						value="${userMasterObj.organizationMaster.logoUploadFlag}">
					<input type="hidden" id="logoImagePath"
						name="organizationMaster.logoImagePath"
						value="${userMasterObj.organizationMaster.logoImagePath}">


				</div>

				<div class="col-md-12 button-wrap">
					<a class="btn btn-success blue-but" id="uploadLogo"
						style="width: auto;" href="#"><span>Click here to
							Change/Upload Logo</span></a>
					<button type="submit" class="btn btn-success blue-but"
						formnovalidate="formnovalidate" style="width: auto;"
						id="UpdateBtn" value="Save">Save</button>
					<button id="CUpdateBtn" type="button" hidden="true" />
				</div>
			</div>

		</div>
</section>


<!-- TERMS AND CONDITIONS  -->
<div id="termsAndConditionsId" style="display: none">
	<section class="block generateInvoice">
		<div class="container">
			<div class="brd-wrap">
				<strong> <a href="<spring:url value="./editUser"/>"
					id="backToMyProfile">My Profile</a></strong> » <strong>Terms and
					Conditions of Services to Merchant</strong>
			</div>
			<div class="card">
				<div class="invoicePage">

					<div class="invoiceDetail">
						<h4>WELCOME TO Bill Lite!</h4>

						<p class="tnclip">Thanks for showing interest and using ‘Bill
							Lite’.</p>

						<p class="tnclip">The terms and conditions contained
							herein-below are the basis of which, we offer our services to
							interested Merchant. By filling the registration form on software
							application to avail the services and by clicking the “I Accept
							the Terms and Conditions” checkbox as shown at the bottom of this
							document, a binding agreement arises between the Service Provider
							and the Merchant.</p>
						<p class="tnclip">The expression “Service Provider” refers to
							us, viz., Reliance SMSL Limited, who is offering the services,
							with its attendant terms and conditions, as envisaged below and
							the expression “Merchant” refers to you. The expressions “Service
							Provider” and “Merchant” shall be collectively referred to as
							“Parties”.</p>


						<h4>TERMS AND CONDITIONS</h4>

						<p class="tncli">
							1. <strong>DEFINITIONS</strong>
						</p>

						<p class="tncli">1.1. Unless the context otherwise requires,
							the following terms and expressions used herein shall have the
							following meanings assigned to them for the purpose of this
							Agreement:</p>
						<p class="tncli">1.1.1 “Agreement” or “this Agreement” shall
							mean these terms and conditions as accepted by the Merchant,
							along with its schedules, annexures and exhibits, if any,
							including any modifications made thereto, from time to time.</p>
						<p class="tncli">1.1.2 “Authorized Representative” shall be
							the authorised persons of the Merchant who is responsible for
							preparation or generation of invoices for the customers / clients
							of the Merchant.</p>
						<p class="tncli">1.1.3 “Bill Lite” means an application
							provided by the Service Provider to enable the Merchant to create
							or generate sales invoices / bills for their customers.</p>
						<p class="tncli">1.1.4 “Modifications” shall have the meaning
							ascribed to in Section 17.1.</p>
						<p class="tncli">1.1.5 “Admin ID” shall be the PAN based first
							Login ID of the Merchant created on Bill Lite.</p>
						<p class="tncli">1.1.6 “Services” shall have the meaning
							ascribed to it in Section 2.1.</p>
						<p class="tncli">1.1.7 “Merchant’s Data” means and includes
							all information which the Merchant
							uploads/creates/shares/accesses/downloads onto or from Bill Lite,
							such as invoices, master data and all other information and data,
							whether or not required for the Services, from time to time.</p>
						<p class="tncli">1.1.8 “Term” shall have the meaning ascribed
							to it in Section 3.1.</p>
						<p class="tncli">1.1.9 “Undersigned Official” shall be the
							Merchant himself/herself or a duly authorized representative of
							the Merchant registering for the Services at Bill Lite and
							accepting this Terms and Conditions on behalf of the Merchant.</p>

						<p class="tncli">
							<strong>2. SCOPE</strong>
						</p>
						<p class="tncli">2.1. Subject to the Terms and Conditions of
							this Agreement, Service Provider shall provide the services as
							broadly described in Annexure 1 (“Services”) to the Merchant.</p>

						<p class="tncli">
							<strong>3. TERM</strong>
						</p>
						<p class="tncli">3.1. This Agreement shall come into effect
							upon creation of Admin ID by the Merchant and thereafter shall
							continue till 31st March 2018 (“Term”).</p>
						<p class="tncli">3.2. This Agreement may further be extended
							by the Service Provider at its sole discretion and at the request
							of the Merchant (as shall be made through its Admin ID). The
							decision of Service Provider for any extension of Term shall be
							final and binding upon the Merchant.</p>

						<p class="tncli">
							<strong>4. CHARGES AND PAYMENTS </strong>
						</p>
						<p class="tncli">4.1. Merchant shall pay the requisite charges
							as may be prescribed for the Services and in accordance with the
							terms and conditions as may be intimated, by the Service Provider
							in writing to the Merchant.</p>
						<p class="tncli">4.2. The charges, as may be determined by the
							Service Provider, may be revised from time to time by the Service
							Provider, whether on account of any charges levied by GSTN upon
							various service providers or otherwise.</p>
						<p class="tncli">4.3. Any revision of the charges by the
							Service Provider, shall be notified to the Merchant as soon as
							may be reasonably practicable.</p>
						<p class="tncli">4.4. Any charges or revisions thereof,
							including attendant terms and conditions, whenever intimated by
							the Service Provider to the Merchant, shall be treated as an
							integral part of this Agreement.</p>
						<p class="tncli">4.5. In case, the Merchant is not in
							agreement to the charges or the revised charges as charged by the
							Service Provider, it may opt to not to use the Services. Any
							continued usage shall be deemed to have been treated as
							acceptance of the charges or the revised charges.</p>

						<p class="tncli">
							<strong>5. REPRESENTATION AND WARRANTIES OF THE MERCHANT</strong>
						</p>
						<p class="tncli">5.1. The Merchant warrants and represents,
							and, where applicable, undertakes and acknowledges, as follows:</p>
						<p class="tncli">5.1.1. The Merchant is legally competent to
							contract and, in the case of an entity, duly organized, validly
							existing, and in good standing under the laws of India, has power
							to carry on its business as it is now being conducted.</p>
						<p class="tncli">5.1.2. The Merchant is qualified to do
							business in India, including entering into this Agreement with
							the Service Provider.</p>
						<p class="tncli">5.1.3. In the case of the Undersigned
							Official, he or she has been duly authorised by the Merchant to
							enter into this Agreement and has full right, power, and all
							requisite authority to bind the Merchant.</p>
						<p class="tncli">5.1.4. No waiver or consent of any person is
							required in connection with the execution, delivery, and
							performance by the Merchant for this Agreement.</p>
						<p class="tncli">5.1.5. Merchant warrants that all software,
							information, data, materials and other products and services used
							by Merchant while consuming the Services offered by the Service
							Provider under this Agreement shall not infringe any intellectual
							property rights of third parties.</p>
						<p class="tncli">5.1.6. Merchant acknowledges and agrees that,
							its use and access of Bill Lite shall always be subject to the
							terms and conditions of this Agreement and has agreed to pay the
							requisite Charges for Services.</p>

						<p class="tncli">
							<strong>6. REPRESENTATION AND WARRANTIES BY THE SERVICE
								PROVIDER</strong>
						</p>
						<p class="tncli">6.1. The Service Provider, and its
							affiliates, distributors and vendors, represent and warrant that:</p>
						<p class="tncli">6.1.1. The Service Provider is an entity duly
							organized, validly existing, and in good standing under the laws
							of India, has power to carry on its business as it is now being
							conducted, and is qualified to do business in India, including
							entering into this Agreement with the Merchant.</p>
						<p class="tncli">6.1.2. The acceptance of this Agreement by
							the Undersigned Official of the Merchant shall be treated as
							acceptance of the offer for provision of Services as made by the
							Service Provider and the very act of acceptance by the Merchant
							shall be treated as execution of an enforceable contract executed
							between the Parties without requiring any further act or
							assurance from the Service Provider.</p>
						<p class="tncli">6.1.3. No waiver or consent of any person is
							required in connection with the execution, delivery, and
							performance by the Service Provider for this Agreement.</p>
						<p class="tncli">6.1.4. Except as set forth in this Agreement,
							it makes no warranties to the Merchant, express or implied, with
							respect to any Services provided hereunder including, without
							limitation, any implied warranties of merchantability or fitness
							for a particular purpose and all such warranties are hereby
							disclaimed.</p>

						<p class="tncli">
							<strong>7. DUTIES AND OBLIGATIONS OF THE MERCHANT</strong>
						</p>
						<p class="tncli">7.1. The Merchant shall adhere to all duties
							and obligations are provided in detail herein-below:</p>
						<p class="tncli">7.1.1. The Merchant shall adhere to the
							following general duties and obligations, at all times:</p>
						<ol type="a" class="tncli">
							<li>The Merchant agrees not to conceal or misrepresent its
								identity or particulars or that of its employees, Undersigned
								Official, Authorized Representative, signatories or
								representatives, in order to access or use Services.</li>
							<li>The Merchant acknowledges that it understands that its
								use of the Services is at its own risk and costs and that the
								Service Provider provides the Services on an “as is” basis “with
								all faults” and “as available”.</li>
							<li>Merchant shall be solely responsible for establishing
								and maintaining data connectivity to Bill Lite, including all
								costs, operating parameters and all other factors associated
								therewith.</li>
							<li>The Merchant shall ensure that all of its computers and
								information technology systems which may, in any manner,
								interact with any machines or systems of the Service Provider or
								which may be responsible for provision of any data or
								information for the purposes of the Services, are fully secured
								against any and all viruses, malicious codes, malwares and all
								other threats, at all times.</li>
							<li>Merchant shall be responsible for assumptions and
								judgments made and correctness of the invoices generated by the
								Merchant using the Services and for the results produced.</li>
						</ol>
						<p></p>
						<p class="tncli">7.1.2. The Merchant shall adhere to the
							following duties and obligations vis-à-vis Bill Lite Registration
							and log-in:</p>
						<ol type="a" class="tncli">
							<li>Undersigned Official shall be responsible, both for
								generating the Admin ID of the Merchant on the Bill Lite and for
								executing this Agreement.</li>
							<li>The Undersigned Official of the Merchant shall create
								the Admin ID and all subsequent User IDs generated from the
								Admin ID shall be governed with this Agreement only and be the
								responsibility of the Merchant.</li>
							<li>The Merchant shall be solely responsible for all the
								acts, omissions, negligence, misconduct of whatsoever nature of
								its employees, Authorized Representative and signatories or
								representatives, including the Undersigned Official (wherever
								applicable), at all times.</li>
						</ol>
						<p></p>
						<p class="tncli">7.1.3. The Merchant shall adhere to the
							following duties and obligations vis-à-vis data input / entry:</p>
						<ol type="a" class="tncli">
							<li>Merchant shall be responsible for entering and
								maintenance of various master records like product master,
								service master and customer master etc. to generate an invoice
								from Bill Lite.</li>
							<li>Service Provider shall facilitate the invoice generation
								and its email-delivery through Bill Lite to its customer.</li>
							<li>Merchant shall be responsible for the data input to Bill
								Lite and all risks, costs and issues in this regard shall be to
								the sole account of the Merchant with no recourse to the Service
								Provider under any circumstances whatsoever.</li>
							<li>It shall be the sole responsibility of the Merchant to
								input the error-free records into Bill Lite.</li>
						</ol>
						<p></p>
						<p class="tncli">7.1.4. The Merchant shall adhere to the
							following duties and obligations vis-à-vis invoice signing:</p>
						<ol type="a" class="tncli">
							<li>The Merchant hereby declares and agrees to adhere to the
								process of invoice generation involving execution and signing in
								terms of Section 16. The Merchant, for the purposes of invoice
								generation, hereby irrevocably agrees that the invoice generated
								from Bill Lite by the Merchant or its employees or Authorised
								Representative or signatories or any other representatives,
								shall be deemed to have been duly signed by the Merchant and is
								valid and binding on the Merchant. The Merchant hereby waives
								any and all rights, defence or recourse for the purposes of
								disputing or denying such signature.</li>
							<li>The Merchant shall indemnify, save and hold Service
								Provider harmless, under all circumstances, from any and all
								claims, disputes, liabilities, costs and expenses, whether third
								party or otherwise, arising from and/or relating to and/or in
								connection with (i) the validity of the invoices generated; (ii)
								the manner of generation of the invoices and/or (iii) the use
								thereof.</li>
						</ol>
						<p></p>

						<p class="tncli">
							<strong>8. DUTIES AND OBLIGATIONS OF THE SERVICE
								PROVIDER</strong>
						</p>
						<p class="tncli">8.1. The Service Provider shall be
							facilitating the Merchant for the Services.</p>
						<p class="tncli">8.2. The Services as offered by the Service
							Provider shall be on “as is where is” basis and as per the scope
							of work as provided in Section 2.</p>
						<p class="tncli">8.3. Service Provider shall not be acting as
							the Merchant or its representative at any point in time during
							the course of offering Services under this Agreement.</p>
						<p class="tncli">8.4. Service Provider shall not offer any
							comments and remarks on the invoices generated from Bill Lite.</p>
						<p class="tncli">8.5. All duties and obligations of the
							Service Provider, as envisaged or contemplated under this
							Agreement, shall be subject to the limitations expressed in
							Section 9.</p>

						<p class="tncli">
							<strong>9. LIMITATION OF SERVICES</strong>
						</p>
						<p class="tncli">9.1. Services under this Agreement are
							restricted to the scope of work as provided in Section 2 and do
							not include any services not covered thereunder.</p>
						<p class="tncli">9.2. The Bill Lite is being developed as per
							current prevailing law. Any subsequent changes in law may require
							some lead time for development. In that time, there may be a
							downtime in the Bill Lite.</p>
						<p class="tncli">9.3. The Service Provider makes no warranties
							(express or implied) or guarantees with respect to the
							availability of the Services, third party applications, material
							and/or products offered through the Services or availability on a
							limited basis or varied availability, depending on region / state
							or device.</p>
						<p class="tncli">9.4. The Service Provider doesn’t guarantee
							the accuracy or timeliness of information available from the
							Services.</p>
						<p class="tncli">9.5. The Service Provider does not undertake
							the responsibility of updating the Merchant with regard to any
							changes or amendments to any of the legislations. The Merchant
							may intimate any such changes, if noticed, impacting its
							computations well in advance. Service Provider may attempt, but
							not guarantee, to do the necessary changes in Bill Lite in due
							course of time. It is recommended, that the Merchant do not avail
							our Services in such a scenario till the changes are implemented.</p>
						<p class="tncli">9.6. The Service Provider does not guarantee
							that the Services will be uninterrupted, timely, secure or
							error-free or that content loss won’t occur, nor does it
							guarantee any connection to or transmission from computer
							networks. It shall strive to keep the Services up and running;
							however, all online services suffer occasional disruptions and
							outages, and the Service Provider shall not be liable for any
							disruption or loss that the Merchant may suffer as a result. In
							the event of an outage, the Merchant may not be able to retrieve
							the data that has been stored.</p>

						<p class="tncli">
							<strong>10. EXPLICIT MERCHANT CONSENT</strong>
						</p>
						<p class="tncli">10.1. Merchant hereby explicitly provides its
							consent to the Service Provider to store Merchant’s Data in its
							cloud storage for 24 months and after that in archives for 6
							years or such other extended period, as required.</p>
						<p class="tncli">10.2. Merchant hereby explicitly provides its
							consent to the Service Provider or its authorised representative
							to use the Merchant’s data for generating MIS dashboard,
							analysis, research etc.</p>
						<p class="tncli">10.3. Merchant hereby explicitly provides its
							consent to the Service Provider to share the data with the
							counterparties and such Government departments or agencies or
							bodies as may deemed fit or necessary by the Service Provider,
							from time to time.</p>

						<p class="tncli">
							<strong>11. LIMITATION OF LIABILITY </strong>
						</p>
						<p class="tncli">11.1. Service Provider shall not be held
							responsible or liable for any failure to offer or perform or
							delay in performing its obligations under this Agreement under
							any event or circumstances and the Merchant shall not be entitled
							to recover any losses, damages, costs or expenses of whatsoever
							nature, from the Service Provider. Merchant shall compensate to
							the Service Provider for any damage to the Bill Lite due to any
							act of the Merchant.</p>

						<p class="tncli">
							<strong>12. INDEMNITY</strong>
						</p>
						<p class="tncli">12.1. Merchant hereby agrees and undertakes
							that it shall at all times indemnify and hold the Service
							Provider harmless for any loss, claim, damages, costs, expenses
							including litigation fees incurred and claim made by any third
							party on the Service Provider including any allegations of
							intellectual property rights’ infringement.</p>

						<p class="tncli">
							<strong>13. PRIVACY OF MERCHANT DATA</strong>
						</p>
						<p class="tncli">13.1. Service Provider collects and uses the
							data from the Merchant, for the purposes of performance of its
							rights, duties and obligations under this Agreement and/or on the
							basis of explicit Merchant’s consent as provided in Section 10 of
							this Agreement.</p>
						<p class="tncli">
							13.2. The Service Provider shall be entitled to utilise the
							Merchant data in accordance with the privacy policy of the
							Service Provider available at <a
								href="https://www.jiogst.com/html/privacyPolicy.html">https://www.jiogst.com/html/privacyPolicy.html</a>,
							to the extent that such policy is not inconsistent with the
							consent so obtained by the Service Provider from the Merchant.
							For the avoidance of doubt, in the event of any inconsistency or
							conflict between the terms of the privacy policy and the explicit
							Merchant’s consent provided under this Agreement for use of
							Merchant data, the latter shall prevail.
						</p>

						<p class="tncli">
							<strong>14. INTELLECTUAL PROPERTY </strong>
						</p>
						<p class="tncli">14.1. The intellectual property of the
							Service Provider in the Bill Lite, the Services and all
							intellectual property rights (of any nature) including without
							limitation, the title, interests, name and/or logo in relation to
							the Services shall, at all times, vest exclusively with the
							Service Provider.</p>
						<p class="tncli">14.2. No intellectual property rights (of any
							nature) are being transferred by way of this Agreement to the
							Merchant.</p>
						<p class="tncli">14.3. Any design, production,
							marketing/promotional materials, support content and study
							papers, materials and documents, video contents or advertisement
							which bears the name, logo and/or trademark of Service Provider,
							shall only be used for internal consumption by the Merchant and
							not further circulated to anyone.</p>


						<p class="tncli">
							<strong>15. TRANSFER AND ASSIGNMENT </strong>
						</p>
						<p class="tncli">15.1. The rights and obligations of the
							Merchant under this Agreement are non-transferable and
							non-assignable to any party.</p>

						<p class="tncli">
							<strong>16. EXECUTION</strong>
						</p>
						<p class="tncli">16.1. By registering for and logging into
							Bill Lite by performing appropriate authentication as prescribed
							and by selecting the “I Accept the Terms and Conditions” button,
							the Merchant agrees and acknowledges that the Merchant is signing
							this Agreement, including any invoices generated by the Merchant
							whilst using the Services, and that such signature is the legal
							equivalent of the manual signature of the Merchant.</p>
						<p class="tncli">16.2. By registering for and logging into
							Bill Lite by performing appropriate authentication as prescribed
							and by selecting "I Accept the Terms and Conditions" the Merchant
							hereby consents to be legally bound and that the Merchant’s use
							of a key pad, mouse or other device to select an item, button,
							icon or similar act/action or to otherwise provide the Service
							Provider, instructions via Bill Lite, or in accessing or making
							any transaction regarding any agreement, invoice,
							acknowledgement, consent, disclosures or conditions, constitutes
							the signature of the Merchant, acceptance and agreement, as if
							actually signed by the Merchant in writing (“E-Signature”).</p>
						<p class="tncli">16.3. The Merchant hereby agrees and
							acknowledges that no certification authority or other third party
							verification is necessary to validate the E-Signature and that
							the lack of such certification or third party verification will
							not in any way affect the enforceability of the E-Signature.</p>
						<p class="tncli">16.4. The Merchant hereby represents,
							warrants and unconditionally agrees that any E-Signature by the
							Undersigned Official or the Authorized Representative or the
							Admin ID using the log-in credentials of the Merchant, shall be
							treated as E-Signature effected by the Merchant, at all times.
							The Merchant hereby agrees to be absolutely and unconditionally
							bound by every such E-Signature effected and hereby waives any
							and all objections, defences or recourses that disclaim, deny or
							dispute the same.</p>

						<p class="tncli">
							<strong>17. MODIFICATIONS</strong>
						</p>
						<p class="tncli">17.1. This Merchant Agreement along with its
							Annexures may only be varied, amended or modified by the Service
							Provider from time to time (“Modifications”).</p>
						<p class="tncli">17.2. Any Modifications shall be intimated to
							the Merchant and shall require the acceptance or rejection
							thereof, by the Merchant through its Admin ID.</p>
						<p class="tncli">17.3. The Merchant shall not be entitled to
							avail the Services unless and until the Merchant accepts or
							rejects the Modifications through its Admin ID.</p>
						<p class="tncli">17.4. In the event that the Merchant rejects
							any Modifications in terms of Section 17.2, then, this Agreement
							shall stand terminated between the Parties, with no liability or
							consequences arising therefrom upon any Party to the other.</p>

						<p class="tncli">
							<strong>18. TERMINATION</strong>
						</p>
						<p class="tncli">18.1. The Service Provider shall be entitled
							to terminate the Services offered under this Agreement forthwith,
							without any liability to the Service Provider, without further
							notice to the Merchant upon the occurrence of any of the
							following events:</p>
						<p class="tncli">18.1.1. If the Merchant commits a breach of
							any of the terms and conditions of this Agreement; or</p>
						<p class="tncli">18.1.2. If Merchant defaults in making
							payment in accordance with the term of this Agreement; or</p>
						<p class="tncli">18.1.3. If Merchant fails to adhere to data
							security standards as may be prescribed under this Agreement; or</p>
						<p class="tncli">18.1.4. If Merchant is found involved in
							fraud or other illegal or unethical activities in relation to any
							subject matter associated with this Agreement; or</p>
						<p class="tncli">18.1.5. If Merchant is found in breach of
							intellectual property rights or for any other act of wilful
							misconduct.</p>

						<p class="tncli">
							<strong>19. GOVERNING LAW AND JURISDICTION</strong>
						</p>
						<p class="tncli">19.1. This Agreement shall be governed by the
							laws of India with the exclusive jurisdiction of the Courts at
							Mumbai.</p>

						<p class="tncli">
							<strong>20. GENERAL</strong>
						</p>
						<p class="tncli">20.1. If any term or condition of this
							Agreement is found to be invalid, void, or for any reason
							unenforceable by any competent court of law, such term or
							condition shall be severed from the remainder of this Agreement
							and such severance shall not affect the validity and
							enforceability of this Agreement.</p>
						<p class="tncli">20.2. Any failure by the Service Provider to
							exercise or enforce any rights conferred upon it by this
							Agreement shall not be deemed to be a waiver of any such rights
							or operate so as to bar the exercise or enforcement thereof at
							any subsequent time or times.</p>

						<p class="tncli">
							<strong>21. SURVIVAL</strong>
						</p>
						<p class="tncli">The sections 4, 5, 6, 9, 10, 11, 12, 13, 14
							and 18 shall survive the termination or expiry of this Agreement.
						</p>

						<p class="tncli">
							<strong>22. NOTICES</strong>
						</p>
						<p class="tncli">
							22.1. Any notices under this Agreement will be sent by or
							received only on the email addresses of the Parties as shown
							herein-below:<br> <strong>Reliance SMSL Limited</strong> <br>
							E-mail: <a href="mailto:ashish.g.saxena@ril.com">ashish.g.saxena@ril.com</a>
							<br> <strong>Merchant</strong> <br> The email address
							and any other contact details as provided in the application form
							duly filled by the Merchant.<br>
							<br>

						</p>

						<p class="tncli text-center">
							<strong>Annexure-1</strong><br> List of Services
						</p>

						<p class="tncli text-center">Services offered by the Service
							Provider through Bill Lite includes but not limited to the
							following:</p>

						<ol class="tncli">
							<li>Manage Product Master</li>
							<li>Manage Service Master</li>
							<li>Manage Employees / Users</li>
							<li>Add / Manage Customer Master</li>
							<li>Add / Manage GSTINs</li>
							<li>Create Invoice</li>
							<li>Enter Purchase Records</li>
						</ol>

						<p class="tncli">It is expressly acknowledged that the
							description as provided herein-above is a broad and generic
							description. The Service Provider shall be entitled to offer
							clarity on the description provided hereinabove, including
							delineation, explanation or qualification to each and every
							foregoing item of service, from time to time. Any and all such
							delineation, explanation or qualification, whenever offered in
							writing by the Service Provider to the Merchant in writing, shall
							be treated as an integral part of this Agreement.</p>

					</div>

					<div class="text-center">
						<button type="button" class="btn btn-primary" id="iAgree"
							value="I Agree">I Agree</button>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>















</form:form>

</section>
<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/organization/manageOrganization.js"/>"></script>
<%-- <script type="text/javascript" src="<spring:url value="/resources/js/pincode-autocomplete.js"/>"></script> --%>
<!-- commented on 16th novemeber 2018 Dibyendu  -->
<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/registration/client.min.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/newUiJs/registration/registration_additional.js"/>"></script>

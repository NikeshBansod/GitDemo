
var jsonDashBoardResponse = '';

$(document).ready(function() {

	/* $('.showStatGraphDiv').hide(); */

	var onlyMonth = $("#onlyMonth").val();

	var onlyYear = $("#onlyYear").val();

	var traverseFrom = $("#traverseFrom").val();

	loadMonthList(onlyMonth);

	loadYearList(onlyYear);

	if (traverseFrom == "gotoDashboard") {

		/* getDataList(onlyMonth, onlyYear); */

		/* $("#radioButtons").show(); */

		calculateStat();

	}

	$('.loader').fadeOut("slow");

});

$("#calculate").click(function() {

	$('.loader').show();

	calculateStat();

	$('.loader').fadeOut("slow");

});

/*
 * $('#startmonth, #years').on('change', function(e){
 * 
 * if($('.showStatGraphDiv').is(':visible')){
 * 
 * $('.showStatGraphDiv').slideToggle();
 * 
 * $('#columnchart').hide();
 * 
 * 
 *  }
 * 
 * });
 */

function calculateStat() {

	var startmonth = $("#startmonth").val();

	var years = $("#years").val();

	if (startmonth == null) {

		bootbox.alert("Please select the month");

	} else {

		getDataList(startmonth, years);

		if (checkForJsonLength() > 0) {

			showChartsDynamic();

		}

	}

}

function getDataList(startmonth, years) {

	var inputData = {

		"startdate" : startmonth,

		"years" : years,

		"eWaybillCount" : 0,

		"cndnCount" : 0,

		"invoiceCount" : 0,

		"invoiceEWaybillCount" : 0,

		"onlyMonth" : "",

		"onlyYear" : "",

	}

	$.ajax({

		url : "getDashboardDataAjax",

		method : "post",

		headers : {

			_csrf_token : $("#_csrf_token").val()

		},

		data : JSON.stringify(inputData),

		contentType : "application/json",

		dataType : "json",

		async : false,

		success : function(json, fTextStatus, fRequest) {

			if (isValidSession(json) == 'No') {

				window.location.href = getDefaultSessionExpirePage();

				return;

			}

			if (isValidToken(json) == 'No') {

				window.location.href = getCsrfErrorPage();

				return;

			}

			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

			jsonDashBoardResponse = json;

		},

		error : function(data, status, er) {

			getInternalServerErrorPage();

		}

	});

}

function showAllRecords(startdate, enddate, getInvType, onlyMonth, onlyYear) {

	$('.loader').show();

	document.invoiceCount.action = "./showAllRecordsList";

	document.invoiceCount.startdate.value = startdate;

	document.invoiceCount.enddate.value = enddate;

	document.invoiceCount.getInvType.value = getInvType;

	document.invoiceCount.onlyMonth.value = onlyMonth;

	document.invoiceCount.onlyYear.value = onlyYear;

	document.invoiceCount._csrf_token.value = $("#_csrf_token").val();

	document.invoiceCount.submit();

	$('.loader').fadeOut("slow");

}

function loadMonthList(selectedMonth) {

	$.ajax({

				url : "getMonthList",

				method : "GET",

				dataType : "json",

				headers : {

					_csrf_token : $("#_csrf_token").val()

				},

				async : false,

				success : function(json, fTextStatus, fRequest) {

					if (isValidSession(json) == 'No') {

						window.location.href = getDefaultSessionExpirePage();

						return;

					}

					if (isValidToken(json) == 'No') {

						window.location.href = getCsrfErrorPage();

						return;

					}

					setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

					$("#startmonth").empty();

					$("#startmonth")
							.append(
									'<option value="00" disabled selected>Select Month</option>');

					$.each(json, function(i, value) {

						if (selectedMonth == value.monthVal) {

							$('#startmonth').append(
									$('<option>').text(value.monthDesc).attr(
											'value', value.monthVal).attr(
											'selected', 'selected'));

						} else {

							$("#startmonth").append(
									$('<option>').text(value.monthDesc).attr(
											'value', value.monthVal));

						}

					});

				},

				error : function(data, status, er) {

					getInternalServerErrorPage();

				}

			});

}

function loadYearList(selectedMonth) {

	$.ajax({

		url : "getYearList",

		method : "GET",

		dataType : "json",

		headers : {

			_csrf_token : $("#_csrf_token").val()

		},

		async : false,

		success : function(json, fTextStatus, fRequest) {

			if (isValidSession(json) == 'No') {

				window.location.href = getDefaultSessionExpirePage();

				return;

			}

			if (isValidToken(json) == 'No') {

				window.location.href = getCsrfErrorPage();

				return;

			}

			setCsrfToken(fRequest.getResponseHeader('_csrf_token'));

			$("#years").empty();

			$.each(json, function(i, value) {

				if (selectedMonth == value) {

					$('#years').append(
							$('<option>').text(value).attr('value', value)
									.attr('selected', 'selected'));

				} else {

					$("#years").append(
							$('<option>').text(value).attr('value', value));

				}

			});

		},

		error : function(data, status, er) {

			getInternalServerErrorPage();

		}

	});

}

function checkForJsonLength() {

	var count = 1;

	var json = jsonDashBoardResponse;

	if (json.invoiceEWaybillCount == 0 && json.eWaybillCount == 0
			&& json.cndnCount == 0 && json.invoiceCount == 0) {

		bootbox.alert("All Records Are Empty");

		$("#piechart").empty();

		$("#columnchart").empty();

		$("#dashboardreport").empty();

		count = 0;

	}

	return count;

}

function showChartsDynamic() {

	var options;

	var chart;

	var json = jsonDashBoardResponse;

	/* $("#radioButtons").hide(); */

	// start for pie chart and column chart
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});

	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
		
		var inputArray=[
						[ "", "", {
							role : 'style'
						}, {
							role : 'annotation'
						} ],
					];
		
		var invoiceEwayBillCount;
		
		var ewaybillCount;
		
		var documentCount;
		
		var cndnCount;
		
		
		
		if(json.invoiceEWaybillCount!=0){
			invoiceEwayBillCount=[ "Ewaybill Through Invoice",json.invoiceEWaybillCount, "#3366CC",json.invoiceEWaybillCount ];
		}

		if(json.eWaybillCount!=0){
			ewaybillCount=[ "Generic Eway Bill", json.eWaybillCount, "#DC3912",json.eWaybillCount ];
		}
		
		if(json.invoiceCount!=0){
			documentCount=[ "Document", json.invoiceCount, "#FF9900",json.invoiceCount ];
		}
		
		if(json.cndnCount!=0){
			cndnCount=[ "CNDN", json.cndnCount, "#109618", json.cndnCount ];
		}
		
		
		if(invoiceEwayBillCount){
			inputArray.push(invoiceEwayBillCount);
		}
		if(ewaybillCount){
			inputArray.push(ewaybillCount);
		}
		if(documentCount){
			inputArray.push(documentCount);
		}
		if(cndnCount){
			inputArray.push(cndnCount);
		}
		var dataForGraph = google.visualization
				.arrayToDataTable(inputArray);

		options = {
				
			pieSliceText : 'none',

			/*sliceVisibilityThreshold : 0,*/

			chartArea : {
				left : 10,
				right : 10,
				top : 20,
				width : "100%",
				height : "100%"
			},

			pieStartAngle : 40,

			is3D : true,

			legend : {

				position : 'labeled',

				labeledValueText : 'value',

				textStyle : {

					color : 'blue',

					fontSize : 11.5,

				},

			},

			tooltip : {

				text : 'value',

			},
			
		};

		chart = new google.visualization.PieChart(document
				.getElementById('piechart'));

		chart.draw(dataForGraph, options);

		$(window).resize(function() {

			drawChart();

		});

		options = {

			bar : {
				groupWidth : "60%"
			},

			legend : {
				position : 'none'
			},

			chartArea : {
				left : 40,
				right : 40,
				top : 20,
				bottom : 40,
				width : "100%",
				height : "100%"
			},

		};

	chart = new google.visualization.ColumnChart(document
				.getElementById('columnchart'));

		chart.draw(dataForGraph, options);

		$(window).resize(function() {

			drawChart();

		});

		$("#dashboardreport").empty();

		$("#dashboardreport")
				.append(

						'<thead>'

								+ '<tr>'

								+ '<th>Report Type</th>'

								+ '<th> Total Count</th>'

								+ '</tr>'

								+ '</thead>'

								+ '<tr>'

								+ '<td>Eway Bill through Invoice</td>'

								+ '<td><a href="#" onclick="javascript:showAllRecords(\''
								+ json.startdate
								+ '\',\''
								+ json.enddate
								+ '\',\'invoiceewaybill\',\''
								+ json.onlyMonth
								+ '\',\''
								+ json.onlyYear
								+ '\');">'
								+ json.invoiceEWaybillCount
								+ '</a>'

								+ '</tr>'

								+ '<tr>'

								+ '<td>Generic Eway Bill</td>'

								+ '<td><a href="#" onclick="javascript:showAllRecords(\''
								+ json.startdate
								+ '\',\''
								+ json.enddate
								+ '\',\'ewaybill\',\''
								+ json.onlyMonth
								+ '\',\''
								+ json.onlyYear
								+ '\');">'
								+ json.eWaybillCount
								+ '</a>'

								+ '</tr>'

								+ '<tr>'

								+ '<td> CNDN </td>'

								+ '<td><a href="#" onclick="javascript:showAllRecords(\''
								+ json.startdate
								+ '\',\''
								+ json.enddate
								+ '\',\'cndn\',\''
								+ json.onlyMonth
								+ '\',\''
								+ json.onlyYear
								+ '\');">'
								+ json.cndnCount
								+ '</a></td>'

								+ '</tr>'

								+ '<tr>'

								+ '<td> Documents </td>'

								+ '<td><a href="#" onclick="javascript:showAllRecords(\''
								+ json.startdate
								+ '\',\''
								+ json.enddate
								+ '\',\'invoiced\',\''
								+ json.onlyMonth
								+ '\',\''
								+ json.onlyYear
								+ '\');">'
								+ json.invoiceCount + '</a></td>'

								+ '</tr>'

				);

		/*
		 * if($('.showStatGraphDiv').is(':hidden')){
		 * 
		 * $('.showStatGraphDiv').slideToggle();
		 * 
		 * $('#columnchart').show();
		 * 
		 * 
		 *  }
		 */

	}

	// } //end for pie chart and column chart

}

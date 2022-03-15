$(document).ready(function(){

$('a').click(function(event){
	var targetLinks="";
	//var targetLinks="/getProducts,/manageServiceCatalogue,/poDetails,/addManageOffers,/generateInvoice,/addGstinDetails,/getGstinUserMap";
	//var targetLinks="/poDetails,/addManageOffers,/generateInvoice";
	var index; 
	var uri;
		if(this.href){
			index= this.href.lastIndexOf("/");
			if(index>0){
				uri=this.href.substring(index);
				
			}
		}
		
		if(targetLinks.includes(uri)){
			
			event.preventDefault();
			document.location.href = "/gstn/comingSoon.jsp";
		}
	
	
});

});
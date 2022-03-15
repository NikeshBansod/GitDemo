function editRecord(idValue){
	document.manageOffers.action = "./editManageOffers";
	document.manageOffers.id.value = idValue;
	document.manageOffers.submit();		
}
	    
function deleteRecord(offerTypeId){
	 var x = confirm("Are you sure you want to delete ?");
	 if (x){
		 document.manageOffers.action = "./deleteManageOffers";
		 document.manageOffers.id.value = offerTypeId;
		 document.manageOffers.submit();    
	}
			
}
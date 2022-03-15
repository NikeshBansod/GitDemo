function validateSelect(id,spanid){
	if ($("#"+id).val() === "" || $("#"+id).val() === null){
		$("#"+id).addClass("input-error").removeClass("input-correct");
		$("#"+spanid).show();
		$("#"+id).focus();
		return true;
	}else{
		$("#"+id).addClass("input-correct").removeClass("input-error");
		$("#"+spanid).hide();
		return false;
	}

}
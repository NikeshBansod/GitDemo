
$(document).ready(function(){
	 $("#userId").on("keyup input",function(){			
		 this.value = this.value.replace(/^0/, '');
		 this.value = this.value.replace(/[^0-9]+/, '');			
		});
});

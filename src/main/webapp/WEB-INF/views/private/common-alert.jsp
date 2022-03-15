<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../layout/taglib.jsp" %> 

<c:if test="${not empty response}">
	<script type="text/javascript">
	bootbox.alert('${response}');
	</script>	
</c:if>
<script type="text/javascript">


/*
function clearForm(formName){
 $('#'+formName+' input:text, #formId select').each(
	    function(index){  
	        var input = $(this);
	        
	        if(input && (input.id.toUpperCase().includes("COUNTRY"))  ){
	        } else { 
	        	  input.val("");
	        }
	      
	    }
	); 
}
	*/
	
</script>

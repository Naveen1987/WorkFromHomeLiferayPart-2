<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/init.jsp" %>
<script src="<%=request.getContextPath() %>/jsfiles/jquery-confirm.min.js"></script>
<link href="<%=request.getContextPath() %>/jsfiles/jquery-confirm.min.css" rel="stylesheet">

<button class="example2 btn btn-primary">example confirm</button>
<script type="text/javascript">
/* Simple example */
/* $('.example2').on('click', function () {
                    $.confirm({
                                	    title: 'Confirm!',
                                	    content: 'Do you want to do this?',
                                	    buttons: {
                                	        yes: function () {
                                	            $.alert('Confirmed!');
                                	        },
                                	        no: function () {
                                	            $.alert('Canceled!');
                                	        }
                                	    }
                   });
                                	        
 }); */
 /* Now Play with button css */
/*  Jconfirm comes bundled with btn-blue btn-green btn-red btn-orange btn-purple btn-default btn-dark
Other bootstrap options are btn-primary btn-inverse btn-warning btn-info btn-danger btn-success. 
to change the text of yes into yes If
yes: {
	text:'Yes If'
    btnClass: 'btn-green',
    action: function(){
    $.alert('Confirm!');
    }
}
 //this option will show show the close icon on confirmation dialog 
 closeIcon: true,
 //this will replace that icon with glyphicon (not rquired)
 closeIconClass:'glyphicon glyphicon-remove-circle',
*/
  $('.example2').on('click', function () {
                    $.confirm({
                    					icon: 'glyphicon glyphicon-question-sign',
                    					title: 'Confirm!',
                       					content: 'Do you want to do this?',
                                	    boxWidth: '500px',
                                	    closeIcon: true,
                                	    closeIconClass:'glyphicon glyphicon-remove-circle',
                                	    useBootstrap: false,
                                	    type: 'orange',
                                	    draggable: true,
                                	    buttons: {
                                	        yes: {
                                	            btnClass: 'btn-green',
                                	            action: function(){
                                	            //start	
                                	            $.alert('Confirm!');
                                	            //end
                                	            }
                                	        },
                                	        no: {
                                	            btnClass: 'btn-red',
                                	            action: function(){
                                	           //start
                                	            $.alert('Cancel!');
                                	           //end
                                	            }
                                	        }
                                	    }
                   });
                                	        
 });
</script>
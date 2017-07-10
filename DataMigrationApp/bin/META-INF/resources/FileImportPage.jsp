<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.repository.model.Folder"%>
<%@page import="com.daffo.DataMigrationUtility.portlet.FolderAndDocType_Service"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/init.jsp" %>

<script src="<%=request.getContextPath() %>/jsfiles/jquery-confirm.min.js"></script>
<link href="<%=request.getContextPath() %>/jsfiles/jquery-confirm.min.css" rel="stylesheet">



<portlet:actionURL name="fileUploadAction" var="formSubmit">
<portlet:param name="mvcPath" value="/view.jsp"/> 
</portlet:actionURL>
<%FolderAndDocType_Service fdt=new FolderAndDocType_Service(); %>
<div class="container-fluid-1280">
<div class="row">
<div class="col-xs-12">
<form action="${formSubmit}" enctype="multipart/form-data" method="post" id="fm" name="fm">
<table class="table">
<tbody>
<tr>
<td>Select Destination Folder</td>
<td>
<%
List<Folder> folders=fdt.getAllFolder(themeDisplay);
%>
<select id="sel-folder" name="<portlet:namespace/>folderName" class="form-control">
<option value="-1">- Select Folder-</option>
<%
for(Folder folder:folders){
%>
<option value="<%=folder.getFolderId()+"|"+folder.getName()%>"><%=folder.getName()%></option>	
<%
}
%>
</select>
</td>
</tr>
<tr>
<td>Related Document Type</td>
<td>
<input type="hidden" value="" name="<portlet:namespace/>docId" id="docId">
<input id="docName" value="" name="<portlet:namespace/>docName" readonly="readonly" class="form-control"/>
</td>
</tr>
<tr>
<td>Select Source Folder</td>
<td>
<input type="file" name="<portlet:namespace/>docFile" class="form-control" id="docFile" webkitdirectory mozdirectory msdirectory odirectory directory multiple />
</td>
</tr>
<tr>
<td colspan="2"><span  id="btn-upload" class="btn btn-warning pull-right">Upload</span></td>
</tr>
</tbody>
</table>
</form>
</div>
</div>
</div>
<portlet:resourceURL var="fetchDocumentType" id="fetchDocumentType">
</portlet:resourceURL>

<script>
$("#sel-folder").change(function(){
	var item=$(this).val();
	if(item==-1){
		alert('Please Select valid value');
		return;
	}
	//alert(item);
	AUI().use('aui-base','aui-io-request', function(A){
		//aui ajax call to get updated content
		A.io.request('<%=fetchDocumentType%>',{
  		dataType: 'json',
  		method: 'POST',
  		data:{'<portlet:namespace/>FolderName':item},
  		on: {
   			 success: function() {
   				var data=this.get('responseData');
   				//alert(data.DocId+" "+data.DocType);	
   				$("#docId").val(data.DocId);
   				$("#docName").val(data.DocType);
    		}
  		}
		});
		});
});

$("#btn-upload").click(function(){
	var inputs = document.getElementsByTagName('input');
	for(var i = 0; i < inputs.length; i++) {
  	  if(inputs[i].type.toLowerCase() == 'file') {
  		  if(inputs[i].value==''){
  			  if(inputs[i].name=="<portlet:namespace/>docFile")
          	  {
  				  $.alert('Please Select Destination folder before submit!');
  				  return;
          	  }
 
  		  }
  		  
  	  }
  	if(inputs[i].type.toLowerCase() == 'text') {
		  if(inputs[i].value==''){
			  if(inputs[i].name=="<portlet:namespace/>docName")
        	  {
				  $.alert('Please Select Source Folder for submit!');
				  return;
        	  }

		  }
	 }
}
	
	
	var files = document.getElementById("docFile").files;
    var path = files[0].webkitRelativePath;
    var Folder = path.split("/");
    $.confirm({
		icon: 'glyphicon glyphicon-question-sign',
		title: 'Confirm!',
		content: 'Do you want upload all files of { '+Folder[0]+' }?',
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
	            //start method
	            submit();
	            //end method
	            }
	        },
	        no: {
	            btnClass: 'btn-red',
	            action: function(){
	           //start
	           //$.alert('Cancel!');
	           //end
	            }
	        }
	    }
	}); 
});

function submit(){
	document.getElementById("fm").submit();
}

</script>

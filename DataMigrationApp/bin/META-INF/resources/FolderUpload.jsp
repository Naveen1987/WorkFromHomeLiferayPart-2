<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/init.jsp" %>

<portlet:actionURL var="fileUploadAction" name="fileUploadAction">
</portlet:actionURL> 

<aui:form action="${fileUploadAction}" method="post" name="fm">
<input type="file" name="<portlet:namespace/>folderName" class="form-control" id="flup" onchange="getfolder(event)" webkitdirectory mozdirectory msdirectory odirectory directory multiple />
<aui:button name="name" value="Submit" type="submit"/>
</aui:form>
<!-- <script type="text/javascript">
function getfolder(e) {
    var files = e.target.files;
    var path = files[0].webkitRelativePath;
    var Folder = path.split("/");
    for(i=0;i<Folder.length;i++){
    	alert(Folder[i]);
    }
}
</script> -->


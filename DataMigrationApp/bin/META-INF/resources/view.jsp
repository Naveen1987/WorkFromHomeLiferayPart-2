<%@page import="com.liferay.portal.kernel.repository.model.Folder"%>
<%@page import="com.liferay.document.library.kernel.model.DLFolder"%>
<%@page import="com.daffo.DataMigrationApp.portlet.FileUpload_Service"%>
<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/init.jsp" %>
<div class="container-fluid-1280">
<div class="row">
<div class="col-xs-12">
<table class=" table table-bordered table-hover">
<thead>
<tr class="bg-primary">
<td colspan="2">List Of Folder In liferay Server</td>
</tr>
<tr class="bg-primary">
<td>Folder ID</td> <td>Folder Name</td>
</tr>
</thead>

<%
FileUpload_Service fus=new FileUpload_Service();
for(DLFolder fu:fus.getAllDLFolder()){
	%>
	<tr>
	<td><%=fu.getFolderId() %></td><td><%=fu.getName()%></td>
	</tr>
	<%
}
%>
<tbody></tbody>
</table>
</div>
</div>
<div class="row">
<div class="col-xs-12">
<table  class=" table table-bordered table-hover">
<thead>
<tr class="bg-primary">
<td colspan="2">List Of Folder In Sites</td>
</tr>
<tr class="bg-primary">
<td>Folder ID</td> <td>Folder Name</td>
</tr>
</thead>

<%

for(Folder fu:fus.getAllFolder(themeDisplay)){
	%>
	<tr>
	<td><%=fu.getFolderId() %></td><td><%=fu.getName()%></td>
	</tr>
	<%
}
%>
<tbody></tbody>
</table>
</div>
</div>
</div>
<%
fus.getAllDocumentType();
%>

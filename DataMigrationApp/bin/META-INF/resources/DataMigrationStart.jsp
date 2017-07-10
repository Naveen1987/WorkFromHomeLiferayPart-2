<%@page import="com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil"%>
<%@page import="java.util.Set"%>
<%@page import="com.liferay.dynamic.data.mapping.model.DDMStructure"%>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.kernel.repository.model.Folder"%>
<%@page import="com.liferay.document.library.kernel.model.DLFolder"%>
<%@page import="com.daffo.DataMigrationUtility.portlet.FolderAndDocType_Service"%>
<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/init.jsp" %>

<%FolderAndDocType_Service fus=new FolderAndDocType_Service(); %>

<div class="container-fluid-1280">

<%-- <div class="row">
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
 --%>
 
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

<%-- <div class="row">
<div class="col-xs-12">
<table  class=" table table-bordered table-hover">
<thead>
<tr class="bg-primary">
<td colspan="3">List Of All Document Type</td>
</tr>
<tr class="bg-primary">
<td>Structure ID</td> <td>Document Type</td><td>Document Fields</td>
</tr>
</thead>
<%
for(DDMStructure fu:fus.getAllDocumentType()){
	%>
	<tr>
	<td><%=fu.getStructureId() %></td><td><%=fu.getNameCurrentValue()%></td><td><%=fu.getFieldNames() %></td>
	</tr>
	<%
}
%>
<tbody></tbody>
</table>
</div>
</div> --%>

<div class="row">
<div class="col-xs-12">
<table  class=" table table-bordered table-hover">
<thead>
<tr class="bg-primary">
<td colspan="3">List Of Document Type In Sites</td>
</tr>
<tr class="bg-primary">
<td>Structure ID</td> <td>Document Type</td><td>Document Fields</td>
</tr>
</thead>

<%
for(DDMStructure fu:fus.getSiteDocumentType(themeDisplay)){
	%>
	<tr>
	<td><%=fu.getStructureId() %></td><td><%=fu.getNameCurrentValue()%></td><td>
	<% Set<String> fields=	fu.getFieldNames();
	for(String field:fields){
		out.println("["+field+"="+fu.getFieldDataType(field)+" "+fu.getFieldRequired(field)+"]");
	}
	%>
	</td>
	</tr>
	<%
}
%>
<tbody></tbody>
</table>
</div>
</div>
<%-- <div class="row">
<div class="col-xs-12">
<jsp:include page="FileImportPage.jsp"></jsp:include>
</div>
</div>
 --%>
</div>
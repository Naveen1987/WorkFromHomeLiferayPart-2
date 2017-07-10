package com.daffo.DataMigrationUtility.portlet;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Administrator
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Naveen Apps",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=DataMigrationUtility Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DataMigrationUtilityPortlet extends MVCPortlet {
	private Log logger=LogFactoryUtil.getLog("DataMigrationUtility");
	FolderAndDocType_Service fad=new FolderAndDocType_Service();
	
	@ProcessAction(name="fileUploadAction")
	public void fileUploadAction(ActionRequest actionRequest,ActionResponse actionResponse)throws IOException, PortletException, PortalException{
		try{
		String folderId= ParamUtil.getString(actionRequest, "folderName").split(Pattern.quote("|"))[0];
		String folderName= ParamUtil.getString(actionRequest, "folderName").split(Pattern.quote("|"))[1];
		String docId=ParamUtil.getString(actionRequest, "docId");
		String docName=ParamUtil.getString(actionRequest, "docName");
		UploadPortletRequest uploadPortletRequest=PortalUtil.getUploadPortletRequest(actionRequest);
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if(new FileUploadOnDocType().uploadInfo(themeDisplay, folderId, folderName, docId, docName, uploadPortletRequest,actionRequest))
		{
			System.out.println("Done");
		}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	
}
	@Override
	public void serveResource(ResourceRequest resourceRequest,ResourceResponse resourceResponse)throws  IOException, PortletException {
	String resourceId=resourceRequest.getResourceID();
	switch (resourceId) {
	case "fetchDocumentType":
		String folderId= ParamUtil.getString(resourceRequest, "FolderName").split(Pattern.quote("|"))[0];
		String folderName= ParamUtil.getString(resourceRequest, "FolderName").split(Pattern.quote("|"))[1];
		logger.info(folderId+" "+folderName);
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<DDMStructure> ddm=fad.getSiteDocumentType(themeDisplay);
		for(DDMStructure dm:ddm){
			if(folderName.equalsIgnoreCase(dm.getNameCurrentValue())){		
				JSONObject docInfo=JSONFactoryUtil.createJSONObject();
				docInfo.put("DocId",dm.getStructureId());
				docInfo.put("DocType", dm.getNameCurrentValue());
				logger.info(docInfo.toJSONString());
				resourceResponse.getWriter().write(docInfo.toJSONString());
				break;
			}
		//System.out.println(dm.getStructureId()+" "+dm.getNameCurrentValue()+" ");
		}
		break;
	default:
		break;
	}
	}
	
	
	
}
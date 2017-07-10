package com.daffo.DataMigrationUtility.portlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.portlet.ActionRequest;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;


public class FileUpload_Service {

	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	private static Log _logger= LogFactoryUtil.getLog(FileUpload_Service.class);
	
	public boolean isFolderExist(ThemeDisplay themeDisplay,String ROOT_FOLDER_NAME){
			boolean folderExist = false;
			try {
				DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, ROOT_FOLDER_NAME);
				folderExist = true;
				_logger.info("Folder already Exists");
			} catch (Exception e) {	
				_logger.error(e.getMessage());
			}
			return folderExist;
	}
	
	public Folder getFolder(ThemeDisplay themeDisplay,String ROOT_FOLDER_NAME){
		Folder folder = null;
		try {
			folder =DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, ROOT_FOLDER_NAME);
		} catch (Exception e) {	
			_logger.error(e.getMessage());
		}
		return folder;
	}
	
	
		public String fileUpload(ThemeDisplay themeDisplay,ActionRequest actionRequest,String fieldName,String folderName) throws PortalException
		{
			
			_logger.info("Uploading in prograess.............");
			UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
			String fileName=uploadPortletRequest.getFileName(fieldName);	
			if(fileName==null){
				return "blank";
			}else{
				try{
					fileName.split("\\.")[1].length();
				}catch (ArrayIndexOutOfBoundsException e) {
					return "No Extension";
				}
				
			}
			
			File file = uploadPortletRequest.getFile(fieldName);
			String mimeType = uploadPortletRequest.getContentType(fieldName);
			String description = "This file is added via programatically";
			long repositoryId = themeDisplay.getScopeGroupId();
			
			try
		    {  
				//Logic for duplicate file
				String mili=new Date().getDate()+"_"+new Date().getTime();
				String title=fileName.split("\\.")[0]+"_"+mili+"."+fileName.split("\\.")[1];
				fileName=title;
				_logger.info("Title=>"+title);
		    	Folder folder = getFolder(themeDisplay,folderName);
		    	ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), actionRequest);
		    	InputStream is = new FileInputStream( file );
		    	DLAppServiceUtil.addFileEntry(repositoryId, folder.getFolderId(), fileName, mimeType, 
		    			title, description, "", is, file.getTotalSpace(), serviceContext);
		    	return fileName;
		    	
		     } catch (Exception e)
		    	{
		    	 _logger.error(e.getMessage());
		    	 return "";
		    }

		}
		
		public Map<String, String> getAllFileLink(ThemeDisplay themeDisplay,String folderName){
			Map<String, String> urlMap = new HashMap<String, String>();//key = file name ,value = url
			long repositoryId = themeDisplay.getScopeGroupId();
			try {
				Folder folder =getFolder(themeDisplay,folderName);
				List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(repositoryId, folder.getFolderId());
				 for (FileEntry file : fileEntries) {
					String url = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + "/" + 
							      file.getFolderId() +  "/" +file.getTitle() ;
					urlMap.put(file.getTitle().split("\\.")[0], url);// remove jpg or pdf 
				}
			} catch (Exception e) {
				 _logger.error(e.getMessage());
			}
			return urlMap;
			
		}
		public Map<String, String> getFileLink(ThemeDisplay themeDisplay,String FileName,String folderName){
			
			Map<String, String> urlMap = new HashMap<String, String>();//key = file name ,value = url
			if(FileName.isEmpty()){
				return urlMap;
			}
			long repositoryId = themeDisplay.getScopeGroupId();
			try {
				Folder folder =getFolder(themeDisplay,folderName);
				List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(repositoryId, folder.getFolderId());
				 for (FileEntry file : fileEntries) {
					if(file.getTitle().contains(FileName)){
						String url = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + "/" + 
							      file.getFolderId() +  "/" +file.getTitle() ;
					  //  urlMap.put(file.getTitle().split("\\.")[0], url);// remove jpg or pdf
						  urlMap.put(file.getTitle(), url);// remove jpg or pdf
					}
				}
			} catch (Exception e) {
				 _logger.error(e.getMessage());
			}
			return urlMap;
			
		}
}
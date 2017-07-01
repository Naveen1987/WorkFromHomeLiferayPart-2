package com.daffo.DataMigrationApp.portlet;

import java.util.List;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryMetadataUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;

public class FileUpload_Service {
	//List of all Folder in liferay server
	public List<DLFolder> getAllDLFolder() 
	{
		List<DLFolder> dlFolders=null;
		try {
			dlFolders= DLFolderLocalServiceUtil.getDLFolders(0,DLFolderLocalServiceUtil.getDLFoldersCount());
			/*for (DLFolder folder : dlFolders) {
				System.out.println("DLFolder Id >> " + folder.getFolderId());
				System.out.println("DLFolder Name >>" + folder.getName());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dlFolders;
	}

	//DLApp deals with folder not DLFolders
	//This will provide more specific results associated with repository Id
	//Only of Current Site Only
	public List<Folder> getAllFolder(ThemeDisplay themeDisplay){
		long repositoryId = themeDisplay.getScopeGroupId();
		
		List<Folder> folders=null;
		 try {
		      folders = DLAppServiceUtil.getFolders(repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			/* for (Folder folder : folders) {
				     System.out.println("Folder Id >> "+folder.getFolderId());
				     System.out.println("Folder Name >>"+folder.getName());
				}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return folders;
	} 
	public List<DDMStructure> getAllDocumentType(){
		
		List<DDMStructure> structures =DDMStructureLocalServiceUtil.getStructures();//or//DDMStructureLocalServiceUtil.getDDMStructures(0, DDMStructureLocalServiceUtil.getDDMStructuresCount()); 
		/*		for(DDMStructure dms:structures){
					System.out.println(dms.getStructureId()+" "+dms.getNameCurrentValue()+" ");
					System.out.println(dms.getFieldNames());
					
				}*/
		//System.out.println(structures);
		return structures;
	}
	public List<DDMStructure> getSiteDocumentType(ThemeDisplay themeDisplay){
		List<DDMStructure> structures =DDMStructureLocalServiceUtil.getStructures(themeDisplay.getScopeGroupId()); 
		/*		for(DDMStructure dms:structures){
			System.out.println(dms.getStructureId()+" "+dms.getNameCurrentValue()+" ");
			System.out.println(dms.getFieldNames());
			
		}*/
		//System.out.println(structures);
		return structures;		
	}
	
	public void addField(){
		Fields fields=new Fields();
		fields.put(new Field("COD", "102"));
		
	}
	
}

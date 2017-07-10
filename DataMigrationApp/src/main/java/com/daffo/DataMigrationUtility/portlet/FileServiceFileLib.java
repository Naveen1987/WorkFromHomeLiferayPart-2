package com.daffo.DataMigrationUtility.portlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;

public class FileServiceFileLib {

	ArrayList<String> ar;
	public FileServiceFileLib() {
		String []ex={"3g2","3g2","3gp","3gp","ai","ai","air","air","asf","asf","avi","avi","bib","bib","cls","cls","csv","csv","deb","deb","djvu","djvu","dmg","dmg","doc","doc","docx","docx","dwf","dwf","dwg","dwg","eps","eps","epub","epub","exe","exe","f","f","f77","f77","f90","f90","flac","flac","flv","flv","gif","gif","gz","gz","ico","ico","indd","indd","iso","iso","jar","jpeg","jpeg","jpg","jpg","key","key","log","log","m4a","m4a","m4v","m4v","midi","midi","mkv","mkv","mov","mov","mp3","mp3","mp4","mp4","mpeg","mpeg","mpg","mpg","msi","msi","odp","odp","ods","ods","odt","odt","oga","oga","ogg","ogg","ogv","ogv","pdf","pdf","png","png","pps","pps","ppsx","ppsx","ppt","ppt","pptx","pptx","psd","psd","pub","pub","py","py","qt","qt","ra","ra","ram","ram","rar","rar","rm","rm","rpm","rpm","rtf","rtf","rv","rv","skp","skp","spx","spx","sql","sql","sty","sty","tar","tar","tex","tex","tgz","tgz","tiff","tiff","ttf","ttf","txt","txt","vob","vob","wav","wav","wmv","wmv","xls","xls","xlsx","xlsx","xml","xml","xpi","xpi","zip","zip"};
		ar=new ArrayList<String>(Arrays.asList(ex));
	}
	public boolean finder(String ext){
		return ar.contains(ext);
	}
	
	public List<FileLibInfo> getRecords(String folderName, ThemeDisplay themeDisplay,Locale locale) throws PortalException{
		List<FileLibInfo> records=new ArrayList<FileLibInfo>();
		long FOLDER_ID = 0;
		long repositoryId = themeDisplay.getScopeGroupId();
		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		List<Folder> lFolder = DLAppServiceUtil.getFolders(repositoryId, parentFolderId);
		for (Folder folder : lFolder)
		{
			//On the base of folder name data fetching
			if(folder.getName().equals(folderName)){
				List<FileEntry> fl=DLAppServiceUtil.getFileEntries(repositoryId, folder.getFolderId());
				
				for(FileEntry fn:fl){
					FileLibInfo record=new FileLibInfo();
					
					DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(fn.getFileEntryId());
					String uuid=dlFileEntry.getUuid();
					FileVersion fileVersion = fn.getLatestFileVersion();
					String urlthumbnail= DLUtil.getPreviewURL(fn, fileVersion, themeDisplay, "");
					String urldown=DLUtil.getDownloadURL(fn, fileVersion, themeDisplay, "");
			        double size=((double)fn.getSize())/1024;
			        List<DDMStructure> ddmStructures = dlFileEntry.getDLFileEntryType().getDDMStructures();
					for(DDMStructure d:ddmStructures){
						//out.println("["+d.getStructureId()+":"+d.getName()+":"+d.getFieldNames()+"]");
					DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil
				                .getFileEntryMetadata(d.getStructureId(),fileVersion.getFileVersionId());
						DDMFormValues vals = StorageEngineManagerUtil.getDDMFormValues(fileEntryMetadata.getDDMStorageId());
						List<DDMFormFieldValue> fieldValueList = vals.getDDMFormFieldValues(); 
						for(DDMFormFieldValue fieldVal : fieldValueList){
							record.getFields().put(fieldVal.getName().toString(),fieldVal.getValue().getString(locale));
						}
					}
					record.setFileuuid(uuid);
					record.setFileName(fn.getFileName());
					record.setThumbnail(urlthumbnail);
					record.setDownloadUrl( urldown);
					
					record.setSize(((size % 1 == 0)?String.format("%.0f", size):String.format("%.2f", size))+" KB");
					
					record.setMime(fn.getMimeType());
					record.setExtension(fn.getExtension());
					record.setImageName("alledit-icon-48x48.png");
					if(ar.contains(record.getExtension()))
					{
					record.setImageName(record.getExtension()+"-icon-48x48.png");
					}
					record.setFileEntryId(fn.getFileEntryId()+"");
					record.setFileVersion(fn.getLatestFileVersion().getFileVersionId()+"");
					record.setRepositoryId(repositoryId+"");
					record.setFolderId(folder.getFolderId()+"");
					records.add(record);
				}
			}
		}
		
		return records;
	}
}
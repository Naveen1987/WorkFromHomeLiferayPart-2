package com.daffo.DataMigrationUtility.portlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;

import org.omg.CORBA.portable.ValueFactory;

import com.demo.mypack.FieldsToDDMFormValuesConverterImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryFinderUtil;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryMetadataUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upgrade.util.ValueMapperUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;

public class FileUploadOnDocType {
private final Log logger=LogFactoryUtil.getLog("FileUploadOnDocumentType");
public boolean uploadInfo(ThemeDisplay themeDisplay, String folderId,String folderName,	String docId,String docName,UploadPortletRequest uploadPortletRequest, ActionRequest actionRequest) throws NumberFormatException, PortalException, IOException{
	//System.out.println("Folder Info:-"+folderId+" "+folderName);
	//System.out.println("Documenet Type Info:-"+docId+" "+docName);
	logger.info("{ Folder Info:-"+folderId+" "+folderName+" }");
	logger.info("{ Documenet Type Info:-"+docId+" "+docName+" }");
	DDMStructure ddm=DDMStructureLocalServiceUtil.getDDMStructure(new Long(docId).longValue());
	Set<String> fieldsName=null;
	if(ddm.getNameCurrentValue().equalsIgnoreCase(docName)){
		logger.info("{ Documenet Type Confirmation - Ok } ");
		fieldsName=ddm.getFieldNames();
		logger.info("{ Documenet Type Fields-"+fieldsName+" } ");
	}
	Folder folder=DLAppServiceUtil.getFolder(new Long(folderId).longValue());
	if(folder.getName().equalsIgnoreCase(folderName)){
		logger.info("{ Folder Name Confirmation - Ok }");
	}
	
	Map<String, FileItem[]> files= uploadPortletRequest.getMultipartParameterMap();
	for (Entry<String, FileItem[]> file2 : files.entrySet()) {
		FileItem item[] =file2.getValue();
			for (FileItem fileItem : item) {
				Fields fields=new Fields();
				String fileNameW=fileItem.getFileName();
				if (fileNameW.indexOf(".") > 0){
					fileNameW = fileNameW.substring(0, fileNameW.lastIndexOf("."));
			     }
				String a[]=fileNameW.split(Pattern.quote("@"));
				for(String sname:fieldsName){
					if(sname.equalsIgnoreCase("COD")){
						Field f=new Field(ddm.getStructureId(), "COD", a[0]);
						fields.put(f);
						
					}
					else if(sname.equalsIgnoreCase("Titolo")){
						Field f=new Field(ddm.getStructureId(), "Titolo", a[1]);
						fields.put(f);
					}
					else if(sname.equalsIgnoreCase("REV")){
						Field f=new Field(ddm.getStructureId(), "REV", a[2]);
						fields.put(f);
						
					}
					else if(sname.equalsIgnoreCase("Lingua")){
						Field f=new Field(ddm.getStructureId(), "Lingua", "IT");
						fields.put(f);
					}
					else if(sname.equalsIgnoreCase("Dimensione")){}
					else if(sname.equalsIgnoreCase("Spec")){}
					else if(sname.equalsIgnoreCase("Applicativo")){}
					else if(sname.equalsIgnoreCase("Anno")){}
					else if(sname.equalsIgnoreCase("Sede")){}
					else if(sname.equalsIgnoreCase("Unità Organizzativa")){}
					else if(sname.equalsIgnoreCase("Dipartimento")){}
					else if(sname.equalsIgnoreCase("Numero")){}
					else if(sname.equalsIgnoreCase("Modulo")){}
					
					
				}
				logger.info("{ File Name-"+fileItem.getFileName()+" }");
				for(Field f:fields){
					logger.info("Value Mapped {"+f.getName()+"="+f.getValue()+"}");
				}
				

				
				DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(new Long(folderId).longValue());
		        long fileEntryTypeId = dlFolder.getDefaultFileEntryTypeId();
		        System.out.println("fileEntryTypeId:-"+fileEntryTypeId);
		        long userId = themeDisplay.getUserId();
				long groupId = themeDisplay.getScopeGroupId();
				long repositoryId = themeDisplay.getScopeGroupId();				
				String title = fileItem.getFileName();
				String description = title +" is added via programatically";
				String mimeType = fileItem.getContentType();	
				File file = fileItem.getStoreLocation();
				String changeLog = "This is By Prgram";
				
//		//Again R&D start
//				DDMForm fm=new DDMForm();
//				for(String s:fieldsName){
//					DDMFormField ft=new DDMFormField(s,"");
//					ft.setDataType(ddm.getFieldDataType(s));
//					fm.addDDMFormField(ft);
//				}
//				for(DDMFormField d:fm.getDDMFormFields()){
//					System.out.println(d.getName()+" "+d.getDataType());
//				}
//				
				
//				DDF
//				fv.setName("COD");
//				Value v= (Value) new UnlocalizedValue("109");
//				fv.setValue(v);
//				
//				DDMFormFieldValue fv1=new DDMFormFieldValue();
//				fv1.setName("Titolo");
//				Value v1= (Value) new UnlocalizedValue("Hello");
//				fv1.setValue(v1);
//				System.out.println(fv1.getDDMFormField());
//				DDMFormFieldValue fv2=new DDMFormFieldValue();
//				fv2.setName("REV");
//				Value v2= (Value) new UnlocalizedValue("Go");
//				fv2.setValue(v2);
//				System.out.println(fv2.getDDMFormField());
//				DDMFormFieldValue fv3=new DDMFormFieldValue();
//				fv3.setName("Lingua");
//				Value v3= (Value) new UnlocalizedValue("IT");
//				fv3.setValue(v3);
//				System.out.println(fv3.getDDMFormField());
//				
//				DDMFormValues fvt=new DDMFormValues(fm);
//				fvt.addDDMFormFieldValue(fv);
//				fvt.addDDMFormFieldValue(fv1);
//				fvt.addDDMFormFieldValue(fv2);
//				fvt.addDDMFormFieldValue(fv3);
//				
//				DDMFormValues fvt=new DDMFormValues(fm);
//				System.out.println("Fields:"+fvt.getDDMForm().getDDMFormFields().get(0).getName());
//				System.out.println("Fileds Type:"+fvt.getDDMForm().getDDMFormFields().get(0).getDataType());
//				Map<String,DDMFormValues> fie=new  HashMap<String, DDMFormValues>();
//				fie.put(ddm.getStructureKey(), fvt);
//				InputStream is =fileItem.getInputStream();
//				ServiceContext serviceContext=new ServiceContext();
////				DLFileEntry dlFileEntry=null;
////		        try {
////				      dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
////						themeDisplay.getScopeGroupId(), folder.getFolderId(),title, mimeType, title, StringPool.BLANK, StringPool.BLANK, 
////						dlFolder.getDefaultFileEntryTypeId(),fie, file, null, file.length(), serviceContext);
////					} catch (Exception e) {
////						System.out.println(e.getMessage());
////						e.printStackTrace();
////					}
////				
////				  DLFileEntryLocalServiceUtil.updateFileEntry(folder.getUserId(), dlFileEntry.getFileEntryId(), file.getName(), mimeType,
////				  title, description, "Draft to save",true,dlFileEntry.getFileEntryTypeId() , fie, file, is, 
////				  file.getTotalSpace(), serviceContext);
//			
//				Map<Locale, String> nameMap = new HashMap<>();
//				nameMap.put(LocaleUtil.getDefault(), "SITRA");
//				Map<Locale, String> descriptionMap = new HashMap<>();
//				InputStream is =fileItem.getInputStream();
//				ServiceContext serviceContext = new ServiceContext();
//
//				serviceContext.setAddGuestPermissions(true);
//				serviceContext.setAddGroupPermissions(true);
//				serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
//				serviceContext.setUserId(themeDisplay.getUserId());
//						DLFileEntryType sp=DLFileEntryTypeLocalServiceUtil.addFileEntryType(
//						themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
//						"SITRA", nameMap, descriptionMap,
//						new long[] {ddm.getStructureId()}, serviceContext);
						
//				List<DLFileEntryType> dlFileEntryTypes=DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
//						ddm.getStructureId());
//				System.out.println(dlFileEntryTypes.get(0).getFileEntryTypeId());
//					DLFileEntry dlFileEntry=null;
//			        try {
//					      dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
//							themeDisplay.getScopeGroupId(), folder.getFolderId(),title, mimeType, title, StringPool.BLANK, StringPool.BLANK, 
//							sp.getFileEntryTypeId(),null, file, null, file.length(), serviceContext);
//						} catch (Exception e) {
//							System.out.println(e.getMessage());
//							e.printStackTrace();
//						}
//					
//					  DLFileEntryLocalServiceUtil.updateFileEntry(folder.getUserId(), dlFileEntry.getFileEntryId(), file.getName(), mimeType,
//					  title, description, "Draft to save",true,dlFileEntry.getFileEntryTypeId() , null, file, is, 
//					  file.getTotalSpace(), serviceContext);
			
			
				

					DLFileEntryType sp=null;
					try{
					List<DLFileEntryType> dlFileEntryTypes =DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(ddm.getStructureId());
					
					if(!dlFileEntryTypes.isEmpty()){
					 System.out.println("Not Null");
						sp=dlFileEntryTypes.get(0);
					}
					else{
						System.out.println("Null");
					}
					}catch(Exception ex){
						
					}
					if(sp==null){
						System.out.println("I am Null");
						Map<Locale, String> nameMap = new HashMap<>();
						nameMap.put(LocaleUtil.getDefault(), "SITRA");
						Map<Locale, String> descriptionMap = new HashMap<>();			
						ServiceContext serviceContext = new ServiceContext();
						serviceContext.setAddGuestPermissions(true);
						serviceContext.setAddGroupPermissions(true);
						serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
						serviceContext.setUserId(themeDisplay.getDefaultUserId());

						sp= DLFileEntryTypeLocalServiceUtil.addFileEntryType(
							themeDisplay.getDefaultUserId(), themeDisplay.getScopeGroupId(),
							"SITRA", nameMap, descriptionMap,
							new long[] {ddm.getStructureId()}, serviceContext);
						System.out.println("This-"+sp.getFileEntryTypeId());
					}
					InputStream is =fileItem.getInputStream();
					//Old COde
					ServiceContext serviceContext = new ServiceContext();
					serviceContext.setAddGuestPermissions(true);
					serviceContext.setAddGroupPermissions(true);
					serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
					serviceContext.setUserId(themeDisplay.getDefaultUserId());
					
					DDMFormValues ddmFormValues = toDDMFormValues(fields,ddm.getStructureId());
					Map<String, DDMFormValues>mp=new HashMap<String,DDMFormValues>();
					mp.put(ddm.getStructureKey(), ddmFormValues);
					
					
					DLFileEntry dlFileEntry=null;
			        try {
					      dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
							themeDisplay.getScopeGroupId(), folder.getFolderId(),title, mimeType, title, StringPool.BLANK, StringPool.BLANK, 
							sp.getFileEntryTypeId(),null, file, null, file.length(), serviceContext);
						} catch (Exception e) {
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
					
					  DLFileEntryLocalServiceUtil.updateFileEntry(folder.getUserId(), dlFileEntry.getFileEntryId(), file.getName(), mimeType,
					  title, description, "Draft to save",true,dlFileEntry.getFileEntryTypeId() , null, file, is, 
					  file.getTotalSpace(), serviceContext);
					 // DLFileEntryLocalServiceUtil.updateStatus(themeDisplay.getUserId(), dlFileEntry.getFileVersion().getFileVersionId() ,WorkflowConstants.ACTION_PUBLISH,  serviceContext,null);
			
					
//					FileEntry fmt=DLAppLocalServiceUtil.addFileEntry(themeDisplay.getUserId(),repositoryId, folder.getFolderId(), title, mimeType, 
//							title, description, "", is, file.getTotalSpace(), serviceContext);
//					
//					DLFileEntry dlfile=DLFileEntryLocalServiceUtil.getDLFileEntry(fmt.getFileEntryId());
//					DLFileVersion _dlFileVersion=dlfile.getFileVersion();
//					System.out.println("versionID"+_dlFileVersion.getFileVersionId());
//					System.out.println("versionID"+_dlFileVersion.getCompanyId());
//					try {
//						DLFileEntry dlFileEntry = _dlFileVersion.getFileEntry();
//						
//						DLFileEntryMetadata	_dlFileEntryMetadata =DLFileEntryMetadataLocalServiceUtil.createDLFileEntryMetadata(CounterLocalServiceUtil.increment());
//
//						long ddmStructureId = ddm.getStructureId();
//
//						
//						
//						DDMFormValues ddmFormValues = toDDMFormValues(fields,ddm.getStructureId());
//						System.out.println(ddmFormValues.getDDMFormFieldValues().get(0).getName());
//						System.out.println(ddmFormValues.getDDMFormFieldValues().get(0).getValue().getString(themeDisplay.getLocale()));
//						
//						long ddmStorageId = StorageEngineManagerUtil.create(
//							_dlFileVersion.getFileVersionId(), ddmStructureId, ddmFormValues,
//							serviceContext);
//						System.out.println(ddmStorageId);
//Nest
//						_dlFileEntryMetadata.setDDMStorageId(ddmStorageId);
//
//						_dlFileEntryMetadata.setDDMStructureId(ddmStructureId);
//						_dlFileEntryMetadata.setFileEntryId(dlFileEntry.getFileEntryId());
//						_dlFileEntryMetadata.setFileVersionId(
//							_dlFileVersion.getFileVersionId());
//
//						_dlFileEntryMetadata =
//								DLFileEntryMetadataLocalServiceUtil.addDLFileEntryMetadata(
//								_dlFileEntryMetadata);
//					}
//					catch (PortalException pe) {
//						throw new SystemException(
//							"Unable to add DDM fields for file version " +
//								_dlFileVersion.getFileVersionId(),
//							pe);
//					}
			
			
			
			}	
				/*Hello Adding*/
			
	}	
	return true;
}

protected DDMFormValues toDDMFormValues(Fields fields,long structureId)
		throws PortalException {
	FieldsToDDMFormValuesConverterImpl fm=new FieldsToDDMFormValuesConverterImpl();
		return fm.convert(DDMStructureManagerUtil.getStructure(structureId),
			fields);
	}
}
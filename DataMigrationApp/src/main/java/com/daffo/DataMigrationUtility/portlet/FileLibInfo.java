package com.daffo.DataMigrationUtility.portlet;

import java.util.HashMap;

public class FileLibInfo {
private String fileName,thumbnail,downloadUrl,size,mime,extension;
private String repositoryId,folderId,imageName;
private String fileEntryId,fileVersion,fileuuid;
private HashMap<String,String> fields=new HashMap<String,String>();
public String getFileuuid() {
	return fileuuid;
}
public void setFileuuid(String fileuuid) {
	this.fileuuid = fileuuid;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getThumbnail() {
	return thumbnail;
}
public void setThumbnail(String thumbnail) {
	this.thumbnail = thumbnail;
}
public String getDownloadUrl() {
	return downloadUrl;
}
public void setDownloadUrl(String downloadUrl) {
	this.downloadUrl = downloadUrl;
}
public String getSize() {
	return size;
}
public void setSize(String size) {
	this.size = size;
}
public String getMime() {
	return mime;
}
public void setMime(String mime) {
	this.mime = mime;
}
public String getExtension() {
	return extension;
}
public void setExtension(String extension) {
	this.extension = extension;
}
public String getFileEntryId() {
	return fileEntryId;
}
public void setFileEntryId(String fileEntryId) {
	this.fileEntryId = fileEntryId;
}
public String getFileVersion() {
	return fileVersion;
}
public void setFileVersion(String fileVersion) {
	this.fileVersion = fileVersion;
}
public String getRepositoryId() {
	return repositoryId;
}
public void setRepositoryId(String repositoryId) {
	this.repositoryId = repositoryId;
}
public String getFolderId() {
	return folderId;
}
public void setFolderId(String folderId) {
	this.folderId = folderId;
}
public HashMap<String, String> getFields() {
	return fields;
}
public void setFields(HashMap<String, String> fields) {
	this.fields = fields;
}
public String getImageName() {
	return imageName;
}
public void setImageName(String imageName) {
	this.imageName = imageName;
}
@Override
public String toString() {
	return "FileLibInfo [fileName=" + fileName + ", thumbnail=" + thumbnail + ", downloadUrl=" + downloadUrl + ", size="
			+ size + ", mime=" + mime + ", extension=" + extension + ", repositoryId=" + repositoryId + ", folderId="
			+ folderId + ", imageName=" + imageName + ", fileEntryId=" + fileEntryId + ", fileVersion=" + fileVersion
			+ ", fileuuid=" + fileuuid + ", fields=" + fields + "]";
}

}
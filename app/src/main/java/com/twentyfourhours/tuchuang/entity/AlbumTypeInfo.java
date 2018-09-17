package com.twentyfourhours.tuchuang.entity;

import java.util.List;

/**
 * 
 * <p>
 * {相册类型}
 * </p>
 * 
 * @author 常瑞 2016-5-25 下午2:26:15
 * @version V1.0
 * 
 * 
 * @modificationHistory=========================创建
 * @modify by user: 常瑞 2016-5-25
 */
public class AlbumTypeInfo {

	private List<Types> data;

	public List<Types> getData() {
		return data;
	}

	public void setData(List<Types> data) {
		this.data = data;
	}

	// 相册大类型类
	public class Types {
		private String type;
		private List<FileNames> fileNames;

		public String getType() {
			return type;
		}

		public List<FileNames> getFileNames() {
			return fileNames;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setFileNames(List<FileNames> fileNames) {
			this.fileNames = fileNames;
		}

	}

	// 每个大类型中所对应的小类型的点的文件名字
	public class FileNames {
		private String fileName;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	}
}

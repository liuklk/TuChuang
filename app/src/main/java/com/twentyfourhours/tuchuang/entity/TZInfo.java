package com.twentyfourhours.tuchuang.entity;

import java.util.List;

public class TZInfo {
	private List<Everyinfo> data;

	public List<Everyinfo> getData() {
		return data;
	}

	public void setData(List<Everyinfo> data) {
		this.data = data;
	}

	public static class Everyinfo {
		private String type;
		private String name;

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}

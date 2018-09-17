package com.twentyfourhours.tuchuang.entity;

import java.util.List;

/**
 * 
 * <p>
 * {每个类型相框的所有小模块}
 * </p>
 * 
 * @author 常瑞 2016-5-25 下午3:16:35
 * @version V1.0
 * 
 * 
 * @modificationHistory=========================重大变更说明
 * @modify by user: 常瑞 2016-5-25
 */
public class RegionPointsInfo {
	private List<RegionPoints> data;

	public List<RegionPoints> getData() {
		return data;
	}

	public void setData(List<RegionPoints> data) {
		this.data = data;
	}

	/**
	 * 
	 * <p>
	 * {每个小模块的所有点集合}
	 * </p>
	 * 
	 * @author 常瑞 2016-5-25 下午3:17:28
	 * @version V1.0
	 * 
	 * 
	 * @modificationHistory=========================重大变更说明
	 * @modify by user: 常瑞 2016-5-25
	 */
	public static class RegionPoints {
		private List<Points> region_points;

		public List<Points> getRegion_points() {
			return region_points;
		}

		public void setRegion_points(List<Points> region_points) {
			this.region_points = region_points;
		}
	}
}

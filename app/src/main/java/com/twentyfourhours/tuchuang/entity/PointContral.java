package com.twentyfourhours.tuchuang.entity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.MotionEvent;

public class PointContral {

	private Bitmap image;
	private Path path;
	// 原始图像矩阵
	private Matrix mMatrix;
	// 定义三种模式：None、Drag、Zoom
	public static final int Mode_None = 0;
	// 当前操作模式
	private int mMode = Mode_None;
	// 存储两点间的距离
	private float mDistance = 0f;
	// 存储旋转角
	private float mAngle = 0f;
	// 存储中点
	private PointF mPoint;
	// 判断是否要显示加号
	private boolean isShowAddImage = true;

	public PointContral() {
		mMatrix = new Matrix();
		path = new Path();
	}

	public Bitmap getImage() {
		return image;
	}

	public Path getPath() {
		return path;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public boolean isPaht(MotionEvent event) {
		Region re = new Region();
		// 构造一个区域对象，左闭右开的。
		RectF r = new RectF();
		// 计算控制点的边界
		path.computeBounds(r, true);
		// 设置区域路径和剪辑描述的区域
		re.setPath(path, new Region((int) r.left, (int) r.top, (int) r.right,
				(int) r.bottom));
		// 判断触摸点是否在封闭的path内 在返回true 不在返回false
		return re.contains((int) event.getX(), (int) event.getY());
	}

	public boolean isShowAddImage() {
		return isShowAddImage;
	}

	public void setShowAddImage(boolean isShowAddImage) {
		this.isShowAddImage = isShowAddImage;
	}

	public int getmMode() {
		return mMode;
	}

	public float getmDistance() {
		return mDistance;
	}

	public float getmAngle() {
		return mAngle;
	}

	public Matrix getmMatrix() {
		return mMatrix;
	}

	public void setmMatrix(Matrix mMatrix) {
		this.mMatrix = mMatrix;
	}

	public PointF getmPoint() {
		return mPoint;
	}

	public void setmMode(int mMode) {
		this.mMode = mMode;
	}

	public void setmDistance(float mDistance) {
		this.mDistance = mDistance;
	}

	public void setmAngle(float mAngle) {
		this.mAngle = mAngle;
	}

	public void setmPoint(PointF mPoint) {
		this.mPoint = mPoint;
	}
}

package com.twentyfourhours.tuchuang.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.entity.PointD;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Utils {

	private static Point point = null;

	public static Point getDisplayWidthPixels(Context context) {
		if (point != null) {
			return point;
		}
		WindowManager wm = ((Activity) context).getWindowManager();
		point = new Point();
		wm.getDefaultDisplay().getSize(point);
		return point;
	}

	/**
	 * 计算两点之间的距离
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double lineSpace(double x1, double y1, double x2, double y2) {
		double lineLength = 0;
		double x, y;
		x = x1 - x2;
		y = y1 - y2;
		lineLength = Math.sqrt(x * x + y * y);
		return lineLength;
	}

	/**
	 * 获取线段中点坐标
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static PointD getMidpointCoordinate(double x1, double y1, double x2,
											   double y2) {
		PointD midpoint = new PointD();
		midpoint.set((x1 + x2) / 2, (y1 + y2) / 2);
		return midpoint;
	}

	/**
	 * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
	 */
	@SuppressLint("NewApi")
	public static String getImageAbsolutePath(Activity context, Uri imageUri) {
		if (context == null || imageUri == null)
			return null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
				&& DocumentsContract.isDocumentUri(context, imageUri)) {
			if (isExternalStorageDocument(imageUri)) {
				String docId = DocumentsContract.getDocumentId(imageUri);
				String[] split = docId.split(":");
				String type = split[0];
				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}
			} else if (isDownloadsDocument(imageUri)) {
				String id = DocumentsContract.getDocumentId(imageUri);
				Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			} else if (isMediaDocument(imageUri)) {
				String docId = DocumentsContract.getDocumentId(imageUri);
				String[] split = docId.split(":");
				String type = split[0];
				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				String selection = MediaStore.Images.Media._ID + "=?";
				String[] selectionArgs = new String[] { split[1] };
				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		} // MediaStore (and general)
		else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
			// Return the remote address
			if (isGooglePhotosUri(imageUri))
				return imageUri.getLastPathSegment();
			return getDataColumn(context, imageUri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
			return imageUri.getPath();
		}
		return null;
	}

	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {
		Cursor cursor = null;
		String column = MediaStore.Images.Media.DATA;
		String[] projection = { column };
		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

	public static Bitmap resizeBitmap(Bitmap bitmap, float w, float h) {
		if (bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			float newWidth = w;
			float newHeight = h;
			float scaleWight = newWidth / width;
			float scaleHeight = newHeight / height;
			float scale = 1;
			if (scaleWight > scaleHeight) {
				scale = scaleWight;
			} else {
				scale = scaleHeight;
			}
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, true);
			return res;

		} else {
			return null;
		}
	}

	/**
	 * 
	 * @desc 拼图效果
	 */
	public static Bitmap getComponseBitmap(Bitmap src, Bitmap src2) {
		if (src == null || src2 == null)
			return null;

		// int width = src.getWidth();
		// int height = src.getHeight();
		// Bitmap newBitmap = Bitmap.createBitmap(width, height,
		// Config.RGB_565);
		// Canvas canvas = new Canvas(newBitmap);
		// canvas.drawBitmap(src, 0, 0, null);
		// // Bitmap zoomBitmap = zoomImage(src2, 480, 420);
		// Bitmap zoomBitmap = zoomImage(src2, width, height);
		// canvas.drawBitmap(zoomBitmap, 0, 0, null);
		// canvas.save(Canvas.ALL_SAVE_FLAG);
		// canvas.restore();

		// 防止出现Immutable bitmap passed to Canvas constructor错误
		Bitmap bitmap1 = src.copy(Config.ARGB_8888, true);
		Bitmap bitmap2 = src2;

		Bitmap newBitmap = null;

		newBitmap = Bitmap.createBitmap(bitmap1);
		Canvas canvas = new Canvas(newBitmap);
		Paint paint = new Paint();

		int w = bitmap1.getWidth();
		int h = bitmap1.getHeight();

		int w_2 = bitmap2.getWidth();
		int h_2 = bitmap2.getHeight();

		paint.setColor(Color.GRAY);
		paint.setAlpha(125);
		canvas.drawRect(0, 0, bitmap1.getWidth(), bitmap1.getHeight(), paint);

		paint = new Paint();
		canvas.drawBitmap(bitmap2, w, h, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		// 存储新合成的图片
		canvas.restore();

		return newBitmap;
	}

	// 等比缩放图片
	public static Bitmap zoomImage(Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 计算缩放比例
		float scaleW = (float) newWidth / width;
		float sacleH = (float) newHeight / height;
		// 取得缩放Matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleW, sacleH);
		// 顺时针旋转30度
		// matrix.postRotate(30);
		// 得到新图片
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newBitmap;
	}

	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 这里压缩100%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 图片旋转处理
	 * 
	 * @return
	 */
	public static Bitmap decodeUriAsBitmap(String path) {
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		File file = new File(path);
		if (file != null && file.exists()) {
			// 可能造成图片模糊
			// Options.inSampleSize=2；//图片宽高都为原来的二分之一，即图片为原来的四分之一
			// if (file.length() < 20480) { // 0-20k
			// opts.inSampleSize = 1;
			// } else if (file.length() < 51200) { // 20-50k
			// opts.inSampleSize = 1;
			// } else if (file.length() < 307200) { // 50-300k
			// opts.inSampleSize = 1;
			// } else if (file.length() < 819200) { // 300-800k
			// opts.inSampleSize = 2;
			// } else if (file.length() < 1048576) { // 800-1024k
			// opts.inSampleSize = 3;
			// } else {
			// opts.inSampleSize = 4;
			// }
			bitmap = BitmapFactory.decodeFile(path, opts);

			int angle = getExifOrientation(path);
			if (angle != 0) { // 如果照片出现了 旋转 那么 就更改旋转度数
				Matrix matrix = new Matrix();
				matrix.postRotate(angle);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);
			}
		}
		return bitmap;
	}

	/**
	 * 得到 图片旋转 的角度
	 * 
	 * @param filePath
	 * @return
	 */
	public static int getExifOrientation(String filePath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filePath);
		} catch (IOException ex) {
		}

		if (exif != null) {
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			}
		}
		return degree;
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
	 * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
	 * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
	 * 
	 * @param imagePath
	 *            图像的路径
	 * @param width
	 *            指定输出图像的宽度
	 * @param height
	 *            指定输出图像的高度
	 * @return 生成的缩略图
	 */
	public static Bitmap createImageThumbnail(String imagePath, int width,
			int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}

		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);

		int angle = getExifOrientation(imagePath);
		if (angle != 0) { // 如果照片出现了 旋转 那么 就更改旋转度数
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
		}
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	public static void saveMyView(View view, String imagePath) {
		Bitmap map = saveViewBitmap(view);

		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String sDir = Environment.getExternalStorageDirectory() + "/"
						+ imagePath;//
				File destDir = new File(sDir);
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				File file = new File(sDir + "/" + System.currentTimeMillis()
						+ ".jpg");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(file);
				map.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.flush();
				out.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveBitmap(View view) {
		Bitmap map = saveViewBitmap(view);

		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String sDir = Environment.getExternalStorageDirectory()
						+ "/test";//
				File destDir = new File(sDir);
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				File file = new File(sDir + "/" + "modify.jpg");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(file);
				map.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * save view as a bitmap
	 */
	public static Bitmap saveViewBitmap(View view) {
		// get current view bitmap
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();

		Bitmap bmp = duplicateBitmap(bitmap, view);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		// clear the cache
		view.setDrawingCacheEnabled(false);
		return bmp;
	}

	public static Bitmap duplicateBitmap(Bitmap bmpSrc, View view) {
		if (null == bmpSrc) {
			return null;
		}

		int bmpSrcWidth = view.getWidth();
		int bmpSrcHeight = view.getHeight();

		Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight,
				Config.ARGB_8888);
		if (null != bmpDest) {
			Canvas canvas = new Canvas(bmpDest);
			final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);

			canvas.drawBitmap(bmpSrc, rect, rect, null);
		}

		return bmpDest;
	}

	/**
	 * 
	 * @author 常瑞 2016-5-26 上午11:18:02
	 * @param //读取assests文件夹下的指定文件
	 * @param context
	 * @return
	 * 
	 * @modificationHistory=========================创建
	 * @modify by user: 常瑞 2016-5-26
	 * @modify by reason: 原因
	 */
	public static String getStringFromAssert(String fileName, Context context) {
		String content = null; // 结果字符串
		try {
			InputStream is = context.getResources().getAssets().open(fileName); // 打开文件
			int ch = 0;
			ByteArrayOutputStream out = new ByteArrayOutputStream(); // 实现了一个输出流
			while ((ch = is.read()) != -1) {
				out.write(ch); // 将指定的字节写入此 byte 数组输出流
			}
			byte[] buff = out.toByteArray();// 以 byte 数组的形式返回此输出流的当前内容
			out.close(); // 关闭流
			is.close(); // 关闭流
			content = new String(buff, "UTF-8"); // 设置字符串编码
		} catch (Exception e) {
			Toast.makeText(context, "对不起，没有找到指定文件！", Toast.LENGTH_SHORT).show();
		}
		return content;
	}

	/**
	 * 
	 * @author 常瑞 2016-5-27 上午10:49:12
	 * @param //得到View
	 *            的宽高
	 * @return
	 * 
	 * @modificationHistory=========================方法变更说明
	 * @modify by user: 常瑞 2016-5-27
	 * @modify by reason: 原因
	 */
	public static int[] getLocation(View v) {
		int[] loc = new int[4];
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		loc[0] = location[0];
		loc[1] = location[1];
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);

		loc[2] = v.getMeasuredWidth();
		loc[3] = v.getMeasuredHeight();

		// base = computeWH();
		System.out.println(loc[0] + ":::::::::::" + loc[1] + "::::::::::::"
				+ loc[2] + ":::::::::::" + loc[3]);
		return loc;
	}

	/**
	 * 
	 * @author 常瑞 2016-5-27 上午10:51:18
	 * @param //把canvas画出来的图形
	 *            保存为Bitmap
	 * @return
	 * 
	 * @modificationHistory=========================方法变更说明
	 * @modify by user: 常瑞 2016-5-27
	 * @modify by reason: 原因
	 */
	public static Bitmap getBitmap(Context context, Bitmap mBackgroundBitmap,
								   ImageView contralImageView, RelativeLayout rl_content_root) {
		Bitmap whiteBgBitmap = Bitmap.createBitmap(
				mBackgroundBitmap.getWidth(), mBackgroundBitmap.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(whiteBgBitmap);
		canvas.drawColor(context.getResources().getColor(R.color.white));
		canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
		canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
		canvas.restore();// 存储
		contralImageView.draw(canvas);
		rl_content_root.draw(canvas);
		return whiteBgBitmap;
	}

	public static File saveBitmap(Bitmap bitmap, String path) {

		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String sDir = Environment.getExternalStorageDirectory() + "/"
						+ path;//
				File destDir = new File(sDir);
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				File file = new File(sDir + "/" + System.currentTimeMillis()
						+ ".png");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				return file;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

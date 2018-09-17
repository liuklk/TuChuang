package com.twentyfourhours.tuchuang.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.SelectVideoAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/9.
 */
public class SelectVideoActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.grid_view)
    GridView mGridView;
    private List<StoreBeen> mData = new ArrayList<>();
    private List<HashMap<String, String>> mListImage = new ArrayList<HashMap<String, String>>();

    @Override
    protected void initListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //预览,跳转
                Intent intent = new Intent();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mData.get(i).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bitmapByte = baos.toByteArray();
                intent.putExtra("bitmap", bitmapByte);
                intent.putExtra("path_video", mData.get(i).getPath());
                intent.putExtra("type", getIntent().getIntExtra("type", -1));
                intent.putExtra("last", getIntent().getBooleanExtra("last", false));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {//设置数据
        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA};
        String orderBy = MediaStore.Video.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri, projection, orderBy);

        // 1. 布局文件转换为View对象
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.load, null);

// 2. 新建对话框对象
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        final AlertDialog dialog = builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(DensityUtil.dip2px(this, 180), DensityUtil.dip2px(this, 140));
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mListImage.size(); i++) {
                    StoreBeen been = new StoreBeen();
                    been.setPath(mListImage.get(i).get("_data"));
                    StoreBeen data = createVideoThumbnail(mListImage.get(i).get("_data"), MediaStore.Images.Thumbnails.MINI_KIND);
                    if (data != null) {
                        been.setBitmap(data.getBitmap());
                        been.setDuration(data.getDuration());
                    } else {
                        been.setBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
                        been.setDuration("0");
                    }
                    mData.add(been);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SelectVideoAdapter adapter = new SelectVideoAdapter(SelectVideoActivity.this);
                        adapter.setData(mData);
                        mGridView.setAdapter(adapter);
                    }
                });
                dialog.dismiss();
            }
        }).start();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_video;
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    /**
     * 获取ContentProvider
     *
     * @param projection
     * @param orderBy
     */
    public void getContentProvider(Uri uri, String[] projection, String orderBy) {
        mListImage = new ArrayList<HashMap<String, String>>();
        Cursor cursor = getContentResolver().query(uri, projection, null, null, orderBy);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < projection.length; i++) {
                map.put(projection[i], cursor.getString(i));
            }
            mListImage.add(map);
        }
    }

    public static StoreBeen createVideoThumbnail(String filePath, int kind) {
        StoreBeen been = new StoreBeen();
        Bitmap bitmap = null;
        String duration = "0";
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
            duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }
        if (bitmap == null)//0:4:28//268736//
        {
            been.setBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
            been.setDuration(duration);
            return been;
        }

        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {//压缩图片 开始处
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }//压缩图片 结束处
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    96,
                    96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
//        duration = retriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        if (duration == null) {
            duration = "0";
        }
        been.setBitmap(bitmap);
        been.setDuration(duration);
        return been;
    }
}

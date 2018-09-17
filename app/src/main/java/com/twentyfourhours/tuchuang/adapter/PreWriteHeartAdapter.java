package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.HeartBeen;
import com.twentyfourhours.tuchuang.ui.activity.PreviewWriteHeartActivity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2018/1/8.
 */
public class PreWriteHeartAdapter extends BaseAdapter {

    private Context mContext;

    private List<HeartBeen> data = new ArrayList<>();

    public List<HeartBeen> getData() {
        return data;
    }

    public void setData(List<HeartBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public PreWriteHeartAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (data != null) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_write_heart_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position), position);
        return convertView;
    }

    class ViewHolder {
        TextView mTitle;
        ImageView mPic;
        JCVideoPlayerStandard mVideoplayer;
        HeartBeen mStoreBeen;

        public ViewHolder(View view) {
            mTitle = (TextView) view.findViewById(R.id.title);
            mPic = (ImageView) view.findViewById(R.id.pic);
            mVideoplayer = (JCVideoPlayerStandard) view.findViewById(R.id.videoplayer);
        }

        public void setData(final HeartBeen storeBeen, int position) {
            mStoreBeen = storeBeen;
            mTitle.setVisibility(TextUtils.isEmpty(storeBeen.getContent()) ? View.GONE : View.VISIBLE);
            mPic.setVisibility(TextUtils.isEmpty(storeBeen.getPath()) ? View.GONE : View.VISIBLE);
            if (TextUtils.isEmpty(storeBeen.getPath())) {
                mPic.setVisibility(View.GONE);
                mVideoplayer.setVisibility(View.GONE);
            } else {
                mPic.setVisibility(storeBeen.isAudio() ? View.GONE : View.VISIBLE);
                mVideoplayer.setVisibility(storeBeen.isAudio() ? View.VISIBLE : View.GONE);
                if (storeBeen.isAudio()) {
                    mVideoplayer.setVisibility(View.VISIBLE);
//                    mVideoplayer.setUp(storeBeen.getPath()
//                            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                    mVideoplayer.setUp(""
                            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");//给个默认值,不然会报错
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mVideoplayer.setUp(storeBeen.getPath()
                                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                            final Bitmap bitmap = createVideoThumbnail(storeBeen.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
                            ((PreviewWriteHeartActivity)mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mVideoplayer.thumbImageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }).start();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmapPic = getSmallBitmap(BitmapFactory.decodeFile(storeBeen.getPath()));
                        ((PreviewWriteHeartActivity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPic.setImageBitmap(bitmapPic);
                            }
                        });
                    }
                }).start();
            }
            mTitle.setText(storeBeen.getContent());
        }

    }

    private Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
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
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
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
        return bitmap;
    }

    //压缩图片
    private Bitmap getSmallBitmap(Bitmap bitmap){
        // Scale down the bitmap if it's too large.
        if (bitmap!=null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }//压缩图片 结束处
        }
        return bitmap;
    }
}

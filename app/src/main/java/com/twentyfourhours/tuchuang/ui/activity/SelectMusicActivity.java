package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.SelectMusicAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/9.
 */
public class SelectMusicActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.list_view)
    ListView mListView;
    private List<StoreBeen> mData = new ArrayList<>();
    private List<HashMap<String, String>> mListImage = new ArrayList<HashMap<String, String>>();

    @Override
    protected void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //预览,跳转
                Intent intent = new Intent();
                String song = mData.get(i).getSong();
                String path = mData.get(i).getPath();
                intent.putExtra("song", song);
                intent.putExtra("path", path);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE};
        String orderBy = MediaStore.Audio.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        getContentProvider(uri, projection, orderBy);
        String head_Str="";
        for (int i = 0; i < mListImage.size(); i++) {
            StoreBeen been = new StoreBeen();
            been.setPath(mListImage.get(i).get("_data"));
            String displayName = mListImage.get(i).get("_display_name");
            head_Str=displayName;
            if (displayName.contains("-")) {
                String[] split = displayName.split("-");
                head_Str = split[1];
                been.setSinger(split[0].trim());
            } else {
                been.setSinger("未知歌手");
            }
            if (head_Str.contains(".")) {
                String[] split1 = head_Str.split("\\.");
                been.setSong(split1[0].trim());
            } else {
                been.setSong(head_Str.trim());
            }
            mData.add(been);
        }
        SelectMusicAdapter adapter = new SelectMusicAdapter(this);
        adapter.setData(mData);
        mListView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;
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
//                System.out.println(projection[i] + ":::::::" + cursor.getString(i) + "\n");
            }
            mListImage.add(map);
        }
//        myImageView.setImageBitmap(createVideoThumbnail(listImage.get(0).get("_data"),MediaStore.Images.Thumbnails.MINI_KIND));
//        myTextView.setText(listImage.toString());
    }
}

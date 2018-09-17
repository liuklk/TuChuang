package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobeta.android.dslv.DragSortListView;
import com.scrat.app.selectorlibrary.ImageSelector;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.LookMoreAdapter;
import com.twentyfourhours.tuchuang.adapter.WriteHeartAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.HeartBeen;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.model.bean.PublishBeen;
import com.twentyfourhours.tuchuang.model.bean.WriteHeartBeen;
import com.twentyfourhours.tuchuang.select.pickerview.widget.CityPicker;
import com.twentyfourhours.tuchuang.view.MatchGoodDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.twentyfourhours.tuchuang.adapter.WriteHeartAdapter.REQUEST_CODE_SELECT_IMG3;
import static com.twentyfourhours.tuchuang.adapter.WriteHeartAdapter.REQUEST_VIDEO_CODE;
import static com.twentyfourhours.tuchuang.adapter.WriteHeartAdapter.WRITE_HEART_REQ;

/**
 * Created by Administrator on 2018/2/26.
 */
public class WriteHeartTextActivity extends BaseActivity {

    //    private ImageView[] mImageViews;
    private static final int REQUEST_CODE_SELECT_IMG = 2;
    public static final int MAX_SELECT_COUNT = 100;
    private static final int REQUEST_CODE_SELECT_IMG4 = 114;
    private static final int MUSIC_REQ = 116;
    @BindView(R.id.gold_back)
    TextView mGoldBack;
    @BindView(R.id.publish)
    TextView mPublish;
    @BindView(R.id.list_view)
    public DragSortListView mListView;
    private List<String> paths = new ArrayList<>();
    private List<WriteHeartBeen> mData = new ArrayList<>();
    private WriteHeartAdapter mWriteHeartAdapter;
    private TextView mTitle;
    private TextView mAddMusic;
    private TextView mEditTitleBg;
    private RelativeLayout mSelectSort;
    private RelativeLayout mSelectShop;
    private RelativeLayout mAddNewView;
    private ImageView mAddView;
    private ImageView mText;
    private ImageView mPhoto;
    private ImageView mVideo;
    private LinearLayout mSelectView;
    private TextView mSortTv;
    private TextView mShopTv;
    private ImageView mTitleBg;
    private PublishBeen mPublishBeen;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected void initListener() {
        mListView.setDropListener(onDrop);
        mListView.setRemoveListener(onRemove);
    }

    // 监听器在手机拖动停下的时候触发
    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
        @Override
        public void drop(int from, int to) {// from to 分别表示 被拖动控件原位置 和目标位置
            for (int i = 0; i < mData.size(); i++) {
                mData.get(i).setShow(true);
                mData.get(i).setShowLast(true);
            }
            if (from != to) {
                WriteHeartBeen item = (WriteHeartBeen) mWriteHeartAdapter.getItem(from);// 得到listview的适配器
                mWriteHeartAdapter.remove(from);// 在适配器中”原位置“的数据。
                mWriteHeartAdapter.insert(item, to);// 在目标位置中插入被拖动的控件。
            }
        }
    };
    // 删除监听器，点击左边差号就触发。删除item操作。
    private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
        @Override
        public void remove(int which) {
            for (int i = 0; i < mData.size(); i++) {
                mData.get(i).setShow(true);
                mData.get(i).setShowLast(true);
            }
            mWriteHeartAdapter.remove(which);
            if (mWriteHeartAdapter.getData().size()==0){
                mAddNewView.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void initData() {
        mPublishBeen = new PublishBeen();
        mPublishBeen.setTitle("");
        mPublishBeen.setSong("");
        selectImg();

        View headView = View.inflate(this, R.layout.head_view, null);
        mTitleBg = (ImageView) headView.findViewById(R.id.title_bg);
        mAddMusic = (TextView) headView.findViewById(R.id.add_music);
        mTitle = (TextView) headView.findViewById(R.id.title);
        mSelectSort = (RelativeLayout) headView.findViewById(R.id.select_sort);
        mSelectShop = (RelativeLayout) headView.findViewById(R.id.select_shop);
        mEditTitleBg = (TextView) headView.findViewById(R.id.edit_title_bg);
        mSortTv = (TextView) headView.findViewById(R.id.sort_tv);
        mShopTv = (TextView) headView.findViewById(R.id.shop_tv);
        mAddNewView = (RelativeLayout) headView.findViewById(R.id.add_new_view);
        mAddView = (ImageView) headView.findViewById(R.id.add_view);
        mSelectView = (LinearLayout) headView.findViewById(R.id.select_view);
        mText = (ImageView) headView.findViewById(R.id.text);
        mPhoto = (ImageView) headView.findViewById(R.id.photo);
        mVideo = (ImageView) headView.findViewById(R.id.video);

        //选择匹配商品
        mSelectShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMatchGoodDialog();
            }
        });
        //选择分类
        mSelectSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        //添加音乐
        mAddMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteHeartTextActivity.this, SelectMusicActivity.class);
                startActivityForResult(intent, MUSIC_REQ);
            }
        });
        //设置标题
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteHeartTextActivity.this, EditTextActivity.class);
                intent.putExtra("mode", 2);
                intent.putExtra("editContent", mTitle.getText().toString());
                startActivityForResult(intent, WRITE_HEART_REQ);
            }
        });
        //编辑封面
        mEditTitleBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTitleImg();
            }
        });
        //数据为空添加
        mAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectView.setVisibility(View.VISIBLE);
            }
        });
        //添加文字
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectView.setVisibility(View.GONE);
                Intent intent1 = new Intent(WriteHeartTextActivity.this, EditTextActivity.class);
                intent1.putExtra("mode", 0);
                intent1.putExtra("position", 0);
                intent1.putExtra("editContent", "");
                startActivityForResult(intent1, WRITE_HEART_REQ);
            }
        });
        //添加视频
        mVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectView.setVisibility(View.GONE);
                setPosition(0);
                Intent intent = new Intent(WriteHeartTextActivity.this, SelectVideoActivity.class);
                intent.putExtra("type",0);
                startActivityForResult(intent, REQUEST_VIDEO_CODE);
            }
        });
        //添加图片
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectView.setVisibility(View.GONE);
                setPosition(0);
                ImageSelector.show(WriteHeartTextActivity.this, REQUEST_CODE_SELECT_IMG3, WriteHeartTextActivity.MAX_SELECT_COUNT);
            }
        });
        mListView.addHeaderView(headView);
        //设置adapter
        for (int i = 0; i < 10; i++) {
            WriteHeartBeen been = new WriteHeartBeen();
            been.setBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
            been.setContent("");
            mData.add(been);
        }
        mWriteHeartAdapter = new WriteHeartAdapter(this);
        mWriteHeartAdapter.setData(mData);
        mListView.setAdapter(mWriteHeartAdapter);
        mListView.setDragEnabled(true);
    }

    private void showDialog() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(20)
//                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#282828")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#ffffff")
                .cancelTextColor("#ffffff")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //一级
                String province = citySelected[0];
                //二级
                String city = citySelected[1];
                //三级（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //id
                String code = citySelected[3];
                mSortTv.setText(district);
//                mSortTv.setTextColor(getResources().getColor(R.color.yellow_dot));
            }

            @Override
            public void onCancel() {
                toast("已取消");
            }
        });
    }

    private void selectTitleImg() {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG4, 1);
    }

    public void selectImg() {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_IMG) {
                showContent(data);
                return;
            } else if (requestCode == WRITE_HEART_REQ) {//选择文字
                String editContent = data.getStringExtra("editContent");
                if (data.getIntExtra("mode", -1) == 0) {//添加文字
                    mAddNewView.setVisibility(View.GONE);
                    WriteHeartBeen been = new WriteHeartBeen();
                    been.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_t_publish));
                    been.setAudio(false);
                    been.setPath("");
                    been.setShowLast(true);
                    been.setShow(true);
                    been.setContent(editContent);
                    if (data.getBooleanExtra("last",false)){
                        been.setShowLast(true);
                        mData.add(been);
                    }else {
                        mData.add(data.getIntExtra("position", -1), been);
                    }
                } else if (data.getIntExtra("mode", -1) == 1) {
                    WriteHeartBeen been = mData.get(data.getIntExtra("position", -1));
                    been.setContent(editContent);
                    mData.set(data.getIntExtra("position", -1), been);
                } else {//设置标题
                    mTitle.setText(editContent);
                    mPublishBeen.setTitle(editContent);
                }
                mWriteHeartAdapter.notifyDataSetChanged();
            } else if (requestCode == WriteHeartAdapter.REQUEST_CODE_SELECT_IMG2) {//选择图片
                changeContent(data);
            } else if (requestCode == REQUEST_CODE_SELECT_IMG3) {//添加图片
                mAddNewView.setVisibility(View.GONE);
                addPicContent(data,false);
            } else if (requestCode == REQUEST_CODE_SELECT_IMG4) {//编辑封面
                editTitleBgContent(data);
            }else if (requestCode == WriteHeartAdapter.REQUEST_CODE_SELECT_IMG5) {//添加图片(最后一个条目)
                addPicContent(data,true);
            }else if (requestCode == REQUEST_VIDEO_CODE) {//选择视频
                byte[] bis = data.getByteArrayExtra("bitmap");
                if (data.getIntExtra("type", -1) == 0) {
                    mAddNewView.setVisibility(View.GONE);
                    WriteHeartBeen been = new WriteHeartBeen();
                    been.setBitmap(BitmapFactory.decodeByteArray(bis, 0, bis.length));
                    been.setAudio(true);
                    been.setShow(true);
                    been.setShowLast(true);
                    been.setShowVideo(true);
                    been.setPath(data.getStringExtra("path_video"));
                    if (data.getBooleanExtra("last",false)){
                        been.setShowLast(true);
                        mData.add(been);
                    }else {
                        mData.add(position, been);
                    }
                } else {
                    WriteHeartBeen been = mData.get(position);
                    been.setBitmap(BitmapFactory.decodeByteArray(bis, 0, bis.length));
                    been.setAudio(true);
                    been.setPath(data.getStringExtra("path_video"));
                    mData.set(position, been);
                }
                mWriteHeartAdapter.notifyDataSetChanged();
            } else if (requestCode == MUSIC_REQ) {//添加音乐
                mAddMusic.setText(data.getStringExtra("song"));
                mPublishBeen.setSong(data.getStringExtra("song"));
                mPublishBeen.setMusicPath(data.getStringExtra("path"));
            }
        } else {
            if (requestCode == REQUEST_CODE_SELECT_IMG) {
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void editTitleBgContent(Intent data) {//编辑封面
        paths = ImageSelector.getImagePaths(data);
        mTitleBg.setImageBitmap(BitmapFactory.decodeFile(paths.get(0)));
        mPublishBeen.setBgPath(paths.get(0));
    }

    /**
     * 添加图片内容
     *
     * @param data
     */
    private void addPicContent(Intent data,boolean b) {
        paths = ImageSelector.getImagePaths(data);
        for (int i = 0; i < paths.size(); i++) {
            WriteHeartBeen been = new WriteHeartBeen();
            been.setBitmap(BitmapFactory.decodeFile(paths.get(i)));
            been.setPath(paths.get(i));
            been.setAudio(false);
            been.setShowLast(true);
            been.setContent("");
            been.setShow(true);
            if (b){
                been.setShowLast(true);
                mData.add(been);
            }else {
                mData.add(position + i, been);
            }
        }
        mWriteHeartAdapter.notifyDataSetChanged();
    }

    private void changeContent(Intent data) {
        paths = ImageSelector.getImagePaths(data);
        WriteHeartBeen been = mData.get(position);
        been.setBitmap(BitmapFactory.decodeFile(paths.get(0)));
        been.setPath(paths.get(0));
        been.setContent("");
        been.setAudio(false);
        mData.set(position, been);
        mWriteHeartAdapter.notifyDataSetChanged();
    }

    private void showContent(Intent data) {//默认第一张为封面
        paths = ImageSelector.getImagePaths(data);
        mTitleBg.setImageBitmap(BitmapFactory.decodeFile(paths.get(0)));
        mPublishBeen.setBgPath(paths.get(0));
        mData.clear();
        for (int i = 0; i < paths.size(); i++) {
            WriteHeartBeen been = new WriteHeartBeen();
            been.setBitmap(BitmapFactory.decodeFile(paths.get(i)));
            been.setPath(paths.get(i));
            been.setContent("");
            been.setShowLast(true);
            been.setAudio(false);
            been.setShow(true);
            mData.add(been);
        }
        mWriteHeartAdapter.setData(mData);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_heart_;
    }

    @OnClick({R.id.gold_back, R.id.publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gold_back:
                finish();
                break;
            case R.id.publish:
                if (mData.size()<1){
                    toast("请至少添加一个");
                    return;
                }
                if (TextUtils.isEmpty(mTitle.getText().toString().trim())) {
                    toast("请添加主题");
                    return;
                }
                if ("请选择分类".equals(mSortTv.getText().toString().trim())){
                    toast("请选择分类");
                    return;
                }
                if ("请选择匹配商品".equals(mShopTv.getText().toString().trim())){
                    toast("请选择匹配商品");
                    return;
                }
                List<HeartBeen> data = new ArrayList<>();
                for (int i = 0; i < mWriteHeartAdapter.getData().size(); i++) {
                    HeartBeen been = new HeartBeen(mWriteHeartAdapter.getData().get(i).getPath(), mWriteHeartAdapter.getData().get(i).getAudioPathPic(), mWriteHeartAdapter.getData().get(i).getContent(), mWriteHeartAdapter.getData().get(i).isAudio(), mWriteHeartAdapter.getData().get(i).isShowVideo(), mWriteHeartAdapter.getData().get(i).isShow());
                    data.add(been);
                }
                mPublishBeen.setData(data);
                Intent intent = new Intent(WriteHeartTextActivity.this, PreviewWriteHeartActivity.class);
                intent.putExtra("publishBeen", mPublishBeen);
                startActivity(intent);
                break;
        }
    }

    //匹配商品
    private void showMatchGoodDialog() {
        View shareDialog = View.inflate(this, R.layout.dialog_match_good_heart, null);
        Button shure = (Button) shareDialog.findViewById(R.id.shure);
        RecyclerView recyclerView = (RecyclerView) shareDialog.findViewById(R.id.recycler_view);
        //相关商品
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);
        LookMoreAdapter adapter = new LookMoreAdapter(this);
        List<HotBeen> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            HotBeen hotBeen = new HotBeen();

            hotBeen.setSelect(false);
            hotBeen.setType(1);
            hotBeen.setShowLimitTime(i == 0 || i == 5);
            hotBeen.setShow1(i == 1 || i == 2 || i == 4);
            hotBeen.setShow2(i == 0 || i == 1 || i == 2);
            hotBeen.setShow3(i == 0 || i == 3);
            data.add(hotBeen);
        }
        adapter.setData(data);
        recyclerView.setAdapter(adapter);

        final MatchGoodDialog machDialog = new MatchGoodDialog(this);
        machDialog.setContentView(shareDialog);
        machDialog.show();
        shure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShopTv.setText("已选择");
//                mShopTv.setTextColor(getResources().getColor(R.color.yellow_dot));
                machDialog.dismiss();
            }
        });

    }
}

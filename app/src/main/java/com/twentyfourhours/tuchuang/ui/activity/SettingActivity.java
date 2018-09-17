package com.twentyfourhours.tuchuang.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.common.util.BitmapToRoundUtils;
import com.twentyfourhours.tuchuang.common.util.DataClearUtil;
import com.twentyfourhours.tuchuang.common.util.ImgUtil;
import com.twentyfourhours.tuchuang.common.util.ToastUtil;
import com.twentyfourhours.tuchuang.view.ClearDialog;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.image_head)
    ImageView mImageHead;
    @BindView(R.id.head)
    RelativeLayout mHead;
    @BindView(R.id.phone_number)
    TextView mPhoneNumber;
    @BindView(R.id.address_manager)
    TextView mAddressManager;
    @BindView(R.id.clear)
    RelativeLayout mClear;
    @BindView(R.id.feedback)
    TextView mFeedback;
    @BindView(R.id.quit)
    TextView mQuit;
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private Bitmap mRoundBitmap;
    private Bitmap queryBitmap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mRoundBitmap = BitmapToRoundUtils.toRoundBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.img_head)).getBitmap());
    }

    public byte[] img(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id)).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 向数据库中插入数据
     */
    public void insert() {
        ContentValues values = new ContentValues();
        values.put("image", img(mRoundBitmap));
        values.clear();
//        values.put("name", "徐良木");
//        values.put("phone", "1836382");
//        values.put("salary", "14000");
//        mDb.insert("person", null, values);
//        values.clear();
//        values.put("name", "徐良木");
//        values.put("phone", "1836382");
//        values.put("salary", "14000");
//        mDb.insert("person", null, values);
//        values.clear();
//        values.put("name", "徐良木");
//        values.put("phone", "1836383");
//        values.put("salary", "13000");
//        mDb.insert("person", null, values);
//        values.clear();
    }

    /**
     * 删除数据
     * db.delete(table, whereClause, whereArgs);
     * table:表名
     * whereClause:删除的where条件
     * whereArgs:填充where条件的占位符
     */
    public void delete() {
        //删除_id为3，姓名为"小智的儿子"的数据
    }

    /**
     * 更改数据
     * db.update(table, values, whereClause, whereArgs);
     * table:表名
     * values:把要更改的数据存放到values中
     * whereClause:更改的where条件
     * whereArgs:填充where条件的占位符
     */
    public void update() {
        //这是是把_id为4的人的salary更改为23000
        ContentValues values = new ContentValues();
        values.put("image", img(mRoundBitmap));
    }

    /**
     * 查询数据
     * db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
     * table:表名
     * columns：要查询的列名，是一个字符串数组
     * selection：查询的where语句
     * selectionArgs:填充where语句的占位符
     * limit：设置数据分页显示，"1,10"代表显示从第一行到第10行,"10,10"代表从第10行到第20行
     */
    public boolean query() {
        //这里我们查询的内容是：查询的列：_id,name,salary;查询的名字为:小智的儿子
        //所以这里我们得到的数据是"小智"那一行中的_id,name,salary
        //这里如果我们查询的列中没有_id的话呢，是不能得到_id的值的，会报一个空指针异常
        //所以我们所能得到的值就是我们所查询的列的条目所包含的内容，一句话，你查询什么内容就会得到相对应的内容

        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.back, R.id.head, R.id.phone_number, R.id.address_manager, R.id.clear, R.id.feedback, R.id.quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.head://设置头像
                openAlbum();
                break;
            case R.id.phone_number://设置手机号
                goTo(PhoneNumberActivity.class, false);
                break;
            case R.id.address_manager:
                goTo(AddressManagerActivity.class, false);
                break;
            case R.id.clear:
                clearCache();
                break;
            case R.id.feedback:
                goTo(FeedBackActivity.class, false);
                break;
            case R.id.quit:
                goTo(LoginActivity.class, true);
                break;
        }
    }

    /**
     * 打开相册的方法
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        bitmap = ImgUtil.handleImageOnKitKat(this, data);
                    } else {
                        bitmap = ImgUtil.handleImageBeforeKitKat(this, data);
                    }
                    mRoundBitmap = BitmapToRoundUtils.toRoundBitmap(bitmap);
                    mImageHead.setImageBitmap(mRoundBitmap);
                    if (query()) {
                        ContentValues values = new ContentValues();
                        values.put("image", img(mRoundBitmap));
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!query()) {
            insert();
        }
        super.onBackPressed();
    }

    private void clearCache() {

        ClearDialog dialog = new ClearDialog(this, new ClearDialog.MyDialogClickListener() {
            @Override
            public void clearCache() {
                DataClearUtil.cleanInternalCache(SettingActivity.this);
                ToastUtil.showStatuDialog(SettingActivity.this, "清除", new ToastUtil.OnActionListener() {
                    @Override
                    public void action() {

                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Setting Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }
}

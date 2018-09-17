package com.twentyfourhours.tuchuang.ui.activity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.PreWriteHeartAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.HeartBeen;
import com.twentyfourhours.tuchuang.model.bean.PublishBeen;
import com.twentyfourhours.tuchuang.widget.MyListView;
import com.twentyfourhours.tuchuang.widget.ObservableScrollview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by Administrator on 2018/3/14.
 */
public class PreviewWriteHeartActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    @BindView(R.id.gold_back)
    ImageView mGoldBack;
    @BindView(R.id.publish)
    TextView mPublish;
    @BindView(R.id.music)
    LinearLayout mMusic;
    @BindView(R.id.list_view)
    MyListView mListView;
    @BindView(R.id.music_iv)
    ImageView mMusicIv;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.song)
    TextView mSong;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.scroll_view)
    ObservableScrollview mScrollView;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.music_paly)
    RelativeLayout mMusicPaly;
    @BindView(R.id.top_view)
    LinearLayout mTopView;
    private PreWriteHeartAdapter mAdapter;
    private List<HeartBeen> mStoreBeens = new ArrayList<>();
    private MediaPlayer mMediaPlayer;
    private boolean isPlaying = true;
    private PublishBeen mPublishBeen;
    private int mPreTopHeight;
    private RotateAnimation mGearAnim;
    private boolean isRun=true;

    @Override
    protected void initListener() {
        ViewTreeObserver vto = mTopView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTopView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPreTopHeight = mTopView.getHeight();
            }
        });

        mScrollView.setOnScollChangedListener(new ObservableScrollview.OnScollChangedListener() {
            @Override
            public void onScrollChanged(ObservableScrollview scrollView, int x, int y, int oldx, int oldy) {
                if (mMusic.getVisibility()==View.VISIBLE) {
                    if (y <=mPreTopHeight) {
                        mMusicPaly.setAlpha(0);
                    } else {
                        mMusicPaly.setAlpha(1);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    protected void initData() {
        mListView.setFocusable(false);
        mMusicPaly.setAlpha(0);
        mTime.setText(getDate());
        mPublishBeen = getIntent().getParcelableExtra("publishBeen");
        mTitle.setText(mPublishBeen.getTitle());
        mScrollView.setBackground(new BitmapDrawable(BitmapFactory.decodeFile(mPublishBeen.getBgPath())));
        mMusic.setVisibility(TextUtils.isEmpty(mPublishBeen.getSong().trim()) ? View.GONE : View.VISIBLE);
        mSong.setText(mPublishBeen.getSong());
        mAdapter = new PreWriteHeartAdapter(this);
        mStoreBeens = mPublishBeen.getData();
//        for (int i = 0; i < 2; i++) {
//            WriteHeartBeen been = new WriteHeartBeen();
//            been.setContent("哈哈");
//            been.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img1_community));
////            been.setBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
//            mStoreBeens.add(been);
//        }
        mAdapter.setData(mStoreBeens);
        mListView.setAdapter(mAdapter);

        mGearAnim=new RotateAnimation(0,-720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        mGearAnim.setDuration(9000);
        mGearAnim.setRepeatCount(Animation.INFINITE);
        mGearAnim.setRepeatMode(Animation.RESTART);
        mMusicPaly.startAnimation(mGearAnim);
        mGearAnim.cancel();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMusicIv.setImageResource(R.drawable.icon_suspend);
        isPlaying=!isPlaying;
        mGearAnim.cancel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_write_heart;
    }

    @OnClick({R.id.gold_back, R.id.publish, R.id.music, R.id.user_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gold_back:
                finish();
                break;
            case R.id.publish:
                JCVideoPlayer.releaseAllVideos();
                if (mMediaPlayer != null) {
                    mMediaPlayer.reset();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                goTo(CommunityDetailActivity.class, false);
                break;
            case R.id.music:
                if (isPlaying) {
                    mGearAnim.start();
                    mMusicIv.setImageResource(R.drawable.icon_play);
                    startPlay();
                } else {
                    mGearAnim.cancel();
                    mMusicIv.setImageResource(R.drawable.icon_suspend);
                    stopPlay();
                }
                isPlaying = !isPlaying;
                break;
            case R.id.user_name:
                goTo(CommunityUserDetailActivity.class, false);
                break;
        }
    }

    // 获取日期时间 String 类型
    private String getDate() {
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "." + month + "." + day);
        return sbBuffer.toString();
    }

    private void stopPlay() {
        mMediaPlayer.pause();
    }

    private void startPlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        try {
            mMediaPlayer.setDataSource(mPublishBeen.getMusicPath());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

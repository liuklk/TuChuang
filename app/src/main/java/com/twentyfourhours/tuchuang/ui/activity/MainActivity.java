package com.twentyfourhours.tuchuang.ui.activity;

import android.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.ui.fragment.CommunityTestFragment;
import com.twentyfourhours.tuchuang.ui.fragment.CustomizedFragment;
import com.twentyfourhours.tuchuang.ui.fragment.FindFragment;
import com.twentyfourhours.tuchuang.ui.fragment.MyFragment;
import com.twentyfourhours.tuchuang.ui.fragment.StoreFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.radio_find)
    RadioButton mRadioFind;
    @BindView(R.id.radio_store)
    RadioButton mRadioStore;
    @BindView(R.id.radio_customized)
    RadioButton mRadioCustomized;
    @BindView(R.id.radio_community)
    RadioButton mRadioCommunity;
    @BindView(R.id.radio_my)
    RadioButton mRadioMy;

    private FindFragment mFindFragment;
    private StoreFragment mStoreFragment;
    private CustomizedFragment mCustomizedFragment;
    private MyFragment mMyFragment;
    private CommunityTestFragment mCommunityFragment;

    @Override
    protected void initListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        applyPermi();
        initFragment1();
    }

//    private void share() {
////        toast(Application.sInstace.getDevice());
////        logD("++++++++++++++++++++++++++++++++++++"+Application.sInstace.getDevice());
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //  大于等于23即为6.0及以上执行内容
//            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    || getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || getActivity().checkSelfPermission(Manifest.permission.READ_LOGS) != PackageManager.PERMISSION_GRANTED
//                    || getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                    || getActivity().checkSelfPermission(Manifest.permission.SET_DEBUG_APP) != PackageManager.PERMISSION_GRANTED || getActivity().checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED
//                    || getActivity().checkSelfPermission(Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED || getActivity().checkSelfPermission(Manifest.permission.WRITE_APN_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
//                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS}, 123);
//            }
//        } else {
//            if (UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN)) {
//
//                UMImage thumb = new UMImage(getActivity(), R.drawable.ic_launcher);
//                thumb.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//                UMWeb web = new UMWeb("https://123.sogou.com/?11661-0058");//url地址
//                web.setTitle("test share");//标题
//                web.setThumb(thumb);  //缩略图
//                web.setDescription("share success");//描述
//
//                // Permission Granted
//                new ShareAction(getActivity())
//                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                        .withMedia(web)
//                        .setCallback(shareListener)//回调监听器
//                        .share();
//            } else {
//                toast("请先安装微信");
//            }
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        if (requestCode == 123) {
//            //权限处理
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                if (UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN)) {
//
//                    UMImage thumb = new UMImage(getActivity(), R.drawable.ic_launcher);
//                    thumb.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//                    UMWeb web = new UMWeb("https://123.sogou.com/?11661-0058");//url地址
//                    web.setTitle("This is music title");//标题
//                    web.setThumb(thumb);  //缩略图
//                    web.setDescription("my description");//描述
//
//                    // Permission Granted
//                    new ShareAction(getActivity())
//                            .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                            .withMedia(web)
//                            .setCallback(shareListener)//回调监听器
//                            .share();
//                }
//            } else {
////                // Permission Denied
////                toast("请先安装微信");
//            }
//        }
//    }

    private void applyPermi() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //  大于等于23即为6.0及以上执行内容
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_LOGS) != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.SET_DEBUG_APP) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_APN_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
//                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS, Manifest.permission.CAMERA}, 123);
//            }
//        }
    }

//    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
//
//                public static String takePhoto(Context context, int requestCode) {
//              String filePath = "";
//                 if (ContextCompat.checkSelfPermission(context,
//                               Manifest.permission.CAMERA)
//                       != PackageManager.PERMISSION_GRANTED) {
//                       ActivityCompat.requestPermissions((Activity) context,
//                                      new String[]{Manifest.permission.CAMERA},
//                                       TAKE_PHOTO_REQUEST_CODE);
//                     } else {
//                        Intent intent = new Intent(
//                                         MediaStore.ACTION_IMAGE_CAPTURE, null);
//                        filePath = AppApplication.getInstance().getCachePath() + File.separator + MD5.md5(String.valueOf(System.currentTimeMillis())) + "camera" + ".png";
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//                                        .fromFile(new File(filePath)));
//                      ((Activity) context).startActivityForResult(intent, requestCode);
//
//                   }
//                return filePath;
//             }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    //显示第一个fragment
    public void initFragment1() {
        mRadioFind.setChecked(true);
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (mFindFragment == null) {
            mFindFragment = new FindFragment();
            fragmentTransaction.add(R.id.container, mFindFragment);
        }
        //隐藏所有fragment
        hideFragment(fragmentTransaction);
        //显示需要显示的fragment
        fragmentTransaction.show(mFindFragment);
        //提交事务
        fragmentTransaction.commit();
    }

    //显示第二个fragment
    private void initFragment3() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (mStoreFragment == null) {
            mStoreFragment = new StoreFragment();
            fragmentTransaction.add(R.id.container, mStoreFragment);
        }
        //隐藏所有fragment
        hideFragment(fragmentTransaction);
        //显示需要显示的fragment
        fragmentTransaction.show(mStoreFragment);
        //提交事务
        fragmentTransaction.commit();
    }

    //显示第三个fragment
    private void initFragment2() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (mCustomizedFragment == null) {
            mCustomizedFragment = new CustomizedFragment();
            fragmentTransaction.add(R.id.container, mCustomizedFragment);
        }
        //隐藏所有fragment
        hideFragment(fragmentTransaction);
        //显示需要显示的fragment
        fragmentTransaction.show(mCustomizedFragment);
        //提交事务
        fragmentTransaction.commit();
    }

    private void initFragment4() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (mMyFragment == null) {
            mMyFragment = new MyFragment();
            fragmentTransaction.add(R.id.container, mMyFragment);
        }
        //隐藏所有fragment
        hideFragment(fragmentTransaction);
        //显示需要显示的fragment
        fragmentTransaction.show(mMyFragment);
        //提交事务
        fragmentTransaction.commit();
    }

    private void initFragment5() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (mCommunityFragment == null) {
            mCommunityFragment = new CommunityTestFragment();
            fragmentTransaction.add(R.id.container, mCommunityFragment);
        }
        //隐藏所有fragment
        hideFragment(fragmentTransaction);
        //显示需要显示的fragment
        fragmentTransaction.show(mCommunityFragment);
        //提交事务
        fragmentTransaction.commit();
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (mFindFragment != null) {
            transaction.hide(mFindFragment);
        }
        if (mStoreFragment != null) {
            transaction.hide(mStoreFragment);
        }
        if (mCustomizedFragment != null) {
            transaction.hide(mCustomizedFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
        if (mCommunityFragment != null) {
            transaction.hide(mCommunityFragment);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int checkedRadioButtonId = group.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.radio_find:
                initFragment1();
                break;
            case R.id.radio_my:
                initFragment4();
                break;
            case R.id.radio_store:
                initFragment3();
                break;
            case R.id.radio_customized:
                initFragment2();
                break;
            case R.id.radio_community:
                initFragment5();
                break;
        }
    }

}

package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class EditTextActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.shure)
    TextView mShure;
    @BindView(R.id.editText)
    EditText mEditText;
    private EditText editText;

    @Override
    protected void initTitleBar() {

    }

    @Override
    protected void initView() {
        editText = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String editContent = getIntent().getStringExtra("editContent");
        if (editContent!=null){
            mEditText.setText(editContent);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edittext_words;
    }

    @OnClick({R.id.back, R.id.shure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.shure:
                Intent data = new Intent();
                data.putExtra("position",getIntent().getIntExtra("position",-1));
                data.putExtra("mode",getIntent().getIntExtra("mode",-1));
                data.putExtra("last",getIntent().getBooleanExtra("last",false));
			    data.putExtra("editContent", editText.getText().toString().trim());
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }

//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.title_btn_left:
//			finish();
//			break;
//
//		case R.id.title_btn_right:
//			Intent data = new Intent();
//			data.putExtra("editContent", editText.getText().toString().trim());
//			setResult(TuYaActivity.SET_WZ_BITMAP, data);
//			finish();
//			break;
//		}
//	}
}

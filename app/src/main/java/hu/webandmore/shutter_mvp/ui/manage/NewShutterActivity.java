package hu.webandmore.shutter_mvp.ui.manage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;

public class NewShutterActivity extends AppCompatActivity {

    @BindView(R.id.channel_name)
    EditText mChannelName;

    @BindView(R.id.copy_buttons_layout)
    LinearLayout mCopyLayout;

    @BindView(R.id.up_btn)
    Button mUpBtn;
    @BindView(R.id.stop_btn)
    Button mStopBtn;
    @BindView(R.id.down_btn)
    Button mDownBtn;

    @BindView(R.id.up_icon)
    ImageView mUpIcon;
    @BindView(R.id.stop_icon)
    ImageView mStopIcon;
    @BindView(R.id.down_icon)
    ImageView mDownIcon;

    @BindView(R.id.upProgress)
    ProgressBar mUpProgress;
    @BindView(R.id.stopProgress)
    ProgressBar mStopProgress;
    @BindView(R.id.downProgress)
    ProgressBar mDownProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shutter);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.start_copy)
    public void startCopyChannel(){
        mCopyLayout.setVisibility(View.VISIBLE);
    }

}

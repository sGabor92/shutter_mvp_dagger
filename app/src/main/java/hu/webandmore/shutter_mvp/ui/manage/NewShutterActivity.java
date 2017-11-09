package hu.webandmore.shutter_mvp.ui.manage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.app.Enums;

public class NewShutterActivity extends AppCompatActivity implements NewShutterScreen {

    @BindView(R.id.channel_name)
    EditText mChannelName;

    @BindView(R.id.shutter_id_text)
    TextView mChannelId;

    @BindView(R.id.start_copy)
    Button mCopyChanelBtn;

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

    private int channelID;
    private NewShutterPresenter newShutterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shutter);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        newShutterPresenter = new NewShutterPresenter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                channelID = extras.getInt("channelID");
                if (getIntent().hasExtra("channelName"))
                    mChannelName.setText(extras.getString("channelName"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        newShutterPresenter.attachScreen(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mChannelId.setText(String.valueOf(channelID));
    }

    @Override
    protected void onStop() {
        super.onStop();
        newShutterPresenter.detachScreen();
    }

    @OnClick(R.id.start_copy)
    public void startCopyChannel() {
        if (mChannelName.getText().toString().isEmpty()) {
            mChannelName.setError(getString(R.string.required_field), null);
            mChannelName.requestFocus();
        } else {
            Channel currentChannel = newShutterPresenter.createChannel(channelID,
                    mChannelName.getText().toString());
            newShutterPresenter.modifyChannel(currentChannel);

        }
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startCopyingChannel() {
        mCopyChanelBtn.setVisibility(View.GONE);
        mCopyLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.up_btn)
    public void upButtonCopy(){

    }

    @OnClick(R.id.stop_btn)
    public void stopButtonCopy(){

    }

    @OnClick(R.id.down_btn)
    public void downButtonCopy(){

    }

    @OnClick(R.id.up_icon)
    public void tryOutUp() {
        newShutterPresenter.tryOutShutter(channelID, Enums.ShutterMovement.UP);
    }

    @OnClick(R.id.stop_icon)
    public void tryOutStop() {
        newShutterPresenter.tryOutShutter(channelID, Enums.ShutterMovement.STOP);
    }

    @OnClick(R.id.down_icon)
    public void tryOutDown() {
        newShutterPresenter.tryOutShutter(channelID, Enums.ShutterMovement.DOWN);
    }

}

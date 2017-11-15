package hu.webandmore.shutter_mvp.ui.groups;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.interactor.GroupsInteractor;
import hu.webandmore.shutter_mvp.interactor.events.GetGroupsEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

public class GroupsPresenter extends Presenter<GroupsScreen> {

    private static String TAG = "GroupsPresenter";

    private Executor networkExecutor;
    private Context context;

    private GroupsInteractor groupsInteractor;

    GroupsPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        groupsInteractor = new GroupsInteractor(context);
    }

    @Override
    public void attachScreen(GroupsScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    void getGroups() {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupsInteractor.getGroups();
            }
        });
    }

    public void addNewGroupDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.add_new_group_popup, null);

        final EditText mNameInput = (EditText) mView.findViewById(R.id.group_name_input);
        RecyclerView mShuttersView = (RecyclerView)mView.findViewById(R.id.shutters_list);

        Button mPositiveButton = (Button) mView.findViewById(R.id.save_group);
        Button mNegativeButton = (Button) mView.findViewById(R.id.cancel_add_group);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        mPositiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "SAVE clicked");
            }
        });

        mNegativeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "CANCEL clicked");
            }
        });

        dialog.show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetGroupsEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.showGroups(event.getGroups());
                } else {
                    screen.showError(event.getErrorMessage());
                }
            }
        }
    }

}

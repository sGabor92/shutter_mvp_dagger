package hu.webandmore.shutter_mvp.ui.groups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.interactor.GroupsInteractor;
import hu.webandmore.shutter_mvp.interactor.events.CreateGroupEvent;
import hu.webandmore.shutter_mvp.interactor.events.DeleteGroupEvent;
import hu.webandmore.shutter_mvp.interactor.events.GetGroupsEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

class GroupsPresenter extends Presenter<GroupsScreen> {

    //private static String TAG = "GroupsPresenter";

    private Executor networkExecutor;
    private Context context;
    private Paint p = new Paint();

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

    private void deleteGroup(final int itemPosition) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int groupId = screen.getSelectedGroupId(itemPosition);
                groupsInteractor.deleteGroup(groupId, itemPosition);
            }
        });
    }

    void addNewGroupDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        @SuppressLint("InflateParams") View mView =
                LayoutInflater.from(context).inflate(R.layout.add_new_group_popup, null);

        final EditText mNameInput = (EditText) mView.findViewById(R.id.group_name_input);
        Button mPositiveButton = (Button) mView.findViewById(R.id.save_group);
        Button mNegativeButton = (Button) mView.findViewById(R.id.cancel_add_group);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        mPositiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mNameInput.getText().toString().isEmpty()) {
                    mNameInput.setError(context.getString(R.string.required_field), null);
                    mNameInput.requestFocus();
                } else {
                    Group group = new Group();
                    group.setName(mNameInput.getText().toString());
                    group.setColor("0x000000");
                    groupsInteractor.createGroup(group);
                    dialog.dismiss();
                }
            }
        });

        mNegativeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final CreateGroupEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.savedSuccessful();
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final DeleteGroupEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 204 || event.getCode() == 200) {
                    screen.removeGroup(event.getItemPosition());
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle(R.string.delete_group_title);
                    alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            deleteGroup(position);
                        }
                    });

                    alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            screen.restoreGroup(position);
                        }
                    });
                    alert.show();
                }
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_delete_accent);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    Paint p2 = new Paint();
                    p2.setTextSize(60);
                    p2.setTextAlign(Paint.Align.CENTER);
                    p2.setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorAccent));

                    if (dX < 0) {
                        p.setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.colorWhite));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        if(icon != null) {
                            c.drawBitmap(icon, null, icon_dest, p);
                        }
                        c.drawText(context.getString(R.string.delete), (float) itemView.getRight() - 100, (float) itemView.getTop() + width + 60, p2);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(screen.getGroupRecyclerView());
    }

}

package hu.webandmore.shutter_mvp.ui.program;

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
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.interactor.AutomationInteractor;
import hu.webandmore.shutter_mvp.interactor.events.DeleteAutomationEvent;
import hu.webandmore.shutter_mvp.interactor.events.GetAutomationsEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

public class AutomationPresenter extends Presenter<AutomationScreen> {

    private static String TAG = "AutomationPresenter";

    private Executor networkExecutor;
    private Context context;
    private Paint p = new Paint();

    private AutomationInteractor automationInteractor;

    public AutomationPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        automationInteractor = new AutomationInteractor(context);
    }

    @Override
    public void attachScreen(AutomationScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    void getAutomations() {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                automationInteractor.getAutomations();
            }
        });
    }

    private void deleteAutomation(final int itemPosition) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int automationId = screen.getSelectedAutomationId(itemPosition);
                automationInteractor.deleteAutomation(automationId, itemPosition);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetAutomationsEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.showAutomations(event.getAutomations());
                } else {
                    screen.showError(event.getErrorMessage());
                }
                screen.hideProgressBar();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final DeleteAutomationEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 204 || event.getCode() == 200) {
                    screen.removeAutomation(event.getItemPosition());
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    // TODO - duplikálás, 1 paraméter átadással elég ebből 1 is
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
                    alert.setTitle(R.string.delete_automation_title);
                    alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            deleteAutomation(position);
                        }
                    });

                    alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            screen.restoreAutomation(position);
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
        itemTouchHelper.attachToRecyclerView(screen.getAutomationRecyclerView());
    }

}

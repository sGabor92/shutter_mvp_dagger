package hu.webandmore.shutter_mvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.app.Enums;
import hu.webandmore.shutter_mvp.interactor.ShutterInteractor;
import hu.webandmore.shutter_mvp.ui.manage.NewShutterActivity;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    private static String TAG = "GroupsAdapter";

    private final Context context;

    private ArrayList<Group> groups = new ArrayList<>();

    public GroupsAdapter(Context c, ArrayList<Group> groups) {
        this.context = c;
        this.groups = groups;
    }

    @Override
    public GroupsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GroupsAdapter.ViewHolder holder, int position) {
        holder.group = groups.get(position);
        holder.groupName.setText(holder.group.getName());

        holder.upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked on item: " + holder.group.getName());

            }
        });

    }

    @Override
    public long getItemId(int position) {
        return groups.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public Group getItem(int position) {
        return groups.get(position);
    }

    public void removeItem(int position) {
        groups.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, groups.size());
    }

    public void restoreItem(int position, Group group) {
        groups.add(position, group);
        notifyDataSetChanged();
        notifyItemInserted(position);
    }

    public int getCurrentId(int position) {
        return groups.get(position).getId();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;
        ImageView upBtn;
        ImageView stopBtn;
        ImageView downBtn;
        Group group;

        ViewHolder(View itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.group_name);
            upBtn = (ImageView) itemView.findViewById(R.id.up_btn);
            stopBtn = (ImageView) itemView.findViewById(R.id.stop_btn);
            downBtn = (ImageView) itemView.findViewById(R.id.down_btn);
        }
    }

}

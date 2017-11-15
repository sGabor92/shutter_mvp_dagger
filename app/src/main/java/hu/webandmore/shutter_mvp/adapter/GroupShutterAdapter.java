package hu.webandmore.shutter_mvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.ui.manage.NewShutterActivity;

public class GroupShutterAdapter extends RecyclerView.Adapter<GroupShutterAdapter.ViewHolder> {

    private static String TAG = "GroupShutterAdapter";

    private final Context context;

    private ArrayList<Channel> channels = new ArrayList<>();

    public GroupShutterAdapter(Context c, ArrayList<Channel> channels) {
        this.context = c;
        this.channels = channels;
    }

    @Override
    public GroupShutterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shutter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GroupShutterAdapter.ViewHolder holder, int position) {
        holder.channel = channels.get(position);
        holder.channelName.setText(holder.channel.getName());

        holder.isGrouped.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "Checked changed: " + isChecked);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return channels.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public Channel getItem(int position) {
        return channels.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView channelName;
        RadioButton isGrouped;
        Channel channel;

        ViewHolder(View itemView) {
            super(itemView);
            channelName = (TextView) itemView.findViewById(R.id.shutter_name);
            isGrouped = (RadioButton) itemView.findViewById(R.id.group_channel);
        }
    }

}

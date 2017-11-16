package hu.webandmore.shutter_mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.interactor.GroupsInteractor;

public class GroupShutterAdapter extends RecyclerView.Adapter<GroupShutterAdapter.ViewHolder> {

    private static String TAG = "GroupShutterAdapter";

    private final Context context;

    private ArrayList<Channel> channels = new ArrayList<>();
    private int groupId;
    private ArrayList<Integer> channelIds;
    private GroupsInteractor groupsInteractor;

    public GroupShutterAdapter(Context c, ArrayList<Channel> channels,
                               int groupId, ArrayList<Integer> channelIds) {
        this.context = c;
        this.channels = channels;
        this.groupId = groupId;
        this.channelIds = channelIds;
        groupsInteractor = new GroupsInteractor(context);
    }

    @Override
    public GroupShutterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shutter_to_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GroupShutterAdapter.ViewHolder holder, int position) {
        holder.channel = channels.get(position);
        holder.channelName.setText(holder.channel.getName());

        if (channelIds.contains(holder.channel.getId())) {
            holder.isGrouped.setChecked(true);
        } else {
            holder.isGrouped.setChecked(false);
        }

        holder.isGrouped.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "Checked changed: " + isChecked);
                holder.channel.setChecked(isChecked);
                if (isChecked) {
                    groupsInteractor.attachChannelToGroup(groupId, holder.channel.getId());
                } else {
                    groupsInteractor.detachChannelFromGroup(groupId, holder.channel.getId());
                }
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

    public ArrayList<Channel> getCheckedShutters() {
        ArrayList<Channel> returnChannels = new ArrayList<>();
        for (Channel channel : channels) {
            if (channel.isChecked()) {
                returnChannels.add(channel);
            }
        }
        return returnChannels;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView channelName;
        CheckBox isGrouped;
        Channel channel;

        ViewHolder(View itemView) {
            super(itemView);
            channelName = (TextView) itemView.findViewById(R.id.shutter_name);
            isGrouped = (CheckBox) itemView.findViewById(R.id.group_channel);
        }
    }

}

package hu.webandmore.shutter_mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.app.Enums;
import hu.webandmore.shutter_mvp.interactor.ShutterMovementInteractor;

public class ShutterAdapter extends RecyclerView.Adapter<ShutterAdapter.ViewHolder> {

    private final Context context;
    private ShutterMovementInteractor shutterMovementInteractor;

    private ArrayList<Channel> channels = new ArrayList<>();

    public ShutterAdapter(Context c, ArrayList<Channel> channels) {
        this.context = c;
        this.channels = channels;
        shutterMovementInteractor = new ShutterMovementInteractor(context);
    }

    @Override
    public ShutterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shutter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShutterAdapter.ViewHolder holder, int position) {
        holder.channel = channels.get(position);
        holder.channelName.setText(holder.channel.getName());

        if(!holder.channel.isActive()) {
            holder.itemView.setEnabled(false);
            holder.upBtn.setEnabled(false);
            holder.stopBtn.setEnabled(false);
            holder.downBtn.setEnabled(false);
        } else {
            holder.itemView.setEnabled(true);
            holder.upBtn.setEnabled(true);
            holder.stopBtn.setEnabled(true);
            holder.downBtn.setEnabled(true);
        }

        holder.upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableChannels();
                shutterMovementInteractor.moveShutter(holder.channel.getId(), Enums.ShutterMovement.UP);
            }
        });

        holder.stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableChannels();
                shutterMovementInteractor.moveShutter(holder.channel.getId(), Enums.ShutterMovement.STOP);
            }
        });

        holder.downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableChannels();
                shutterMovementInteractor.moveShutter(holder.channel.getId(), Enums.ShutterMovement.DOWN);
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

    public void removeItem(int position) {
        channels.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, channels.size());
    }

    public void restoreItem(int position, Channel channel) {
        channels.add(position, channel);
        notifyDataSetChanged();
        notifyItemInserted(position);
    }


    public int getCurrentId(int position) {
        return channels.get(position).getId();
    }

    public void update() {
        notifyDataSetChanged();
    }

    private void disableChannels() {
        for (Channel channel : channels) {
            channel.setActive(false);
        }
        notifyDataSetChanged();
    }

    public void activateChannels() {
        for(Channel channel: channels){
            channel.setActive(true);
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView channelName;
        ImageView upBtn;
        ImageView stopBtn;
        ImageView downBtn;
        Channel channel;

        ViewHolder(View itemView) {
            super(itemView);
            channelName = (TextView) itemView.findViewById(R.id.shutter_name);
            upBtn = (ImageView) itemView.findViewById(R.id.up_btn);
            stopBtn = (ImageView) itemView.findViewById(R.id.stop_btn);
            downBtn = (ImageView) itemView.findViewById(R.id.down_btn);
        }
    }

}

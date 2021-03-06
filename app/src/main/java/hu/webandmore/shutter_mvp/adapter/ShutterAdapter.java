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
import hu.webandmore.shutter_mvp.app.Enums;
import hu.webandmore.shutter_mvp.interactor.ShutterInteractor;
import hu.webandmore.shutter_mvp.ui.manage.NewShutterActivity;

public class ShutterAdapter extends RecyclerView.Adapter<ShutterAdapter.ViewHolder> {

    private static String TAG = "ShutterAdapter";

    private final Context context;
    private ShutterInteractor shutterMovementInteractor;

    private ArrayList<Channel> channels = new ArrayList<>();

    public ShutterAdapter(Context c, ArrayList<Channel> channels) {
        this.context = c;
        this.channels = channels;
        shutterMovementInteractor = new ShutterInteractor(context);
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
                shutterMovementInteractor.moveShutter(holder.channel.getId(), Enums.ShutterMovement.MOVE_UP);
            }
        });

        holder.stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableChannels();
                shutterMovementInteractor.moveShutter(holder.channel.getId(), Enums.ShutterMovement.MOVE_STOP);
            }
        });

        holder.downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableChannels();
                shutterMovementInteractor.moveShutter(holder.channel.getId(), Enums.ShutterMovement.MOVE_DOWN);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked on item: " + holder.channel.getName());
                Intent intent = new Intent(context, NewShutterActivity.class);
                intent.putExtra("channelID", holder.channel.getId());
                intent.putExtra("channelName", holder.channel.getName());
                context.startActivity(intent);

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

    public void descendingByUpdate() {
        Collections.sort(channels, new Comparator<Channel>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Channel o1, Channel o2) {
                SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    return Long.compare(sdf.parse(o2.getUpdated_at()).getTime(),
                            sdf.parse(o1.getUpdated_at()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        this.notifyDataSetChanged();
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

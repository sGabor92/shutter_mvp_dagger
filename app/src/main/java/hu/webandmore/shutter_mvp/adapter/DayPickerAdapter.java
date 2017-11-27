package hu.webandmore.shutter_mvp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.PickedDay;

public class DayPickerAdapter extends RecyclerView.Adapter<DayPickerAdapter.ViewHolder> {

    private static String TAG = "DayPickerAdapter";

    private final Context context;

    private ArrayList<PickedDay> days = new ArrayList<>();

    public DayPickerAdapter(Context context, ArrayList<PickedDay> days) {
        this.context = context;
        this.days = days;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_days, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.pickedDay = days.get(position);
        Log.i(TAG, "OnBind calling! isSelected: " + holder.pickedDay.isSelected());

        holder.dayName.setText(holder.pickedDay.getName());

        if(holder.pickedDay.isSelected()) {
            holder.dayName.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_blue));
        } else {
            holder.dayName.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_grey));
        }

        holder.dayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked on day item!");
                if(holder.pickedDay.isSelected()) {
                    holder.pickedDay.setSelected(false);
                    holder.dayName.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_grey));
                } else {
                    holder.pickedDay.setSelected(true);
                    holder.dayName.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_blue));
                }
            }
        });

    }

    public ArrayList<PickedDay> getSelectedDays() {
        ArrayList<PickedDay> returnDays = new ArrayList<>();
        for(PickedDay day: days) {
            if(day.isSelected()) {
                returnDays.add(day);
            }
        }
        return returnDays;
    }

    public void selectAllDay() {
        Log.i(TAG, "Select every day!");
        for(PickedDay day: days) {
            day.setSelected(true);
        }
        notifyDataSetChanged();
    }

    public void deselectDays() {
        for(PickedDay day: days) {
            day.setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button dayName;
        PickedDay pickedDay;

        ViewHolder(View itemView) {
            super(itemView);
            dayName = (Button) itemView.findViewById(R.id.dayText);
        }
    }
}

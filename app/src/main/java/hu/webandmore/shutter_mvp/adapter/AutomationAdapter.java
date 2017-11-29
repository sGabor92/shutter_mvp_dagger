package hu.webandmore.shutter_mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Automation;

public class AutomationAdapter extends RecyclerView.Adapter<AutomationAdapter.ViewHolder> {

    private static String TAG = "AutomationAdapter";

    private final Context context;

    private ArrayList<Automation> automations = new ArrayList<>();

    public AutomationAdapter(Context c, ArrayList<Automation> automations) {
        this.context = c;
        this.automations = automations;
    }

    @Override
    public AutomationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_automation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AutomationAdapter.ViewHolder holder, int position) {
        holder.automation = automations.get(position);
        holder.automationName.setText(holder.automation.getName());
        holder.automationDays.setText(holder.automation.getPickedDayString());

        if(holder.automation.isActive()){
            holder.isActive.setChecked(true);
        } else {
            holder.isActive.setChecked(false);
        }

        holder.isActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.automation.setActive(isChecked);
                // TODO - API hívás actívra rakni vagy deaktiválni
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return automations.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return automations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView automationName;
        TextView automationDays;
        Switch isActive;
        Automation automation;

        ViewHolder(View itemView) {
            super(itemView);
            automationName = (TextView) itemView.findViewById(R.id.automation_name);
            automationDays = (TextView) itemView.findViewById(R.id.daysText);
            isActive = (Switch) itemView.findViewById(R.id.automation_active);
        }
    }

}

package hu.webandmore.shutter_mvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Automation;
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.interactor.AutomationInteractor;
import hu.webandmore.shutter_mvp.ui.automation.CreateAutomationAcivity;
import hu.webandmore.shutter_mvp.ui.groups.EditGroupActivity;

public class AutomationAdapter extends RecyclerView.Adapter<AutomationAdapter.ViewHolder> {

    private static String TAG = "AutomationAdapter";

    private final Context context;
    private AutomationInteractor automationInteractor;

    private ArrayList<Automation> automations = new ArrayList<>();

    public AutomationAdapter(Context c, ArrayList<Automation> automations) {
        this.context = c;
        this.automations = automations;
        automationInteractor = new AutomationInteractor(context);
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
        Log.i(TAG, "Size: " + holder.automation.getPicked_days().size());
        if(holder.automation.isEveryDay()) {
            holder.automationDays.setText(R.string.every_day);
        } else {
            holder.automationDays.setText(holder.automation.getPickedDayString());
        }

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
                automationInteractor.modifyAutomation(holder.automation);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - itt még valami hiba van a szerkesztésnél!
                Intent intent = new Intent(context, CreateAutomationAcivity.class);
                intent.putExtra("automationId", holder.automation.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return automations.get(position).getId();
    }

    public Automation getItem(int position) {
        return automations.get(position);
    }

    public void removeItem(int position) {
        automations.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, automations.size());
    }

    public void restoreItem(int position, Automation automation) {
        automations.add(position, automation);
        notifyDataSetChanged();
        notifyItemInserted(position);
    }

    public int getCurrentId(int position) {
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

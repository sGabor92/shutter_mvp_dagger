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
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.interactor.GroupsInteractor;
import hu.webandmore.shutter_mvp.ui.automation.CreateAutomationAcivity;

public class GroupAutomationAdapter extends RecyclerView.Adapter<GroupAutomationAdapter.ViewHolder> {

    private static String TAG = "GroupAutomationAdapter";

    private final Context context;

    private ArrayList<Group> groups = new ArrayList<>();

    public GroupAutomationAdapter(Context c, ArrayList<Group> groups) {
        this.context = c;
        this.groups = groups;
    }

    @Override
    public GroupAutomationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_group_to_automation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GroupAutomationAdapter.ViewHolder holder, int position) {
        holder.group = groups.get(position);
        holder.groupName.setText(holder.group.getName());

        holder.isSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "Checked changed: " + isChecked);
                holder.group.setActive(isChecked);
                ((CreateAutomationAcivity)context).updateSelectedItemsCount();
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return groups.get(position).getId();
    }

    public ArrayList<Group> getSelectedGroups() {
        ArrayList<Group> returnGroup = new ArrayList<>();
        for (Group group : groups) {
            if (group.isActive()) {
                returnGroup.add(group);
            }
        }
        return returnGroup;
    }

    public int getSelectedItemsCount() {
        int count = 0;
        for (Group group : groups) {
            if (group.isActive()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;
        CheckBox isSelected;
        Group group;

        ViewHolder(View itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.group_name);
            isSelected = (CheckBox) itemView.findViewById(R.id.selected);
        }
    }

}

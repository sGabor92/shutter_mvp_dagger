package hu.webandmore.shutter_mvp.ui.program;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.webandmore.shutter_mvp.R;

class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    private AutomationFragment automationFragment = new AutomationFragment();
    private ShutterCenterFragment shutterCenterFragment = new ShutterCenterFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return shutterCenterFragment;
        if (position == 1)
            return automationFragment;
        return settingsFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.shutter_center);
            case 1:
                return mContext.getString(R.string.automation);
            case 2:
                return mContext.getString(R.string.settings);
        }
        return null;
    }

}

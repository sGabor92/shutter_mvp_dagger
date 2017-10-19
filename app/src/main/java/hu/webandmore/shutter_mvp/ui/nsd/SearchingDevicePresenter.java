package hu.webandmore.shutter_mvp.ui.nsd;

import android.content.Context;

import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.ui.Presenter;
import hu.webandmore.shutter_mvp.utils.TokenStorage;

public class SearchingDevicePresenter extends Presenter<SearchingDeviceScreen> {

    Context context;
    private TokenStorage mToken = null;

    public SearchingDevicePresenter(Context context){
        this.context = context;
        mToken = new TokenStorage(context);
    }

    void saveDevice(String ip_address){
        mToken.setIp(context, ip_address);
    }

    void setServiceGeneratorAddress(String address){
        ServiceGenerator.DEV_ADDRESS = address;
    }

}

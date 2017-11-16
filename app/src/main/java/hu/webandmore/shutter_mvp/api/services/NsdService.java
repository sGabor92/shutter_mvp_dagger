package hu.webandmore.shutter_mvp.api.services;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import hu.webandmore.shutter_mvp.utils.DiscoveryTimer;

public class NsdService implements DiscoveryTimer.OnTimeoutListener {

    private Context mContext;

    private NsdManager mNsdManager;
    private NsdManager.ResolveListener mResolveListener;
    private NsdManager.DiscoveryListener mDiscoveryListener;
    private static final String TAG = "NSD";
    private static final String SERVICE_TYPE = "_http._tcp.";
    private String mServiceName = "redonyinnyo";
    private NsdServiceInfo mService;
    private NsdDeviceListener listener;
    private long mDiscoveryTimeout = 10;
    private DiscoveryTimer mDiscoveryTimer;

    public NsdService(Context context) {
        Log.i(TAG, "Nsd Service Construct");
        mContext = context;
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        this.mDiscoveryTimer = new DiscoveryTimer(this, mDiscoveryTimeout);
    }

    public void setListener(NsdDeviceListener l) {
        this.listener = l;
    }

    public void initializeNsd() {
        Log.i(TAG, "Nsd Service Init");
        initializeDiscoveryListener();
        initializeResolveListener();
    }

    private void initializeDiscoveryListener() {
        mDiscoveryListener = new NsdManager.DiscoveryListener() {

            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started: " + regType);
                mDiscoveryTimer.start();
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                Log.d(TAG, "listener is " + String.valueOf(listener != null));
                mDiscoveryTimer.reset();
                if (listener != null)
                    listener.foundDevice(service.getHost());
                Log.d(TAG, "Service discovery success " + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    Log.d(TAG, "Cannot resolve service: " + service);
                } else if (service.getServiceName().equals(mServiceName)) {
                    mNsdManager.resolveService(service, mResolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.e(TAG, "service lost" + service);
                if (mService == service) {
                    mService = null;
                }
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                if (listener != null)
                    listener.deviceNotFound();
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    private void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Resolve failed" + errorCode);

                if (listener != null)
                    listener.deviceNotFound();
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);
                mDiscoveryTimer.reset();
                if (serviceInfo.getServiceName().equals(mServiceName)) {
                    mService = serviceInfo;
                    if (listener != null)
                        listener.foundDevice(serviceInfo.getHost());
                }

            }
        };
    }

    public void discoverServices() {
        try {
            mNsdManager.discoverServices(
                    SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopDiscovery() {
        mDiscoveryTimer.cancel();
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
    }

    private void discoveryFailed() {
        mDiscoveryTimer.cancel();
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
        if (listener != null)
            listener.deviceNotFound();
    }

    public void tearDown() {
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
    }

    @Override
    public void onNsdDiscoveryTimeout() {
        discoveryFailed();
    }
}

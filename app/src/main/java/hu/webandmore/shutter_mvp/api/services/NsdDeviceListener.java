package hu.webandmore.shutter_mvp.api.services;

import java.net.InetAddress;

public interface NsdDeviceListener {
    void foundDevice(InetAddress address);
    void deviceNotFound();
}

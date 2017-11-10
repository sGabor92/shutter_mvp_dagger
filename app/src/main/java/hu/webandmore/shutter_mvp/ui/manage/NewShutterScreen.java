package hu.webandmore.shutter_mvp.ui.manage;

import hu.webandmore.shutter_mvp.app.Enums;

interface NewShutterScreen {
    void showError(String errorMessage);
    void startCopyingChannel();
    void hideCopyProgressBar(Enums.ShutterMovement movement);
    void showCopyProgressBar(Enums.ShutterMovement movement);
    void buttonSetBackground(int resId, Enums.ShutterMovement movement);
}

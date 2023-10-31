package com.johndeweydev.awps.viewmodels;

import com.johndeweydev.awps.data.DeviceConnectionParamData;

/**
 * Interface for the view to communicate with the usb serial device
 *
 * @author John Dewey (johndewey02003@gmail.com)
 *
 * */
public interface DefaultViewModelUsbSerial {

  /**
   * Sets the repository serial event callback in the launcher
   * */
  void setLauncherEventHandler();

  /**
   * Connects to the usb serial device
   * @param deviceConnectionParamData the necessary parameters such as baud rate, stop bits and
   * etc to connect to the device
   * */
  String connectToDevice(DeviceConnectionParamData deviceConnectionParamData);

  /**
   * Disconnects from the usb serial device
   * */
  void disconnectFromDevice();

  /**
   * Starts the thread for reading serial output in the usb device
   * */
  void startEventDrivenReadFromDevice();

  /**
   * Stops the thread for reading serial output in the usb device
   * */
  void stopEventDrivenReadFromDevice();
}

package com.johndeweydev.awps.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.johndeweydev.awps.repository.UsbSerialRepository;
import com.johndeweydev.awps.repository.UsbDeviceModel;
import com.johndeweydev.awps.repository.UsbSerialOutputModel;
import com.johndeweydev.awps.usbserial.UsbSerialStatus;

import java.util.ArrayList;

public class UsbSerialViewModel extends ViewModel {

  public UsbSerialRepository usbSerialRepository;
  public MutableLiveData<ArrayList<UsbDeviceModel>> devicesList = new MutableLiveData<>();
  public MutableLiveData<UsbSerialOutputModel> currentMessageFormatted = new MutableLiveData<>();
  public MutableLiveData<UsbSerialOutputModel> currentMessageRaw = new MutableLiveData<>();
  public MutableLiveData<String> currentErrorInput = new MutableLiveData<>();
  public MutableLiveData<String> currentErrorOnNewData = new MutableLiveData<>();

  public UsbSerialViewModel(UsbSerialRepository aUsbSerialRepository) {
    UsbSerialViewModelCallback usbSerialViewModelCallback = new UsbSerialViewModelCallback() {
      @Override
      public void onNewDataRaw(UsbSerialOutputModel usbSerialOutputModel) {
        currentMessageRaw.postValue(usbSerialOutputModel);
      }
      @Override
      public void onNewDataFormatted(UsbSerialOutputModel usbSerialOutputModel) {
        currentMessageFormatted.postValue(usbSerialOutputModel);
      }
      @Override
      public void onErrorNewData(String errorMessageOnNewData) {
        currentErrorOnNewData.postValue(errorMessageOnNewData);
      }
      @Override
      public void onErrorWriting(String dataToWrite) {
        currentErrorInput.postValue(dataToWrite);
      }
    };

    usbSerialRepository = aUsbSerialRepository;
    usbSerialRepository.setUsbSerialViewModelCallback(usbSerialViewModelCallback);
  }

  public int checkAvailableUsbDevices() {
    ArrayList<UsbDeviceModel> devices = usbSerialRepository.discoverDevices();
    devicesList.setValue(devices);
    return devices.size();
  }

  public UsbSerialStatus connectToDevice(
          int baudRate, int dataBits, int stopBits, int parity, int deviceId, int portNum) {
    return usbSerialRepository.connect(baudRate, dataBits, stopBits, parity, deviceId, portNum);
  }

  public void disconnectFromDevice() {
    usbSerialRepository.disconnect();
  }

  public void startEventDrivenReadFromDevice() {
    usbSerialRepository.startReading();
  }

  public void stopEventDrivenReadFromDevice() {
    usbSerialRepository.stopReading();
  }

  public void writeDataToDevice(String data) {
    usbSerialRepository.writeData(data);
  }
}

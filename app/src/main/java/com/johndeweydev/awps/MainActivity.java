package com.johndeweydev.awps;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.johndeweydev.awps.launcher.LauncherSingleton;
import com.johndeweydev.awps.repository.terminalrepository.TerminalRepository;
import com.johndeweydev.awps.viewmodels.terminalviewmodel.TerminalViewModel;
import com.johndeweydev.awps.viewmodels.terminalviewmodel.TerminalViewModelFactory;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
    LauncherSingleton.setUsbManager(usbManager);

    // Initialize the view model to be use for terminal serial input/output
    TerminalRepository terminalRepository = new TerminalRepository();
    TerminalViewModelFactory terminalViewModelFactory = new TerminalViewModelFactory(
            terminalRepository);
    new ViewModelProvider(this, terminalViewModelFactory).get(TerminalViewModel.class);

    setContentView(R.layout.activity_main);
    fragmentChangeListener();
  }

  private void fragmentChangeListener() {
    NavController navController = Navigation.findNavController(
            this, R.id.fragmentActivityMain);
    navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
      String destLabel = (String) destination.getLabel();
      String logText = "MainActivity.fragmentChangeListener: " +
              "Fragment destination changed to " + destLabel;

      Log.d("dev-log", logText);
      countFragmentOnTheStack();
    });
  }

  private void countFragmentOnTheStack() {
    Fragment navHostFragment = getSupportFragmentManager()
            .findFragmentById(R.id.fragmentActivityMain);
    assert navHostFragment != null;
    int backStackEntryCount = navHostFragment.getChildFragmentManager().getBackStackEntryCount();

    String logText = "MainActivity.countFragmentOnTheStack: " +
            "Current fragment back stack count is -> " + backStackEntryCount;

    Log.i("dev-log", logText);
  }
}
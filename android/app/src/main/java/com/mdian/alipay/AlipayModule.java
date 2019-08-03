package com.mdian.alipay;

import com.alipay.sdk.app.PayTask;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import android.util.Log;
import android.app.Activity;
import android.os.AsyncTask;

import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class AlipayModule extends ReactContextBaseJavaModule {

  public AlipayModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "Alipay";
  }

  @Override
  public void initialize() {
  }

  @Override
  public void onCatalystInstanceDestroy() {
  }


  @ReactMethod
  public void pay(String orderInfo, Promise promise) {
    AsyncPayTask task = new AsyncPayTask();
    Log.d("MDian", orderInfo);
    task.orderInfo = orderInfo;
    task.showLoading = false;
    task.promise = promise;
    task.activity = this.getCurrentActivity();
    task.execute();
  }

  private static class AsyncPayTask extends AsyncTask<Void, Void, Void> {
    public String orderInfo;
    public boolean showLoading;
    public Promise promise;
    public Activity activity;

    @Override
    protected Void doInBackground(Void... params) {
      try {
        PayTask alipay = new PayTask(activity);
        Map<String, String> result = alipay.payV2(orderInfo, showLoading);
        Log.d("MDian", result.toString());
        WritableMap map = Arguments.createMap();
        map.putInt("code", Integer.parseInt(result.get("resultStatus")));
        promise.resolve(map);
      } catch (Throwable e) {
        promise.reject(e);
      }
      return null;
    }
  }
}

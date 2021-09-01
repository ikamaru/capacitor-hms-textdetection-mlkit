package com.ikamaru.capacitor.hms.textdetection.mlkit;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "TextDetectionMlKit")
public class TextDetectionMlKitPlugin extends Plugin {

    private static final String TAG = "TextDetectionMlKitP";
    private TextDetectionMlKit implementation = new TextDetectionMlKit();

    @PluginMethod
    public void getTextFromImage(PluginCall call) {
        Log.i(TAG, "getTextFromImage: start");
        Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(call,intent,"pickImageResult");
    }
    @ActivityCallback
    private void pickImageResult(final PluginCall call, ActivityResult result) {
        Intent intent=result.getData();
        if(intent!=null){
            Uri uri=intent.getData();
            implementation.getTextFromImage(bridge.getContext(), uri, new TextDetectionMlKit.OnListener() {
                @Override
                public void onSuccess(JSObject jsonObject) {
                    call.resolve(jsonObject);
                }

                @Override
                public void onFailure(Exception exception) {
                    call.reject(exception.getMessage());
                }
            });
        }else{
            Log.i(TAG, "pickImageResult: Intent Null");
            call.reject("Intent Null");
        }
    }
}

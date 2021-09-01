package com.ikamaru.capacitor.hms.textdetection.mlkit;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;

import java.io.IOException;

public class TextDetectionMlKit {

    private static final String TAG = "TextDetectionMlKit";

    public void getTextFromImage(Context context, Uri uri, OnListener textDetectionMLKitListener) {
        // Method 1: Use default parameter settings to configure the on-device text analyzer. Only Latin-based languages can be recognized.
        MLTextAnalyzer analyzer = MLAnalyzerFactory.getInstance().getLocalTextAnalyzer();
        /*
        // Method 2: Use the customized parameter MLLocalTextSetting to configure the text analyzer on the device.
        MLLocalTextSetting setting = new MLLocalTextSetting.Factory()
            .setOCRMode(MLLocalTextSetting.OCR_DETECT_MODE)
            // Specify languages that can be recognized.
            .setLanguage("zh")
            .create();
        MLTextAnalyzer analyzer = MLAnalyzerFactory.getInstance().getLocalTextAnalyzer(setting);
        */
        // Create an MLFrame object using the bitmap, which is the image data in bitmap format.
        try {
            MLFrame frame = MLFrame.fromFilePath(context,uri);;
            //MLFrame frame = MLFrame.fromBitmap(MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri));
            Task<MLText> task = analyzer.asyncAnalyseFrame(frame);
            task.addOnSuccessListener(new OnSuccessListener<MLText>() {
                @Override
                public void onSuccess(MLText text) {
                    // Processing for successful recognition.

                    Log.i(TAG, "onSuccess: "+text.getStringValue());
                    stopAnalyzer(analyzer);
                    textDetectionMLKitListener.onSuccess(new JSObject().put("result",text.getStringValue()));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    // Processing logic for recognition failure.
                    stopAnalyzer(analyzer);
                    Log.i(TAG, "onFailure: "+e.getMessage());
                    textDetectionMLKitListener.onFailure(e);
                }
            });
        } catch (IOException e) {
            Log.i(TAG, "getTextFromImage: "+e.getMessage());
            stopAnalyzer(analyzer);
            textDetectionMLKitListener.onFailure(e);

        }

    }
    void stopAnalyzer(MLTextAnalyzer analyzer){
        try {
            if (analyzer != null) {
                analyzer.stop();
            }
        } catch (IOException e2) {
            // Exception handling.
        }
    }
    interface OnListener{
        void onSuccess(JSObject jsonObject);
        void onFailure(Exception exception);
    }
}

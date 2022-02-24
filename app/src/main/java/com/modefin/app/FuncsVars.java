package com.modefin.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;

import com.modefin.app.api.ApplicationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@lombok.extern.java.Log
public class FuncsVars {

    public static void loadProperties(int resourceId, Properties properties) {
        InputStream rawResource = null;
        try {
            Resources resources = ApplicationProperties.getContext().getResources();
            rawResource = resources.openRawResource(resourceId);
            properties.load(rawResource);
        } catch (Resources.NotFoundException e) {
            log.info("loadProperties, Did not find raw resource: " + e);
            log.info("loadProperties, Did not find raw resource: " + resourceId);
        } catch (IOException e) {
            log.info("loadProperties, Failed to open property file " + e);
        } finally {
            if (rawResource != null)
                try {
                    rawResource.close();
                } catch (IOException e) {
                    log.info("Error Closing Inputstream in loadProperties," + "Failed to close " + e);
                }
        }
    }

    public static ProgressDialog showDialog(Activity activity) {
        ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Loading Please Wait...");
        pd.setCancelable(true);
        return pd;
    }

}

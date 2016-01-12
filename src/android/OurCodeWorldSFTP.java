package com.phonegap.plugins.ourcodeworldsftp;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import com.jcraft.jsch.*;

public class ourcodeworldsftp extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("connect")) {

            String name = data.getString(0);
            String message = "SFTP CONNECTED:			" + name;
            callbackContext.success(message);

            return true;

        } else {
            
            return false;

        }
    }
}

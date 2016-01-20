package com.ourcodeworld.plugins.sftp;

import org.apache.cordova.*;
import com.jcraft.jsch.SftpProgressMonitor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class progressMonitor implements SftpProgressMonitor{
    private long max                = 0;
    private long count              = 0;
    private long percent            = 0;
    private CallbackContext callbacks = null;
    
    
    public progressMonitor(CallbackContext callbackContext) {
        this.callbacks  = callbackContext;
    }

    public void init(int op, java.lang.String src, java.lang.String dest, long max) {
        this.max = max;
        try{
            JSONObject item = new JSONObject();
            item.put("starting",true);
            item.put("from",src);
            item.put("to",dest);
            item.put("filesize",max);
            PluginResult result = new PluginResult(PluginResult.Status.OK, item.toString());
            result.setKeepCallback(true);
            callbacks.sendPluginResult(result);
        } catch (JSONException e) {
            callbacks.error(e.getMessage().toString());
            e.printStackTrace();
        }
    }

    public boolean count(long bytes){
        this.count += bytes;
        long percentNow = this.count*100/max;
        if(percentNow>this.percent){
            this.percent = percentNow;
            try{
                JSONObject item = new JSONObject();
                item.put("finished",false);
                item.put("progress",this.percent);
                item.put("filesizebytes",max);
                item.put("bytesprogress",this.count);
                PluginResult result = new PluginResult(PluginResult.Status.OK, item.toString());
                result.setKeepCallback(true);
                callbacks.sendPluginResult(result);
            } catch (JSONException e) {
                callbacks.error(e.getMessage().toString());
                e.printStackTrace();
            }
        }
        return(true);
    }

    public void end(){
        try{
            JSONObject item = new JSONObject();
            item.put("finished",true);
            item.put("progress",this.percent);
            item.put("filesizebytes",max);
            item.put("bytesprogress",this.count);

            PluginResult result = new PluginResult(PluginResult.Status.OK, item.toString());
            result.setKeepCallback(true);
            callbacks.sendPluginResult(result);
        } catch (JSONException e) {
            callbacks.error(e.getMessage().toString());
            e.printStackTrace();
        }
    }
}
package com.ourcodeworld.plugins;

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
        //Log.d("SFTP Plugin - JJDLTC", "Action Start, From: "+src+" <=> To: "+dest);
        ///Log.d("SFTP Plugin - JJDLTC", "Action Start, Size: "+max+" <=> Opt: "+op);
        //this.jsEvent("SFTPActionStart", "{from:'"+src+"',to:'"+dest+"',size:'"+max+"'}");
    }

    public boolean count(long bytes){
        this.count += bytes;
        long percentNow = this.count*100/max;
        if(percentNow>this.percent){
            this.percent = percentNow;
            //this.jsEvent("SFTPActionProgress", "{percent:'"+percent+"'}");
           // Log.d("SFTP Plugin - JJDLTC", "Action Progress: "+this.percent+"%");
            try{
                JSONObject item = new JSONObject();
                item.put("isOver",false);
                item.put("progress",this.percent);
                item.put("totalbytes",max);
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

            PluginResult result = new PluginResult(PluginResult.Status.OK, item.toString());
            result.setKeepCallback(true);
            callbacks.sendPluginResult(result);
        } catch (JSONException e) {
            callbacks.error(e.getMessage().toString());
            e.printStackTrace();
        }
    }
}
package com.ourcodeworld.plugins;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.jcraft.jsch.*;

public class OurCodeWorldSFTP extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("connect")) {
			 
			try {
				JSONObject arg_object = data.getJSONObject(0);
				
				String hostname = arg_object.getString("host");
				String login =  arg_object.getString("username");
				String password =  arg_object.getString("password");
				String directory =  arg_object.getString("path");
		 
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
		 
				JSch ssh = new JSch();
				Session session = ssh.getSession(login, hostname, 22);
				session.setConfig(config);
				session.setPassword(password);
				session.connect();
				Channel channel = session.openChannel("sftp");
				channel.connect();
		 
				ChannelSftp sftp = (ChannelSftp) channel;
				sftp.cd(directory);
		/*	java.util.Vector files = sftp.ls("*");
				System.out.printf("Found %d files in dir %s%n", files.size(), directory);
		 
				for (ChannelSftp.LsEntry file : files) {
					if (file.getAttrs().isDir()) {
						continue;
					}
					//System.out.printf("Reading file : %s%n", file.getFilename());
					BufferedReader bis = new BufferedReader(new InputStreamReader(sftp.get(file.getFilename())));
					String line = null;
					while ((line = bis.readLine()) != null) {
						//System.out.println(line);
						String name = line.getString(0);
						String message = name;
						callbackContext.success(message);
					}
					bis.close();
				}
				*/
				java.util.Vector filelist = sftp.ls(directory); 
				
				for(int i=0; i<filelist.size();i++){ 
					callbackContext.success(filelist.get(i).toString());
				} 
		 
				channel.disconnect();
				session.disconnect();
			} catch (JSchException e) {
				callbackContext.error("jsch exception");
				e.printStackTrace();  
			} catch (SftpException e) {
				callbackContext.error("SftpException exception");
				e.printStackTrace();
			}
			
			
			
			
            //String name = data.getString(0);
            //String message = "SFTP CONNECTED:			" + name;
            //callbackContext.success(message);

            return true;

        } else {
            
            return false;

        }
    }
}

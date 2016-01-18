package com.ourcodeworld.plugins;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.jcraft.jsch.*;

public class OurCodeWorldSFTP extends CordovaPlugin {
    private static final String ACTION_LIST = "list";

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (ACTION_LIST.equals(action)) {
            JSONObject arg_object = data.getJSONObject(0);
            final String hostname = arg_object.getString("host");
            final String login =  arg_object.getString("username");
            final String password =  arg_object.getString("password");
            final String directory =  arg_object.getString("path");
            final CallbackContext callbacks = callbackContext;

            cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                            try {
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
                                //callbacks.success(directory);
                                JSONArray contenedor = new JSONArray();
                                java.util.Vector<ChannelSftp.LsEntry> list = sftp.ls("*.*");
                                for(ChannelSftp.LsEntry entry : list) {
                                    JSONObject item = new JSONObject();
                                    item.put("path", entry.getFileName().toString());
                                    item.put("filepath", directory + "FilenameFicticio");

                                    contenedor.put(item);
                                    //channelSftp.get(entry.getFileName(), destinationPath + entry.getFileName());
                                }

/** Funcionando "perfecto"
                                java.util.Vector filelist = sftp.ls(directory);
                                JSONArray contenedor = new JSONArray();
                                for(int i=0; i<filelist.size();i++){
                                    JSONObject item = new JSONObject();
                                    item.put("path", filelist.get(i).toString());
                                    item.put("filepath", directory + "FilenameFicticio");

                                    contenedor.put(item);

                                    // Grap and get the file by WORKING_DIR/filelist.get(i).toString();
                                    // Save it to your local directory with its original name. 

                                }
*/
                                

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
 

                                channel.disconnect();
                                session.disconnect();

                                callbacks.success(contenedor.toString());
                        } catch (JSchException e) {
                                callbacks.error(e.getMessage().toString());
                                e.printStackTrace();  
                        } catch (SftpException e) {
                                callbacks.error(e.getMessage().toString());
                                e.printStackTrace();
                        } catch (JSONException e) {
                                callbacks.error(e.getMessage().toString());
                                e.printStackTrace();
                        }
                    }
            });

            return true;

        } else {
            
            return false;

        }
    }
}

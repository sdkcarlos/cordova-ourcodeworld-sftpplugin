/*global cordova, module*/

module.exports = {
    createSFTPClient: function(){
        //This variable will be private
        var _settings = {
            host:null,
            username:null,
            password:null,
            path:'/root',
            port:"22",
            identity: null,
            known_hosts:null
        };
        
        return {
            setCredentials: function(host,username,password,port){
                if(typeof(host) === "undefined"){
                    _settings.host = null;
                }else{
                    _settings.host = host;	
                }

                if(typeof(username) === "undefined"){
                    _settings.username = null;
                }else{
                    _settings.username = username;	
                }

                if(typeof(password) === "undefined"){
                    _settings.password = null;
                }else{
                    _settings.password = password;
                }

                if(typeof(port) === "undefined"){
                    _settings.port = "22";
                }else{
                    _settings.port = port.toString();	
                }
            },
            /**
             * Set the global path of the remote connection
             * 
             * @default /root
             * @param {type} path
             * @returns {undefined}
             */
            setPath: function(path){
                if(typeof(path) === "undefined"){
                    _settings.path = '/root';
                }else{
                    _settings.path = path;	
                }
            },
            getPath: function(){
                return _settings.path;
            },
            /**
             * Returns a json object with the information of all folders and files of the global path
             * 
             * @param {type} success
             * @param {type} error
             * @returns {undefined}
             */
            list: function(success,error){
                var datos = _settings;
                cordova.exec(function(data){
                    success(JSON.parse(data));
                }, function(err){
                    error(err);
                }, "OurCodeWorldSFTP", "list", [datos]);
            },
            /**
             * List the parent folder of the global path
             * 
             * @param {type} success
             * @param {type} error
             * @returns {module.exports.createSFTPClient.ourcodeworldsftpAnonym$0.listParent.path|String}
             */
            listParent: function(success,error){
                var parentPath = (_settings.path).split("/").filter(function(n){ return n != undefined });
                var path = "LAST_FOLDER";
                parentPath.pop();
                
                if(parentPath.length == 1){
                    return path;
                }else{
                    path = parentPath.join("/");
                }
                
                setPath(path);
                list(success,error);
                
                return path;
            },
            downloadFile: function(sourcePath,destinationPath,callbacks){
                var datos = _settings;
                
                datos.filesource = sourcePath;
                datos.filedestination = destinationPath;
                
                cordova.exec(function(data){
                    try{
                        callbacks.success(JSON.parse(data));
                    }catch(e){
                        callbacks.error(data);
                    }
                }, function(err){
                    callbacks.error(err);
                }, "OurCodeWorldSFTP", "download", [datos]);
            },
            uploadFile: function(sourcePath,destinationPath,callbacks){
                var datos = _settings;
                
                datos.filesource = sourcePath;
                datos.filedestination = destinationPath;
                
                cordova.exec(function(data){
                    try{
                        callbacks.success(JSON.parse(data));
                    }catch(e){
                        callbacks.error(data);
                    }
                }, function(err){
                    callbacks.error(err);
                }, "OurCodeWorldSFTP", "upload", [datos]);
            },
            removeFile: function(remotePath,callbacks){
                var datos = _settings;
                
                datos.remotepath = remotePath;
                
                cordova.exec(function(data){
                    callbacks.success(data);
                }, function(err){
                    callbacks.error(err);
                }, "OurCodeWorldSFTP", "delete", [datos]);
            },
            /**
             * If you need to add a private key to the connection use the addIdentity method
             * null to remove a identity and string to give a path
             * 
             * @param {type} filepath
             * @returns {undefined}
             */
            setIdentity: function(filepath){
                _settings.identity = filepath;
            },
            setKnownHosts: function(filepath){
                _settings.known_hosts = filepath;
            }
        };
    }
};

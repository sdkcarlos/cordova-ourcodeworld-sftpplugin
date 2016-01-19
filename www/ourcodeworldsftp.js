/*global cordova, module*/

module.exports = {
    createSFTPClient: function(){
        return {
            _settings:{
                host:null,
                username:null,
                password:null,
                path:'/root',
                port:"22"
            },
            setCredentials: function(host,username,password,port){
                if(typeof(host) === "undefined"){
                    this._settings.host = null;
                }else{
                    this._settings.host = host;	
                }

                if(typeof(username) === "undefined"){
                    this._settings.username = null;
                }else{
                    this._settings.username = username;	
                }

                if(typeof(password) === "undefined"){
                    this._settings.password = null;
                }else{
                    this._settings.password = password;
                }

                if(typeof(port) === "undefined"){
                    this._settings.port = "22";
                }else{
                    this._settings.port = port.toString();	
                }
            },
            setPath: function(path){
                if(typeof(path) === "undefined"){
                    this._settings.path = '/root';
                }else{
                    this._settings.path = path;	
                }
            },
            getPath: function(){
                return this._settings.path;
            },
            list: function(success,error){
                var datos = this._settings;
                cordova.exec(function(data){
                    success(JSON.parse(data));
                }, function(err){
                    error(err);
                }, "OurCodeWorldSFTP", "list", [datos]);
            },
            listParent: function(success,error){
                var parentPath = (this._settings.path).split("/").filter(function(n){ return n != undefined });
                var path = "LAST_FOLDER";
                parentPath.pop();
                
                if(parentPath.length == 1){
                    return path;
                }else{
                    path = parentPath.join("/");
                }
                
                this.setPath(path);
                this.list(success,error);
                
                return path;
            },
            downloadFile: function(sourcePath,destinationPath,callbacks){
                var datos = this._settings;
                
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
                var datos = this._settings;
                
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
                var datos = this._settings;
                
                datos.remotepath = remotePath;
                
                cordova.exec(function(data){
                    callbacks.success(data);
                }, function(err){
                    callbacks.error(err);
                }, "OurCodeWorldSFTP", "delete", [datos]);
            }
        };
    }
};

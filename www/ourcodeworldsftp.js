/*global cordova, module*/

module.exports = {
	_settings:{
		host:null,
		username:null,
		password:null,
		path:'/root'
	},
	setCredentials: function(host,username,password){
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
	},
	setPath: function(path){
            if(typeof(path) === "undefined"){
                this._settings.path = '/root';
            }else{
                this._settings.path = path;	
            }
	},
    connect: function (successCallback, errorCallback) {
		var datos = this._settings;
		cordova.exec(successCallback, errorCallback, "OurCodeWorldSFTP", "connect", [datos]);
        //cordova.exec(successCallback, errorCallback, "OurCodeWorldSFTP", "connect", [name]);
    }
};

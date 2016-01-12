/*global cordova, module*/

module.exports = {
    connect: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "OurCodeWorldSFTP", "connect", [name]);
    }
};

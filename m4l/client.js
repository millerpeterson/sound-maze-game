var net = require('net');

var M4LClient = function(serverIP, serverPort) {
  var that = this;
  if (typeof(serverIP) === 'undefined') {
    serverIP = '127.0.0.1';
  }
  if (typeof(serverPort) === 'undefined') {
    serverPort = 6000;
  }
  this.serverIP = serverIP;
  this.serverPort = serverPort;
  var client = new net.Socket();
  client.setNoDelay(true);
  client.on('error', function(error) {
    console.log(error);
  });
  this.client = client;
}

M4LClient.prototype.sendMsg = function(message) {
  var c = this.client;
  this.client.connect(this.serverPort, this.serverIP, function() {
    c.write(message, 'utf8', function() {
      c.end();
    });
  });
}

var client = M4LClient;
module.exports.client = client;
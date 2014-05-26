var dgram = require('dgram');

var M4LClient = function(serverAddr, serverPort) {
  var that = this;
  if (typeof(serverAddr) === 'undefined') {
    serverAddr = 'localhost';
  }
  if (typeof(serverPort) === 'undefined') {
    serverPort = 6000;
  }
  this.serverAddr = serverAddr;
  this.serverPort = serverPort;
  this.udp = new dgram.createSocket("udp4");
  this.udp.on('error', function(err) {
    console.log('UDP Error: ' + err.stack);
  });
};

M4LClient.prototype.sendMsg = function(message) {
  var buff = new Buffer(message);
  this.udp.send(buff, 0, buff.length, this.serverPort, this.serverAddr);
};

var client = M4LClient;
module.exports.client = client;
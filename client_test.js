var m4l = require('./m4l/client');
var midiInt = require('../phi-launchpad-js/node_midi_interface');
var lPad = require('../phi-launchpad-js/launchpad');
var util = require('util');

var client = new m4l.client();
var mInt = new midiInt.midi();
var pad = new lPad.launchpad();
pad.init(mInt);
pad.on('press', function(row, col) {
  var message = util.format("press %d %d", row, col);
  console.log(message);
  client.sendMsg(message);
});
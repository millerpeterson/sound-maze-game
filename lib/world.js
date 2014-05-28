var _ = require('underscore');
var h = require('helpers')
var lPad = require('../phi-launchpad-js/launchpad');
var midiInt = require('../phi-launchpad-js/node_midi_interface');
var gen = require('./maze_gen');

var World = function() {
  this.areas = [];
};

var mInt = new midiInt.midi();
var pad = new lPad.launchpad();
pad.init(mInt);
pad.resetDevice();

var testArea = new Area();
testArea.init(9, 9);
// console.log(testArea.rooms[4][4].neighbors());
var mGen = new gen.mazeGen(testArea);
mGen.generate('RecursiveBacktrack', {
  'start': testArea.rooms[0][0]
});

// var gen = new DepthFirstGen(testArea);
// gen.visit(0,0);

//testArea.draw(pad);
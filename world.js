var _ = require('underscore');
var h = require('helpers')
var lPad = require('../phi-launchpad-js/launchpad');
var midiInt = require('../phi-launchpad-js/node_midi_interface');
var gen = require('./maze_gen');

var World = function() {
  this.areas = [];
};

var Area = function() { };

Area.prototype.init = function(rows, columns) {
  this.rooms = new Array(rows);
  for (var r = 0; r < rows; r++)  {
    this.rooms[r] = new Array(columns);
    for (var c = 0; c < columns; c++) {
      console.log("r " + r + "c " + c);
      this.rooms[r][c] = new Room(r, c, this);
    }
  }
}

Area.prototype.rows = function() {
  return this.rooms.length;
}

Area.prototype.cols = function() {
  return this.rooms[0].length;
}

Area.prototype.draw = function(lPad) {
  var room, numWalls;
  for (var r = 0; r < this.rooms.length; r++) {
    for (var c = 0; c < this.rooms[r].length; c++) {
      room = this.rooms[r][c];
      numWalls = room.walls.length;
      lPad.setLed(r, c, [numWalls, numWalls]);
    }
  }
}

var Room = function(row, col, area) {
  this.area = area;
  this.walls = [ // up, right, down, left
    [-1, 0], [0, 1], [1, 0], [0, -1]
  ];

  this.pos = [row, col]; // row, column
  this.passages = [];
};

Room.prototype.neighbors = function() {
  // Generate all possible vectors for rooms.
  var ns = _.map(this.walls, _.bind(function(w) {
    return [this.pos[0] + w[0], this.pos[1] + w[1]];
  }, this));
  // Trim rooms that would be off the board.
  ns = _.reject(ns, _.bind(function(n) {
    return (n[0] < 0) || (n[0] >= this.area.rows()) ||
    (n[1] < 0) || (n[1] >= this.area.cols());
  }, this));
  // Replace positions with room objects.
  return _.map(ns, _.bind(function(n) {
    return this.area.rooms[n[0]][n[1]];
  }, this));
};

Room.prototype.createPassage = function(otherRoom) {
  console.log(this.pos + " -> " + otherRoom.pos);
  this.passages.push(otherRoom.pos);
  this.passages = _.uniq(this.passages);
  otherRoom.passages.push(this.pos);
  otherRoom.passages = _.uniq(otherRoom.passages);
};

Room.prototype.passageTo = function(otherRoom) {
  return !!_.find(this.passages, function(r) {
    h.posEq(r.pos, otherRoom.pos);
  });
}

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
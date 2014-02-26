var lPad = require('../phi-launchpad-js/launchpad');
var midiInt = require('../phi-launchpad-js/node_midi_interface');

var World = function() {
  this.areas = new Array();
};

var Area = function() { };

Area.prototype.init = function(rows, columns) {
  this.rooms = new Array(rows);
  for (var r = 0; r < this.rooms.length; r++)  {
    this.rooms[r] = new Array(columns);
    for (var c = 0; c < this.rooms[r].length; c++) {
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
  // list of vectors
  this.walls = [ // up, right, down, left
    [-1, 0], [0, 1], [1, 0], [0, -1]
  ];
  this.pos = [row, col]; // row, column
  this.area = area;
};

Room.prototype.neighbors = function() {
  var n = _.map(this.walls, _.bind(function(wall) {
    return [this.pos[0] + wall[0], this.pos[1] + wall[1]];
  }, this));
  return _.reject(n, _.bind(function(neighbor) {
    (neighbor[0] < 0) || (neighbor[0] >= this.area.rows()) ||
    (neighbor[1] < 0) || (neighbor[1] >= this.area.cols());
  }, this));
}

var mInt = new midiInt.midi();
var pad = new lPad.launchpad();
pad.init(mInt);
pad.resetDevice();

var testArea = new Area();
testArea.init(9, 9);
console.log(testArea.rooms[4][4].neighbors());
//testArea.draw(pad);
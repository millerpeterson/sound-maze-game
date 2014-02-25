//var lPad = require('../phi-launchpad-js/launchpad')

var World = function() {
  this.areas = new Array();
};

var Area = function(rows, columns) {
};

Area.prototype.init = function(rows, columns) {
  this.rooms = new Array(rows);
  for (var r = 0; r < this.rooms.length; r++)  {
    this.rooms[r] = new Array(columns);
    for (var c = 0; c < this.rooms[r].length; c++) {
      this.rooms[r][c] = new Room(r, c);
    }
  }
}

var Room = function(row, col) {
  // list of vectors
  this.walls = [ // up, right, down, left
    [-1, 0], [0, 1], [1, 0], [0, -1]
  ];
  this.pos = [row, col]; // row, column
};

var testArea = new Area(9, 9);
testArea.init();
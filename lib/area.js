var _ = require('underscore');
var Room = require('./room').room;
var func = require('./util/func');

var Area = function() {};

Area.method('init', function(rows, columns) {
  this.rooms = new Array(rows);
  for (var r = 0; r < rows; r++)  {
    this.rooms[r] = new Array(columns);
    for (var c = 0; c < columns; c++) {
      this.rooms[r][c] = new Room([r, c], this);
    }
  }
});

Area.method('rows', function() {
  return this.rooms.length;
});

Area.method('cols', function() {
  return this.rooms[0].length;
});

Area.method('getRoom', function(pos) {
  var v = _.isArray(pos) ?
    pos : pos.val();
  return this.rooms[v[0]][v[1]];
});

var area = Area;
module.exports.area = area;

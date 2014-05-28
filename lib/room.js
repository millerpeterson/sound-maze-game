var _ = require('underscore');
var func = require('./util/func');
var $V = require('./vec').vec;

// Possible directions for walls.
var wallVecs = [
  [-1, 0], [0, 1], [1, 0], [0, -1]
];

var Room = function(pos, area) {
  this.area = area;
  this.walls = _.map(wallVecs,
    function(v) { return new $V(v); }
  );
  this.pos = new $V(pos);
  this.passages = [];
};

Room.method('neighbors', function() {
  // Generate all possible vectors for rooms.
  var n1, n2;
  var vecs = _.map(this.walls, _.bind(function(w) {
    return this.pos.add(w);
  }, this));
  // Trim rooms that would be off the board.
  vecs = _.reject(vecs, _.bind(function(nVec) {
    n1 = nVec.val();
    return (n1[0] < 0) || (n1[0] >= this.area.rows()) ||
    (n1[1] < 0) || (n1[1] >= this.area.cols());
  }, this));
  // Replace positions with room objects.
  return _.map(vecs, _.bind(function(nVec) {
    n2 = nVec.val();
    return this.area.getRoom(nVec);
  }, this));
});

Room.method('createPassage', function(otherRoom) {
  this.passages.push(otherRoom);
  this.passages = _.uniq(this.passages);
  otherRoom.passages.push(this);
  otherRoom.passages = _.uniq(otherRoom.passages);
});

Room.method('passageTo', function(otherRoom) {
  return !!_.find(this.passages, function(r) {
    return r === otherRoom;
  });
});

var room = Room;
module.exports.room = room;
module.exports.wallVecs = wallVecs;
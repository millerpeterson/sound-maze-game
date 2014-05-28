var assert = require('assert');
var _ = require('underscore');
var area = require('../lib/area');
var room = require('../lib/room');
var Room = room.room;
var Area = area.area;
var wallVecs = room.wallVecs;

var testArea;

suite('room', function() {

  beforeEach(function() {
    testArea = new Area();
    testArea.init(10, 10);
  });

  test('has a position', function() {
    var r = new Room([7, 7]);
    assert(r.pos.isEq([7, 7]));
  });

  test('has four walls', function() {
    var r = new Room([5, 7]);
    assert.equal(r.walls.length, 4);
    _.each(wallVecs, function(targetWallPos) {
      assert(!!_.find(r.walls, function(w) {
        return w.isEq(targetWallPos);
      }));
    });
  });

  test('has four neighbors', function() {
    var r = new Room([3, 2], testArea);
    var expectedNeighbors = [
      [2, 2], [3, 3], [3, 1], [4, 2]
    ];
    var neighbors = r.neighbors();
    assert.equal(neighbors.length, expectedNeighbors.length);
    _.each(expectedNeighbors, function(targetNeighbor) {
      assert(!!_.find(neighbors, function(n) {
        return n.pos.isEq(targetNeighbor);
      }));
    });
  });

});
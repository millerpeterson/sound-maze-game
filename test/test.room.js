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
    var r = new Room([7, 7], testArea);
    assert(r.pos.isEq([7, 7]));
  });

  test('has four walls', function() {
    var r = new Room([5, 7], testArea);
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

  test('can create passages', function() {
    var r1 = new Room([7, 9], testArea);
    var r2 = new Room([8, 9], testArea);
    var r3 = new Room([7, 10], testArea);
    assert(!r1.passageTo(r2));
    assert(!r2.passageTo(r1));
    r1.createPassage(r2);
    assert(r1.passageTo(r2));
    assert(r2.passageTo(r1));
    assert(!r1.passageTo(r3));
    assert(!r3.passageTo(r1));
  });

  test('createPassage makes neighbors accessible', function() {
    var r1 = new Room([2, 4], testArea);
    var r2 = new Room([3, 4], testArea);
    var r3 = new Room([1, 4], testArea);
    assert.equal(r1.accessibleNeighbors().length, 0);
    r1.createPassage(r2);
    r1.createPassage(r3);
    assert.equal(r1.accessibleNeighbors().length, 2);
  });

});
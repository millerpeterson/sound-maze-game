var _ = require('underscore');
var h = require('helpers');

var MazeGen = function(area) {
  this.area = area;
}

MazeGen.prototype.generate = function(algorithm, params) {
  var generator = null;
  if (algorithm == 'RecursiveBacktrack') {
    generator = new RecursiveBacktrack();
  }
  if (generator) {
    return generator.generate(this.area, params);
  }
}

var mazeGen = MazeGen;
module.exports.mazeGen = mazeGen;

var RecursiveBacktrack = function() {
  this.visited = new Array();
}

RecursiveBacktrack.prototype.generate = function(area, params) {
  this.area = area;
  this.startRoom = params['start'];
  this.visit(this.startRoom);
}

RecursiveBacktrack.prototype.visit = function(room) {
  var allNeightbors
  var unvisitedNeighbors;
  var nextRoom;
  this.visited.push(room);
  allNeightbors = room.neighbors();
  do {
    unvisitedNeighbors = _.reject(allNeightbors, _.bind(function(room) {
      return this.wasVisited(room);
    }, this));
    if (unvisitedNeighbors.length > 0) {
      nextRoom = _.sample(unvisitedNeighbors)
      room.createPassage(nextRoom);
      this.visit(nextRoom);
    }
  } while (unvisitedNeighbors.length > 0)
}

RecursiveBacktrack.prototype.wasVisited = function(room) {
  var target = room;
  return _.find(this.visited, function(r) {
    return !!h.posEq(target.pos, r.pos);
  });
}

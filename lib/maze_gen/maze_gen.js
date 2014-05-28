var _ = require('underscore');
var h = require('helpers');

var MazeGen = function(area) {
  this.area = area;
}

MazeGen.prototype.generate = function(algorithm, params) {
  var generator = null;
  if (algorithm === 'RecursiveBacktrack') {
    generator = new RecursiveBacktrack();
  }
  if (generator) {
    return generator.generate(this.area, params);
  }
}

var mazeGen = MazeGen;
module.exports.mazeGen = mazeGen;

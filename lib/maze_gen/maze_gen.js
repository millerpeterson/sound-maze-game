require('../util/func')
var _ = require('underscore');
var backtrack = require('./recursive_backtrack');
var RecursiveBacktrack = backtrack.recursiveBacktrack;

var MazeGen = function(area) {
  this.area = area;
}

MazeGen.method('generate', function(algorithm, params) {
  var generator = null;
  if (algorithm === 'RecursiveBacktrack') {
    generator = new RecursiveBacktrack();
  }
  if (generator) {
    return generator.generate(this.area, params);
  }
});

var mazeGen = MazeGen;
module.exports.mazeGen = mazeGen;

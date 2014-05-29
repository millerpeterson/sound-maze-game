var assert = require('assert');
var mazeGen = require('../lib/maze_gen/maze_gen');
var area = require('../lib/area');
var Area = area.area;
var Gen = mazeGen.mazeGen;

var testArea;

suite('maze gen', function() {

  beforeEach(function() {
    testArea = new Area();
    testArea.init(10, 10);
  });

  test('recursive backtrack', function() {
    var gen = new Gen(testArea);
    var startRoom = testArea.getRoom([0, 4]);
    gen.generate('RecursiveBacktrack', {
      'start': startRoom
    });
  });

});
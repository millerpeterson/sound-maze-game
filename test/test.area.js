var assert = require('assert');
var $V = require('../lib/vec').vec;
var area = require('../lib/area');
var Area = area.area;

suite('area', function() {

  test('rows and cols', function() {
    var a = new Area();
    a.init(15, 24);
    assert.equal(a.rows(), 15);
    assert.equal(a.cols(), 24);
  });

  test('getting rooms', function() {
    var a = new Area();
    var r1, r2;
    a.init(43, 22);
    r1 = a.getRoom([32, 2]);
    r2 = a.getRoom(new $V([5, 12]));
    assert(r1.pos.isEq([32, 2]));
    assert(r2.pos.isEq([5, 12]));
  });

});
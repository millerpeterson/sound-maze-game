var assert = require('assert');
var $V = require('../lib/vec').vec;

suite('vec', function() {

  test('initialization, equality', function() {
    var v = new $V([32, -4]);
    var val = v.val();
    assert(v.isEq([32, -4]));
    assert(v.isEq(new $V([32, -4])));
    assert.equal(val[0], 32);
    assert.equal(val[1], -4);
  });

  test('defaults to [0,0]', function() {
    var v = new $V();
    assert(v.isEq([0, 0]));
    assert(v.isEq(new $V([0, 0])));
  });

  test('addition', function() {
    var v = new $V([2, 15]);
    var sum1 = v.add([6, 3]);
    var sum2 = v.add(new $V([-4, 11]));
    assert(sum1.isEq([8, 18]));
    assert(sum2.isEq([-2, 26]));
  });

  test('subtraction', function() {
    var v = new $V([7, 3]);
    var diff1 = v.sub([2, 11]);
    var diff2 = v.sub([11, -9]);
    assert(diff1.isEq([5, -8]));
    assert(diff2.isEq([-4, 12]));
  });

});
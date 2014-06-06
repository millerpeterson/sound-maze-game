var assert = require('assert')
  , sinon = require('sinon')
  , $V = require('../lib/vec').vec
  , Room = require('../lib/room').room
  , Area = require('../lib/area').area
  , LcdRenderer = require('../lib/m4l/max_lcd_renderer').renderer;

var testArea
  , renderer
  , dummyM4l
  , m4lSpy;

suite('max lcd renderer', function() {

  beforeEach(function() {
    testArea = new Area();
    testArea.init(10, 10);

    renderer = new LcdRenderer();
    dummyM4l = { 'sendMsg': function() {} };
    renderer.init(dummyM4l, {
      'lcdWidth': 100,
      'lcdHeight': 100,
      'numRows': 10,
      'numCols': 10,
      'doorWidth': 4,
      'doorHeight': 4
    });

    sinon.spy(dummyM4l, 'sendMsg');
  });

  test('offset', function() {
    var r1 = new Room([2, 3], testArea);
    assert(renderer.roomOffset(r1).isEq([30, 20]));
  });

  test('drawRect', function() {
    renderer.drawRect(
      new $V([10, 10]),
      new $V([100, 100])
    );
    assert(dummyM4l.sendMsg.calledOnce);
    assert(dummyM4l.sendMsg.calledWith("paintrect 10 10 100 100 0 0 0"));
  });

});
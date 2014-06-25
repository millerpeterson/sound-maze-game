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
      new $V([50, 50])
    );
    assert(dummyM4l.sendMsg.calledOnce);
    assert(dummyM4l.sendMsg.calledWith("paintrect 10 10 50 50"));
  });

  test('renderInterior', function() {
    var r1 = new Room([3, 6], testArea)
      , call;
    sinon.spy(renderer, 'drawRect');
    renderer.renderInterior(r1);
    assert(renderer.drawRect.calledOnce);
    call = renderer.drawRect.firstCall;
    //console.log(call.args[0].val());
    assert(call.args[0].isEq([63, 33]));
  });

  test('renderRoom', function() {
    var r1 = new Room([3, 6], testArea);
    var r2 = new Room([2, 6], testArea);
    var r3 = new Room([2, 5], testArea);

    sinon.spy(renderer, 'renderInterior');
    sinon.spy(renderer, 'renderPassage');

    // No accessible neighbors.
    renderer.renderRoom(r1);
    assert(!renderer.renderInterior.called);

    // One accessible neighbor.
    r1.createPassage(r2);
    r1.createPassage(r3);
    renderer.renderRoom(r1);
    assert.equal(r1.accessibleNeighbors().length, 2);
    assert(renderer.renderInterior.calledOnce);
    assert.equal(renderer.renderPassage.callCount, 2);
  });

});
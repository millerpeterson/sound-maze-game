var $V = require('../vec').vec;
var util = require('util');
var _ = require('underscore');

var LcdRenderer = function() {}

LcdRenderer.method('init', function(m4lClient, params) {
  this.client = m4lClient;
  this.lcdWidth = params['lcdWidth'];
  this.lcdHeight = params['lcdHeight'];
  this.numRows = params['numRows'];
  this.numCols = params['numCols'];
  this.doorWidth = params['doorWidth'];
  this.doorHeight = params['doorHeight'];
  this.roomWidth = this.lcdWidth / this.numCols;
  this.roomHeight = this.lcdHeight / this.numRows;
});

LcdRenderer.method('drawRect', function(ulVec, brVec) {
  var diff,
    width,
    height;
  diff = brVec.sub(ulVec).val();
  width = diff[0];
  height = diff[1];
  this.client.sendMsg(util.format(
    'paintrect %d %d %d %d',
    ulVec.val()[0], ulVec.val()[1],
    brVec.val()[0], brVec.val()[1]
  ));
});

LcdRenderer.method('lineSeg', function(sVec, eVec) {
  this.client.sendMsg(util.format(
    'lineSeg %d %d %d %d',
    sVec.val()[0], sVec.val()[1],
    eVec.val()[0], eVec.val()[1]
  ));
});

LcdRenderer.method('renderRoom', function(room) {
  var wallVec,
    that;
  if (room.accessibleNeighbors().length > 0) {
    this.renderInterior(room);
  }
  that = this;
  _.each(room.accessibleNeighbors(), function(n) {
    that.renderPassage(room, n);
  });
});

LcdRenderer.method('renderInterior', function(room) {
  var offset = this.roomOffset(room)
    , verticalPad = (this.roomHeight / 2) - (this.doorHeight / 2)
    , horizontalPad = (this.roomWidth / 2) - (this.doorWidth / 2);
  this.drawRect(
    offset.add([horizontalPad, verticalPad]),
    offset.add([
      this.roomWidth - horizontalPad,
      this.roomHeight - verticalPad
    ])
  );
});

LcdRenderer.method('roomOffset', function(room) {
  return new $V([
    room.pos.val()[1] * this.roomWidth,
    room.pos.val()[0] * this.roomHeight
  ]);
});

LcdRenderer.method('renderPassage', function(r1, r2) {
  var centerOffset,
    startPos,
    endPos;
  centerOffset = new $V([
    this.roomWidth / 2,
    this.roomHeight / 2
  ]);
  startPos = this.roomOffset(r1).add(centerOffset);
  endPos = this.roomOffset(r2).add(centerOffset);
  this.lineSeg(startPos, endPos);
});

var renderer = LcdRenderer;
module.exports.renderer = renderer;
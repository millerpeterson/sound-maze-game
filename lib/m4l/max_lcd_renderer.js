var $V = require('../vec').vec;
var util = require('util');

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
  console.log(util.format(
    'paintrect %d %d %d %d 0 0 0',
    ulVec.val()[0], ulVec.val()[1],
    brVec.val()[0], brVec.val()[1]
  ));
  this.client.sendMsg(util.format(
    'paintrect %d %d %d %d 0 0 0',
    ulVec.val()[0], ulVec.val()[1],
    brVec.val()[0], brVec.val()[1]
  ));
});

LcdRenderer.method('renderRoom', function(room) {
  var offset = this.roomOffset();
  if (this.accessibleNeighbors().length > 0) {
    this.drawRect(
      new offset.add($V([
        (this.roomWidth / 2) - this.doorWidth,
        (this.roomHeight / 2) - this.doorHeight
      ])),
      new offset.add($V([
        (roomWidth / 2) + (doorWidth / 2),
        (roomHeight / 2) + (doorHeight / 2)
      ]))
    );
  }
  // _.each(this.accessibleNeighbors(), function(n) {
  //   renderWall(wall);
  // });
});

LcdRenderer.method('roomOffset', function(room) {
  return new $V([
    room.pos.val()[1] * this.roomWidth,
    room.pos.val()[0] * this.roomHeight
  ]);
});

LcdRenderer.method('renderWall', function(wall) {
  // var offset = [
  //   room.pos[0] * roomHeight; // row
  //   room.pos[1] * roomWidth; // col
  // ];
  // var topLeft, bottomRight;
  // if (wall == [0,1]) { // right
  //   topLeft = [0,]
  // } else if (wall == [1,0]) { // down

  // } else if (wall == [0,-1]) { // left

  // } else if (wall == [-1, 0]) { // up

  // }
});

var renderer = LcdRenderer;
module.exports.renderer = renderer;
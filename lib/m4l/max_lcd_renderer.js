var LcdRenderer = function() {}

LcdRenderer.prototype.init = function(m4lClient, numRows, numCols, lcdWidth, lcdHeight) {
  this.client = m4lClient;
  this.lcdWidth = lcdWidth;
  this.lcdHeight = lcdHeight;
  this.numRows = numRows;
  this.numCols = numCols;
  this.roomWidth = lcdWidth / numCols;
  this.roomHeight = lcdHeight / numRows;
}

LcdRenderer.prototype.renderRoom = function(room) {
  var neighbors = room.neighbors();
  var that = this;
  var wall;
  if (neighbors.length > 0) {
    // draw center
  }
  _.each(neighbors, function(n) {
    wall = [(n.pos[0] - room.pos[0]),
            (n.pos[1] - room.pos[1])];
    renderWall(wall, that);
  });
}

LcdRenderer.prototype.renderWall = function(wall, roomPos) {
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
}
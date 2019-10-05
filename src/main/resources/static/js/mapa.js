var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");

document.body.onresize = function resize() {

	var height = window.innerHeight;
	
	var ratio = canvas.width/canvas.height;
	var width = height * ratio;
	
	canvas.style.width = width+'px';
	canvas.style.height = height+'px';
}

document.body.onload = function resize() {

	var height = window.innerHeight;
	
	var ratio = canvas.width/canvas.height;
	var width = height * ratio;
	
	canvas.style.width = width+'px';
	canvas.style.height = height+'px';
}

//Level 1
//Círculo 1
ctx.beginPath();    
ctx.arc(50,290,23,0,2*Math.PI);
ctx.fillStyle="#98FB98";
ctx.fill();
ctx.stroke();

//Texto '1'
ctx.beginPath();    
ctx.font="25px Verdana";
ctx.fillStyle="black";
ctx.fillText("1",40,300);

//Level 2
//Círculo 2
ctx.beginPath();    
ctx.arc(150,290,23,0,2*Math.PI);
ctx.fillStyle="#3CB371";
ctx.fill();
ctx.stroke();

//Texto '2'
ctx.beginPath();    
ctx.font="25px Verdana";
ctx.fillStyle="black";
ctx.fillText("2",140,300);

//Level 3
//Círculo 3
ctx.beginPath();    
ctx.arc(250,290,23,0,2*Math.PI);
ctx.fillStyle="#2E8B57";
ctx.fill();
ctx.stroke();

//Texto '3'
ctx.beginPath();    
ctx.font="25px Verdana";
ctx.fillStyle="black";
ctx.fillText("3",240,300);

//Level 4
//Círculo 4
ctx.beginPath();    
ctx.arc(350,290,23,0,2*Math.PI);
ctx.fillStyle="#008000";
ctx.fill();
ctx.stroke();

//Texto '4'
ctx.beginPath();    
ctx.font="25px Verdana";
ctx.fillStyle="black";
ctx.fillText("4",340,300);

//Level 5
//Círculo 5
ctx.beginPath();    
ctx.arc(450,290,23,0,2*Math.PI);
ctx.fillStyle="#003300";
ctx.fill();
ctx.stroke();

//Texto '5'
ctx.beginPath();    
ctx.font="25px Verdana";
ctx.fillStyle="white";
ctx.fillText("5",440,300);

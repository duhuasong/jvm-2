$(function(){
	
	setColor($("#template"),randomColor());
	
	$("#template").click(function(){
		setColor($(this),randomColor());
		$("#answer_span").text("");
	});
	
	$("#show_answer").click(function(){
		var color = $("#template").css("background-color");
		color = color.substring(4,color.length-1);
		var arr = color.split(",");
		$("#answer_span").text(arr[0]+","+arr[1]+","+arr[2]);
	});
	
	
	
	$("input").keyup(function(){
		var r = $("#r_in").val();
		var g = $("#g_in").val();
		var b = $("#b_in").val();
		var color = randomColorWithNumber(new Number(r),new Number(g),new Number(b));
		setColor($("#worker"),color);
	});
	
});

function setColor(jObj,colorNum){
	jObj.css({ "background-color": colorNum });
}
/**
 * 10进制的string类型的r，g，b
 */
function randomColorWithNumber(r,g,b){
	return "rgb("+r+","+g+","+b+")";
}

function randomColor(){
	var r = randomNumber(0,255);
	var g = randomNumber(0,255);
	var b = randomNumber(0,255);
	return randomColorWithNumber(r,g,b);
}

function randomNumber(start, end){
    return Math.floor(Math.random() * (end - start) + start);
}
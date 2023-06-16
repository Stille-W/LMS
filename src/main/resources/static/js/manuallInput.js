$(document).ready(function(){
	$("input[name='workHours']").val(calcTime());
	
	$("input[name='startTime'], input[name='endTime'], input[name='project']").change(function(){
		$("input[name='workHours']").val(calcTime());
	})
})


function calcTime(){
	let start = $("input[name='startTime']").val();
	let end = $("input[name='endTime']").val();
	
	
	let _startTime = start.split(":");
	let _endTime = end.split(":");
	let startDate = new Date(0, 0, 0, _startTime[0], _startTime[1], 0);
	let endDate = new Date(0, 0, 0, _endTime[0], _endTime[1], 0);
	let diff = endDate.getTime()-startDate.getTime();
	let hours = Math.floor(diff / 1000 / 60 / 60);
	diff -= hours * 1000 * 60 * 60;
	let minutes = Math.floor(diff / 1000 / 60);
	if($("input[name='project']").val()!= '新人教育（愚痴相談）'){
		hours -= 1;
	}
	
	let resultTime = (hours<10?("0"+hours):hours)+":"+(minutes==0?"00":minutes);
	return resultTime;
}





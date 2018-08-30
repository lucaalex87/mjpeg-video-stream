/**
 * 
 */
(function(){
	var video = document.querySelector('video');
	var canvas = document.querySelector('canvas');
	var img = document.querySelector('img');
	var timestamp = document.querySelector('#timestamp');
	var url = "ws://localhost:8080/videoFrame";
	
	var socket = new WebSocket(url);
	
	socket.onopen=onOpen;


	socket.addEventListener('message',function(event){
		img.src=window.URL.createObjectURL(event.data);

	});

	setInterval(updateTimestamp, 20);

	function updateTimestamp() {
		timestamp.innerHTML = new Date().getTime();
	}

	function onOpen(event){
		console.log('websocket connection open')
	}

})();
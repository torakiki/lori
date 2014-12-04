var client;
$("#connect").on("click", function(event) {
	$('#logArea').append("\nconnecting...");

	client = new WebSocket('ws://localhost:9000');

	client.onopen = function(evt) {
		$('#logArea').append("\nconnected");
		$("#connect").prop("disabled", true);
		$("#disconnect").prop("disabled", false);
	};
	client.onclose = function(evt) {
		$('#logArea').append("\ndisconnected");
		$("#disconnect").prop("disabled", true);
		$("#connect").prop("disabled", false);
	};
	client.onmessage = function(data) {
		$('#logArea').append("\nreceived: " + data.data);
	};
	client.onerror = function(error) {
		$('#logArea').append("\nerror: " + error.data);
	};
});

$("#disconnect").on("click", function(event) {
	if (client !== undefined) {
		$('#logArea').append("\ndisconnecting...");
		client.close();
	}
});
$("#up").on("click", function(event) {
	if (client !== undefined) {
		$('#logArea').append("\nsending UP...");
		client.send('U');
	}
});
$("#down").on("click", function(event) {
	if (client !== undefined) {
		$('#logArea').append("\nsending DOWN...");
		client.send('D');
	}
});
$("#left").on("click", function(event) {
	if (client !== undefined) {
		$('#logArea').append("\nsending LEFT...");
		client.send('L');
	}
});
$("#right").on("click", function(event) {
	if (client !== undefined) {
		$('#logArea').append("\nsending RIGHT...");
		client.send('R');
	}
});
var stompClient = null;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });

    $("#send-private").click(function() {
        sendPrivateMessage();
    });
});

function connect() {
    var socket = new SockJS('/ws/stomp/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/sub/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

        //stompClient.subscribe('/user/topic/private-messages', function (message) {
        //    showMessage(JSON.parse(message.body).content);
        //});
    });
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/pub/message", {}, JSON.stringify({'messageContent': $("#message").val()}));
}

function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/pub/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}
var stompClient = null;

$(document).ready(function() {
    console.log("Index page is ready");
    $("#connect").click(function(){
        connect();
    })

    $("#send").click(function() {
        sendMessage();
    });

    $("#send-private").click(function() {
        sendPrivateMessage();
    });
});

let nickname = sessionStorage.getItem('access_token')
let headers = {Authorization: nickname}

function connect() {
    var socket = new SockJS('/ws/stomp/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect(headers, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/sub/messages/'+$("#chatRoomId").val(), function (message) {
            //showMessage(message.body);
            showMessage2(message);
        });

        //stompClient.subscribe('/user/topic/private-messages', function (message) {
        //    showMessage(JSON.parse(message.body).content);
        //});
    });
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}
function showMessage2(message){
    $("#messages").append("<tr><td>"+message.headers.sender+ " : " + message.body + "</td></tr>");
}
function notify(){
    alert("안녕하세요");
}
function sendMessage() {
    console.log("sending message");
    stompClient.send("/pub/message", headers, JSON.stringify({'messageContent': $("#message").val(),
    'receiver':$("#receiver").val(), 'chatRoomId':$("#chatRoomId").val()}));
}
function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/pub/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}


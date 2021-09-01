var stompClient = null;
var text="";
$(document).ready(function(message) {
    console.log("Index page is ready");
    fetch('http://localhost:8080/rooms', {
        method: "GET",
        headers: {
            "Content-Type": "application/json;charset=utf-8",
            "Authorization": nickname
        }
        /*,
        body:JSON.stringify({
            user2:$("#receiver").val(),
            chatTitle:$("#receiver").val()
        }),*/
    })
        .then(res=>res.json())
        .then(res=>{
            for(var i=0; i <res.length; i++){
                showChatRoom(res[i]);
            }
        console.log(res[0].user2)});

    /*
        .then(async (res) => {
        const data = await res.json();
        const { user2 } = data[0];
        console.log(user2);
    });


     */
    connect();

    $("#send").click(function() {
        sendMessage();
    });

    $("#send-private").click(function() {
        sendPrivateMessage();
    });
    //동적생성 쿼리는 특정한 이벤트 처리를 해줘야함.
    $(document).on("click", "#Chat-lists tr",function(){
        var tr = $(this);
        alert(+tr.text());
    });

    $("#createChatRoom").click(function(){
        fetch('http://localhost:8080/room',{
            method:"POST",
            headers: {
                "Content-Type":"application/json",
                "Authorization": nickname
            },
            body:JSON.stringify({
                user2:$("#receiver").val(),
                chatTitle:$("#receiver").val()
            }),
        });
    });
});
let nickname = sessionStorage.getItem('access_token')
let headers = {Authorization: nickname}

async function getChat(){
    const response = fetch('http://localhost:8080/rooms', {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": nickname
        }
        /*,
        body:JSON.stringify({
            user2:$("#receiver").val(),
            chatTitle:$("#receiver").val()
        }),*/
    });
    return response;
        /*
        .then(res => res.json())
        .then(data => {
            const { user2 } = data;
            console.log(user2);
        });

         */
    /*
    .then(json => {
        if(json.success){
            console.log(json.user2);
        }
    });

     */
    //.then(json => { text = json;});
    // console.log(text);
}
function connect() {
    var socket = new SockJS('/ws/stomp/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect(headers, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/sub/messages/'+nickname, function (message) {
            // showMessage(message.body);
            showChat(message.body);
        });

        //stompClient.subscribe('/user/topic/private-messages', function (message) {
        //    showMessage(JSON.parse(message.body).content);
        //});
    });
}

function showChatRoom(json){
    let user="";
    if(json.user2!=nickname){
        user = json.user2;
    }
    else
        user = json.user1;
    $("#Chat-lists").append("<tr><td id=\""+user+"\"><br>" + user + "</br><br>" + json.chatText+ "</br><br></br></td></tr>");
}
function showChat(message){
    let message_str = message.split(":");
    let user = message_str[0];
    let payload = message_str[1];
    document.getElementById(user).innerHTML = "<tr><td id="+user+"><br>" + user + "</br><br>" + payload+ "</br><br></br></td></tr>";
}
function showMessage(message) {
    //$("#messages").append("<tr><td>" + message + "</td></tr>");
    $("#Chat-lists").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/pub/message", headers, JSON.stringify({'messageContent': $("#message").val(),
        'receiver':$("#receiver").val()}));
}

function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/pub/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}


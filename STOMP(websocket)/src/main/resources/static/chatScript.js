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
                connectAll(res[i].id);
                console.log(res[i].id);
            }
        });

    /*
        .then(async (res) => {
        const data = await res.json();
        const { user2 } = data[0];
        console.log(user2);
    });


     */
    connect();

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
        }).then(res=>{handShake(res)});
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
        stompClient.subscribe('/sub/messages/'+nickname, function (roomId) {
            //여기서는 chatroom id만 받는거야
            stompClient.subscribe("/sub/messages/"+roomId.body, function(message){
                showChatById(message, roomId.body);
            })
            // showMessage(message.body);

        });

        //stompClient.subscribe('/user/topic/private-messages', function (message) {
        //    showMessage(JSON.parse(message.body).content);
        //});
    });
}

function connectAll(roomId){
    var socket = new SockJS('/ws/stomp/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect(headers, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/sub/messages/'+roomId, function (message) {
            showChatById(message, roomId);
        })
    });
}
function showChatRoom(json){
    let user="";
    if(json.user2!=nickname){
        user = json.user2;
    }
    else
        user = json.user1;
    $("#Chat-lists").append("<tr><td id=\""+json.id+"\"><br>" + user + "</br><br>" + json.chatText+ "</br><br></br></td></tr>");
}
function showChat(message){
    let message_str = message.split(":");
    let user = message_str[0];
    let payload = message_str[1];
    document.getElementById(user).innerHTML = "<tr><td id="+user+"><br>" + user + "</br><br>" + payload+ "</br><br></br></td></tr>";
}
function showChatById(message, roomId){
    let user;
    if(message.sender == nickname){
        user = message.headers.receiver;
    }
    else{
        user = message.headers.sender;
    }
    console.log("roomId = "+ roomId);
    if(document.getElementById(roomId)==null){
        $("#Chat-lists").append( "<tr><td id="+roomId+"><br>" + user + "</br><br>" + message.body+ "</br><br></br></td></tr>");
    }
    else{
        document.getElementById(roomId).innerHTML = "<tr><td id="+roomId+"><br>" + user + "</br><br>" + message.body+ "</br><br></br></td></tr>";
    }

}
function showMessage(message) {
    //$("#messages").append("<tr><td>" + message + "</td></tr>");
    $("#Chat-lists").append("<tr><td>" + message + "</td></tr>");
}
function notify(){
    alert("안녕하세요");
}
function handShake(id) {
    console.log("handShake message");
    stompClient.send("/pub/message2", headers, JSON.stringify({'messageContent': $("#message").val(),
        'receiver':$("#receiver").val(), 'chatRoomId':id}));
}

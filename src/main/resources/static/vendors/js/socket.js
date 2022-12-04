//url của server chứa socket
const url = 'http://localhost:8080';

let stompClient;
let selectedRoom;
let newMessages = new Map();
var idRoomChat = getCookie("idRoom");
var html_append_message ="";
if(idRoomChat != ""){
    connectToChat(idRoomChat)
}

function connectToChat(idRoomChat) {
	//tạo một kết nối tới socket
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);

		//subcribe 1 topic với tên là userName
        stompClient.subscribe("/topic/messages/" + idRoomChat, function (response) {
//        response trả về là một cấu trúc của message sẽ được móc ra  tu db
            let data = JSON.parse(response.body);
            console.log("data", data)
            //sử dụng response để làm j đó ở dưới đây..
            if(data.fromLogin != getCookie("idUser")){
            console.log(data.fromLogin+ "khac"+ idRoomChat)
               html_append_message = '<li class="clearfix">'+
                            '<div class="message-data text-left">'+
                              '<span class="message-data-time">10:10 AM, Today</span>'+
                              '<img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="avatar">'+
                            '</div>'+
                            '<div  class="message other-message float-left">'+ data.message+ '</div>'+
                          '</li>';
            }else{
              html_append_message = '<li class="clearfix">'+
                            '<div class="message-data text-right">'+
                              '<span class="message-data-time">10:10 AM, Today</span>'+
                              '<img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="avatar">'+
                            '</div>'+
                            '<div   style="background: #2e7f91; color: white" class="message other-message float-right" class="message other-message float-right">'+ data.message+ '</div>'+
                          '</li>';
            }
            document.getElementById("chat-history").innerHTML += html_append_message
            html_append_message = "";
        });
    });
}
function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for(let i = 0; i <ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

//thực hiện việc send message đến 1 idROOM
function sendMsg() {
    roomID = idRoomChat;
    idUserSend= getCookie("idUser")
    let message = $('#value-message').val();
    console.log("content: " + message)
    if (message == '') {
        alert("chua điền thong tin");
    }else{
        stompClient.send("/app/chat/" + roomID, {}, JSON.stringify({
            fromLogin:  idUserSend,
            message: message
        }));
        $('#value-message').val("")
    }
}

////chọn một roomID để thực hiện chat, lấy id của room đó và show ra tất cả tin nhắn của room đó
//function selectRoomSK(idRoom) {
//    document.addEventListener('click', function handleClick(event) {
//       idRoomChat = idRoom;
//    });
//    connectToChat(idRoom)
//}
function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for(let i = 0; i <ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}


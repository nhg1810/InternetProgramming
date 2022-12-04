//url của server chứa socket
const url = 'http://localhost:8080';
let stompClient;
let selectedRoom;
var idTokenRoom = getCookie("room-token");
var idRoomChat = getCookie("idRoom");
if(idRoomChat != ""){
    connectToChat(idRoomChat)
}

function connectToSocket(idRoomChat) {
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
        });
    });
}
//thực hiện việc send 1 tooken đến server để lưu vào csdl
function sendTokenRoom() {
    roomID = idRoomChat;
    roomToken = idTokenRoom;

    console.log("roomToken: " + roomToken)
//        stompClient.send("/app/chat/" + roomID, {}, JSON.stringify({
//            fromLogin:  idUserSend,
//            message: message
//        }));

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


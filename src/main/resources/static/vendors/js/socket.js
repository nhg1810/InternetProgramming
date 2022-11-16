//url của server chứa socket
const url = 'http://localhost:8080';

let stompClient;
let selectedRoom;
let newMessages = new Map();
var idRoomChat = 0;
 layout_chat = document.getElementById("layout-chat")
layout_chat.style.display = "none";

 layout_page_null = document.getElementById("page-null")
layout_page_null.style.display = "block";
function connectToChat(roomID) {
	//tạo một kết nối tới socket
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);

		//subcribe 1 topic với tên là userName
        stompClient.subscribe("/topic/messages/" + roomID, function (response) {
//        response trả về là một cấu trúc của message sẽ được móc ra  tu db
            let data = JSON.parse(response.body);
            console.log("data", data)
            //sử dụng response để làm j đó ở dưới đây..

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
            let html = '<li class="clearfix">'+
                '<div class="message-data text-right">'+
                  '<span class="message-data-time">10:10 AM, Today</span>'+
                  '<img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="avatar">'+
                '</div>'+
                '<div class="message other-message float-right">'+ message+ '</div>'+
              '</li>';

            document.getElementById("chat-history").innerHTML += html
        //    lưu 3 trường này vào db qua 1 api...
//        postData('/chat/'+idRoomChat+'', {
//                idUserSend:  idUserSend,
//                message: message })
//          .then((data) => {
//            console.log("save inf into db: " + data);
//          });
    }
}

//chọn một roomID để thực hiện chat, lấy id của room đó và show ra tất cả tin nhắn của room đó
function selectUser(idRoom, nameRoom) {
    document.addEventListener('click', function handleClick(event) {
       layout_chat.style.display = "block";
       layout_page_null.style.display = "none";
        document.getElementById("name-chat").innerHTML = nameRoom;
       idRoomChat = idRoom;

       //gọi ajax để get tất cả các tin nhắn theo id phòng
           $.get("/dashboard-chat/message/"+idRoom+"", function(data, status){
             console.log(data);
           });
    });
//console.log(idRoom);
//hàm này đc gắn sự kiện click bên trang html
//nếu được làm check xem coi một user muốn vào idroom thì đc host cấp quyền hay chưa
//gọi hàm connectToChat tới idRoom đấy
    connectToChat(idRoom)
//lấy ra value idRoom của một room chat, và làm .., gì đó
}


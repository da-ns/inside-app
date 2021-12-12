(function(){
    function login() {
        alert("Start login");
        /*
        $.ajax({
          type: "POST",
          url: "/user/login",
          data: {
            login: $("#login").val(),
            password: $("#password").val()
          },
          success: function() {
                $("#loginForm").hide();
                $("#messageButtons").show();
          },
          dataType: "json",
          contentType : "application/json"
        });

        alert("Sended ajax");*/
    }

    function sendMessage() {
        alert("sendMessage");
    }

    function showHistory() {
        alert("showHistory");
    }

    $(document).ready(function() {
        $("#loginButton").click(login);
        $("#sendMessageButton").click(sendMessage);
        $("#showHistoryButton").click(showHistory);
    });
}());
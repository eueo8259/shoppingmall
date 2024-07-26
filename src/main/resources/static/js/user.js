$(document).ready(function() {
    $("#idCheck").click(function() {
        var userId = $("#id").val();
        $.ajax({
            url: '/user/checkId',
            type: 'POST',
            data: { id: userId },
            success: function(response) {
                if(response.isDuplicate) {
                    alert("아이디가 중복되었습니다. 다른 아이디를 사용하세요.");
                } else {
                    alert("사용 가능한 아이디입니다.");
                }
            },
            error: function() {
                alert("아이디 중복 체크 중 오류가 발생했습니다. 다시 시도해 주세요.");
            }
        });
    });
    $("form").submit(function(event) {
        var birth = $("#birth").val();
        var rrn2 = $("#rrn2").val();
        var rrnFull = birth + '-' + rrn2;
        console.log("RRN: " + rrnFull);
        $("#RRN").val(rrnFull);

        var mailId = $("#mailId").val();
        var domain = $("#domain").val();
        var emailFull = mailId + '@' + domain;
        console.log("Email: " + emailFull);
        $("#email").val(emailFull);
    });
});
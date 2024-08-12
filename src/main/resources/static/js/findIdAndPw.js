$(document).ready(function() {
    $("#findId").click(function() {
        var userName = $("#name").val();
        var RRN = $("#RRN").val();
        var phoneNumber = $("#phoneNumber").val();
        var RRNCheck = /^(?:[0-9]{2})(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2][0-9]|3[0-1])[-][1-6][0-9]{6}$/;
        var phoneCheck = /^010-\d{4}-\d{4}$/;
        if (userName.length === 0) {
            alert("이름이 입력되지 않았습니다.");
        } else if(!RRNCheck.test(RRN)) {
            alert("주민등록번호를 확인해주세요")
        } else if (!phoneCheck.test(phoneNumber)) {
            alert("휴대폰번호를 확인해주세요")
        } else {
            $.ajax({
                url: '/user/findId',
                type: 'POST',
                data: { userName: userName,
                        RRN: RRN,
                        phoneNumber: phoneNumber },
                success: function(data) {
                    if(data.response === "NotFound") {
                        var strContent = "<span style='font-size: 18px;'>해당 정보로 가입된 아이디가 없습니다.</span>"
                    } else {
                        var strContent = "<span style='font-size: 18px;'>가입된 아이디 : </span>" + "<span style='font-size: 18px; font-weight: bold;'>" + data.userId + "</span>"
                    }
                    $(".findId, .btn1").hide();
                    $(".showUserId, .confirm1").show();
                    $(".showUserId").html(strContent);
                },
                error: function() {
                    alert("아이디 찾기에 실패하였습니다.");
                }
            });
        }
    });
    $("#findPw").click(function() {
         var id = $("#id").val();
         var phoneNumber = $("#phoneNumber2").val();
         var email = $("#email").val();
         var phoneCheck = /^010-\d{4}-\d{4}$/;
         var emailCheck = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
         if (id.length === 0) {
             alert("아이디가 입력되지 않았습니다.");
         } else if(!phoneCheck.test(phoneNumber)) {
             alert("휴대폰번호를 확인해주세요")
         } else if (!emailCheck.test(email)) {
             alert("이메일을 확인해주세요")
         } else {
             $.ajax({
                 url: '/user/findPw',
                 type: 'POST',
                 data: { id: id,
                         phoneNumber: phoneNumber,
                         email: email },
                 success: function(data) {
                     if(data.response === "NotFound") {
                         alert("해당 정보와 일치하는 회원이 없습니다.")
                     } else {
                        $(".findPw, .btn").hide();
                        $(".updatePw, .confirm2").show();
                        $("#id").val(data.userId);
                     }
                 },
                 error: function() {
                     alert("비밀번호 찾기에 실패하였습니다.");
                 }
            });
        }
    });

    $('#confirmPw').on('input', function() {
        var updatePw = $('#updatePw').val();
        var confirmPw = $('#confirmPw').val();
        if (updatePw !== confirmPw) {
            $('#passwordError').text('비밀번호가 일치하지 않습니다.');
        } else {
            $('#passwordError').text('');
        }
    });

    $("#pwUpdate").click(function() {
        var updatePw = $('#updatePw').val();
        var id = $("#id").val();
        $.ajax({
            url: '/user/updatePw',
            type: 'POST',
            data: { id: id,
                    updatePw: updatePw },
            success: function(response) {
                if(response.save === "OK") {
                    alert("비밀번호 변경이 완료되었습니다.");
                    location.reload();
                }
            },
            error: function() {
                alert("비밀번호 변경에 실패하였습니다.");
                location.reload();
            }
        });
    });
    $('#findIdModal, #findPwModal').on('hidden.bs.modal', function () {
        // 초기화
        location.reload();
    });
});
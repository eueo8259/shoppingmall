$(document).ready(function() {

    $(".cancelBtn").click(function() {
        window.history.back();
    });

    $("input[name='role']").on("change", function() {
        if ($("#role_seller").is(":checked")) {
            confirm("'SELLER'는 회원가입완료 후 관리자의 승인이 필요합니다.");
        }
    });

    var checkOk = false;

    $('#password, #password2').on('input', function() {
        var password = $('#password').val();
        var password2 = $('#password2').val();
        if (password.length === 0 && password2.length === 0) {
                $('#passwordError').text('');
        } else if (password !== password2) {
            $('#passwordError').text('비밀번호가 일치하지 않습니다.').css('color', 'red');
        } else {
            $('#passwordError').text('비밀번호가 일치합니다.').css('color', 'blue');
        }
    });

    $('#id').on('input', function() {
        checkOk = false;
    });
    $("#idCheck").click(function() {
        var userId = $("#id").val();
        if (userId.length === 0) {
            alert("아이디가 입력되지 않았습니다.");
        } else if (userId.length < 4 || userId.length > 20) {
            alert("아이디는 4~20자로 입력해주세요");
        } else {
            $.ajax({
                url: '/checkId',
                type: 'POST',
                data: { id: userId },
                success: function(response) {
                    if(response.isDuplicate) {
                        alert("아이디가 중복되었습니다. 다른 아이디를 사용하세요.");
                    } else {
                        alert("사용 가능한 아이디입니다.");
                        checkOk = true;
                    }
                },
                error: function() {
                    alert("아이디 중복 체크 중 오류가 발생했습니다. 다시 시도해 주세요.");
                }
            });
        }
    });
    $("#signUp").click(function() {
        var birth = $("#birth").val();
        var rrn2 = $("#rrn2").val();
        var mailId = $("#mailId").val();
        var domain = $("#domain").val();
        var password = $('#password').val();
        var password2 = $('#password2').val();

        if(checkOk === false) {
            alert("아이디 중복을 확인해주세요")
            $("#id").focus();
            return false;
        }
        if($("#id").val().length === 0 ) {
            alert("아이디가 입력되지 않았습니다.")
            $("#id").focus();
            return false;
        }
        if(password.length === 0) {
            alert("비밀번호를 확인해주세요")
            $("#password").focus();
            return false;
        }
        if(password !== password2 || password2.length === 0) {
            alert("비밀번호를 확인해주세요")
            $("#password2").focus();
            return false;
        }
        if($("#userName").val().length === 0) {
            alert("이름을 입력해주세요")
            $("#userName").focus();
            return false;
        }
        if($("#birth").val().length !== 6) {
            alert("생년월일은 주민등록번호 앞 6자리로 입력해주세요")
            $("#birth").focus();
            return false;
        }
        if($("#rrn2").val().length !== 7) {
            alert("주민등록번호를 확인해주세요")
            $("#rrn2").focus();
            return false;
        }
        var phoneCheck = /^010-\d{4}-\d{4}$/;
        var phoneNumber = $("#phoneNumber").val();
        if(!phoneCheck.test(phoneNumber)) {
            alert("휴대폰번호를 확인해주세요")
            return false;
        }
        if($("#mailId").val().length === 0) {
            alert("이메일을 확인해주세요")
            $("#mailId").focus();
            return false;
        }
        var domainCheck = /^[a-zA-Z0-9-]{2,63}\.[a-zA-Z]{2,3}$/;
        if(!domainCheck.test(domain)) {
            alert("메일 도메인을 확인해주세요")
            $("#domain").focus();
            return false;
        }
        if(!$("#agreement1").prop("checked") || !$("#agreement2").prop("checked")){
            alert("[필수] 항목에 체크해주세요")
            $("#agreement1").focus();
            return false;
        }
        var rrnFull = birth + '-' + rrn2;
        console.log("RRN: " + rrnFull);
        $("#RRN").val(rrnFull);

        var emailFull = mailId + '@' + domain;
        console.log("Email: " + emailFull);
        $("#email").val(emailFull);

        alert("회원가입이 완료되었습니다.")
            $("#signupForm").submit()
            return true;
    });

    $("#confirmBtn").click(function() {
        var userId = $("#username").text();
        var confirmPassword = $("#confirmPassword").val();
        $.ajax({
            url: '/user/checkPassword',
            type: 'POST',
            data: { userId: userId,
                    password: confirmPassword },
            success: function(response) {
                if(response === "ok") {
                    $(".confirmUser").hide();
                    $(".modifyUserInfo").show();
                } else {
                    alert("비밀번호가 맞지 않습니다.");
                }
            },
            error: function() {
                alert("비밀번호 확인중 오류가 발생했습니다. 다시 시도해 주세요.");
            }
        });
    });

    $("#applySeller").on("change", function() {
        if ($(this).prop('checked')) {
            if (confirm("'SELLER'는 요청 후 관리자의 승인이 필요합니다.")) {
                $('#applySellerHidden').val('apply');
            } else {
                $(this).prop('checked', false);
                $('#applySellerHidden').val('none');
            }
        } else {
            $('#applySellerHidden').val('none');
        }
    });

    $("#applyCancel").on("change", function() {
        if ($(this).prop("checked")) {
            if (confirm("'SELLER' 요청을 취소합니다.")) {
                $('#applySellerHidden').val('cancel');
            } else {
                $(this).prop('checked', false);
                $('#applySellerHidden').val('none');
            }
        } else {
            $('#applySellerHidden').val('none');
        }
    });

    $("#save").click(function() {
        var password = $("#password").val();
        var password2 = $("#password2").val();
        var phoneNumber = $("#phoneNumber").val();
        var email = $("#email").val();
        var phoneCheck = /^010-\d{4}-\d{4}$/;
        var emailCheck = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        if (password !== password2) {
            alert("비밀번호를 확인해주세요")
            $("#password2").focus();
            return false;
        } else if(!phoneCheck.test(phoneNumber)) {
            alert("휴대폰번호를 확인해주세요");
            return false;
        } else if (!emailCheck.test(email)) {
            alert("이메일을 확인해주세요");
            return false;
        }
        alert("회원 정보 수정이 완료되었습니다.");
        $("#userModifyForm").submit();
        return true;
    });
});
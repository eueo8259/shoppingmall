// 우편번호 찾기 화면을 넣을 element
var $element_layer = $('#layer');

function closeDaumPostcode() {
    $element_layer.hide();
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                $("#extraAddress").val(extraAddr);

            } else {
                $("#extraAddress").val('');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $('#postcode').val(data.zonecode);
            $("#address").val(addr);
            $("#detailAddress").focus();

            $element_layer.hide();
        },
        width : '100%',
        height : '100%',
        maxSuggestItems : 5
    }).embed($element_layer[0]);

    $element_layer.show();

    // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
    initLayerPosition();
}

function initLayerPosition(){
    var width = 300;
    var height = 400;
    var borderWidth = 5;

    $element_layer.css({
        width: width + 'px',
        height: height + 'px',
        border: borderWidth + 'px solid'
    });

    // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
    $element_layer.css({
        left: (((window.innerWidth || document.documentElement.clientWidth) - width) / 2 - borderWidth) + 'px',
        top: (((window.innerHeight || document.documentElement.clientHeight) - height) / 2 - borderWidth) + 'px'
    });
}

    $('#defaultYnCheckbox').change(function() {
        if ($(this).is(':checked')) {
            alert('해당 배송지를 기본 배송지로 설정합니다.');
        }
    });

$("#deliverySave").click(function() {
    var address1 = $("#address").val();
    var address2 = $("#detailAddress").val();
    var address = address1 + "," + address2;
    $("#deliveryAddress").val(address);

    if($("#contactName").val().length === 0 ) {
        alert("이름이 입력되지 않았습니다.")
        $("#contactName").focus();
        return false;
    }

    var phoneCheck = /^010-\d{4}-\d{4}$/;
    var phoneNumber = $("#contactNumber").val();
    if(!phoneCheck.test(phoneNumber)) {
        alert("전화번호를 확인해주세요")
        return false;
    }

    if($("#detailAddress").val().length === 0 ) {
        alert("상세주소를 입력해주세요")
        $("#detailAddress").focus();
        return false;
    }

    if ($("#defaultYnCheckbox").prop("checked")) {
        $("#defaultYn").val("Y");
    } else {
        $("#defaultYn").val("N");
    }

    alert("입력이 완료되었습니다.")
        $("#form").submit()
        return true;
});

    $("#insertBtn").click(function() {
        const userId = $("#userId").val();
        $.ajax({
            url: '/delivery/deliveryCount',
            type: 'POST',
            data: { userId: userId },
            success: function(data) {
                console.log(data);
                if (data >= 7) {
                    alert('배송지 등록 가능 범위를 초과했습니다. 삭제 후 등록해주세요.');
                } else {
                    window.location.href = '/delivery/insertDelivery';
                }
            },
            error: function() {
                alert("페이지 구동 시 오류가 발생했습니다.");
            }
        });
    });






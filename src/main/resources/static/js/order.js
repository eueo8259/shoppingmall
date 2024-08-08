$(document).ready(function() {
    var newDeliveryPop;
    var deliveryPop;
    var chargePop;
    var couponPop;
    var total = 0;
    var discount = 0;
    var payment = 0;
    totalPrice();
    gradeDiscount();
    paymentAmount();

    $('#newDelivery').click(function(){
       newDeliveryPop = window.open('http://localhost:8080/delivery/newDeliveryPop', 'newDeliveryPop', 'width=700px,height=800px');
    });
    $('#newDeliverySave').click(function(){
        var contactName = $("#contactName").val();
        var address1 = $("#address").val();
        var address2 = $("#detailAddress").val();
        var address = address1 + "," + address2;
        var contactNumber = $("#contactNumber").val();
        var postalCode = $("#postcode").val();
        var userId = $("#username").val();
        var defaultYn = $("#defaultYn").val();
        $("#deliveryAddress").val(address);
        if($("#contactName").val().length === 0 ) {
            alert("이름이 입력되지 않았습니다.")
            $("#contactName").focus();
            return false;
        }
        var phoneCheck = /^010-\d{4}-\d{4}$/;
        if(!phoneCheck.test(contactNumber)) {
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

        console.log(userId);


        $.ajax({
            url: '/delivery/popSave',
            type: 'POST',
            data: {
                contactName: contactName,
                contactNumber: contactNumber,
                address: address,
                postalCode: postalCode,
                defaultYn: defaultYn,
                userId: userId
            },
            success: function(response) {
                if(response) {
                   alert("배송지가 등록되었습니다.");
                   $(window.opener.document).find("#parentDeliveryCode").val(response.deliveryCode);
                   parentDataApply();
                   window.close();  // 팝업 창 닫기
                } else {
                   alert("배송지 등록에 실패했습니다.");
                }
            },
            error: function(error) {
                alert("배송지 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
            }
        });
    });
    function parentDataApply() {
       // 부모 창이 열려 있는지 확인
       if (window.opener) {
           // 부모 창의 요소 업데이트
           var contactName = $("#contactName").val();
           var address1 = $("#address").val();
           var address2 = $("#detailAddress").val();
           var address = address1 + "," + address2;
           var contactNumber = $("#contactNumber").val();

           $(window.opener.document).find("#parentContactName").text(contactName);
           $(window.opener.document).find("#parentAddress").text(address);
           $(window.opener.document).find("#parentContactNumber").text(contactNumber);

           // 새로운 배송지 정보 표시
           $(window.opener.document).find(".defaultDelivery").hide();
           $(window.opener.document).find(".newDelivery").show();
       } else {
           console.error("Opener window is not available.");
       }
    }
    $('#deliveryList').click(function(){
      deliveryPop = window.open('http://localhost:8080/delivery/deliveryPop', 'deliveryList', 'width=700px,height=800px');
    });
    $('.choiceBtn').click(function(){
        var $tr = $(this).closest('tr'); //버튼이 속한 tr
        if (window.opener) {
           // 부모 창의 요소 업데이트
           var contactName = $tr.find("#contactName").text();
           var address = $tr.find("#address").text();
           var contactNumber = $tr.find("#contactNumber").text();
           var deliveryCode = $tr.find("#deliveryCode").val();

           $(window.opener.document).find("#parentContactName").text(contactName);
           $(window.opener.document).find("#parentAddress").text(address);
           $(window.opener.document).find("#parentContactNumber").text(contactNumber);
           $(window.opener.document).find("#parentDeliveryCode").val(deliveryCode);

           // 새로운 배송지 정보 표시
           $(window.opener.document).find(".defaultDelivery").hide();
           $(window.opener.document).find(".newDelivery").show();
           window.close();
        } else {
           console.error("Opener window is not available.");
        }
    });
    $('#chargePopBtn').click(function(){
      chargePop = window.open('http://localhost:8080/point/chargePointPop', 'chargePointPop', 'width=700px,height=800px');
    });

    $("#chargeBtn").click(function() {
        var userId = $("#username").val();
        var userInfoCode = $(window.opener.document).find("#userInfoCode").val();
        if($("#chargePoint").val().length === 0 ) {
            alert("충전할 금액을 입력해주세요.")
            $("#chargePoint").focus();
            return false;
        }
        alert("충전이 완료되었습니다.")
        $("#charge-form").submit();
        $.ajax({
            url: '/point/pointReflection',
            type: 'POST',
            data: { userInfoCode: userInfoCode },
            success: function(data) {
                if(data != null && window.opener) {
                    var formattedPoint = Number(data.currentPoint).toLocaleString();
                    $(window.opener.document).find("#currentPoint").text(formattedPoint);
                    window.close();
                }
            },
            error: function() {
                alert("포인트 반영중 오류가 발생했습니다. 다시 시도해 주세요.");
            }
        });
        return true;
    });
    function totalPrice() {

        $('.cartListEach').each(function() {
            var price = $(this).find('.price').val();
            var quantity = $(this).find('.quantity').val();
            var subtotal = price * quantity;
            total += subtotal;
        });
        var formattedPrice = total.toLocaleString();
        $('#totalPrice').text(formattedPrice);
    }
    function gradeDiscount() {
        var userGrade = $('#userGrade').val();
        var totalPrice = total;
        console.log(userGrade);
        console.log(totalPrice);
        if(userGrade === "VIP") {
            discount = totalPrice * -0.1;
        } else {
            discount = totalPrice * -0.05;
        }
        console.log(formattedPrice);
        var formattedPrice = discount.toLocaleString('ko-KR', {
            minimumFractionDigits: 0,  // 최소 소수점 자릿수
            maximumFractionDigits: 0   // 최대 소수점 자릿수
        });
        $('#gradeDiscount').text(formattedPrice);
    }
    function paymentAmount() {
        var totalPrice = total;
        var discountPrice = discount;
        var couponAmount = 0;   // 쿠폰 작업 진행 후 수정 필요
        console.log(totalPrice);
        console.log(discountPrice);
        console.log(couponAmount);
        payment = totalPrice + discount + couponAmount;
        console.log(payment);
        var formattedPrice = payment.toLocaleString('ko-KR', {
            minimumFractionDigits: 0,  // 최소 소수점 자릿수
            maximumFractionDigits: 0   // 최대 소수점 자릿수
        });
        $('#paymentAmount').text(formattedPrice);
    }
    $("#paymentBtn").click(function() {
        var currentPoint = $("#currentPoint").text();
        if(currentPoint < payment ) {
            alert("포인트가 부족합니다.")
            return false;
        }
        confirm("결제를 진행하시겠습니까?")
        inputDto();
        return true;
    });

    function inputDto() {
        var userInfoCode = $("#userInfoCode").val();
        var paymentPrice = payment;
        var gradeDiscount = parseInt($("#gradeDiscount").text());
        var couponAmount = parseInt($("#couponAmount").text());
        var discountAmount = gradeDiscount + couponAmount;
        var deliveryCode = $("#parentDeliveryCode").val();
        console.log(gradeDiscount);
        console.log(couponAmount);
        console.log(discountAmount);
        console.log(deliveryCode);
        console.log(paymentPrice);
        var orderDetailList = [];
            $('.cartListEach').each(function(index) {
                var productCode = $(this).find('.productCode').val();
                var orderPrice = $(this).find('.price').val();
                var orderQuantity = $(this).find('.quantity').val();
                orderDetailList.push({ productCode: Number(productCode), orderPrice: Number(orderPrice), orderQuantity: Number(orderQuantity) });
            });

        var orderDto = {
                userInfo: {
                    userInfoCode: userInfoCode
                },
                paymentPrice: Number(paymentPrice),
                discountAmount: discountAmount,
                delivery: {
                    deliveryCode: deliveryCode
                }
            };

        $.ajax({
            url: '/order/insertOrder',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                orderDetailList: orderDetailList,
                orderDto: orderDto
            }),
            success: function(response) {
                alert("결제가 완료되었습니다.");
                window.location.href = "http://localhost:8080/"
            },
            error: function() {
                alert("결제 진행중 오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    }
});
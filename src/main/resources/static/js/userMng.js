$(document).ready(function() {
    $("#multiSelect").click(function() {
        $(".checkBox, #selectApprovalBtn, #cancel").show();
        $("#multiSelect, .checkBoxArea").hide();
    });
    $("#selectAllCheckbox").on("click", function(){
        if($("#selectAllCheckbox").is(":checked")){
            $('input[name="selectedIds"]').each(function(){
                $(this).prop('checked', true);
            });
        } else {
            $('input[name="selectedIds"]').each(function(){
                $(this).prop('checked', false);
            });
        }
    });
    $("#cancel").click(function() {
        confirm('변경사항을 취소하고, 첫화면으로 이동합니다.')
        location.reload();
    });
    $("#roleAndIsActiveChange").click(function() {
        $("#save, #cancel, .selectIsActive, .selectUserRole").show();
        $("#roleAndIsActiveChange, #roleChangePop, #bulkChange, .searchBar, .selBox, #active, #userRoleVal").hide();
    });

    let changes = [];

    $('.selectIsActive, .selectUserRole').change(function() {
        let selectedValue = $(this).val(); // 변경된 셀렉트 박스의 값
        let userId = $(this).closest('tr').attr('id').replace('user-row-', '');

        // 현재 셀렉트 박스의 클래스를 확인
        let isActive = $(this).hasClass('selectIsActive');
        let userRole = $(this).hasClass('selectUserRole');

        // 배열에서 해당 userId를 가진 객체를 찾습니다.
        let existingChange = changes.find(change => change.userId === userId);

        // 기존 변경 사항이 있을 경우
        if (existingChange) {
            if (isActive) {
                existingChange.isActive = selectedValue;
            }
            if (userRole) {
                existingChange.userRole = selectedValue;
            }
        } else {
            // 기존 변경 사항이 없으면 새로운 객체를 추가합니다.
            let newChange = { userId: userId };

            if (isActive) {
                newChange.isActive = selectedValue;
            }
            if (userRole) {
                newChange.userRole = selectedValue;
            }

            changes.push(newChange);
        }
    });

    // 변경 적용 버튼 클릭 시 모든 변경사항 서버로 전송
    $("#save").click(function() {
        if (changes.length > 0) {
            $.ajax({
                url: '/manage/updateIsActive',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(changes),
                success: function(response) {
                    alert('모든 변경사항이 성공적으로 업데이트되었습니다.');
                    updateUserList(response);
//                    window.location.reload();
                $("#save, #cancel, .selectIsActive, .selectUserRole").hide();
                $("#roleAndIsActiveChange, #roleChangePop, #bulkChange, .searchBar, .selBox, #active, #userRoleVal").show();
                },
                error: function(error) {
                    alert('업데이트 중 오류가 발생했습니다.');
                }
            });
        } else {
            alert('변경된 사항이 없습니다.');
        }
    });

    function updateUserList(updatedList) {
        updatedList.forEach(function(userInfoDto) {
            let userId = userInfoDto.user.id;
            let isActive = userInfoDto.isActive;
            let userRole = userInfoDto.user.userRole;
            let row = $("tr#user-row-" + userId);   // 각 행의 요소 찾기

            let userRoleVal = row.find("#userRoleVal");
            let isActiveSpan = row.find("#active");
            let isActiveSelect = row.find(".selectIsActive");

            isActiveSpan.text(isActive);
            isActiveSelect.val(isActive);
            userRoleVal.text(userRole);

            isActiveSelect.hide();
            isActiveSpan.show();
        });
    }

    $('#roleChangePop').click(function(){
      chargePop = window.open('http://localhost:8080/manage/approvalPop', 'chargePointPop', 'width=800px,height=800px');
    });

    $("#selectApprovalBtn").on("click", function(){
        let selectedIds = [];
        $("input[name='selectedIds']:checked").each(function(){
            let userId = $(this).closest('tr').attr('id').replace('user-row-', '');
            selectedIds.push(userId);
        });
        userRoleAjax(selectedIds);
    });

    $("#approvalBtn").on("click", function(){
        let selectedIds = [];
        let userId = $(this).closest('tr').attr('id').replace('user-row-', '');
        selectedIds.push(userId);
        userRoleAjax(selectedIds);
    });

    function userRoleAjax(data) {
        $.ajax({
            url: '/manage/updateUserRoles',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                alert('모든 변경사항이 성공적으로 업데이트되었습니다.');
                if (window.opener && !window.opener.closed) {
                    window.opener.location.reload();
                }
                // 자식 창 닫기
                window.close();
            },
            error: function(error) {
                alert('업데이트 중 오류가 발생했습니다.');
            }
        });
    }

    function filter() {
        let gradeFilter = $('#gradeFilter').val();
        let userRoleFilter = $('#userRoleFilter').val();
        let isActiveFilter = $('#isActiveFilter').val();
        let searchKeyword = $('#keyword').val();
        $.ajax({
            url: '/manage/filterUsers',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                grade: gradeFilter,
                userRole: userRoleFilter,
                isActive: isActiveFilter,
                keyword: searchKeyword
            }),
            success: function(data) {
                if (data.length > 0 || data !== null) {
                    updateTable(data);
                } else {
                    updateUserList(data);
                }
                console.log(data)
            },
            error: function(error) {
                alert('필터 적용 시 오류가 발생하였습니다. 다시 시도해주세요.');
            }
        });
    }

    $('#gradeFilter, #userRoleFilter, #isActiveFilter').change(function() {
       filter();
    });

    $('#searchBtn').click(function() {
       filter();
    });

    function updateTable(users) {
        const $tbody = $('tbody');
        $tbody.empty(); // Clear existing rows

        const formatCurrency = (amount) => {
            const formattedAmount = new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(amount);
            return formattedAmount.replace('₩', '').trim();
        };

        users.forEach(userInfoDto => {
            const formattedPoint = formatCurrency(userInfoDto.currentPoint);
            const isAdmin = userInfoDto.user.userRole === 'ADMIN';
            const $row = $(`
                <tr id="user-row-${userInfoDto.user.id}" style="text-align: center; height: 50px; margin: auto;">
                    <td>${users.indexOf(userInfoDto) + 1}</td>
                    <td>${userInfoDto.user.id}</td>
                    <td>${userInfoDto.userName}</td>
                    <td>${userInfoDto.birthDate}</td>
                    <td>${userInfoDto.phoneNumber}</td>
                    <td>${userInfoDto.email}</td>
                    <td>${userInfoDto.createdDate}</td>
                    <td>${formattedPoint}</td>
                    <td>${userInfoDto.grade}</td>
                    <td style="width: 8%;">
                        <span class="userRoleVal">${userInfoDto.user.userRole}</span>
                        <select class="form-select-sm selectUserRole" style="display: none; width: 90%;">
                            <option>${userInfoDto.user.userRole}</option>
                            <option value="SELLER">SELLER</option>
                            <option value="USER">USER</option>
                        </select>
                    </td>
                    <td style="width: 8%;">
                        <span class="isActiveVal" data-active="${userInfoDto.isActive}">${userInfoDto.isActive}</span>
                        <select class="form-select-sm selectIsActive" style="display: none; width: 50%;">
                            <option>${userInfoDto.isActive}</option>
                            <option value="Y">Y</option>
                            <option value="N">N</option>
                        </select>
                    </td>
                </tr>
            `);

            $tbody.append($row);
        });
    }
    $("#updateGrade").click(function() {
        if(confirm('회원 등급을 갱신합니다.')) {
            $("#updateGradeForm").submit();
        }
    });
});
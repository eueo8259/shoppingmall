$(document).ready(function() {
    $("#bulkChange").click(function() {
        $(".checkBox, #save, #cancel").show();
        $("#roleChange, #bulkChange, .searchBar").hide();
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
        confirm('변경사항을 취소하고 고객관리 첫화면으로 돌아갑니다.')
        location.reload();
//        $(".checkBox, #save, #cancel, .selectRole").hide();
//        $("#roleChange, #bulkChange, .searchBar, #Role, .selBox").show();
//        $(".selBox").val("");
    });
    $("#isActiveChange").click(function() {
        $("#save, #cancel, .selectIsActive").show();
        $("#isActiveChange, #bulkChange, .searchBar, .selBox, #active").hide();
    });
    var changes = [];

    $(".selectIsActive").change(function() {
        var selectedActive = $(this).val();
        var userId = $(this).closest('tr').attr('id').replace('user-row-', '');

        // 변경된 정보가 있는지 확인
        var existingChange = changes.find(change => change.userId === userId);

        if (existingChange) {
            // 이미 있으면 업데이트
            existingChange.isActive = selectedActive;
        } else {
            // 없으면 새로운 변경사항 추가
            changes.push({ userId: userId, isActive: selectedActive });
        }
    });
    // 변경 적용 버튼 클릭 시 모든 변경사항 서버로 전송
    $("#save").click(function() {
        if (changes.length > 0) {
            $.ajax({
                url: '/manage/updateUserRoles',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(changes),
                success: function(response) {
                    alert('모든 변경사항이 성공적으로 업데이트되었습니다.');
                },
                error: function(error) {
                    alert('업데이트 중 오류가 발생했습니다.');
                }
            });
        } else {
            alert('변경된 사항이 없습니다.');
        }
        location.reload();
    });
});
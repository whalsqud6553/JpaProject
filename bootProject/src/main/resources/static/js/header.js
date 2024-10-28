// html이 다 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {
    // 모달
	$('#member_img').click(function() {
    	const member_modal = document.querySelector('.member_modal');
    	member_modal.showModal();
    })
    $('.member_modal_closeBtn').click(function() {
    	const member_modal = document.querySelector('.member_modal');
    	member_modal.close();
    })
    
    // 로그인
    $('.login_btn').click(function() {
    	location.href = '/member/login';
    })
    // 회원가입
    $('.join_btn').click(function() {
    	location.href = '/member/join';
    })
    // 로그아웃
    $('.logout_btn').click(function() {
    	location.href = '/member/logout';
    })
    // 마이페이지
    $('.mypage_btn').click(function() {
    	location.href = '/member/mypage';
    })
    // 카카오 로그아웃
    $('.kakaoLogout_btn').click(function() {
    	location.href = '/kakaoLogout';
    })
});

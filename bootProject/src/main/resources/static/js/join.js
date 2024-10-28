document.addEventListener('DOMContentLoaded', function() {
	
	// 전부다 인증 되었을때만 회원가입 버튼 활성화
	var id = 0;
	var pw = 0;
	var email = 0;
	var nick = 0;
	
	// 이메일 인증
	var code;
	$('#emailButton').click(function() {
		$.ajax({
			type: "POST",
			url: "/mail/auth",
			data: {
				"email" : $('#userEmail').val()
			},
			success : function(data) {
				console.log($('#userEmail').val());
				alert("해당 이메일로 인증번호 발송이 완료되었습니다. 코드를 입력해 주세요");
				code = data;
				console.log("code : " + code);
			}
		})
	})
	
	const userEmailLabel = document.querySelector('.userEmailLabel');
	const authcodeBox = document.getElementById('authcodeBox');
	authcodeBox.addEventListener('focusout', function() {
		if(code == authcodeBox.value) {
			userEmailLabel.hidden = "";
			userEmailLabel.innerText = "인증이 완료되었습니다.";
			$('.userEmailLabel').css("color", "blue");
			email = 1;
		}
		else {
			userEmailLabel.hidden = "";
			userEmailLabel.innerText = "인증번호가 틀립니다.";
			$('.userEmailLabel').css("color", "red");
			email = 0;
		}
	})
	
	// ID 중복체크
	const userIdLabel = document.querySelector('.userIdLabel');
	$('#userIdButton').click(function() {
		const userid = document.getElementById('userId').value;
		$.ajax({
			type: "POST",
			url: "/member/userIdDuplicationCheck",
			data: {
				"userid" : $('#userId').val()
			},
			success: function(data) {
				if(userid == '' || userid.length < 4) {
					alert('아이디는 4글자 이상만 가능합니다');
					userIdLabel.hidden = "hidden";
					userIdLabel.innerText = "";
					$('#userId').focus();
					id = 0;
				}
				else {
					if(data == 1) {
						userIdLabel.hidden = "";
						userIdLabel.innerText = "사용 가능한 아이디 입니다.";
						$('.userIdLabel').css("color", "blue");
						id = 1;
					}
					else {
						userIdLabel.hidden = "";
						userIdLabel.innerText = "이미 사용중인 아이디 입니다.";
						$('.userIdLabel').css("color", "red");
						id = 0;
					}
				}
			}
		})
	})
	
	// 비밀번호 확인
	const userPasswordLabel = document.querySelector('.userPasswordLabel');
	$('#userPassword').focusout(function() {
		var userpw = document.getElementById('userPassword').value;
		var userpwCheck = document.getElementById('userPasswordCheck').value;
		
		if(userpw.length < 4 || userpwCheck.length < 4) {
			userPasswordLabel.hidden = "";
			userPasswordLabel.innerText = "비밀번호는 4글자 이상만 가능합니다";
			$('.userPasswordLabel').css("color", "red");
			pw = 0;
		}
		else {
			if(userpw == userpwCheck) {
				userPasswordLabel.hidden = "";
				userPasswordLabel.innerText = "비밀번호가 일치합니다.";
				$('.userPasswordLabel').css("color", "blue");
				pw = 1;
			}
			else {
				userPasswordLabel.hidden = "";
				userPasswordLabel.innerText = "비밀번호가 일치하지 않습니다.";
				$('.userPasswordLabel').css("color", "red");
				pw = 0;
			}
		}
		
	})
	$('#userPasswordCheck').focusout(function() {
		var userpw = document.getElementById('userPassword').value;
		var userpwCheck = document.getElementById('userPasswordCheck').value;
		
		if(userpw.length < 4 || userpwCheck.length < 4) {
			userPasswordLabel.hidden = "";
			userPasswordLabel.innerText = "비밀번호는 4글자 이상만 가능합니다";
			$('.userPasswordLabel').css("color", "red");
			pw = 0;
		}
		else {
			if(userpw == userpwCheck) {
				userPasswordLabel.hidden = "";
				userPasswordLabel.innerText = "비밀번호가 일치합니다.";
				$('.userPasswordLabel').css("color", "blue");
				pw = 1;
			}
			else {
				userPasswordLabel.hidden = "";
				userPasswordLabel.innerText = "비밀번호가 일치하지 않습니다.";
				$('.userPasswordLabel').css("color", "red");
				pw = 0;
			}
		}
	})
	
	// 닉네임 중복체크
	const userNicknameLabel = document.querySelector('.userNicknameLabel');
	$('#userNicknameButton').click(function() {
		const userNickname = document.getElementById('userNickname').value;
		$.ajax({
			type: "POST",
			url: "/member/userNicknameDuplicationCheck",
			data : {
				"userNickname" : userNickname
			},
			success: function(data) {
				if(userNickname.length < 2) {
					alert('닉네임은 두글자 이상만 가능합니다');
					userNicknameLabel.hidden = "hidden";
					userNicknameLabel.innerText = "";
					$('#userNickname').focus();
					nick = 0;
				}
				else {
					if(data == 1) {
						userNicknameLabel.hidden = "";
						userNicknameLabel.innerText = "사용 가능한 닉네임 입니다.";
						$('.userNicknameLabel').css("color", "blue");
						nick = 1;
					}
					else {
						userNicknameLabel.hidden = "";
						userNicknameLabel.innerText = "이미 사용중인 닉네임 입니다.";
						$('.userNicknameLabel').css("color", "red");
						nick = 0;
					}
				}
			}
		});
	})
	
	// 클릭했을때 subit
	$('#joinSubmitButton').click(function() {
		
		var joinSubmit = document.getElementById('joinSubmitButton');
		
		if(id == 1 && pw == 1 && nick == 1 && email == 1) {
			joinSubmit.type = "submit";
		}
		else {
			if(id != 1) {
				alert('아이디 중복확인이 완료되지 않았습니다.');
			}
			else if(pw != 1) {
				alert('비밀번호가 유효하지 않습니다.');
			}
			else if(nick != 1) {
				alert('닉네임 중복확인이 완료되지 않았습니다.');
			}
			else {
				alert('이메일 인증이 완료되지 않았습니다.');
			}
		}
		
	})
});

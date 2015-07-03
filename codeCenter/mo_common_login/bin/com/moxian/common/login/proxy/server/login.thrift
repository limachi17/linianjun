namespace java com.moxian.com.login.proxy

struct AuthModel{
	1:i64 userId;  
	2:string token;
}

service LoginServerThrift {


	bool validateToken(1:i64 userId,2:string token)
	
	i64 whetherRegisted(1:string phoneCountryCode,2:string phoneNo)
	
	AuthModel saveAndGetToken(1:i64 userId)
	
	bool destoryAuthByUserId(1:i64 userId)
}

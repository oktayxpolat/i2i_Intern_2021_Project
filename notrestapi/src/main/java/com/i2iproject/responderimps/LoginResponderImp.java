package com.i2iproject.responderimps;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.i2iproject.database.UserInfoGetterUsingPartialUserInfo;
import com.i2iproject.database.models.FullUserInfo;
import com.i2iproject.database.models.PartialUserInfo;
import com.i2iproject.responders.LoginResponder;
import com.i2iproject.responders.models.LoginResponderModel;
import com.i2iproject.responders.models.LoginResponse;

@Component
@Scope("prototype")
public class LoginResponderImp implements LoginResponder{
	private UserInfoGetterUsingPartialUserInfo userInfoGetter;
	
	private LoginResponderModel loginResponderInfo;
	private PartialUserInfo partialUserInfo;
	private FullUserInfo fullUserInfo;
	private LoginResponse response;
	
	public LoginResponderImp(UserInfoGetterUsingPartialUserInfo userInfoGetter) {
		this.userInfoGetter = userInfoGetter;
	}

	@Override
	public LoginResponse respond(LoginResponderModel loginResponderInfo) {
		this.loginResponderInfo = loginResponderInfo;
		
		getPartialUserInfoForRequestingFullUserInfo();
		getFullUserInfo();
		produceLoginResponse();
		return response;
	}

	private void getPartialUserInfoForRequestingFullUserInfo() {
		PartialUserInfo partialInfoForRequestingFullUserInfo = new PartialUserInfo();
		partialInfoForRequestingFullUserInfo.setMsisdn(loginResponderInfo.getMsisdn());
		partialInfoForRequestingFullUserInfo.setPassword(loginResponderInfo.getHashedPassword());
		partialUserInfo = partialInfoForRequestingFullUserInfo;
	}

	private void getFullUserInfo() {
		fullUserInfo = userInfoGetter.getFullUserInfoFromPartialInfo(partialUserInfo);
	}
	
	private void produceLoginResponse() {
		response = new LoginResponse();
		setResponseInfoFromFullUserInfo();
		response.setJwt(loginResponderInfo.getJwt());
	}
	
	private void setResponseInfoFromFullUserInfo() {
		response.setLoginSuccess(true);
		response.setUserId(fullUserInfo.getUserId());
		response.setEmail(fullUserInfo.getEmail());
		response.setLastName(fullUserInfo.getLastName());
		response.setName(fullUserInfo.getName());
		response.setMsisdn(fullUserInfo.getMsisdn());
	}
}

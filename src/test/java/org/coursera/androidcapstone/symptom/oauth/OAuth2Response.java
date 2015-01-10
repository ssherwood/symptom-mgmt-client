package org.coursera.androidcapstone.symptom.oauth;

import com.google.gson.annotations.SerializedName;

/**
 * 
 */
public class OAuth2Response {
	
	@SerializedName("access_token")
	public String accessToken;
	
	@SerializedName("token_type")
	private String tokenType;
	
	@SerializedName("refresh_token")
	public String refreshToken;

	@SerializedName("expires_in")
	private Integer expiresIn;

	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}

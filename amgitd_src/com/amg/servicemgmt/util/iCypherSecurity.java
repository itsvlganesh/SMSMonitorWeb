package com.amg.servicemgmt.util;

public interface iCypherSecurity {
	
	public String encodePassword(String rawSecret, String secretKey);
	public boolean isValidSecret(String encodedSecret, String rawSecret, String secretKey);
}

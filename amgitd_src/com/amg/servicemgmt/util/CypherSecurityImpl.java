package com.amg.servicemgmt.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;


public class CypherSecurityImpl implements iCypherSecurity {

	public String encodePassword(String rawSecret, String secretKey) {
		 
		return new ShaPasswordEncoder(256).encodePassword(rawSecret, secretKey);
	}

	public boolean isValidSecret(String encodedSecret, String rawSecret, String secretKey) {

		return new ShaPasswordEncoder(256).isPasswordValid(encodedSecret, rawSecret, secretKey);
	}

	
}

package org.apache.commons.mail.test;

import java.util.Map;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

public class EmailImpl extends Email {

	@Override
	public Email setMsg(String msg) throws EmailException {
		return null;
	}
	
	/**
	 * I am writing this function as there is no fucntion in Email.java 
	 * that gives me access the headers.
	 * @return
	 */
	public  Map<String, String> getHeaders () {
		return this.headers;
	}
	
	public String getContentType() {
		return this.contentType;
	}
	
	public Object getContent() {
		return this.content;
	}
}

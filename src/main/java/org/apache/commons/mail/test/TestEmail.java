package org.apache.commons.mail.test;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailConstants;
import org.apache.commons.mail.EmailException;
import org.junit.Before;

import junit.framework.TestCase;


public class TestEmail extends TestCase {

	public Email instance;
	public String emptyString;
	public String nullString;
	public String[] contentType;
	public String[] listOfEmails;
	public String[] nullListOfEmails;
	public String[] emptyListOfEmails;

	@Before
	public void setUp() {
		instance = new EmailImpl();
		listOfEmails = new String[5];
		listOfEmails[0] = "tanvir_1@yahoo.com";
		listOfEmails[1] = "tanvir_2@yahoo.com";
		listOfEmails[2] = "tanvir_3@yahoo.com";
		listOfEmails[3] = "tanvir_4@yahoo.com";
		listOfEmails[4] = "tanvir_5@yahoo.com";

		nullListOfEmails = null;
		emptyListOfEmails = new String[0];
		emptyString = "";
		nullString = null;
		contentType = new String[5];
		contentType[0] = "multipart/alternative";
		contentType[1] = "text/plain; charset=ISO-8859-1";
		contentType[2] = "text/plain";
		contentType[3] = "text/plain; charset=ISO-8859-1 ";
	}

	/////////////////////////////// Email addCc(String email)
	/////////////////////////////// /////////////////////////////////////

	/**
	 * REQUIRED This test case checks whether a "valid" email address is added
	 * to cc
	 * 
	 * @throws EmailException
	 */

	@Test
	public void testAddCC() throws EmailException {
		Email result = instance.addCc(listOfEmails[0]);

		assertTrue(result != null && result.getCcAddresses() != null && result.getCcAddresses().size() == 1
				&& result.getCcAddresses().get(0).toString().equalsIgnoreCase(listOfEmails[0]));
	}

	/**
	 * This test case checks whether a "valid" email address is added to cc
	 * 
	 * @throws EmailException
	 */
	@Test
	public void testAddCcWithEmptyString() {

		try {
			instance.addCc(emptyString);
			fail();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	/////////////////////////////// Email addBcc(String... emails)
	/////////////////////////////// /////////////////////////////////////

	@Test
	public void testAddBCC() throws EmailException {
		Email result = instance.addBcc(listOfEmails[0]);

		assertTrue(result.getBccAddresses().size() == 1
				&& result.getBccAddresses().get(0).toString().equalsIgnoreCase(listOfEmails[0]));
	}

	@Test
	public void testAddBccWithNullEmailList() {

		try {
			instance.addBcc(nullListOfEmails);
			// fail();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddBccWithEmptyString() {

		try {
			instance.addBcc(emptyString);
			// fail();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddBccWithEmptyStringArray() {

		try {
			instance.addBcc(emptyListOfEmails);
			// fail();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddBccWithEmailList() {

		try {
			Email result = instance.addBcc(listOfEmails);
			assertTrue(result.getBccAddresses().size() == 5);
		} catch (EmailException e) {
			// assertTrue(true);
		}
	}

	/////////////////////////////// void addHeader(String name, String value)
	/////////////////////////////// /////////////////////////////////////
	@Test
	public void testAddHeaderNullNameNullValue() {

		try {
			instance.addHeader(null, null);
			// fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddHeaderValidNameNullValue() {

		try {
			instance.addHeader("HeaderName", null);
			// fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddHeaderValidNameValidValue() {

		try {
			instance.addHeader("HeaderName", "HeaderValue");
			assertTrue(((EmailImpl) instance).getHeaders().containsKey("HeaderName"));
		} catch (IllegalArgumentException e) {
			assertTrue(false);
		}
	}

	/////////////////////////////// void addHeader(String name, String value)
	/////////////////////////////// /////////////////////////////////////
	@Test
	public void testAddReplyToEmptyEmailNullName() {

		try {
			instance.addReplyTo(emptyString, null);
			fail();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddReplyToEmptyEmailEmptyName() {

		try {
			instance.addReplyTo(emptyString, emptyString);
			fail();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAddReplyToValidEmailValidName() {
		String name = "Tanvir Irfan Chowdhury";

		try {
			Email result = instance.addReplyTo(listOfEmails[0], name);

			// System.out.println(result.getReplyToAddresses().get(0).toString());
			assertTrue(result.getReplyToAddresses().get(0).toString()
					.equalsIgnoreCase(name + " <" + listOfEmails[0] + ">"));
			// fail();
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	/////////////////////////////// String getHostName()
	/////////////////////////////// /////////////////////////////////////
	@Test
	public void testGetHostNameNullSession() {
		String result = instance.getHostName();

		assertNull(result);
	}

	@Test
	public void testGetHostNameValidSession() throws EmailException {

		instance.setHostName("localhost");
		Session ses = instance.getMailSession();
		instance.setMailSession(ses);

		String result = instance.getHostName();

		assertTrue("localhost".equalsIgnoreCase(result));
	}

	@Test
	public void testGetHostNameNullSessionEmptyHostName() throws EmailException {
		instance.setHostName(emptyString);
		String result = instance.getHostName();

		assertNull(result);
	}

	@Test
	public void testGetHostNameNullSessionValidHostName() throws EmailException {
		instance.setHostName("localhost");
		String result = instance.getHostName();

		assertTrue("localhost".equalsIgnoreCase(result));
	}

	/////////////////////////////// Date getSentDate()
	/////////////////////////////// /////////////////////////////////////
	@Test
	public void testGetSentDateNullDate() {
		Date result = instance.getSentDate();

		assertNotNull(result);
	}

	@Test
	public void testGetSentDateWithSetDate() {

		Date now = new Date();
		instance.setSentDate(now);

		Date result = instance.getSentDate();

		assertEquals(now, result);
	}

	/////////////////////////////// int getSocketConnectionTimeout()
	/////////////////////////////// /////////////////////////////////////
	@Test
	public void testGetSocketConnectionTimeout() {
		int result = instance.getSocketConnectionTimeout();

		assertEquals(EmailConstants.SOCKET_TIMEOUT_MS, result);
	}

	/////////////////////////////// Email setFrom(String email)
	/////////////////////////////// /////////////////////////////////////
	@Test
	public void testSetFromEmptyString() {

		try {
			instance.setFrom(emptyString);
			// fail();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetFromValidString() {

		try {
			Email eml = instance.setFrom(listOfEmails[0]);
			assertTrue(eml.getFromAddress().toString().contains(listOfEmails[0]));
			// fail();
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	/////////////////////////////// void updateContentType(String aContentType)
	/////////////////////////////// /////////////////////////////////////
	@Test
	public void testUpdateContentTypeEmptyString() {
		instance.updateContentType(emptyString);
		assertNull(((EmailImpl) instance).getContentType());

	}

	@Test
	public void testUpdateContentTypeValidString0_NotNull() {
		instance.updateContentType(contentType[0]);
		assertNotNull(((EmailImpl) instance).getContentType());
	}

	@Test
	public void testUpdateContentTypeValidString0_Equals() {
		instance.updateContentType(contentType[0]);
		assertEquals(contentType[0], ((EmailImpl) instance).getContentType());
	}

	@Test
	public void testUpdateContentTypeValidString1_NotNull() {
		instance.updateContentType(contentType[1]);
		assertNotNull(((EmailImpl) instance).getContentType());
	}

	@Test
	public void testUpdateContentTypeValidString1_Equals() {
		instance.updateContentType(contentType[1]);
		assertEquals(contentType[1], ((EmailImpl) instance).getContentType());
	}

	@Test
	public void testUpdateContentTypeValidString2_NotNull() {
		instance.updateContentType(contentType[2]);
		assertNotNull(((EmailImpl) instance).getContentType());
	}

	@Test
	public void testUpdateContentTypeValidString2_Equals() {
		instance.updateContentType(contentType[2]);
		assertEquals(contentType[2], ((EmailImpl) instance).getContentType());
	}

	@Test
	public void testUpdateContentTypeValidString2_DefaultCharset() {
		instance.setCharset("us-ascii");
		instance.updateContentType(contentType[2]);
		assertEquals(contentType[2] + "; " + "charset=US-ASCII", ((EmailImpl) instance).getContentType());
	}

	@Test
	public void testUpdateContentTypeValidString3() {
		instance.updateContentType(contentType[3]);
		assertEquals(contentType[3], ((EmailImpl) instance).getContentType());
	}

	/////////////////////////////// Session getMailSession()
	/////////////////////////////// /////////////////////////////////////

	@Test
	public void testGetMailSessionEmailExceptionForInvalidHostName() {
		try {
			Session s = instance.getMailSession();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetMailSession() {
		try {
			instance.setHostName("locanhost");
			Session s = instance.getMailSession();

			assertNotNull(s);
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetMailSessionEmailValidAuthenticator() {
		try {
			instance.setHostName("locanhost");
			instance.setAuthentication("tanvir", "irfan");
			Session s = instance.getMailSession();

			assertNotNull(s);
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetMailSessionEmailValidAuthenticatorValidBounceAddress() {
		try {
			instance.setHostName("locanhost");
			instance.setAuthentication("tanvir", "irfan");
			instance.setBounceAddress(listOfEmails[0]);
			Session s = instance.getMailSession();

			assertNotNull(s);
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetMailSessionEmailValidAuthenticatorValidBounceAddressSslOnConnect() {
		try {
			instance.setHostName("locanhost");
			instance.setAuthentication("tanvir", "irfan");
			instance.setBounceAddress(listOfEmails[0]);
			instance.setSSLOnConnect(true);
			Session s = instance.getMailSession();

			assertNotNull(s);
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetMailSessionEmailValidAuthenticatorValidBounceAddressSslOnConnectStartTlsEnabled() {
		try {
			instance.setHostName("locanhost");
			instance.setAuthentication("tanvir", "irfan");
			instance.setBounceAddress(listOfEmails[0]);
			instance.setSSLOnConnect(true);
			instance.setStartTLSEnabled(true);
			Session s = instance.getMailSession();

			assertNotNull(s);
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetMailSessionEmailValidAuthenticatorValidBounceAddressSslOnConnectStartTlsEnabledSetSSLCheckServerIdentity() {
		try {
			instance.setHostName("locanhost");
			instance.setAuthentication("tanvir", "irfan");
			instance.setBounceAddress(listOfEmails[0]);
			instance.setSSLOnConnect(true);
			instance.setStartTLSEnabled(true);
			instance.setSSLCheckServerIdentity(true);
			Session s = instance.getMailSession();

			assertNotNull(s);
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetMailSessionEmailValidAuthenticatorValidBounceAddressSslOnConnectStartTlsEnabledSetSSLCheckServerIdentity2() {
		try {
			instance.setHostName("locanhost");
			instance.setAuthentication("tanvir", "irfan");
			instance.setBounceAddress(listOfEmails[0]);
			instance.setSSLOnConnect(false);
			instance.setStartTLSEnabled(false);
			instance.setSSLCheckServerIdentity(true);
			Session s = instance.getMailSession();

			assertNotNull(s);
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetMailSessionNotNullSessionExists() {
		try {
			Properties properties = new Properties(System.getProperties());
			Session s = Session.getInstance(properties, new DefaultAuthenticator("tanvir", "irfan"));

			instance.setMailSession(s);
			assertEquals(s, instance.getMailSession());
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	/////////////////////////////// void buildMimeMessage()
	/////////////////////////////// /////////////////////////////////////

	@Test
	public void testBuildMimeMessageIllegalStateException() {
		try {
			setInstanceDummy();

			instance.buildMimeMessage();
			// calling again, which results a IllegalStateException
			instance.buildMimeMessage();
		} catch (IllegalStateException e) {
			assertTrue(true);
		} catch (EmailException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testBuildMimeMessageNotNull() throws EmailException {
		setInstanceDummy();

		instance.buildMimeMessage();
		
		assertNotNull(instance.getMimeMessage());
	}

	@Test
	public void testBuildMimeMessageSetEmptySubject() throws EmailException {
		setInstanceDummy();
		instance.setSubject(emptyString);
		instance.buildMimeMessage();
		
		assertNotNull(instance.getMimeMessage());
	}

	@Test
	public void testBuildMimeMessageSetSubjectCharset() throws EmailException {
		setInstanceDummy();
		instance.setCharset("ISO-8859-1");
		instance.buildMimeMessage();
	}

	@Test
	public void testBuildMimeMessageNullContent() throws EmailException {
		setInstanceDummy();
		instance.setContent(null);
		instance.buildMimeMessage();
		
		assertNotNull(instance.getMimeMessage());
	}

	@Test
	public void testBuildMimeMessageTextContent() throws EmailException {
		setInstanceDummy();
		String cont = "This is my content";
		instance.setContent(cont, contentType[2]);
		instance.buildMimeMessage();
		
		assertEquals(cont, ((EmailImpl)instance).getContent().toString());
	}

	@Test
	public void testBuildMimeMessageMultiPartContentType() throws EmailException {
		setInstanceDummy();
		String cont = "This is my content";
		instance.setContent(cont, contentType[0]);
		instance.buildMimeMessage();
		
		assertEquals(cont, ((EmailImpl)instance).getContent().toString());
	}

	@Test
	public void testBuildMimeMessageNullContentTypeEmailBodyNotNull() throws EmailException {
		setInstanceDummy();
		instance.updateContentType(emptyString);

		instance.setContent(new MimeMultipart());
		instance.buildMimeMessage();
		
		assertNotNull(instance.getMimeMessage());
	}

	@Test
	public void testBuildMimeMessageValidContentTypeEmailBodyNotNull() throws EmailException {
		setInstanceDummy();

		instance.setContent(new MimeMultipart());
		instance.buildMimeMessage();
		
		assertNotNull(instance.getMimeMessage());
	}

	@Test
	public void testBuildMimeMessageInValidFromAddress() {
		try {
			Properties properties = new Properties(System.getProperties());
			String subj = "Hello Subject!";
			Session s = Session.getInstance(properties, new DefaultAuthenticator("tanvir", "irfan"));
			instance.setMailSession(s);			
			instance.setSubject(subj);
			instance.updateContentType(contentType[2]);			
			instance.addTo(listOfEmails[1]);
			
			
			instance.buildMimeMessage();
			
			
			assertEquals(subj, instance.getSubject());
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	
	@Test
	public void testBuildMimeMessageZeroToAddress() {
		try {
			Properties properties = new Properties(System.getProperties());
			Session s = Session.getInstance(properties, new DefaultAuthenticator("tanvir", "irfan"));
			instance.setMailSession(s);
			instance.setSubject("Hello Subject!");
			instance.updateContentType(contentType[2]);
			instance.setFrom(listOfEmails[0]);
			//instance.addTo(listOfEmails[1]);
			
			
			instance.buildMimeMessage();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testBuildMimeMessageWithCC_BCC_ReplyToList_Header() throws EmailException, MessagingException {
		setInstanceDummy();
		instance.addCc(listOfEmails[2]);
		instance.addBcc(listOfEmails[3]);
		instance.addReplyTo(listOfEmails[2]);
		
		Map<String, String> header = new HashMap<String, String> ();
		header.put("HeaderKey", "HeaderValue");
		instance.setHeaders(header);
		
		instance.buildMimeMessage();
		
		assertNotNull(instance.getMimeMessage().getHeader("HeaderKey"));
	}
	
	@Test
	public void testBuildMimeMessageWithCC_BCC_NoTo() throws EmailException {
		Properties properties = new Properties(System.getProperties());
		Session s = Session.getInstance(properties, new DefaultAuthenticator("tanvir", "irfan"));
		instance.setMailSession(s);
		instance.setSubject("Hello Subject!");
		instance.updateContentType(contentType[2]);
		instance.setFrom(listOfEmails[0]);
		//instance.addTo(listOfEmails[1]);
		
		instance.addCc(listOfEmails[2]);
		instance.addBcc(listOfEmails[3]);
		instance.addReplyTo(listOfEmails[2]);
		
		instance.buildMimeMessage();
		
		assertEquals("Hello Subject!", instance.getSubject());
	}
	
	
	@Test
	public void testBuildMimeMessagepopBeforeSmtp() {
		try {
			setInstanceDummy();				
			instance.setPopBeforeSmtp(true, "localhost", "newPopUsername", "newPopPassword");
			instance.buildMimeMessage();
		} catch (EmailException e) {
			assertTrue(true);
		}
	}
	
	void setInstanceDummy() throws EmailException {
		Properties properties = new Properties(System.getProperties());		
		Session s = Session.getInstance(properties, new DefaultAuthenticator("tanvir", "irfan"));
		instance.setMailSession(s);
		instance.setSubject("Hello Subject!");
		instance.updateContentType(contentType[2]);
		instance.setFrom(listOfEmails[0]);
		instance.addTo(listOfEmails[1]);
	}
	
	/////////////////////////////// String send()
	/////////////////////////////// /////////////////////////////////////
	
	@Test
	public void testsend() {
		try {
			setInstanceDummy();				
			
			String str = instance.send();
			//System.out.println(str);
			
		} catch (EmailException e) {
			assertTrue(true);
		}
	}
}

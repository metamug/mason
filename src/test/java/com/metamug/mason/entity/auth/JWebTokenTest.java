/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mason.entity.auth;

import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author user
 */
public class JWebTokenTest {

	LocalDateTime ldt;
	JSONObject payload;

	public JWebTokenTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
		ldt = LocalDateTime.now().plusDays(90);
		payload = new JSONObject("{\"sub\":\"1234\",\"aud\":[\"admin\"],"
				+ "\"exp\":" + ldt.toEpochSecond(ZoneOffset.UTC) + "}");
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of HMACSHA256 method, of class JWebToken.
	 */
	@org.junit.Test
	public void testWithData() {
		//generate JWT
		long exp = LocalDateTime.now().plusDays(90).toEpochSecond(ZoneOffset.UTC);
		String token = new JWebToken("1234", new JSONArray("['admin']"), exp).toString();
		//verify and use
		JWebToken incomingToken;
		System.out.println(token);
		try {
			incomingToken = new JWebToken(token);
			if (incomingToken.isValid()) {
				Assert.assertEquals("1234", incomingToken.getSubject());
				Assert.assertEquals("admin", incomingToken.getAudience().get(0));
			}
		} catch (NoSuchAlgorithmException ex) {
			fail("Invalid Token" + ex.getMessage());
		}

	}

	@org.junit.Test
	public void testWithJson() {

		String token = new JWebToken(payload).toString();
		//verify and use
		JWebToken incomingToken;
		try {
			incomingToken = new JWebToken(token);
			if (incomingToken.isValid()) {
				Assert.assertEquals("1234", incomingToken.getSubject());
				Assert.assertEquals("admin", incomingToken.getAudience().get(0));
			}
		} catch (NoSuchAlgorithmException ex) {
			fail("Invalid Token" + ex.getMessage());
		}
	}

	@org.junit.Test(expected = IllegalArgumentException.class)
	public void testBadHeaderFormat() {

		String token = new JWebToken(payload).toString();
		token = token.replaceAll("\\.", "X");
		//verify and use
		JWebToken incomingToken;
		try {
			incomingToken = new JWebToken(token);
			if (incomingToken.isValid()) {
				Assert.assertEquals("1234", incomingToken.getSubject());
				Assert.assertEquals("admin", incomingToken.getAudience().get(0));
			}
		} catch (NoSuchAlgorithmException ex) {
			fail("Invalid Token" + ex.getMessage());
		}
	}

	@org.junit.Test(expected = NoSuchAlgorithmException.class)
	public void testIncorrectHeader() throws NoSuchAlgorithmException {

		String token = new JWebToken(payload).toString();
		token = token.replaceAll("[^.]", "X");
		//verify and use
		JWebToken incomingToken;

		incomingToken = new JWebToken(token);
		if (incomingToken.isValid()) {
			Assert.assertEquals("1234", incomingToken.getSubject());
			Assert.assertEquals("admin", incomingToken.getAudience().get(0));
		}
	}
}

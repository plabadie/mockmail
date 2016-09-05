/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Patrick Labadie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.plabadie.mockmail;

import com.plabadie.mockmail.server.MockMailServer;
import org.apache.commons.mail.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MailTests {


	protected MockMailServer mockMailServer;

	@Before
	public void setup()
	{
		mockMailServer = new MockMailServer();
		mockMailServer.serve();
	}

	@After
	public void tearDown()
	{
		mockMailServer.stop();
	}

	@Test
	public void testCase01SendSimpleMessage() throws Exception {
		Email email = new SimpleEmail();
		email.setHostName("localhost");
		email.setSmtpPort(2525);
		email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setTLS(true);
		email.setFrom("testcase@plabadie.com");
		email.setSubject("Test");
		email.setMsg("This is a test.");
		email.addTo("test@plabadie.com");
		email.send();
	}

	@Test
	public void testCase02SendAttachement() throws Exception {
		// Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("/Users/plabadie/Projects/mockmail/src/test/java/com/plabadie/mockmail/attachment.jpg");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Picture description");
        attachment.setName("Picture");

        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("localhost");
        email.setSmtpPort(2525);
        email.setFrom("testcase@plabadie.com");
        email.setSubject("Test");
        email.setMsg("This is a test.");
        email.addTo("test@plabadie.com");

        // add the attachment
        email.attach(attachment);

        // send the email
        email.send();
	}

	

}

/*
 * Copyright 2014 Patrick Labadie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.plabadie.mockmail.handler;

import org.apache.commons.io.FileUtils;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;

import java.io.*;

public class FileOutMessageHandler implements MessageHandlerFactory
{
	private int sequence = 0;
	File fileOutFolder = null;



    public MessageHandler create(MessageContext ctx)
    {
        return new Handler(ctx);
    }
    

    class Handler implements MessageHandler
    {
        MessageContext ctx;



        public Handler(MessageContext ctx)
        {
                this.ctx = ctx;
        }



        public void data(InputStream data) throws IOException
        {
        		String mailData = this.convertStreamToString(data);

        		sequence++;
                
                File dataFile = new File( getFileOutFolder().getAbsolutePath() + "/mail-" + sequence + ".eml" );
                
                while ( dataFile.exists() )
                {
                	sequence++;
                	dataFile = new File( getFileOutFolder().getAbsolutePath() + "/mail-" + sequence + ".eml" );
                }
                
                FileUtils.writeStringToFile( dataFile , mailData);
                System.out.println("Recieved " + dataFile.getName() );
        }



        public String convertStreamToString(InputStream is)
        {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                
                String line = null;
                try
                {
                    while ((line = reader.readLine()) != null)
                    {
                            sb.append(line + "\n");
                    }
                }
                catch (IOException e)
                {
                        e.printStackTrace();
                }
                return sb.toString();
        }



        public void from(String from) throws RejectException{}

        public void recipient(String recipient) throws RejectException {}

        public void done(){}
    }



	public File getFileOutFolder()
    {
		return fileOutFolder;
	}



	public void setFileOutFolder(File fileOutFolder)
    {
		this.fileOutFolder = fileOutFolder;
	}
}
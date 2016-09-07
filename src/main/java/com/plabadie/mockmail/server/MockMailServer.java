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
package com.plabadie.mockmail.server;


import java.io.File;

import com.plabadie.mockmail.util.App;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import com.plabadie.mockmail.handler.FileOutMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.SMTPServer;

public class MockMailServer
{

    private static Logger logger = LoggerFactory.getLogger( MockMailServer.class );

    private int port;
	private String outDirPath;
	SMTPServer smtpServer;

	public MockMailServer()
    {
		super();
		
		this.setOutDirPath("mail");
		this.setPort(2525);
		
	}



	public static void main(String[] args)
    {
        try
        {
            MockMailServer mms = new MockMailServer();

            mms.initWithCommandLine( args );

            mms.serve();
        }
        catch ( Exception e )
        {
            logger.error( "Mockmail error " , e );
        }
	}



	public void serve()
    {
		File outdir = new File(getOutDirPath());

        logger.info( "Mockmail " + App.getVersion() + " SMTP Server starting on port " + this.getPort() );
        logger.info( "Output in " + outdir.getAbsolutePath() );

		FileOutMessageHandler messageHandler = new FileOutMessageHandler();
		messageHandler.setFileOutFolder( outdir );
		
		smtpServer = new SMTPServer(messageHandler);
		smtpServer.setPort(this.getPort());
		smtpServer.start();

        logger.info( "Mockmail ready. Press ctrl-c to stop." );
	}

	public void stop()
	{
		logger.info( "Mockmail shutting down." );
		smtpServer.stop();
	}



	protected void initWithCommandLine( String[] args )
    {
		Option help = new Option( "help", "print this message" );

		Option port   = OptionBuilder.withArgName( "portnumber" )
                .hasArg()
                .withDescription(  "Port number where mockmail listens for smtp requests. Default: 2525." )
                .create( "port" );
		
		Option outdir = OptionBuilder.withArgName( "path" )
                .hasArg()
                .withDescription(  "Folder where the email files will be stored. Can be relative to the working dir. Default: 'mail'" )
                .create( "outdir" );

		Options options = new Options();

		options.addOption( help );
		options.addOption( port );
		options.addOption( outdir );

		// create the parser
	    CommandLineParser parser = new GnuParser();
	    CommandLine line = null;

	    try
        {
	        // parse the command line arguments
	        line = parser.parse( options, args );
	    }
	    catch( ParseException exp )
        {
	        // oops, something went wrong
	        System.err.println( exp.getMessage() );
	        System.exit(1);
	    }		

	    if( line.hasOption( "help" ) )
        {
		    // automatically generate the help statement
		    HelpFormatter formatter = new HelpFormatter();
		    formatter.printHelp( "java -jar mockmail.jar or ./mockmail.sh", options );
		    System.exit(0);
	    }
	    
	    if( line.hasOption( "port" ) )
        {
	    	String portStringValue = line.getOptionValue( "port" );

	    	if ( StringUtils.isNotBlank( portStringValue ) && StringUtils.isNumeric( portStringValue ) )
            {
	    		
	    		this.setPort( Integer.parseInt( portStringValue ) );
	    		
	    	}
	    	
	    }
	    if( line.hasOption( "outdir" ) )
        {
	    	
	    	String outDirStringValue = line.getOptionValue( "outdir" ); 

	    	if ( StringUtils.isNotBlank( outDirStringValue  ) )
            {
	    		
	    		this.setOutDirPath(outDirStringValue);
	    		
	    	}
	    }
	}



	public int getPort()
    {
		return port;
	}



	public void setPort(int port)
    {
		this.port = port;
	}



	public String getOutDirPath()
    {
		return outDirPath;
	}



	public void setOutDirPath(String outDirPath)
    {
		this.outDirPath = outDirPath;
	}
}
package com.plabadie.mockmail.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by plabadie on 2014-05-18.
 */
public class App
{
    private static Logger logger = LoggerFactory.getLogger( App.class );
    private static Properties properties;

    public static String getVersion()
    {
        return getProperties().getProperty( "version" );
    }

    public static Properties getProperties()
    {
        if ( properties != null )
        {
            return properties;
        }

        properties = new Properties();
        InputStream input = null;

        try
        {
            input = App.class.getClassLoader().getResourceAsStream( "application.properties" );
            properties.load( input );
        }
        catch ( Exception e )
        {
            logger.error( "An error occured trying to read the application properties" , e );
        }
        finally
        {
            if ( input != null )
            {
                try
                {
                    input.close();
                }
                catch ( IOException e )
                {
                    logger.error( "An error occured trying to close the application properties"  );
                }
            }
        }

        return properties;
    }
}

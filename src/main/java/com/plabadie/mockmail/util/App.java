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
package com.plabadie.mockmail.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

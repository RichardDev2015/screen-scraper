package scrape;
/**
 * This class is thrown if an invalid url is found
 *
 */
public class InvalidURLException extends Exception
{
    public InvalidURLException(String msg) 
    {
        super(msg);
    }

    public InvalidURLException(String msg, Throwable cause) 
    {
        super(msg, cause);
    }
}

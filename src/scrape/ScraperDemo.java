package scrape;

import java.io.IOException;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.OrderedJSONObject;

/**
 * This defines the interface class that will be used to scrapes an html page containing a list of product details.
 *
 */
public interface ScraperDemo
{
  public  final String TITLE="title";
  public  final String UNIT_PRICE="unit_price";
  public  final String TOTAL="total";
  public  final String RESULT="result";
  public  final String DESCRIPTION="description";
  public  final String SIZE="size";
  public OrderedJSONObject processScreen(String url)throws InvalidURLException, IOException, JSONException;

}

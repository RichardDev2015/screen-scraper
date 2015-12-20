package scrape;

import java.io.IOException;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.OrderedJSONObject;
import org.apache.wink.json4j.JSONArray;

/**
 * This class acts as a mock replacement of the actual implementation.
 * This class validates the url passed in. It also returns the expected
 * result of scraping the html page as a Json object.
 *
 */
public class TestScraperDemoImpl implements ScraperDemo
{
  
 /**
   * This simulates the results of scraping the html page.
   * It builds up a json object of all the product details on the 
   * page.
   * @param url location of html page
   * @return scraped product result as a json object
   */
  public OrderedJSONObject processScreen(String url)throws InvalidURLException, IOException, JSONException
  {
    if ((url==null)||(url.length()==0)) throw new InvalidURLException("Invalid url, has no length"); 
    OrderedJSONObject result = new OrderedJSONObject();
    JSONArray siteProducts = new JSONArray();
    OrderedJSONObject product;
    result.put("result", siteProducts);
   
    product = new OrderedJSONObject();
    product.put(TITLE, "Sainsbury's Apricot Ripe & Ready x5");
    product.put(UNIT_PRICE, "3.5");
    siteProducts.add(product);
    
    product = new OrderedJSONObject();
    product.put(TITLE, "Sainsbury's Avocado Ripe & Ready XL Loose 300g");
    product.put(UNIT_PRICE, "1.5");
    siteProducts.add(product);
     
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Avocado, Ripe & Ready x2");
    product.put("unit_price", "1.8");
    siteProducts.add(product);       
    
    product = new OrderedJSONObject();
    product.put(TITLE, "Sainsbury's Avocados, Ripe & Ready x4");
    product.put(UNIT_PRICE, "3.2");
    siteProducts.add(product);       
    
    product = new OrderedJSONObject();
    product.put(TITLE, "Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)");
    product.put(UNIT_PRICE, "1.5");
    siteProducts.add(product);           
    
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Golden Kiwi x4");
    product.put("unit_price", "1.8");
    siteProducts.add(product);        
    
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Kiwi Fruit, Ripe & Ready x4");
    product.put("unit_price", "1.8");
    siteProducts.add(product);             
               
    
    result.put(RESULT, siteProducts);
    result.put(TOTAL, "15.1");   
        
    return result;
  }
 
}

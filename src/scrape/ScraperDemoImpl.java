package scrape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.OrderedJSONObject;
import org.apache.wink.json4j.JSONArray;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

/**
 * This class scrapes an html page containing a list of product details (i.e. title, description and unit price) 
 * Each product details a summary of the product with a link to another page containing more details about the product.
 * Each products title, description and unit price are stored in a Json object along with the size of the products linked page
 * excluding assets.
 *
 */
public class ScraperDemoImpl implements ScraperDemo
{
	
 /**
   * This method scrapes a html page for product information and returns the result as a json object
   * @param url location of the html page
   * @return json object containing the result from the html scrape
   */
  public OrderedJSONObject processScreen(String url)throws IOException,InvalidURLException, JSONException
  {
	//Throw an exception if we do not have a valid url
    if ((url==null)||(url.length()==0)) throw new InvalidURLException("Invalid url, has no length"); 
    
    //Holds the result from scraping the html page
    OrderedJSONObject result = new OrderedJSONObject();
    
    //Contains a list of OrderedJSONObject. Each object represents
    //a product that has been scraped.
    JSONArray siteProducts = new JSONArray();
    
    //Define local variables used to process each product during screen scraping
    String title="";
    long size=0;     
    String unit_price="";
    String description="";
    float totalPrice=0.0f;
        
    //Ref the list of scraped products
    result.put("result", siteProducts);
    
    //Connect to the html file to be scraped. 
    //Store the contents of the file within a Document object
    Document doc = Jsoup.connect(url).get();
    
    //Holds a scraped products details
    OrderedJSONObject product=null; 
    
    //Get all of the products within the html page
    Elements products=doc.getElementsByClass("product");
    
    //Loop around all the products
    for (Element productElement : products)
    {
      //Get all of the Product links
      Elements links = productElement.getElementsByTag("a");
      if (links.size()==1)
      {
         //Get the product title
         Element link= links.get(0);
         size=getFileSize(link.attr("href"));
         title=link.ownText();
         
         //Get the product unit price
         Elements prices= productElement.getElementsByClass("pricePerUnit");
         if (prices.size()==1)
         {
           //Get the price
           Element price = prices.get(0);
           unit_price=price.text();
         }
         
      }// if (links.size()==1)
      
      //Create a new Product holder object
      product = new OrderedJSONObject();

      //Add the current product unit_cost to the total price
      totalPrice+=getPrice(unit_price);
    
      //Add the product details to a OrderedJSONObject object
      product.put(TITLE, title);
      product.put(DESCRIPTION, description);
      product.put(UNIT_PRICE, ""+getPrice(unit_price));
      product.put(SIZE, size+"kb");
      
      //Add the json product object to the siteProducts
      siteProducts.add(product);
      
    }//for (Element element : elements)
       
    //Add the total price (i.e. all the unit price for each product)
    result.put(TOTAL, ""+totalPrice);
    
    //Return the result as a json object containing all the products scraped details
    return result;
  }
  /**
   * This method removes /, pound and unit to allow the unit price of the
   * product to be found.
   * @param price
   * @return
   */
  private float getPrice(String price)
  {
    price=price.replaceAll("[^a-zA-Z0-9\\s\\._-]", "");
    price=price.replaceFirst("unit", "");
    price=price.replaceFirst("pound", "");
    return new Float(price);
  }
  
  
  /**
   * This method returns back the size of the html page associated with the link excluding any assets.
   * The method reads each line of the html file and sums the byte counts for all resources loaded.
   * The size of any assets such as css, javascript and images are removed from the total html page size.
   * @param url
   * @return
   * @throws IOException
   */
  private static int getFileSize(String url) throws IOException 
  {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    httpGet.addHeader("User-Agent", "Mozilla/5.0");
    CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
 
    BufferedReader reader = new BufferedReader(new InputStreamReader(
            httpResponse.getEntity().getContent()));

    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = reader.readLine()) != null) 
    {
    	//Don't process any asset, obtain the size, so that
    	//it can be taken away from the total file size

    	//Store the line content
    	response.append(inputLine);
    }
    reader.close();
    httpClient.close();
    
    //Convert to kbytes
    int fileSize=response.toString().getBytes().length/1000;
    
    //Remove the total asset size away from the current file size with asset, to obtain
    //the file size without assets.
    
    return fileSize;
  }

  
  public static void main(String[] args) throws IOException, InvalidURLException, JSONException
  {   
    String url="http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";   
    OrderedJSONObject result =new ScraperDemoImpl().processScreen(url);
    System.out.println(result);
  } 
  
  
  
  
}

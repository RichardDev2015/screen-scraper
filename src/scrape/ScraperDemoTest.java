package scrape;

import java.io.IOException;
import java.util.Iterator;
import org.apache.wink.json4j.OrderedJSONObject;
import org.junit.Test;
import junit.framework.TestCase;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONArray;

/**
 * This class will be used to unit test the ScraperDemoImpl class.
 * It will check the following:-
 * 1) Checks that the product data returned from the scrape is as expected.
 * 2) Checks that the sum of the products unit price is as expected.
 * 3) Checks that the url location to the html page to be scraped is valid.
 * 4) Checks that the total number of products scraped is as expected.
 * This class sets up valid expected product test data. This data is used by
 * the test to validate the product scrape result.
 *
 */
public class ScraperDemoTest extends TestCase
{
  private OrderedJSONObject validData;
   
  private static final String VALID_TOTAL_VALUE="15.1";
  private static final String INVALID_TOTAL_VALUE="151.10";
  
  private static final int VALID_NO_PRODUCTS=7;
  
  private static final String validURL="http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
  private static final String inValidURL=null;
    
  private ScraperDemo scraperDemo=null;
  
  /**
   * Setup the expected valid test data, containing
   * expected
   * @throws JSONException
   */
  private void setUpValidTestData()throws JSONException
  { 
    validData = new OrderedJSONObject();
    JSONArray siteProducts = new JSONArray();
    OrderedJSONObject product;
    validData.put("result", siteProducts);
   
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Apricot Ripe & Ready x5");
    product.put("unit_price", "3.5");
    siteProducts.add(product);
    
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Avocado Ripe & Ready XL Loose 300g");
    product.put("unit_price", "1.5");
    siteProducts.add(product);
    
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Avocado, Ripe & Ready x2");
    product.put("unit_price", "1.8");
    siteProducts.add(product);    
    
    
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Avocados, Ripe & Ready x4");
    product.put("unit_price", "3.2");
    siteProducts.add(product);       
    
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)");
    product.put("unit_price", "1.5");
    siteProducts.add(product);           
    
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Golden Kiwi x4");
    product.put("unit_price", "1.8");
    siteProducts.add(product);      
    
    product = new OrderedJSONObject();
    product.put("title", "Sainsbury's Kiwi Fruit, Ripe & Ready x4");
    product.put("unit_price", "1.8");
    siteProducts.add(product);             
    
    validData.put("result", siteProducts);
    validData.put("total", "15.10");
  }
 
    
  /**
   * Setup the valid and invalid test data representing a screen scrape
   */
  @Override
  public void setUp() throws JSONException
  {
    //Setup valid test data
    setUpValidTestData();
        
    //Create Mock object of the ScraperDemoImpl.java
    //scraperDemo=new TestScraperDemoImpl();
    
    //Setup the actual implementation object
    scraperDemo=new ScraperDemoImpl();
   } 
   

 /**
   * This test checks the result from the screen scrape with valid test values
   * The test checks each scraped product title and associated unit price against
   * valid product title and associated unit price test data values. The test will pass if
   * all the scraped products match the valid test data
   */ 
  @Test  
  public void testValidResponse()
  {
    try
    {
      //Get the result from the screen scrape of the html page
      OrderedJSONObject screenScrapeResult=scraperDemo.processScreen(validURL);
      
      //Get the screen scrape products
      JSONArray siteProducts=(JSONArray) screenScrapeResult.get(TestScraperDemoImpl.RESULT);
      Iterator<OrderedJSONObject> products=siteProducts.iterator();

      //Screen scrape product
      OrderedJSONObject product=null;
            
      //Check that all the screen scrape products are valid using their title and unit price
      //------------------------------------------------------------------------------------
      while(products.hasNext())
      {
        //Get next screen scrape product
        //------------------------------
        product=products.next();
                   
        //Get an valid product that matches the screen scrape product Title and Unit Price 
        OrderedJSONObject validProduct=getValidProduct(product.getString(ScraperDemo.TITLE), product.getString(ScraperDemo.UNIT_PRICE));
        
        //Check that we have an valid Product that matches the screen scrape product tile associated unit price
        assertNotNull(validProduct);
         
      }//while(products.hasNext())
    }
    catch(InvalidURLException invurlexp)
    {
      fail();
    }
    catch(IOException ioexp)
    {
      fail();
    }
    catch(JSONException jasonexp)
    {
      fail();
    }    
  }
  
  
  
  /**
   * 
   * This test checks the sum of all the scraped product unit price against the expected
   * product total. The test will pass if these values are equal.
   * @throws JSONException
   * @throws IOException
   * @throws InvalidURLException
   */
  @Test
  public void testValidProductTotal()throws JSONException,IOException,InvalidURLException
  {
    //Get the result from the screen scrape of the html page
    OrderedJSONObject screenScrapeResult=scraperDemo.processScreen(validURL);	  
	String actualTotal= screenScrapeResult.getString(ScraperDemo.TOTAL); 
 
    //Check that the actual product total is equal to the expected
    assertEquals(VALID_TOTAL_VALUE,actualTotal);
  }
  
  /**
   * 
   * This test checks the sum of all the scraped product unit price against the invalid expected
   * product total. The test will pass if these values are not equal.
   * @throws JSONException
   * @throws IOException
   * @throws InvalidURLException
   */
  @Test
  public void testInValidProductTotal()throws JSONException,IOException,InvalidURLException
  {
	   //Get the result from the screen scrape of the html page
	    OrderedJSONObject screenScrapeResult=scraperDemo.processScreen(validURL);	  
		String actualTotal= screenScrapeResult.getString(ScraperDemo.TOTAL); 
	    //Check that the actual product total is not equal to the expected
	    assertNotSame(INVALID_TOTAL_VALUE,actualTotal);	
 } 
     
  /**
   * This test checks if a valid url has been passed, representing the location of the html page to scrape.
   * The test will pass if an InvalidURLException is not thrown. 
   * @throws IOException
   * @throws JSONException
   */
  @Test
  public void testValidURL()throws IOException, JSONException
  {
    try
    {
		//Get the result from the screen scrape of the html page
		OrderedJSONObject screenScrapeResult=scraperDemo.processScreen(validURL);	
    }
	catch(InvalidURLException inve)
	{
		fail();	
	}	  
  }
  
  /**
   * This test checks if an in valid url has been passed as the location to the html page to scrape.
   * The test will pass if an InvalidURLException is thrown. 
   * @throws IOException
   * @throws JSONException
   */
  @Test
  public void testInValidURL()throws IOException, JSONException
  {
	try
	{
		//Get the result from the screen scrape of the html page
		OrderedJSONObject screenScrapeResult=scraperDemo.processScreen(inValidURL);	 
		fail();		
	}
	catch(InvalidURLException	inve)
	{
		//expected result
	}
  } 
    
 
  /**
   * This test checks that the number of of products scraped is as expected
   * @throws IOException
   * @throws InvalidURLException
   * @throws JSONException
   */
  @Test
  public void testNoProducts()throws IOException, InvalidURLException, JSONException
  {
     //Get the result from the screen scrape of the html page
     OrderedJSONObject screenScrapeResult=scraperDemo.processScreen(validURL);
     //Get the screen scrape products
     JSONArray siteProducts=(JSONArray) screenScrapeResult.get(TestScraperDemoImpl.RESULT);
     
     //Check if no of scraped products is correct
     assertEquals(VALID_NO_PRODUCTS,siteProducts.length());
  } 
  
   
  /**
   * This test checks if a valid product can be found using the title and unit price of a scraped 
   * product.
   * @param actualTitle
   * @param actualUnitPrice
   * @return
   * @throws JSONException
   */
  private OrderedJSONObject getValidProduct(String actualTitle, String actualUnitPrice)throws JSONException
  {
    OrderedJSONObject validProduct=null;
    JSONArray validSiteProducts=(JSONArray) validData.get(ScraperDemo.RESULT);
    Iterator<OrderedJSONObject> products= validSiteProducts.iterator();
    
    //Loop around all of the valid products until we find a matching product that contains actualTitle and actualUnitPrice
    while(products.hasNext())
    {
      //Get a valid product
      validProduct=products.next();
      
      //Check if we have found a valid product that matches an actual title corresponding unit_value
      validProduct.getString(ScraperDemo.TITLE);
      
      if ((validProduct.getString(ScraperDemo.TITLE).equals(actualTitle)&&
           validProduct.getString(ScraperDemo.UNIT_PRICE).equals(actualUnitPrice)))
      {
        //Since we have found one stop the search
        break;
      }else validProduct=null;
    }// while(products.hasNext())
   
    return validProduct;
  }
  
   
  @Override
  public void tearDown()
  {
 
  }
  
}

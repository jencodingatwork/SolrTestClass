package my.solr.catalog.test;

import java.io.File;
import org.apache.solr.client.solrj.embedded.JettyConfig;
import org.apache.solr.client.solrj.embedded.JettySolrRunner;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.SolrJettyTestBase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import my.solr.catalog.SolrCatalogClient;
import my.solr.catalog.TestObject;

public class SolrAnalyticClientTest extends SolrJettyTestBase {

	private static SolrCatalogClient solrClient;
	private static TestObject testObject1;
	
	protected static JettySolrRunner SOLR;
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("tests.asserts", "false");
		System.setProperty("solr.solr.home", "solr/conf");
		System.setProperty("solr.core.name", "mySolrCore");
		System.setProperty("solr.data.dir", new File("target/solr-embedded-data").getAbsolutePath());
		
		System.out.println("Initializing Solr Client for JUnit tests");
		
		SOLR = createJetty(
				"target/solr-embedded-data",
				JettyConfig.builder()
					.setPort(8983)
					.setContext("/solr")
					.stopAtShutdown(true)
					.build()); 
		
		solrClient = new SolrCatalogClient();
		
		// Create a test analytic
		testObject1 = new TestObject();
		testObject1.setTestID("1234");
		testObject1.setDescription("The first test object");
		
		solrClient.addObject(testObject1);		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(solrClient != null){
			solrClient.deleteByQuery("testID:*");
		}
	
	}
	
	@Test
	public void testQueryByIdResponse() {
		// query for all of the analytics
		QueryResponse response = solrClient.getQueryResponse("testID:1234");
		System.out.println("Solrj response = " + response);
	}
}

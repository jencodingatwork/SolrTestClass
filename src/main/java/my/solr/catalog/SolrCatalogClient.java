package my.solr.catalog;

import java.io.IOException;
import java.util.Map;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrCatalogClient{

	private SolrClient solrClient;
	private final Logger LOGGER = LoggerFactory.getLogger(SolrCatalogClient.class);

	/**
	 * Constructor with no parameters, used in autowiring
	 */
	public SolrCatalogClient(){}
	
	/**
	 * Constructor.
	 *
	 * @param urlString the url string
	 */
	public SolrCatalogClient(String urlString) {
		LOGGER.info("Initializing new Solr Client");
		// create a single node solr client representing the Solr instance
		if (solrClient == null) {
			solrClient = new HttpSolrClient.Builder(urlString).build();
		}
	}
	
	/**
	 * Instantiates a new solr analytic client.
	 *
	 * @param customClient the custom client
	 */
	public SolrCatalogClient(SolrClient customClient) {
		LOGGER.info("Initializing new Solr Client for testing");
		this.solrClient = customClient;
	}
	
	/**
	 * Initialize the Catalog Client
	 */
	public void initCatalogClient(Map<String, Object> initParams){
		LOGGER.info("Initializing new Solr Client");
		
		String urlString = (String) initParams.get("urlString");
		
		// create a single node solr client representing the Solr instance
		if (solrClient == null) {
			solrClient = new HttpSolrClient.Builder(urlString).build();
		}
	}

	/**
	 * Delete an analytic based on a query.
	 *
	 * @param queryString the query string
	 */
	public void deleteByQuery(String queryString) {
		try {
			// Delete document from the index based on a query
			solrClient.deleteByQuery(queryString);
			solrClient.commit();
		} catch (Exception e) {
			LOGGER.error("Exception thrown while deleting query", e);
		}
	}
	

	/**
	 * Query Solr for the analytic.
	 *
	 * @param queryString the query string
	 * @return queryResponse
	 */
	public QueryResponse getQueryResponse(String queryString) {
		// Create a SolrQuery
		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);

		QueryResponse queryResponse = null;
		try {
			// Perform a query to the Solr server
			queryResponse = solrClient.query(query);
		} catch (SolrServerException e) {
			LOGGER.error("SolrServerException thrown performing query", e);
		} catch (IOException e) {
			LOGGER.error("IOException thrown performing query", e);
		}

		return queryResponse;
	}	

	/**
	 * Add the provided object to Solr.
	 *
	 * @param objectToAdd the object to add
	 * @throws Exception the exception
	 */
	public void addObject(TestObject objectToAdd) throws Exception {
		
		try {
			SolrInputDocument doc = createDocument(objectToAdd);
			
			if(doc != null){
				LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				LOGGER.info("Document to Add = " + doc.toString());
				LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				solrClient.add(doc);
				solrClient.commit();
			}
			else{
				LOGGER.error("An error occurred while creating the document");
				throw new Exception("Error adding object to analytic catalog");
			}
		} catch (SolrServerException e) {
			LOGGER.error("Solr Server Exception", e);
			throw new Exception("Error adding object to analytic catalog");
		} catch (IOException e) {
			LOGGER.error("IO Exception", e);
			throw new Exception("Error adding object to analytic catalog");
		}	
	}
	
	
	/**
	 * Creates the document.
	 *
	 * @param objectToAdd the object to add
	 * @return the solr input document
	 */
	private SolrInputDocument createDocument(TestObject objectToAdd) {
		
		// Create Solr input document
		SolrInputDocument doc;

			doc = new SolrInputDocument();
			doc.addField("testID", objectToAdd.getTestID());
			doc.addField("description", objectToAdd.getDescription());

			return doc;
	}
}

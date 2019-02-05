package com.wol.mock.hplink.web.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wol.mock.hplink.Utils;
import com.wol.mock.hplink.model.Job;
import com.wol.mock.hplink.model.Links;
import com.wol.mock.hplink.model.Product;
import com.wol.mock.hplink.service.JobService;
import com.wol.mock.hplink.service.ProductService;
import com.wol.mock.hplink.service.SessionService;
import com.wol.mock.hplink.web.entity.CreateJobRequest;
import com.wol.mock.hplink.web.entity.CreateJobResponse;
import com.wol.mock.hplink.web.entity.CreateProductRequest;
import com.wol.mock.hplink.web.entity.ProductResponse;


@RestController
@RequestMapping(path="/products")
public class ProductRestService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestService.class);
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private ProductService productService;
	@Autowired
	private JobService jobService;
	
	@RequestMapping(method=RequestMethod.GET, produces={"application/json", "text/plain"})
	public ResponseEntity<String> findProduct(@RequestParam("name") String productName,
			HttpServletRequest request) {
		LOGGER.info("Received findProduct request for " + productName);
		
		ResponseEntity<String> sessionVerification = RestServiceUtils.verifySession(sessionService, request, true);
		if(sessionVerification != null) {
			return sessionVerification;
		}
		
		if(StringUtils.isBlank(productName)) {
			return new ResponseEntity<String>("Product name is a required parameter!", 
					HttpStatus.BAD_REQUEST);
		}
		
		Product product = productService.findByName(productName);
		
		if(product != null) {
			ObjectMapper mapper = new ObjectMapper();
			ProductResponse response = createProductResponseFromProduct(product, request);
			String result = null;
			
			try {
				result = mapper.writeValueAsString(response);
			} catch (JsonProcessingException e) {
				String errorMsg = "Unable to marshall retrieved product: " + e.getMessage();
				LOGGER.error(errorMsg, e) ;
				return new ResponseEntity<String>("Failed to marshall found products", 
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			LOGGER.info("Sending found products response: " + result);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(("Failed to locate product with name=" 
				+ productName), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(consumes="application/json", method=RequestMethod.POST, 
			produces="application/json ")
	public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest createProductRequest, 
			HttpServletRequest request) {
		LOGGER.info("Received createProductRequest");
		
		ResponseEntity<String> sessionVerification = RestServiceUtils.verifySession(sessionService, request, true);
		if(sessionVerification != null) {
			LOGGER.warn("createProductRequest is invalid and will not be processed");
			return sessionVerification;
		}
		
		String name = createProductRequest.getProduct().getName();
		String customer = createProductRequest.getProduct().getBrandName();
		String errorMsg = null;
		
		if(StringUtils.isBlank(name)) {
			errorMsg = "Name is required to create product.";		  
		}
		
		if(StringUtils.isBlank(customer)) {
			errorMsg = "Customer is required to create product";
		}
		
		if(StringUtils.isNotBlank(errorMsg)) {
			return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
		}
		
		Product product = productService.save(createProductRequest.getProduct());
		
		if(product != null) {
			ProductResponse productResponse = createProductResponseFromProduct(product, request);
 
			ObjectMapper mapper = new ObjectMapper();	
			String result = null;
			
			try {
				result = mapper.writeValueAsString(productResponse);
			} catch (JsonProcessingException e) {
				errorMsg = "Unable to marshall created product: " + e.getMessage();
				LOGGER.error(errorMsg, e);
				return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			LOGGER.info("Successfully created product [" + product.getName() + "]");
			LOGGER.debug(result);
			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		}
		
		
		return new ResponseEntity<String>("Failed to create product [" + name + "]", 
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(consumes="application/json", method=RequestMethod.POST, 
			path="/{product_id}/uid_jobs", produces="application/json")
	public ResponseEntity<String> createUidJob(@PathVariable("product_id")String productId, 
			@RequestBody CreateJobRequest createJobRequest, HttpServletRequest request) {
		LOGGER.info("Recevied create job request");
		
		ResponseEntity<String> sessionVerification = RestServiceUtils.verifySession(sessionService, request, true);
		if(sessionVerification != null) {
			return sessionVerification;
		}
		
		jobService.save(convertFromUIDJob(createJobRequest.getUidJob()));
		
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		try {
			mapper.writeValueAsString(new CreateJobResponse());
		} 
		catch (JsonProcessingException e) {
			String errorMsg = "Unable to marshall retrieved product: " + e.getMessage();
			LOGGER.error(errorMsg, e);			
			return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(result, HttpStatus.CREATED);
	}
	
	private ProductResponse createProductResponseFromProduct(Product product, HttpServletRequest request) {
		String baseUri = request.getRequestURL().toString();
		Links links = new Links();
		links.setParent(baseUri + "/" + product.getName());
		links.setSelf(baseUri);
		links.setUidJobs(baseUri + "/uid_jobs");
		ProductResponse.Product responseProduct = new ProductResponse.Product();
		responseProduct.setBrandName(product.getBrandName());
		responseProduct.setCreatedAt(Utils.formatDate(null, null));
		responseProduct.setDescription(product.getDescription());
		responseProduct.setLinks(links);
		responseProduct.setName(product.getName());
		responseProduct.setProductUrl(baseUri);
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProduct(responseProduct);
		return productResponse;
	}
	
	private Job convertFromUIDJob(CreateJobRequest.UIDJob uidJob) {
		Job job = new Job();
		job.setMarkType(uidJob.getMarkType());
		job.setQuantity(uidJob.getQuantity());
		job.setDescription(uidJob.getDescription());
		return job;
	}
}

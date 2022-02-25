package com.serverless;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.serverless.model.StromPriceInfo;
import com.serverless.service.FetchDataService;
import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = Logger.getLogger(Handler.class);

	@Autowired
	private FetchDataService fetchService;

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: " + input);
		Response responseBody = new Response("Go Serverless v1.x! Your function executed successfully!", input);
		Response errorResponseBody = new Response("Error while function execution!", input);

		try {
			final List<StromPriceInfo> data = fetchService.getData("NO01");
			LOG.info("Data Value is : " + data);
			return ApiGatewayResponse.builder()
					.setStatusCode(200)
					.setObjectBody(responseBody)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
					.build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ApiGatewayResponse.builder()
					.setStatusCode(HttpStatus.EXPECTATION_FAILED.value())
					.setObjectBody(errorResponseBody)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
					.build();
		}


	}

}

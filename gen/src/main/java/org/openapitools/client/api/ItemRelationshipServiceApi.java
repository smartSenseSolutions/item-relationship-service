/*
 * IRS API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.2
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiCallback;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.Configuration;
import org.openapitools.client.Pair;
import org.openapitools.client.ProgressRequestBody;
import org.openapitools.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.openapitools.client.model.ErrorResponse;
import java.util.UUID;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRelationshipServiceApi {
    private ApiClient localVarApiClient;

    public ItemRelationshipServiceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ItemRelationshipServiceApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for cancelJobForJobId
     * @param jobId Id of the job in registry. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job with {jobId} was canceled </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request. JobId must be a string in UUID format. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Authorization information is missing or invalid. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> A job with the specified jobId was not found. </td><td>  -  </td></tr>
        <tr><td> 5XX </td><td> Unexpected error. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call cancelJobForJobIdCall(UUID jobId, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/irs/jobs/{jobId}/cancel"
            .replaceAll("\\{" + "jobId" + "\\}", localVarApiClient.escapeString(jobId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call cancelJobForJobIdValidateBeforeCall(UUID jobId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new ApiException("Missing the required parameter 'jobId' when calling cancelJobForJobId(Async)");
        }
        

        okhttp3.Call localVarCall = cancelJobForJobIdCall(jobId, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param jobId Id of the job in registry. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job with {jobId} was canceled </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request. JobId must be a string in UUID format. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Authorization information is missing or invalid. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> A job with the specified jobId was not found. </td><td>  -  </td></tr>
        <tr><td> 5XX </td><td> Unexpected error. </td><td>  -  </td></tr>
     </table>
     */
    public void cancelJobForJobId(UUID jobId) throws ApiException {
        cancelJobForJobIdWithHttpInfo(jobId);
    }

    /**
     * 
     * 
     * @param jobId Id of the job in registry. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job with {jobId} was canceled </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request. JobId must be a string in UUID format. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Authorization information is missing or invalid. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> A job with the specified jobId was not found. </td><td>  -  </td></tr>
        <tr><td> 5XX </td><td> Unexpected error. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> cancelJobForJobIdWithHttpInfo(UUID jobId) throws ApiException {
        okhttp3.Call localVarCall = cancelJobForJobIdValidateBeforeCall(jobId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     *  (asynchronously)
     * 
     * @param jobId Id of the job in registry. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job with {jobId} was canceled </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request. JobId must be a string in UUID format. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Authorization information is missing or invalid. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> A job with the specified jobId was not found. </td><td>  -  </td></tr>
        <tr><td> 5XX </td><td> Unexpected error. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call cancelJobForJobIdAsync(UUID jobId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = cancelJobForJobIdValidateBeforeCall(jobId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getBOMForJobId
     * @param jobId Id of the job in registry.  (required)
     * @param returnUncompletedResultTree If true, the endpoint returns the uncompleted results of the bom tree. (optional, default to true)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> livecycle tree representation with the starting point of the given jobId </td><td>  -  </td></tr>
        <tr><td> 201 </td><td> job details for given jobId </td><td>  -  </td></tr>
        <tr><td> 206 </td><td> uncompleted livecycle tree representation with the starting point of the given jobId </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> processing of job was canceled </td><td>  -  </td></tr>
        <tr><td> 417 </td><td> Processing of job failed </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getBOMForJobIdCall(UUID jobId, Boolean returnUncompletedResultTree, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/irs/jobs/{jobId}"
            .replaceAll("\\{" + "jobId" + "\\}", localVarApiClient.escapeString(jobId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (returnUncompletedResultTree != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("returnUncompletedResultTree", returnUncompletedResultTree));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getBOMForJobIdValidateBeforeCall(UUID jobId, Boolean returnUncompletedResultTree, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new ApiException("Missing the required parameter 'jobId' when calling getBOMForJobId(Async)");
        }
        

        okhttp3.Call localVarCall = getBOMForJobIdCall(jobId, returnUncompletedResultTree, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param jobId Id of the job in registry.  (required)
     * @param returnUncompletedResultTree If true, the endpoint returns the uncompleted results of the bom tree. (optional, default to true)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> livecycle tree representation with the starting point of the given jobId </td><td>  -  </td></tr>
        <tr><td> 201 </td><td> job details for given jobId </td><td>  -  </td></tr>
        <tr><td> 206 </td><td> uncompleted livecycle tree representation with the starting point of the given jobId </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> processing of job was canceled </td><td>  -  </td></tr>
        <tr><td> 417 </td><td> Processing of job failed </td><td>  -  </td></tr>
     </table>
     */
    public void getBOMForJobId(UUID jobId, Boolean returnUncompletedResultTree) throws ApiException {
        getBOMForJobIdWithHttpInfo(jobId, returnUncompletedResultTree);
    }

    /**
     * 
     * 
     * @param jobId Id of the job in registry.  (required)
     * @param returnUncompletedResultTree If true, the endpoint returns the uncompleted results of the bom tree. (optional, default to true)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> livecycle tree representation with the starting point of the given jobId </td><td>  -  </td></tr>
        <tr><td> 201 </td><td> job details for given jobId </td><td>  -  </td></tr>
        <tr><td> 206 </td><td> uncompleted livecycle tree representation with the starting point of the given jobId </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> processing of job was canceled </td><td>  -  </td></tr>
        <tr><td> 417 </td><td> Processing of job failed </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> getBOMForJobIdWithHttpInfo(UUID jobId, Boolean returnUncompletedResultTree) throws ApiException {
        okhttp3.Call localVarCall = getBOMForJobIdValidateBeforeCall(jobId, returnUncompletedResultTree, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     *  (asynchronously)
     * 
     * @param jobId Id of the job in registry.  (required)
     * @param returnUncompletedResultTree If true, the endpoint returns the uncompleted results of the bom tree. (optional, default to true)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> livecycle tree representation with the starting point of the given jobId </td><td>  -  </td></tr>
        <tr><td> 201 </td><td> job details for given jobId </td><td>  -  </td></tr>
        <tr><td> 206 </td><td> uncompleted livecycle tree representation with the starting point of the given jobId </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> processing of job was canceled </td><td>  -  </td></tr>
        <tr><td> 417 </td><td> Processing of job failed </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getBOMForJobIdAsync(UUID jobId, Boolean returnUncompletedResultTree, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = getBOMForJobIdValidateBeforeCall(jobId, returnUncompletedResultTree, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getBomLifecycleByGlobalAssetId
     * @param globalAssetId Readable ID of manufacturer including plant (required)
     * @param bomLifecycle Unique identifier of a single, unique (sub)component/part/batch, given by its globalAssetId/ digital twin id (required)
     * @param aspect Aspect information to add to the returned tree (optional)
     * @param depth Max depth of the returned tree, if empty max depth is returned (optional, default to 1)
     * @param direction Direction in which the tree shall be traversed (optional, default to downward)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> job id response for successful job registration </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getBomLifecycleByGlobalAssetIdCall(String globalAssetId, String bomLifecycle, List<String> aspect, Integer depth, String direction, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/irs/items/{globalAssetId}"
            .replaceAll("\\{" + "globalAssetId" + "\\}", localVarApiClient.escapeString(globalAssetId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (bomLifecycle != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("bomLifecycle", bomLifecycle));
        }

        if (aspect != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("csv", "aspect", aspect));
        }

        if (depth != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("depth", depth));
        }

        if (direction != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("direction", direction));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getBomLifecycleByGlobalAssetIdValidateBeforeCall(String globalAssetId, String bomLifecycle, List<String> aspect, Integer depth, String direction, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'globalAssetId' is set
        if (globalAssetId == null) {
            throw new ApiException("Missing the required parameter 'globalAssetId' when calling getBomLifecycleByGlobalAssetId(Async)");
        }
        
        // verify the required parameter 'bomLifecycle' is set
        if (bomLifecycle == null) {
            throw new ApiException("Missing the required parameter 'bomLifecycle' when calling getBomLifecycleByGlobalAssetId(Async)");
        }
        

        okhttp3.Call localVarCall = getBomLifecycleByGlobalAssetIdCall(globalAssetId, bomLifecycle, aspect, depth, direction, _callback);
        return localVarCall;

    }

    /**
     * Get a BOM for a part
     * Registers and starts a AAS crawler job for given {globalAssetId}
     * @param globalAssetId Readable ID of manufacturer including plant (required)
     * @param bomLifecycle Unique identifier of a single, unique (sub)component/part/batch, given by its globalAssetId/ digital twin id (required)
     * @param aspect Aspect information to add to the returned tree (optional)
     * @param depth Max depth of the returned tree, if empty max depth is returned (optional, default to 1)
     * @param direction Direction in which the tree shall be traversed (optional, default to downward)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> job id response for successful job registration </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
     </table>
     */
    public void getBomLifecycleByGlobalAssetId(String globalAssetId, String bomLifecycle, List<String> aspect, Integer depth, String direction) throws ApiException {
        getBomLifecycleByGlobalAssetIdWithHttpInfo(globalAssetId, bomLifecycle, aspect, depth, direction);
    }

    /**
     * Get a BOM for a part
     * Registers and starts a AAS crawler job for given {globalAssetId}
     * @param globalAssetId Readable ID of manufacturer including plant (required)
     * @param bomLifecycle Unique identifier of a single, unique (sub)component/part/batch, given by its globalAssetId/ digital twin id (required)
     * @param aspect Aspect information to add to the returned tree (optional)
     * @param depth Max depth of the returned tree, if empty max depth is returned (optional, default to 1)
     * @param direction Direction in which the tree shall be traversed (optional, default to downward)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> job id response for successful job registration </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> getBomLifecycleByGlobalAssetIdWithHttpInfo(String globalAssetId, String bomLifecycle, List<String> aspect, Integer depth, String direction) throws ApiException {
        okhttp3.Call localVarCall = getBomLifecycleByGlobalAssetIdValidateBeforeCall(globalAssetId, bomLifecycle, aspect, depth, direction, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Get a BOM for a part (asynchronously)
     * Registers and starts a AAS crawler job for given {globalAssetId}
     * @param globalAssetId Readable ID of manufacturer including plant (required)
     * @param bomLifecycle Unique identifier of a single, unique (sub)component/part/batch, given by its globalAssetId/ digital twin id (required)
     * @param aspect Aspect information to add to the returned tree (optional)
     * @param depth Max depth of the returned tree, if empty max depth is returned (optional, default to 1)
     * @param direction Direction in which the tree shall be traversed (optional, default to downward)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> job id response for successful job registration </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad request </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getBomLifecycleByGlobalAssetIdAsync(String globalAssetId, String bomLifecycle, List<String> aspect, Integer depth, String direction, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = getBomLifecycleByGlobalAssetIdValidateBeforeCall(globalAssetId, bomLifecycle, aspect, depth, direction, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getJobsByProcessingState
     * @param processingState List of jobs (globalAssetIds) for a certain processing state (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> list of jobs with given processingState </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getJobsByProcessingStateCall(String processingState, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/irs/jobs/{processingState}"
            .replaceAll("\\{" + "processingState" + "\\}", localVarApiClient.escapeString(processingState.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getJobsByProcessingStateValidateBeforeCall(String processingState, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'processingState' is set
        if (processingState == null) {
            throw new ApiException("Missing the required parameter 'processingState' when calling getJobsByProcessingState(Async)");
        }
        

        okhttp3.Call localVarCall = getJobsByProcessingStateCall(processingState, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param processingState List of jobs (globalAssetIds) for a certain processing state (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> list of jobs with given processingState </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
     </table>
     */
    public void getJobsByProcessingState(String processingState) throws ApiException {
        getJobsByProcessingStateWithHttpInfo(processingState);
    }

    /**
     * 
     * 
     * @param processingState List of jobs (globalAssetIds) for a certain processing state (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> list of jobs with given processingState </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> getJobsByProcessingStateWithHttpInfo(String processingState) throws ApiException {
        okhttp3.Call localVarCall = getJobsByProcessingStateValidateBeforeCall(processingState, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     *  (asynchronously)
     * 
     * @param processingState List of jobs (globalAssetIds) for a certain processing state (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> list of jobs with given processingState </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getJobsByProcessingStateAsync(String processingState, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = getJobsByProcessingStateValidateBeforeCall(processingState, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}

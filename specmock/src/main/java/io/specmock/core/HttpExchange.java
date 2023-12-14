/*
 * Copyright 2023 SpecMock
 * (c) 2023 SpecMock Contributors
 * SPDX-License-Identifier: Apache-2.0
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.specmock.core;

import java.util.Map;
import java.util.Objects;

/**
 * Represents an HTTP exchange configuration, containing request and response parameters.
 */
public final class HttpExchange {
    private final Map<String, String> pathParamMap;
    private final Map<String, String> queryParamMap;
    private final Map<String, String> headerMap;
    private final Object requestObject;
    private final HttpStatus responseStatus;
    private final Object responseObject;

    /**
     * Creates a new instance of HttpExchangeBuilder.
     * @return A new HttpExchangeBuilder instance.
     */
    public static HttpExchangeBuilder builder() {
        return new HttpExchangeBuilder();
    }

    /**
     * Constructs an HTTP exchange object with the specified parameters.
     *
     * @param pathParamMap    The map containing path parameters.
     * @param queryParamMap   The map containing query parameters.
     * @param headerMap       The map containing headers.
     * @param requestObject   The object representing the expected request.
     * @param responseStatus  The HTTP status for the response.
     * @param responseObject  The object representing the expected response.
     */
    public HttpExchange(Map<String, String> pathParamMap, Map<String, String> queryParamMap,
                        Map<String, String> headerMap, Object requestObject, HttpStatus responseStatus,
                        Object responseObject) {
        this.pathParamMap = pathParamMap;
        this.queryParamMap = queryParamMap;
        this.headerMap = headerMap;
        this.requestObject = requestObject;
        this.responseStatus = responseStatus;
        this.responseObject = responseObject;
    }

    /**
     * Retrieves the object representing the expected request.
     *
     * @return The object representing the expected request.
     */
    public Object getRequestObject() {
        return requestObject;
    }

    /**
     * Retrieves the HTTP status for the response.
     *
     * @return The HTTP status for the response.
     */
    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Retrieves the object representing the expected response.
     *
     * @return The object representing the expected response.
     */
    public Object getResponseObject() {
        return responseObject;
    }

    /**
     * Checks if the provided request and response classes match with the stored request and response objects.
     *
     * @param requestClass  The class of the expected request object.
     * @param responseClass The class of the expected response object.
     * @return {@code true} if the request and response objects match the provided classes,
     *      {@code false} otherwise.
     */
    public boolean isMatchRequest(Class<?> requestClass, Class<?> responseClass) {
        if (requestObject == null) {
            return responseObject.getClass().equals(responseClass);
        } else {
            return requestObject.getClass().equals(requestClass) &&
                   responseObject.getClass().equals(responseClass);
        }
    }

    /**
     * Checks if the provided request and response classes do not match
     * with the stored request and response objects.
     *
     * @param requestClass  The class of the expected request object.
     * @param responseClass The class of the expected response object.
     * @return {@code true} if the request and response objects do not match the provided classes,
     *      {@code false} otherwise.
     */
    public boolean isNotMatchRequest(Class<?> requestClass, Class<?> responseClass) {
        return !isMatchRequest(requestClass, responseClass);
    }

    /**
     * Checks if the path parameters do not match the provided path parameters.
     *
     * @param pathParams The path parameters to compare.
     * @return {@code true} if the path parameters do not match, {@code false} otherwise.
     */
    public boolean isNotMatchPathParam(Map<String, String> pathParams) {
        if (pathParamMap.isEmpty()) {
            return false;
        }

        for (Map.Entry<String, String> each : pathParamMap.entrySet()) {
            if (!Objects.equals(pathParams.get(each.getKey()), each.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the query parameters do not match the provided query parameters.
     *
     * @param queryParams The query parameters to compare.
     * @return {@code true} if the query parameters do not match, {@code false} otherwise.
     */
    public boolean isNotMatchQueryParam(Map<String, String> queryParams) {
        if (queryParamMap.isEmpty()) {
            return false;
        }

        for (Map.Entry<String, String> each : queryParamMap.entrySet()) {
            if (!Objects.equals(queryParams.get(each.getKey()), each.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the headers do not match the provided request headers.
     *
     * @param headers The request headers to compare.
     * @return {@code true} if the headers do not match, {@code false} otherwise.
     */
    public boolean isNotMatchHeader(Map<String, String> headers) {
        if (headerMap.isEmpty()) {
            return false;
        }

        for (Map.Entry<String, String> each : headerMap.entrySet()) {
            if (!Objects.equals(headers.get(each.getKey()), each.getValue())) {
                return true;
            }
        }
        return false;
    }
}

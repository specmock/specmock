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

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for constructing HttpExchange instances.
 */
public final class HttpExchangeBuilder {
    private Map<String, String> pathParamMap = new HashMap<>();
    private Map<String, String> queryParamMap = new HashMap<>();
    private Map<String, String> headerMap = new HashMap<>();
    private Object requestObject;
    private HttpStatus responseStatus = HttpStatus.OK;
    private Object responseObject;

    HttpExchangeBuilder() {
    }

    /**
     * Sets the path parameter map.
     * @param pathParamMap The map containing path parameters.
     * @return The HttpExchangeBuilder instance.
     */
    public HttpExchangeBuilder pathParamMap(Map<String, String> pathParamMap) {
        this.pathParamMap = pathParamMap;
        return this;
    }

    /**
     * Sets the query parameter map.
     * @param queryParamMap The map containing query parameters.
     * @return The HttpExchangeBuilder instance.
     */
    public HttpExchangeBuilder queryParamMap(Map<String, String> queryParamMap) {
        this.queryParamMap = queryParamMap;
        return this;
    }

    /**
     * Sets the header map.
     * @param headerMap The map containing headers.
     * @return The HttpExchangeBuilder instance.
     */
    public HttpExchangeBuilder headerMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
        return this;
    }

    /**
     * Sets the request object.
     * @param requestObject The object representing the expected request.
     * @return The HttpExchangeBuilder instance.
     */
    public HttpExchangeBuilder requestObject(Object requestObject) {
        this.requestObject = requestObject;
        return this;
    }

    /**
     * Sets the response status.
     * @param responseStatus The HTTP status for the response.
     * @return The HttpExchangeBuilder instance.
     */
    public HttpExchangeBuilder responseStatus(HttpStatus responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    /**
     * Sets the response object.
     * @param responseObject The object representing the expected response.
     * @return The HttpExchangeBuilder instance.
     */
    public HttpExchangeBuilder responseObject(Object responseObject) {
        this.responseObject = responseObject;
        return this;
    }

    /**
     * Builds a new HttpExchange instance based on the configured parameters.
     * @return The constructed HttpExchange instance.
     */
    public HttpExchange build() {
        return new HttpExchange(
                pathParamMap,
                queryParamMap,
                headerMap,
                requestObject,
                responseStatus,
                responseObject
        );
    }
}

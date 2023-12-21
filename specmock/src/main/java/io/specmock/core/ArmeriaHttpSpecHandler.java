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
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.common.QueryParams;
import com.linecorp.armeria.common.RequestHeaders;
import com.linecorp.armeria.server.AbstractHttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.logging.LoggingService;

import io.netty.util.AsciiString;

/**
 * ArmeriaHttpSpecHandler class extends AbstractHttpService to handle HTTP requests based on a given HttpSpec.
 */
public final class ArmeriaHttpSpecHandler extends AbstractHttpService {
    private final ObjectMapper mapper = new ObjectMapper().registerModules(new KotlinModule.Builder().build(), new JavaTimeModule());
    private final HttpSpec spec;

    /**
     * Constructs an ArmeriaHttpSpecHandler with a provided HttpSpec.
     *
     * @param spec The HttpSpec defining supported HTTP methods and request/response specifications.
     */
    public ArmeriaHttpSpecHandler(HttpSpec spec) {
        decorate(LoggingService.newDecorator());
        this.spec = spec;
    }

    @Override
    protected HttpResponse doGet(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        return handle(ctx, req);
    }

    @Override
    protected HttpResponse doPost(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        return handle(ctx, req);
    }

    @Override
    protected HttpResponse doPut(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        return handle(ctx, req);
    }

    @Override
    protected HttpResponse doPatch(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        return handle(ctx, req);
    }

    @Override
    protected HttpResponse doDelete(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        return handle(ctx, req);
    }

    /**
     * Handles the HTTP request based on the defined HttpSpec.
     *
     * @param context The ServiceRequestContext containing request-specific information.
     * @param request The HttpRequest to be handled.
     * @return HttpResponse based on the defined HttpSpec's exchanges or a NOT_ACCEPTABLE response.
     */
    private HttpResponse handle(ServiceRequestContext context, HttpRequest request) {
        return HttpResponse.of(request.aggregate().thenApply(aggregated -> {
            for (HttpExchange exchange : spec.getExchanges()) {
                try {
                    if (exchange.isNotMatchPathParam(context.pathParams())) {
                        continue;
                    }
                    if (exchange.isNotMatchQueryParam(toMap(context.queryParams()))) {
                        continue;
                    }
                    if (exchange.isNotMatchHeader(toMap(request.headers()))) {
                        continue;
                    }
                    if (exchange.getRequestObject() != null) {
                        final String body = aggregated.contentUtf8();

                        final Object temp = mapper.readValue(body, exchange.getRequestObject().getClass());
                        final String formattedActualRequest = mapper.writeValueAsString(temp);
                        final String exchangeRequest = mapper.writeValueAsString(exchange.getRequestObject());
                        if (!exchangeRequest.equals(formattedActualRequest)) {
                            continue;
                        }
                    }
                    return HttpResponse.of(
                            HttpStatus.valueOf(exchange.getResponseStatus().getCode()),
                            MediaType.JSON_UTF_8,
                            mapper.writeValueAsString(exchange.getResponseObject())
                    );
                } catch (JsonProcessingException e) {
                    return HttpResponse.ofFailure(e);
                }
            }
            return HttpResponse.of(HttpStatus.NOT_ACCEPTABLE);
        }));
    }

    private static Map<String, String> toMap(QueryParams queryParams) {
        final Map<String, String> map = new HashMap<>();
        for (Entry<String, String> entry : queryParams) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    private static Map<String, String> toMap(RequestHeaders headers) {
        final Map<String, String> map = new HashMap<>();
        for (Entry<AsciiString, String> each : headers) {
            map.put(String.valueOf(each.getKey()), each.getValue());
        }
        return map;
    }
}

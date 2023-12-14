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

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Builder class for constructing HTTP specifications based on Spring Web's annotations and exchanges.
 * It enables mapping Spring's RequestMapping annotations to HTTP specifications.
 */
public final class SpringWebHttpSpecBuilder {
    private List<HttpRouteClassMapping> routeClassMappings = new ArrayList<>();
    private List<HttpExchange> exchanges = new ArrayList<>();

    /**
     * Constructs a SpringWebHttpSpecBuilder.
     * Package-private access to limit instantiation outside the package.
     */
    SpringWebHttpSpecBuilder() {
    }

    /**
     * Binds Spring Web annotations to HTTP specifications.
     *
     * @param webBindClass The class containing Spring Web annotations.
     * @return The SpringWebHttpSpecBuilder instance.
     */
    public SpringWebHttpSpecBuilder springWebBind(Class<?> webBindClass) {
        routeClassMappings = new ArrayList<>();
        final Method[] methods = webBindClass.getDeclaredMethods();
        for (Method method : methods) {
            final RequestMapping requestMappingAnnotation = method.getDeclaredAnnotation(RequestMapping.class);
            if (requestMappingAnnotation == null) {
                continue;
            }
            final Class<?> specResponseClass = method.getReturnType();

            Class<?> specRequestClass = null;
            for (Parameter parameter : method.getParameters()) {
                final RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
                if (requestBody != null) {
                    specRequestClass = parameter.getType();
                }
            }

            final Set<String> pathList = Arrays.stream(requestMappingAnnotation.value())
                                               .collect(Collectors.toSet());
            pathList.addAll(Arrays.stream(requestMappingAnnotation.path()).collect(Collectors.toSet()));
            for (String path : pathList) {
                for (RequestMethod requestMethod : requestMappingAnnotation.method()) {
                    routeClassMappings.add(
                            new HttpRouteClassMapping(
                                    HttpRoute.of(requestMethod.toString(), path),
                                    specRequestClass,
                                    specResponseClass
                            )
                    );
                }
            }
        }
        return this;
    }

    /**
     * Adds an HTTP exchange to the list of exchanges.
     *
     * @param exchange The HTTP exchange to add.
     * @return The SpringWebHttpSpecBuilder instance.
     */
    public SpringWebHttpSpecBuilder exchange(HttpExchange exchange) {
        exchanges.add(exchange);
        return this;
    }

    /**
     * Adds multiple HTTP exchanges to the list of exchanges.
     *
     * @param exchange An array of HTTP exchanges to add.
     * @return The SpringWebHttpSpecBuilder instance.
     */
    public SpringWebHttpSpecBuilder exchanges(HttpExchange... exchange) {
        exchanges.addAll(Arrays.asList(exchange));
        return this;
    }

    /**
     * Sets the list of exchanges.
     *
     * @param exchanges The list of HTTP exchanges to set.
     * @return The SpringWebHttpSpecBuilder instance.
     */
    public SpringWebHttpSpecBuilder exchanges(List<HttpExchange> exchanges) {
        this.exchanges = exchanges;
        return this;
    }

    /**
     * Builds a list of HTTP specifications based on Spring Web annotations and exchanges.
     *
     * @return The list of constructed HttpSpec instances.
     */
    public List<HttpSpec> build() {
        final List<HttpSpec> specs = new ArrayList<>();
        for (HttpRouteClassMapping mapping : routeClassMappings) {
            final List<HttpExchange> matchedExchangeSpecs = new ArrayList<>();
            for (HttpExchange exchange : exchanges) {
                if (exchange.isNotMatchRequest(mapping.getSpecRequestClass(), mapping.getSpecResponseClass())) {
                    continue;
                }
                matchedExchangeSpecs.add(exchange);
            }
            specs.add(new HttpSpec(mapping.getRoute(), matchedExchangeSpecs));
        }
        return specs;
    }
}

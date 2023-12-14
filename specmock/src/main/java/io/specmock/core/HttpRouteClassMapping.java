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

/**
 * Represents the mapping between an HTTP method, path, and corresponding request and response classes.
 */
final class HttpRouteClassMapping {
    private final HttpRoute route;
    private final Class<?> requestClass;
    private final Class<?> responseClass;

    /**
     * Constructs an HttpRouteClassMapping with the specified route, request class, and response class.
     *
     * @param route         The HTTP route for this mapping.
     * @param requestClass  The class representing the expected request object.
     * @param responseClass The class representing the expected response object.
     */
    HttpRouteClassMapping(
            HttpRoute route,
            Class<?> requestClass,
            Class<?> responseClass
    ) {
        this.route = route;
        this.requestClass = requestClass;
        this.responseClass = responseClass;
    }

    /**
     * Retrieves the HTTP route for this mapping.
     *
     * @return The HTTP route.
     */
    public HttpRoute getRoute() {
        return route;
    }

    /**
     * Retrieves the class representing the expected request object.
     *
     * @return The class representing the expected request object.
     */
    public Class<?> getSpecRequestClass() {
        return requestClass;
    }

    /**
     * Retrieves the class representing the expected response object.
     *
     * @return The class representing the expected response object.
     */
    public Class<?> getSpecResponseClass() {
        return responseClass;
    }
}

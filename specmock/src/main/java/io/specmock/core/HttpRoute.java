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
 * Represents an HTTP route with its method and path.
 */
public final class HttpRoute {
    private final HttpMethod method;
    private final String path;

    /**
     * Creates an HttpRoute instance using the provided method and path.
     *
     * @param method The HTTP method as a string.
     * @param path   The path for this route.
     * @return An HttpRoute instance.
     */
    public static HttpRoute of(String method, String path) {
        return new HttpRoute(HttpMethod.valueOf(method.toUpperCase()), path);
    }

    /**
     * Creates an HttpRoute instance with GET method for the specified path.
     *
     * @param path The path for this route.
     * @return An HttpRoute instance with GET method.
     */
    public static HttpRoute get(String path) {
        return new HttpRoute(HttpMethod.GET, path);
    }

    /**
     * Creates an HttpRoute instance with POST method for the specified path.
     *
     * @param path The path for this route.
     * @return An HttpRoute instance with POST method.
     */
    public static HttpRoute post(String path) {
        return new HttpRoute(HttpMethod.POST, path);
    }

    /**
     * Creates an HttpRoute instance with PATCH method for the specified path.
     *
     * @param path The path for this route.
     * @return An HttpRoute instance with PATCH method.
     */
    public static HttpRoute put(String path) {
        return new HttpRoute(HttpMethod.PUT, path);
    }

    /**
     * Creates an HttpRoute instance with PATCH method for the specified path.
     *
     * @param path The path for this route.
     * @return An HttpRoute instance with PATCH method.
     */
    public static HttpRoute patch(String path) {
        return new HttpRoute(HttpMethod.PATCH, path);
    }

    /**
     * Creates an HttpRoute instance with DELETE method for the specified path.
     *
     * @param path The path for this route.
     * @return An HttpRoute instance with DELETE method.
     */
    public static HttpRoute delete(String path) {
        return new HttpRoute(HttpMethod.DELETE, path);
    }

    /**
     * Constructs an HttpRoute using the provided method and path.
     *
     * @param method The HttpMethod for this route.
     * @param path   The path for this route.
     */
    private HttpRoute(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

    /**
     * Retrieves the HTTP method for this route.
     *
     * @return The HttpMethod for this route.
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Retrieves the path for this route.
     *
     * @return The path for this route.
     */
    public String getPath() {
        return path;
    }
}

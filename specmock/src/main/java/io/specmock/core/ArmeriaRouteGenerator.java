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

import com.linecorp.armeria.server.Route;

/**
 * ArmeriaRouteGenerator generates Armeria Routes based on the given HttpSpec.
 */
public final class ArmeriaRouteGenerator {
    /**
     * Generates an Armeria Route based on the provided HttpSpec.
     *
     * @param spec The HttpSpec defining the path and method for the route.
     * @return Route An Armeria Route instance built from the provided HttpSpec.
     */
    public Route generate(HttpSpec spec) {
        return Route.builder()
                    .path(spec.getRoute().getPath())
                    .methods(decide(spec.getRoute().getMethod()))
                    .build();
    }

    /**
     * Decides the Armeria HttpMethod based on the given HttpMethod.
     *
     * @param method The HttpMethod to be converted to the corresponding Armeria HttpMethod.
     * @return HttpMethod The Armeria HttpMethod equivalent to the given HttpMethod.
     * @throws UnsupportedOperationException if the method provided is not supported.
     */
    private com.linecorp.armeria.common.HttpMethod decide(HttpMethod method) {
        switch (method) {
            case POST:
                return com.linecorp.armeria.common.HttpMethod.POST;
            case PUT:
                return com.linecorp.armeria.common.HttpMethod.PUT;
            case PATCH:
                return com.linecorp.armeria.common.HttpMethod.PATCH;
            case DELETE:
                return com.linecorp.armeria.common.HttpMethod.DELETE;
            case GET:
            default:
                return com.linecorp.armeria.common.HttpMethod.GET;
        }
    }
}

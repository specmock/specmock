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
package io.specmock.core.example;

import java.math.BigDecimal;

/**
 * Represents an Example Request object.
 */
public class Example6Request {
    private String stringValue;
    private Integer integerValue;
    private Long longValue;
    private BigDecimal bigDecimalValue;

    /**
     * Empty constructor for ExampleRequest.
     */
    public Example6Request() {
    }

    /**
     * Constructs an ExampleRequest with specified values.
     *
     * @param stringValue     The string value.
     * @param integerValue    The integer value.
     * @param longValue       The long value.
     * @param bigDecimalValue The BigDecimal value.
     */
    public Example6Request(
            String stringValue,
            Integer integerValue,
            Long longValue,
            BigDecimal bigDecimalValue
    ) {
        this.stringValue = stringValue;
        this.integerValue = integerValue;
        this.longValue = longValue;
        this.bigDecimalValue = bigDecimalValue;
    }

    /**
     * Retrieves the string value.
     *
     * @return The string value.
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Retrieves the integer value.
     *
     * @return The integer value.
     */
    public Integer getIntegerValue() {
        return integerValue;
    }

    /**
     * Retrieves the long value.
     *
     * @return The long value.
     */
    public Long getLongValue() {
        return longValue;
    }

    /**
     * Retrieves the BigDecimal value.
     *
     * @return The BigDecimal value.
     */
    public BigDecimal getBigDecimalValue() {
        return bigDecimalValue;
    }
}

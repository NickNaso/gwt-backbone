/*
 * Copyright 2015, Liraz Shilkrot
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.lirazs.gbackbone.client.generator;


import org.lirazs.gbackbone.client.core.data.Options;

public interface Reflection {
    <T, V extends T> T instantiateModel( Class<V> clazz, Options attributes, Options options );

    <T, V extends T> T[] instantiateArray( Class<V> clazz, int length );
}

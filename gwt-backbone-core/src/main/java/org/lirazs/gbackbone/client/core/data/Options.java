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
package org.lirazs.gbackbone.client.core.data;

import com.google.gwt.json.client.*;
import com.google.gwt.query.client.Properties;

import java.util.HashMap;


public class Options extends HashMap<String, Object> implements JsonSerializable {

    public Options() {
        super();
    }

    public Options(JSONObject jsonObject) {
        for (String s : jsonObject.keySet()) {
            JSONValue jsonValue = jsonObject.get(s);
            Object value = jsonValue;

            JSONNumber number = jsonValue.isNumber();
            if(number != null)
                value = number.doubleValue();

            JSONBoolean jsonBoolean = jsonValue.isBoolean();
            if(jsonBoolean != null)
                value = jsonBoolean.booleanValue();

            JSONNull jsonNull = jsonValue.isNull();
            if(jsonNull != null)
                value = null;

            JSONString jsonString = jsonValue.isString();
            if(jsonString != null)
                value = jsonString.stringValue();

            put(s, value);
        }
    }

    public Options(Object ...keyValue) {
        super();
        for(int i = 0; i < keyValue.length && (i + 1) < keyValue.length; i = i + 2) {
            put(String.valueOf(keyValue[i]), keyValue[i + 1]);
        }
    }

    // for compatibility with GQuery
    public final Properties toProperties() {
        Properties props = Properties.create();
        for (String k : keySet()) {
            props.set(k, get(k));
        }

        return props;
    }

    public Options defaults(Options ...args) {
        for (Options source : args) {
            for (String key : source.keySet()) {
                if (!containsKey(key)) {
                    put(key, source.get(key));
                }
            }
        }
        return this;
    }

    public Options extend(Options o) {
        if (o != null) {
            for (String k : o.keySet()) {
                put(k, o.get(k));
            }
        }
        return this;
    }

    public Options put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Integer getInt(String key) {
        Integer integer = get(key, Integer.class);
        return integer == null ? 0 : integer;
    }

    public boolean getBoolean(String key) {
        Boolean r = (Boolean) super.get(key);
        return r == null ? false : r;
    }

    public <T> T get(String key) {
        return (T) super.get(key);
    }

    public final <T> T get(String id, Class<? extends T> clz) {
        Object o = super.get(id);
        if (clz != null) {
            if (o instanceof Double) {
                Double d = (Double)o;
                if (clz == Float.class) o = d.floatValue();
                else if (clz == Integer.class) o = d.intValue();
                else if (clz == Long.class) o = d.longValue();
                else if (clz == Short.class) o = d.shortValue();
                else if (clz == Byte.class) o = d.byteValue();
            } else if (clz == Boolean.class && !(o instanceof Boolean)) {
                o = Boolean.valueOf(String.valueOf(o));
            } else if (clz == String.class && !(o instanceof String)) {
                o = String.valueOf(o);
            }
        }
        return (T)o;
    }

    public Options clone() {
        Options options = new Options();

        for (String key : this.keySet()) {
            Object o = this.get(key);
            options.put(key, o);
        }
        return options;
    }

    public JSONObject toJsonObject() {
        return (JSONObject) toJsonValue();
    }

    @Override
    public JSONValue toJsonValue() {
        JSONObject j = new JSONObject();

        for (String key : this.keySet()) {
            JSONValue value = null;
            Object o = this.get(key);

            if (o instanceof Double)
                value = new JSONNumber((Double) o);
            else if (o instanceof Float)
                value = new JSONNumber((Float) o);
            else if (o instanceof Integer)
                value = new JSONNumber((Integer) o);
            else if (o instanceof Long)
                value = new JSONNumber((Long) o);
            else if (o instanceof Short)
                value = new JSONNumber((Short) o);
            else if (o instanceof Byte)
                value = new JSONNumber((Byte) o);
            else if (o instanceof Boolean) {
                value = new JSONString(String.valueOf(o));
            } else if (o instanceof String) {
                value = new JSONString((String) o);
            }
            j.put(key, value);
        }
        return j;
    }

    @Override
    public String toJsonString() {
        return toJsonValue().toString();
    }
}

package com.github.abalone.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sardemff7
 */
public class Config
{
    private HashMap<String, Value> conf;
    static private Config singleton;

    private Config()
    {
        this.conf = new HashMap<String, Value>();
        this.conf.put("theme", new Theme("Theme", "glossy"));
        this.conf.put("AI", new Value<Boolean>("Human vs. AI", false));
    }

    static private HashMap<String, Value> getConf()
    {
        if ( Config.singleton == null )
            Config.singleton = new Config();
        return Config.singleton.conf;
    }

    static public Object get(String name)
    {
        if ( ( name == null ) || ( ! getConf().containsKey(name) ) )
            return null;
        return getConf().get(name).get();
    }

    static public void set(String name, Object value)
    {
        Value v = getConf().get(name);
        v.set(value);
    }

    static public Map<String, Value> getConfig()
    {
        return Collections.unmodifiableMap(getConf());
    }

    static public void addValueListener(String key, ValueListener listener)
    {
        getConf().get(key).addValueListener(listener);
    }
}

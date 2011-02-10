package com.github.abalone;

import java.util.HashMap;

/**
 *
 * @author sardemff7
 */
public class Config
{
    private HashMap<String, Object> conf;
    static private Config singleton;

    private Config()
    {
        this.conf = new HashMap<String, Object>();
        this.conf.put("theme", "classic");
    }

    static private HashMap<String, Object> getConf()
    {
        if ( Config.singleton == null )
            Config.singleton = new Config();
        return Config.singleton.conf;
    }

    static public Object get(String name)
    {
        return getConf().get(name);
    }

    static public void set(String name, Object value)
    {
        getConf().put(name, value);
    }
}

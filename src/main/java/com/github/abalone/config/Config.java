package com.github.abalone.config;

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
        this.conf.put("theme", new Theme("glossy"));
    }

    static private HashMap<String, Object> getConf()
    {
        if ( Config.singleton == null )
            Config.singleton = new Config();
        return Config.singleton.conf;
    }

    static public Object get(String name)
    {
        if ( ( name == null ) || ( ! getConf().containsKey(name) ) )
            return null;
        Object r = getConf().get(name);
        if ( r instanceof ConstraintValue )
        {
            return ((ConstraintValue)r).get();
        }
        else
            return r;
    }

    static public void set(String name, Object value)
    {
        Object r = getConf().get(name);
        if ( r.getClass() == ConstraintValue.class )
        {
            ((ConstraintValue)r).set(value);
        }
        else
            getConf().put(name, value);
    }
}

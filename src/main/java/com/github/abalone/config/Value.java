package com.github.abalone.config;

import java.util.HashSet;

/**
 *
 * @author sardemff7
 */
public class Value<T>
{
    public final String description;
    protected T value;
    HashSet<ValueListener> listeners;

    public Value(String description, T value)
    {
        this.description = description;
        this.listeners = new HashSet<ValueListener>();
        if ( value != null )
            this.set(value);
    }

    public Class getType()
    {
        return this.value.getClass();
    }

    public void set(T value)
    {
        this.value = value;
        for ( ValueListener l : this.listeners )
            l.valueUpdated(value);
    }

    final public T get()
    {
        return this.value;
    }

    void addValueListener(ValueListener listener)
    {
        this.listeners.add(listener);
    }
}

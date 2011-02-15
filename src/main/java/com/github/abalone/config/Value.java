package com.github.abalone.config;

/**
 *
 * @author sardemff7
 */
public class Value<T>
{
    public final String description;
    protected T value;

    public Value(String description, T value)
    {
        this.description = description;
        this.set(value);
    }

    public Class getType()
    {
        return this.value.getClass();
    }

    public void set(T value)
    {
        this.value = value;
    }

    public T get()
    {
        return this.value;
    }
}

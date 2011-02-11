package com.github.abalone.config;

/**
 *
 * @author sardemff7
 */
public abstract class ConstraintValue<T>
{
    abstract protected void initConstraint();
    abstract protected Boolean check(T value);

    protected T value;

    protected ConstraintValue(T value)
    {
        this.initConstraint();
        this.set(value);
    }

    public T get()
    {
        return this.value;
    }

    public void set(T value)
    {
        if ( this.check(value) )
            this.value = value;
    }
}

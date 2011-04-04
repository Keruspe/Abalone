package com.github.abalone.config;

/**
 *
 * @author sardemff7
 */
public class Range extends Value<Integer>
{
    private Integer min;
    private Integer max;

    public Range(String description, Integer value, Integer min, Integer max) {
        super(description, null);
        this.min = min;
        this.max = max;
        this.set(value);
    }

    public Integer getMin() {
        return this.min;
    }

    public Integer getMax() {
        return this.max;
    }

    @Override
    public final void set(Integer value) {
        if ( value <= this.max && value >= this.min)
            super.set(value);
    }

}

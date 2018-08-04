package com.tompy.attribute.api;

/**
 * An enumeration of all the attributes available for entities.
 */
public enum Attribute {
    TEST_NORMALA(false, false), TEST_NORMALB(false, false), TEST_STACKABLE(true, false), TEST_HAS_VALUE(false,
            true), TAKABLE(false, false), USABLE(false, false), TARGETABLE(false, false), VISITABLE(false, true), BONUS(
            false, true);

    /**
     * A stackable Attribute is one that when added increments the value by 1,
     * and reduces the value by 1 when removed.  Zero is the lowest value.
     */
    private boolean stackable;

    /**
     * Allows the attribute to contain a value which is set when added.
     * The value is an Integer.
     */
    private boolean hasValue;

    Attribute(boolean stackable, boolean hasValue) {
        this.stackable = stackable;
        this.hasValue = hasValue;
    }

    public boolean stackable() {
        return stackable;
    }

    public boolean hasValue() {
        return hasValue;
    }
}

package com.tompy.attribute.api;

/**
 * An enumeration of all the attributes available for entities.
 */
public enum Attribute {
    TEST_NORMALA(false, false, "TEST A", "APPLIES", "DOES NOT APPLY"), TEST_NORMALB(false, false, "TEST B", "APPLIES",
            "DOES NOT APPLY"), TEST_STACKABLE(true, false, "TEST STACKABLE", "APPLIES",
            "DOES NOT APPLY"), TEST_HAS_VALUE(false, true, "TEST HAS VALUE", "APPLIES", "DOES NOT APPLY"), TAKABLE(
            false, false, "Can take", "can take", "can not take"), USABLE(false, false, "Usable", "usable",
            "not usable"), TARGETABLE(false, false, "Can target", "can target", "can not target"), VISIBLE(false,
            false, "Visible", "see", "do not see"), BONUS(false, true, "Bonus", "bonus", "no bonus"), OPEN(false, false,
            "Open", "open", "closed"), LOCKED(false, false, "Locked", "locked", "unlocked");

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

    /**
     * The printable name of the attribute
     */
    private String name;

    /**
     * The printable verbiage if the attribute applies
     */
    private String doesApply;

    /**
     * The printable verbiage if the attribute does not apply
     */
    private String doesNotApply;

    Attribute(boolean stackable, boolean hasValue, String name, String doesApply, String doesNotApply) {
        this.stackable = stackable;
        this.hasValue = hasValue;
        this.name = name;
        this.doesApply = doesApply;
        this.doesNotApply = doesNotApply;
    }

    public boolean stackable() {
        return stackable;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public String getName() {
        return name;
    }

    public String getDoesApply() {
        return doesApply;
    }

    public String getDoesNotApply() {
        return doesNotApply;
    }
}

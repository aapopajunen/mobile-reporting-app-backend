package com.swp.Entity;

enum SubType {
    HAS_OPTIONS, NO_OPTIONS
}

public enum FieldType {
    TEXTFIELD_SHORT(SubType.NO_OPTIONS),
    TEXTFIELD_LONG(SubType.NO_OPTIONS),
    CALENDAR(SubType.NO_OPTIONS),
    TIME(SubType.NO_OPTIONS),
    INSTRUCTIONS(SubType.NO_OPTIONS),
    LINK(SubType.NO_OPTIONS),
    NUMBERFIELD(SubType.NO_OPTIONS),
    DROPDOWN(SubType.HAS_OPTIONS),
    NESTED_DROPDOWN(SubType.HAS_OPTIONS),
    CHECKBOX(SubType.HAS_OPTIONS),
    RADIOBUTTON(SubType.HAS_OPTIONS);

    private final SubType subType;
    private FieldType(final SubType subType) { this.subType = subType; }

    public boolean hasOptions() {
        return this.subType == SubType.HAS_OPTIONS;
    }

    public boolean hasNoOptions() {
        return this.subType == SubType.NO_OPTIONS;
    }
}




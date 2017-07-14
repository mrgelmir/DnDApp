package be.sanderdecleer.dndapp.views;

public enum ItemViewType {

    ITEM(0), INFO(1), EDIT(2);

    private final int value;

    ItemViewType(int value) {
        this.value = value;
    }

    public static ItemViewType fromInt(int i) {
        switch (i) {
            default:
            case 0:
                return ITEM;
            case 1:
                return INFO;
            case 2:
                return EDIT;
        }
    }

    public int toInt() {
        return value;
    }
}

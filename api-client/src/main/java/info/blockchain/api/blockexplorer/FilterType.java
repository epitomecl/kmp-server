package info.blockchain.api.blockexplorer;

public enum FilterType {
    All(4),
    ConfirmedOnly(5),
    RemoveUnspendable(6);

    private final Integer filterInt;

    FilterType(Integer filterInt) {
        this.filterInt = filterInt;
    }

    public int getFilterInt() {
        return filterInt;
    }
}

package pl.sdacademy.gamechanger.model;

public enum AuctionDuration {

    DAYS_3(3, "3 dni"),
    DAYS_7(7, "7 dni"),
    DAYS_10(10, "7 dni"),
    DAYS_14(14, "14 dni");

    private String description;
    private int daysCount;

    AuctionDuration(int daysCount, String description) {
        this.description = description;
        this.daysCount = daysCount;
    }

    public String getDescription() {
        return description;
    }

    public int getDaysCount() {
        return daysCount;
    }
}

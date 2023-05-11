package pl.javastart.tasks;

public enum Category {

    ALL("Wszystkie"),HOUSE("Dom"), GARDEN("Ogród"), FINANCE("Finanse"), PERSONAL("Osobiste");

    private String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

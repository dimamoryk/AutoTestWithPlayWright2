package data;

public enum PopupData {

    POPUP_DATA("/");

    private String path;

    PopupData(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

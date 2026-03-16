package data;

public enum LessonData {

    LESSON_DATA("/");

    private String path;

    LessonData(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

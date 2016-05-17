package gov.sequarius.domain;

/**
 * Created by Sequarius on 2016/4/14.
 */
public class ClassInfo {
    private String id;
    private String classId;
    private String className;
    private String teacher;
    private boolean evaluated = false;

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public ClassInfo(String className, String id) {
        this.className = className;
        this.id = id;
    }

    public ClassInfo() {
    }

    public ClassInfo(String id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "id='" + id + '\'' +
                ", classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", teacher='" + teacher + '\'' +
                ", evaluated=" + evaluated +
                '}';
    }
}

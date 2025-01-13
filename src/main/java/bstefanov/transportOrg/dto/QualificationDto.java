package bstefanov.transportOrg.dto;

public class QualificationDto {
    private long id;
    private String name;

    public QualificationDto() {
    }

    public QualificationDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "QualificationDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

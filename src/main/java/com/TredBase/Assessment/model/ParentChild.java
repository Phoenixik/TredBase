package com.TredBase.Assessment.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@Getter
//@Setter
@Entity
//@NoArgsConstructor
public class ParentChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;
    private Long studentId;

    public ParentChild() {
    }

    public ParentChild(Long parentId, Long studentId) {
        this.parentId = parentId;
        this.studentId = studentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "ParentChild{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", studentId=" + studentId +
                '}';
    }
}

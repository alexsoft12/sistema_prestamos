package pe.a3ya.msauth.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
public class Auditory {
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "deleted_by")
    private Long deletedBy;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    public void onCreated() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.createdBy = 1L; // TODO: get user from security context
    }

    @PreUpdate
    public void onUpdated() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        this.updatedBy = 1L; // TODO: get user from security context
    }

    @PreRemove
    public void onDeleted() {
        this.deletedAt = new Timestamp(System.currentTimeMillis());
        this.deletedBy = 1L; // TODO: get user from security context
    }
}

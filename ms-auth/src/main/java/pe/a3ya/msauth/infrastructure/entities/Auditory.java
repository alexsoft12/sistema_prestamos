package pe.a3ya.msauth.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
        this.createdBy = getUserId();
    }

    @PreUpdate
    public void onUpdated() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        this.updatedBy = getUserId();
    }

    @PreRemove
    public void onDeleted() {
        this.deletedAt = new Timestamp(System.currentTimeMillis());
        this.deletedBy = getUserId();
    }


    private static Long getUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserEntity) userDetails).getId();
    }

}

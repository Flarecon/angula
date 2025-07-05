package com.example.angula.auditor;

import com.example.angula.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class VelocityAuditor implements Serializable {

    @CreatedBy
    @Column(name = "created_by")
    public String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    public String updatedBy;

    @CreatedDate
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    public LocalDateTime updatedAt;
}

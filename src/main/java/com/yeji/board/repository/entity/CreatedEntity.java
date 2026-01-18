package com.yeji.board.repository.entity;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Getter
@MappedSuperclass
public class CreatedEntity {

    @CreatedBy
    private Long createdAt;

    @CreatedDate
    private LocalDateTime createdTime;

}

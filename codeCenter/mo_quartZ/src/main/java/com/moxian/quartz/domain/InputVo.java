package com.moxian.quartz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import com.moxian.quartz.validation.SensitiveWords;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InputVo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @SensitiveWords
    private String name;

    @Column(nullable = false)
    private String password;

}

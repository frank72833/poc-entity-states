package com.fns.pocentitystates.porting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class MobilePorting {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ORIGINAL_MOBILE")
    private String originalMobile;
    @Column(name = "ORIGINAL_PROVIDER")
    private String originalProvider;
    @Column(name = "NEW_PROVIDER")
    private String newProvider;

    @Column(name = "STATUS")
    private MobilePortingStatus status;
}

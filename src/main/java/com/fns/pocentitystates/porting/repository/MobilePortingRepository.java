package com.fns.pocentitystates.porting.repository;

import com.fns.pocentitystates.porting.MobilePorting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface MobilePortingRepository extends JpaRepository<MobilePorting, String> {}

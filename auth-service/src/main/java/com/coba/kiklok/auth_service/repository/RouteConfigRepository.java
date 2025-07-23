package com.coba.kiklok.auth_service.repository;

import com.coba.kiklok.auth_service.entity.RouteConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteConfigRepository extends JpaRepository<RouteConfig, Long> {
}

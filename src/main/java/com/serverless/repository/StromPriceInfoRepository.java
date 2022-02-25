package com.serverless.repository;

import com.serverless.model.StromPriceInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StromPriceInfoRepository  extends CrudRepository<StromPriceInfo, Integer> {
    Optional<StromPriceInfo> findById(Integer id);
}

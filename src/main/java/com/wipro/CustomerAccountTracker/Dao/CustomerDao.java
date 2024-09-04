package com.wipro.CustomerAccountTracker.Dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.wipro.CustomerAccountTracker.Bean.CustomerBean;

@Repository
public interface CustomerDao extends CrudRepository<CustomerBean, Integer> {
    Optional<CustomerBean> findByAccountNumber(long accountNumber);

    void deleteByAccountNumber(Long accountNumber);
}

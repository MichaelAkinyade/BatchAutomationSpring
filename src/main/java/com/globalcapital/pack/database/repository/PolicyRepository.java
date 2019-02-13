/*
 * package com.globalcapital.pack.database.repository;
 * 
 * 
 * import java.util.List; import java.util.stream.Stream;
 * 
 * import org.springframework.boot.autoconfigure.SpringBootApplication; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.jpa.repository.config.EnableJpaRepositories; import
 * org.springframework.data.repository.CrudRepository; import
 * org.springframework.data.repository.query.Param; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.globalcapital.pack.database.entity.PolicyType;
 * 
 * @Repository public interface PolicyRepository extends
 * CrudRepository<PolicyType, Long> {
 * 
 * List<PolicyType> findPolicyType(Long codeid);
 * 
 * 
 * // custom query example and return a stream
 * //@Query("select c from POLICY_TYPE c where c.CODEID = :codeid") //
 * Stream<PolicyType> findByEmailReturnStream(@Param("codeid") int codeid);
 * 
 * }
 */
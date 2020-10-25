package apap.tugas.sipes.repository;

import apap.tugas.sipes.model.PenerbanganModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface PenerbanganDb extends JpaRepository<PenerbanganModel,BigInteger>{
    Optional<PenerbanganModel> findById(Long id);
    
}

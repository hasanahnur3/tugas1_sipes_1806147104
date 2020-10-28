package apap.tugas.sipes.repository;

import apap.tugas.sipes.model.TipeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface TipeDb extends JpaRepository<TipeModel,Long>{
    Optional<TipeModel> findById(Long id);

}

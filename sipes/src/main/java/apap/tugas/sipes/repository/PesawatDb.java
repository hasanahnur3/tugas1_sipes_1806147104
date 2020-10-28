package apap.tugas.sipes.repository;

import apap.tugas.sipes.model.PesawatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PesawatDb extends JpaRepository<PesawatModel,Long>{
    Optional<PesawatModel> findById(Long id);
    // List<PesawatModel> 	findByTanggal_dibuatBefore(Date date);
}

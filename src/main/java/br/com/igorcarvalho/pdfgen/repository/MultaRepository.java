package br.com.igorcarvalho.pdfgen.repository;

import br.com.igorcarvalho.pdfgen.model.entity.MultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultaRepository extends JpaRepository<MultaEntity, Long> {
}

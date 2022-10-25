package ch.ninja.tahitinumerique.dao;

import ch.ninja.tahitinumerique.entities.SolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, Integer > {

}

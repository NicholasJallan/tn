package ch.ninja.tahitinumerique.service;


import ch.ninja.tahitinumerique.dao.SolutionRepository;
import ch.ninja.tahitinumerique.entities.SolutionEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MathService {

    @Autowired
    SolutionRepository repository;

    //avoid to recompute and also hack for breaking recursion search
    private boolean solutionFound = false;

    // last solution found
    private int[] solution;

    Logger logger = LoggerFactory.getLogger(MathService.class);


    private void computePermutation(int[] listToShuffle, int[] listBuilt, boolean computeAllSolutions){
        if (listToShuffle.length == 0 && testPermutation(listBuilt)) {
            if (!computeAllSolutions) {
                this.solutionFound = true;
            }
            this.solution = listBuilt;
            logger.info("Saving to H2 solution " + Arrays.toString(solution));
            SolutionEntity solutionEntity = new SolutionEntity();
            solutionEntity.setSolutionValues(solution);
            repository.save(solutionEntity);
        }

        for (int i = 0; i < listToShuffle.length && !solutionFound; i++) {
            int[] tmp = ArrayUtils.add(listBuilt, listToShuffle[i]);
            int[] sub = Arrays.copyOfRange(listToShuffle, 0, i);
            if (i+1 < listToShuffle.length) {
                sub = ArrayUtils.addAll(sub, Arrays.copyOfRange(listToShuffle, i+1, listToShuffle.length));
            }
            computePermutation(sub, tmp, computeAllSolutions);
        }
    }

    private boolean testPermutation(int[] l){
        try {
            return (l[0] + ((13f * l[1]) / l[2]) + l[3] + (12f * l[4]) - l[5] - 11f + (((float)l[6] * l[7]) / l[8]) - 10) == 60f;
        } catch (java.lang.ArithmeticException e){
            return false;
        }
    }

    public int[] getProblemResult(boolean computeAllSolutions) {
        int[] list = {1,2,3,4,5,6,7,8,9};

        if (computeAllSolutions){
            solutionFound = false;
            repository.deleteAll();
        }

        if (!solutionFound) {
            long start = System.currentTimeMillis();
            logger.info("Computing");
            computePermutation(list, new int[0], computeAllSolutions);
            logger.info("Computed in " + (System.currentTimeMillis() - start) + "ms");
            logger.info("Solution found : " + Arrays.toString(solution));
        }
        else {
            logger.info("Returning (last) precomputed solution " + Arrays.toString(solution));
        }

        return solution;
    }

    public List<SolutionEntity> getAll() {
        logger.info("Get all from H2");
        return repository.findAll();
    }

    public void deleteAll() {
        logger.info("Deleting all from H2");
        repository.deleteAll();
    }
}

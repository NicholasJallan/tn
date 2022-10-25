package ch.ninja.tahitinumerique.controller;

import ch.ninja.tahitinumerique.entities.SolutionEntity;
import ch.ninja.tahitinumerique.service.MathService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/math")
public class MathController {

	@Autowired
	private MathService service;

	@ApiOperation(value = "Compute one solution.", notes = "Will feed H2 database (console enabled) with the solution found")
	@GetMapping("/computeOne")
	public int[] compute() {
		return service.getProblemResult(false);
	}

	@ApiOperation(value = "Compute all solutions.", notes = "Will feed H2 database (console enabled) with all solutions. Last solution found is returned.")
	@GetMapping("/computeAll")
	public int[] computeAll() {
		return service.getProblemResult(true);
	}

	@ApiOperation(value = "Get all solutions.", notes = "Return all solutions stored in H2")
	@GetMapping("/getAll")
	public List<SolutionEntity> getAll() {
		return service.getAll();
	}

	@ApiOperation(value = "Delete all solutions.", notes = "Delete all solutions stored in H2")
	@DeleteMapping("/deleteAll")
	public ResponseEntity<SolutionEntity> deleteAll() {
		service.deleteAll();
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Count all solutions stored.", notes = "Count all solutions stored in H2")
	@GetMapping("/count")
	public int count() {
		return service.getAll().size();
	}
}
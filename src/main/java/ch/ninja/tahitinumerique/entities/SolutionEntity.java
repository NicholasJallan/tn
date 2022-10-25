package ch.ninja.tahitinumerique.entities;

import com.google.gson.Gson;

import javax.persistence.*;

@Entity
@Table(name = "SOLUTION")
public class SolutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String solutionValues;

    public Long getId() {
        return id;
    }

    public int[] getSolutionValues() {
        Gson gson = new Gson();
        return gson.fromJson(solutionValues, int[].class);
    }

    public void setSolutionValues(int[] solutionValues) {
        Gson gson = new Gson();
        this.solutionValues = gson.toJson(solutionValues);
    }

    public void setValues(String values) {
        this.solutionValues = values;
    }
}

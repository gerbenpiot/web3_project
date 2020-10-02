package domain.db;

import domain.model.DomainException;
import domain.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDbInMemory {
    private Map<String,Person>  persons;

    public PersonDbInMemory() {
        this.persons =  new HashMap<>();
    }

    public Person get(String personId) {
        return persons.get(personId);
    }

    public List<Person> getAll() {
        return new ArrayList<>(persons.values());
    }

    public void add(Person person) {
        if(persons.get(person.getUserid()) != null) throw new IllegalArgumentException("User already exists");
        persons.put(person.getUserid(),person);
    }

    public void update(Person person) {
    }

    public void delete(String id) {
    }
}

package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    /**public List<Person> findAll() {
     return this.persons.findAll();
     }
     у меня CrudRepository возмоащал Iterable, по этой причине CrudRepository
     поменял на JPARepository. Я прочитал, что JPA это более
     развитая технология чем CRUD
     * */
}

package example.restcontroller_sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.restcontroller_sample.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{

}

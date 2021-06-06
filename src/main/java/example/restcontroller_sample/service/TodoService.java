package example.restcontroller_sample.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import example.restcontroller_sample.entity.Todo;
import example.restcontroller_sample.enumtype.PriorityType;
import example.restcontroller_sample.enumtype.StatusType;
import example.restcontroller_sample.repository.TodoRepository;
import example.restcontroller_sample.vo.TodoParam;

@Service
@Transactional
public class TodoService {

	@Autowired
	private TodoRepository repo;
	
	public List<Todo> getAll() {
		return repo.findAll();
	}
	
	public Todo getTodoById(String taskId) {
		var optTodo = repo.findById(Long.valueOf(taskId));
		if (optTodo.isEmpty()) {
			throw new EntityNotFoundException("Todoが見つかりません。 TaskId = " + taskId);
		}
		return optTodo.get();
	}
	
	public Todo postTodo(TodoParam param) {
		Todo todo = new Todo();
		todo.setTaskName(param.getTaskName());
		todo.setStatus(StatusType.getByCode(param.getStatus()));
		todo.setPriority(PriorityType.getByCode(param.getPriority()));
		todo.setTimelimit(param.getTimelimit());
		
		return repo.save(todo);
	}
	
	public Todo putTodo(TodoParam param) {
		var optTodo = repo.findById(Long.valueOf(param.getTaskId()));
		if (optTodo.isEmpty()) {
			throw new EntityNotFoundException("更新対象が見つかりません。 TaskId = " + param.getTaskId());
		}
		var todo = optTodo.get();

		todo.setTaskName(param.getTaskName());
		todo.setStatus(StatusType.getByCode(param.getStatus()));
		todo.setPriority(PriorityType.getByCode(param.getPriority()));
		todo.setTimelimit(param.getTimelimit());
		
		return repo.save(todo);
	}
	
	public Todo deleteTodo(TodoParam param) {
		var optTodo = repo.findById(Long.valueOf(param.getTaskId()));
		if (optTodo.isEmpty()) {
			throw new EntityNotFoundException("削除対象が見つかりません。 TaskId = " + param.getTaskId());
		}
		var todo = optTodo.get();
		repo.delete(todo);
		return todo;
	}
}

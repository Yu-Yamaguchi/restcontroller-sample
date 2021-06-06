package example.restcontroller_sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import example.restcontroller_sample.entity.Todo;
import example.restcontroller_sample.service.TodoService;
import example.restcontroller_sample.vo.TodoParam;

@RestController
public class TodoController {

	@Autowired
	private TodoService service;
	
	@RequestMapping(path = "/todo", method = RequestMethod.GET)
	public List<Todo> getAll() {
		return service.getAll();
	}
	
	@RequestMapping(path = "/todo/{taskId}", method = RequestMethod.GET)
	public Todo getTodoById(@PathVariable("taskId") String taskId) {
		return service.getTodoById(taskId);
	}
	
	@RequestMapping(path = "/todo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Todo postTodo(@RequestBody @Validated TodoParam param) {
		return service.postTodo(param);
	}
	
	@RequestMapping(path = "/todo", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Todo putTodo(@RequestBody @Validated TodoParam param) {
		return service.putTodo(param);
	}
	
	@RequestMapping(path = "/todo", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Todo deleteTodo(@RequestBody @Validated TodoParam param) {
		return service.deleteTodo(param);
	}
}

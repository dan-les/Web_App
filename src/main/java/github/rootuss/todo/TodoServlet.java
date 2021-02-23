package github.rootuss.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Todo", urlPatterns = {"/api/todos/*"})
public class TodoServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(TodoServlet.class);

    private TodoRepository todoRepository;
    private ObjectMapper objectMapper;

    public TodoServlet() {
        this(new TodoRepository(), new ObjectMapper());
    }

    TodoServlet(TodoRepository todoRepository, ObjectMapper objectMapper) {
        this.todoRepository = todoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(" ---- Obsłużono REQUEST z parametrem: " + req.getParameterMap());

        resp.setContentType("application/json;charset=UTF-8");
        //mapowanie na JSON'a
        objectMapper.writeValue(resp.getOutputStream(), todoRepository.findAll());

    }

    // update data
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var pathinfo = req.getPathInfo();

        try {
            //substring, bo bez slash'a :
            var todoId = Integer.valueOf(pathinfo.substring(1));
            var todo = todoRepository.toogleTodo(todoId);

            resp.setContentType("application/json;charset=UTF-8");
            objectMapper.writeValue(resp.getOutputStream(), todo);

        } catch (NumberFormatException exception) {
            logger.warn("Wrong path used: " + pathinfo);
        }
    }

    //add new data
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var newTodo = objectMapper.readValue(req.getInputStream(), Todo.class);

        resp.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(resp.getOutputStream(), todoRepository.addTodo(newTodo));
    }
}
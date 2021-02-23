package github.rootuss.lang;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//   /langs - frontend pobiera
@WebServlet(name = "Lang", urlPatterns = {"/api/langs"})
public class LangServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LangServlet.class);

    private LangService langService;
    private ObjectMapper objectMapper;

    public LangServlet() {
        this(new LangService(), new ObjectMapper());
    }

    LangServlet(LangService langService, ObjectMapper objectMapper) {
        this.langService = langService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(" ---- Obsłużono REQUEST z parametrem: " + req.getParameterMap());

        resp.setContentType("application/json;charset=UTF-8");
        //mapowanie na JSON'a
        objectMapper.writeValue(resp.getOutputStream(), langService.findAll());

    }


}
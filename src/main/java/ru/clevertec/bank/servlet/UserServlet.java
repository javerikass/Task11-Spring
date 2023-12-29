package ru.clevertec.bank.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.bank.dao.impl.UserDaoImpl;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.mapper.UserMapperImpl;
import ru.clevertec.bank.service.UserService;
import ru.clevertec.bank.service.impl.UserServiceImpl;
import ru.clevertec.bank.validator.impl.UserDtoValidatorImpl;

@Slf4j
@WebServlet(urlPatterns = {"/users/save", "/users/delete", "/users/update", "/users/get",
    "/users/getAll"})
public class UserServlet extends HttpServlet {

    private final Gson mapper = new Gson();
    private UserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl(new UserMapperImpl(), new UserDaoImpl(),
            new UserDtoValidatorImpl());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/users/save" -> saveUser(request, response);
                case "/users/delete" -> deleteUser(request, response);
                case "/users/update" -> updateUser(request, response);
                case "/users/get" -> getUser(request, response);
                case "/users/getAll" -> getAllUser(request, response);
                default -> UtilServlet.defaultMethod(response);
            }
        } catch (IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }

    private void saveUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String age = request.getParameter("age");
        String email = request.getParameter("email");

        try {
            UserDto user = UserDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(Integer.parseInt(age))
                .mail(email)
                .build();

            userService.createUser(user);
            log.info("User saved");
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NumberFormatException e) {
            log.info("Saving user was failed");
            UtilServlet.defaultMethod(response);
        }
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        String id = request.getParameter("id");
        UserDto userById = userService.getUserById(UUID.fromString(id));

        try (PrintWriter out = response.getWriter()) {
            out.print(mapper.toJson(userById));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            UtilServlet.defaultMethod(response);
        }
    }

    private void getAllUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        String pageSize =
            request.getParameter("pageSize") == null ? "0" : request.getParameter("pageSize");
        String pageNumber = request.getParameter("pageNumber");

        List<UserDto> listUser = userService.findAll(
            Integer.parseInt(pageSize),
            Integer.parseInt(pageNumber));

        String userListJson = mapper.toJson(listUser);

        try (PrintWriter out = response.getWriter()) {
            out.print(userListJson);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            UtilServlet.defaultMethod(response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String age = request.getParameter("age");
        String email = request.getParameter("email");

        UserDto user = UserDto.builder().
            id(UUID.fromString(id))
            .firstName(firstName)
            .lastName(lastName)
            .age(Integer.parseInt(age))
            .mail(email)
            .build();

        userService.updateUser(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        String id = request.getParameter("id");
        userService.deleteUser(UUID.fromString(id));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

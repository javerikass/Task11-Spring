package ru.clevertec.bank.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class UtilServlet {

    public static void defaultMethod(HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.print("ERROR");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}

package ui.controller;

import domain.db.PersonDbInMemory;
import domain.model.DomainException;
import domain.model.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    PersonDbInMemory db = new PersonDbInMemory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processrequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processrequest(request,response);
    }

    private void processrequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination="";
        String command="";
        if (request.getParameter("command") != null){
            command=request.getParameter("command");
        }
        if (request.getCookies()!=null){
            Cookie c = getCookiewitkkey(request,"loggedin");
            if(c!=null){
                request.setAttribute("requestcookie",c.getValue());
            }
        }


        switch (command){
            case "overview" : destination=Overview(request,response);
                break;
            case "Register" : destination=Register(request,response);
                break;
            case "signUp" :destination=voegtoe(request,response);
                break;
            case "login": destination=login(request,response);
                break;
            case "logout": destination=logout(request,response);
                break;


            default: destination= home(request,response);
        }



        request.getRequestDispatcher(destination).forward(request,response);
    }

    private String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie c= getCookiewitkkey(request,"loggedin");
        if (c != null) {
            c.setMaxAge(0);
            response.addCookie(c);

        }
        request.setAttribute("requestcookie","");
        return home(request,response);
    }

    private Cookie getCookiewitkkey(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie c: cookies){
            if(c.getName().equals(key)){
                return c;
            }

        }return null;
    }

    private String login(HttpServletRequest request, HttpServletResponse response) {
        String userid= request.getParameter("userid");
        String pass= request.getParameter("password");
        for (Person p: db.getAll()) {
            if(p.getUserid().equals(userid) || p.getEmail().equals(userid)){
                if (p.isCorrectPassword(pass)){
                    Cookie c= new Cookie("loggedin",p.getFirstName());
                    response.addCookie(c);
                    request.setAttribute("requestcookie",p.getFirstName());
                    return home(request,response);
                }
            }
        }

        String error ="No matching Name/Password";
        request.setAttribute("error",error);
        return home(request,response);
    }

    private String voegtoe(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errors=new ArrayList<>();
        Person person = new Person();
        setUserid(request,person,errors);
        setEmail(request,person,errors);
        setPassword(request,person,errors);
        setFirstName(request,person,errors);
        setLastName(request,person,errors);

        if (errors.size() ==0){
            try {
                db.add(person);
                return Overview(request,response);
            } catch (IllegalArgumentException e){
                errors.add(e.getMessage());
            }

        }
        request.setAttribute("errors",errors);

        return "register.jsp";
    }

    private void setUserid(HttpServletRequest request, Person person, ArrayList<String> errors) {
        String userid= request.getParameter("userid");
        try{
            person.setUserid(userid);
            request.setAttribute("voriguserid",userid);
        } catch (DomainException e){
            errors.add(e.getMessage());
        }
    }
    private void setEmail(HttpServletRequest request, Person person, ArrayList<String> errors) {
        String email= request.getParameter("email");
        try{
            person.setEmail(email);
            request.setAttribute("vorigemail",email);
        } catch (DomainException e){
            errors.add(e.getMessage());
        }
    }
    private void setPassword(HttpServletRequest request, Person person, ArrayList<String> errors) {
        String password= request.getParameter("password");
        try{
            person.setPassword(password);
            request.setAttribute("vorigpassword",password);
        } catch (DomainException e){
            errors.add(e.getMessage());
        }
    }
    private void setFirstName(HttpServletRequest request, Person person, ArrayList<String> errors) {
        String first= request.getParameter("firstName");
        try{
            person.setFirstName(first);
            request.setAttribute("vorigfirstname",first);
        } catch (DomainException e){
            errors.add(e.getMessage());
        }
    }
    private void setLastName(HttpServletRequest request, Person person, ArrayList<String> errors) {
        String last= request.getParameter("lastName");
        try{
            person.setLastName(last);
            request.setAttribute("voriglastname",last);
        } catch (DomainException e){
            errors.add(e.getMessage());
        }
    }

    private String home(HttpServletRequest request, HttpServletResponse response) {
        return "index.jsp";
    }

    private String Register(HttpServletRequest request, HttpServletResponse response) {
        return "register.jsp";
    }

    private String Overview(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<Person> p = new ArrayList<>(db.getAll());
        request.setAttribute("persons",p);
        return "personoverview.jsp";
    }
}

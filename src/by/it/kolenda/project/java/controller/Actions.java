package by.it.kolenda.project.java.controller;


import javax.servlet.http.HttpServletRequest;

enum Actions {
    SIGNUP {{
        this.command = new CommandSignup();
    }},
    LOGIN {{
        this.command = new CommandLogin();
    }},
    LOGOUT {{
        this.command = new CommandLogout();
    }},
    ERROR {{
        this.command = new CommandError();
    }};

    public String jsp = "/error.jsp";
    public ActionComand command;

    static Action defineFrom(HttpServletRequest request);

    String command = request.getParameter("command");
    Action res;
    try

            catch

            return res;



//    public ActionComand getCurrentCommand()
//
//    {
//        return command;
//    }

    ;


}

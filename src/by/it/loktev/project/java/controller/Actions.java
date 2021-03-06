package by.it.loktev.project.java.controller;

import javax.servlet.http.HttpServletRequest;

enum Actions {

    SIGNUP {
        {
            this.command=new CmdSignup();
        }
    },
    LOGIN {
        {
            this.command=new CmdLogin();
        }
    },
    PROFILE {
        {
            this.command=new CmdProfile();
        }
    },
    ERROR {
        {
            this.command=new CmdError();
        }
    },
    USERLIST {
        {
            this.command=new CmdUserList();
        }
    },
    USEREDIT {
        {
            this.command=new CmdUserEdit();
        }
    },
    USERSETPASS {
        {
            this.command=new CmdUserSetPass();
        }
    },
    USERDELETE {
        {
            this.command=new CmdUserDelete();
        }
    },
    TASKLIST {
        {
            this.command=new CmdTaskList();
        }
    },
    TASKCREATE {
        {
            this.command=new CmdTaskCreate();
        }
    },
    TASKDELETE {
        {
            this.command=new CmdTaskDelete();
        }
    },
    TASKGET {
        {
            this.command=new CmdTaskGet();
        }
    },
    TASKREADY {
        {
            this.command=new CmdTaskReady();
        }
    },
    TASKEDIT {
        {
            this.command=new CmdTaskEdit();
        }
    };


    public Action command;

    static Action defineFrom(HttpServletRequest request){
        String command=request.getParameter("command");
        if ( command==null ){
            command="index";
        }
        Action res;
        try {
            res = Actions.valueOf(command.toUpperCase()).command;
        } catch ( IllegalArgumentException e ){
            res=Actions.ERROR.command;
        }
        return res;
    }
}
